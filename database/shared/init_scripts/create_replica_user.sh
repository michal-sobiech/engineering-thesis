#!/bin/bash

check_if_env_var_not_empty() {
    if [ -z "${!1}" ]; then
        echo "ERROR: Environmental variable $1 is not set."
        exit 1
    fi
}

check_if_env_var_not_empty "DB_REPLICA_USER"
check_if_env_var_not_empty "DB_REPLICA_PASSWORD"
check_if_env_var_not_empty "POSTGRES_PASSWORD"
check_if_env_var_not_empty "POSTGRES_DB"

user_creation_query=$(cat << EOF
CREATE ROLE $DB_REPLICA_USER REPLICATION LOGIN PASSWORD '$DB_REPLICA_PASSWORD';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO $DB_REPLICA_USER;
CREATE PUBLICATION all_tables_pub FOR TABLE
    enterprise,
    "user",
    enterprise_service,
    enterprise_service_slot,
    review,
    appointment,
    "report";
EOF
)

PGPASSWORD="$POSTGRES_PASSWORD" psql -U postgres -d "$POSTGRES_DB" -c "$user_creation_query"