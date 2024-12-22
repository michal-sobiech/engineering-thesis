#!/bin/bash

check_if_env_var_not_empty() {
    if [ -z "${!1}" ]; then
        echo "ERROR: Environmental variable $1 is not set."
        exit 1
    fi
}

check_if_env_var_not_empty "POSTGRES_PASSWORD"
check_if_env_var_not_empty "POSTGRES_DB"
check_if_env_var_not_empty "DB_REPLICA_USER"
check_if_env_var_not_empty "DB_REPLICA_PASSWORD"

user_creation_query=$(cat << EOF
CREATE SUBSCRIPTION all_tables_sub
CONNECTION 'host=database_primary dbname=$POSTGRES_DB user=$DB_REPLICA_USER password=$DB_REPLICA_PASSWORD'
PUBLICATION all_tables_pub;
EOF
)

PGPASSWORD="$POSTGRES_PASSWORD" psql -U postgres -d "$POSTGRES_DB" -c "$user_creation_query"