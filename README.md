# Written part of the engineering thesis
Available in the `docs/` directory: [docs/Michał_Sobiech_Engineering_Thesis.pdf](docs/Michał_Sobiech_Engineering_Thesis.pdf)

# Screenshots
Screenshots are also available in the written part of the thesis.

![Home page](docs/screenshots/home.png)
Home page

![Public on-site service page, part 1](docs/screenshots/non_custom_service_public_1.png)
Public on-site service page, part 1

![Public on-site service page, part 2](docs/screenshots/non_custom_service_public_2.png)
Public on-site service page, part 2

![Public mobile service page, part 1](docs/screenshots/custom_service_public.png)
Public mobile service page, part 1

![Public mobile service page, part 2](docs/screenshots/custom_service_public_2.png)
Public mobile service page, part 2

![Reporting a service](docs/screenshots/report_service.png)
Reporting a service

![Public enterprise page](docs/screenshots/enteprise_public_page.png)
Public enterprise page

![Customer appointments page](docs/screenshots/customer_appointments.png)
Customer appointments page

![Payment service page](docs/screenshots/payment.png)
Payment service page

![Review writing page](docs/screenshots/review.png)
Review writing page

![Entrepreneur landing page](docs/screenshots/entrepreneur_landing_page.png)
Entrepreneur landing page

![Enterprise creation page](docs/screenshots/create-enterprise.png)
Enterprise creation page

![Editing enterprise, part 1](docs/screenshots/edit_enterprise_1.png)
Editing enterprise, part 1

![Editing enterprise, part 2](docs/screenshots/edit_enterprise_2.png)
Editing enterprise, part 2

![Service creation, part 1](docs/screenshots/create_service_1.png)
Service creation, part 1

![Service creation, part 2](docs/screenshots/create_service_2.png)
Service creation, part 2

![Service creation, part 3](docs/screenshots/create_service_3.png)
Service creation, part 3

![Employee landing page](docs/screenshots/employee_landing-page.png)
Employee landing page

![Admin creation page](docs/screenshots/create_admin.png)
Admin creation page

![Admin report list](docs/screenshots/report_list.png)
Admin report list

# How to run
1. In main directory: create a copy of the `.env.template` file and name it `.env`
2. Some fields will be missing, e.g. the adyen merchant account. You will need to provide those on your own or ask me (author, Michał Sobiech) for them.
2. Change direcotry to "docker" folder
3. Run `docker compose --env-file=../.env up`
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
6. There is also an enterprise (`http://localhost:3000/enterprises/1/public`) with 2 services (`http://localhost:3000/services/1`, `http://localhost:3000/services/2`)

# Test VISA card
Number: 4871 0499 9999 9910

Expiry: 03/2030

CVC: 737
