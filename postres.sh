docker run --name chat-postgres -e POSTGRES_PASSWORD=password -e POSTGRES_INITDB_ARGS="--auth-host=md5 --auth-local=md5" -p 5432:5432 -d postgres
