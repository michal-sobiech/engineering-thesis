# How to run
1. In main directory: create a copy of the `.env.template` file and name it `.env`
2. Some fields will be missing, e.g. the adyen merchant account. You will need to provide those on your own or ask me (author, Michał Sobiech) for them.
2. Change direcotry to "docker" folder
3. Run `sudo docker compose --env-file=../.env up --no-deps database_primary database_replica database_cache backend_main_service_1 backend_main_service_2 nginx backend_payout_worker frontend`
4. App will be available on `http://localhost:3000`. To log in as admin use url `http://localhost:3000/admins/log-in`
5. There are some default user accounts available. Every one has the same password: `aaaaaaaaa` (8 times letter a)
   - Head admin:
     - Username: `michal.sobiech`
   - Regular admin
     - Username: `andrzej.kowal`
   - Entrepreneur
     - Email: `adam.adamczyk@example.com`
   - Customer
     - email: `jan.kowalski@example.com`
   - Employee
     - Enterprise ID: `1`
     - Username: `anna.nowak.1`
6. There is also an enterprise (`http://localhost:3000/enterprises/1`) with 2 services (`http://localhost:3000/services/1`, `http://localhost:3000/services/2`)

# Test VISA card
Number: 4871 0499 9999 9910
Expiry: 03/2030
CVC: 737