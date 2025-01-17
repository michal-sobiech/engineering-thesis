# Komendy do testowania

curl http://localhost:8000

curl -X POST http://localhost:8000/create_customer -H "Content-Type: application/json" -d '{"firstName":"Michał", "lastName": "Sobiech", "email": "michal.sobiech@test.com", "password": "abcd1234"}'

curl 'http://localhost:8000/get_customer?id=1'