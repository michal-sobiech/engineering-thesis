#!/bin/bash

check_if_env_var_not_empty() {
    if [ -z "${!1}" ]; then
        echo "ERROR: Environmental variable $1 is not set."
        exit 1
    fi
}

check_if_env_var_not_empty "POSTGRES_PASSWORD"
check_if_env_var_not_empty "POSTGRES_DB"
check_if_env_var_not_empty "DB_BE_USER"
check_if_env_var_not_empty "DB_BE_PASSWORD"

user_creation_query=$(cat << EOF
CREATE USER $DB_BE_USER WITH PASSWORD '$DB_BE_PASSWORD';
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO $DB_BE_USER;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO $DB_BE_USER;
EOF
)

PGPASSWORD="$POSTGRES_PASSWORD" psql -U postgres -d "$POSTGRES_DB" -c "$user_creation_query"