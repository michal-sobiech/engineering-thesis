# Test commands

## Simple endpoint
`curl http://localhost:8000`

## User creation
`curl -X POST http://localhost:8000/create_customer -H "Content-Type: application/json" -d '{"firstName":"Michał", "lastName": "Sobiech", "email": "michal.sobiech@test.com", "password": "abcd1234"}'`

## User data retrieval
`curl 'http://localhost:8000/get_customer?id=1'`

## Cache
- Terminal 1: `sudo docker exec -it engineering-thesis-database_cache-1 bash`
- Terminal 2: `curl 'http://localhost:8000/get_customer?id=1'`
- Terminal 1: `redis-cli KEYS *`
