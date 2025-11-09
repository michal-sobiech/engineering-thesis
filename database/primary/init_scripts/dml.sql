INSERT INTO public.app_user (user_id, user_group, username, first_name, last_name, password_hash)
VALUES (
    1,
    'ENTREPRENEUR',
    'adam.adamczyk@example.com',
    'Adam',
    'Adamczyk',
    '$2a$14$H3Xb1uyNVBoVq7aXZI.mQO/L.iavvGIGjqsrOhm/5HRT9D0A0xkOO'
);

INSERT INTO public.app_user (user_id, user_group, username, first_name, last_name, password_hash)
VALUES (
    2,
    'CUSTOMER',
    'jan.kowalski@example.com',
    'Jan',
    'Kowalski',
    '$2a$14$H3Xb1uyNVBoVq7aXZI.mQO/L.iavvGIGjqsrOhm/5HRT9D0A0xkOO'
);

INSERT INTO public.enterprise (enterprise_id, owner_user_id, name, description, address, latitude, longitude, suspended_by_admin)
VALUES (
    1,
    1,
    'The Good Hairdresser',
    'Description here',
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346,
    FALSE
);

INSERT INTO public.enterprise_service
VALUES (
    1,
    1,
    'Female haircut',
    'A basic female haircut',
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346,
    'Europe/Warsaw',
    FALSE,
    NULL,
    'HAIRDRESSER',
    80,
    'PLN',
    FALSE
);

INSERT INTO public.enterprise_service
VALUES (
    2,
    1,
    'Haircut at home',
    'Special offer: haircut at home',
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346,
    'Europe/Warsaw',
    TRUE,
    10,
    'HAIRDRESSER',
    200,
    'PLN',
    FALSE
);

INSERT INTO public.enterprise_service_slot_template
VALUES(1, 1, 1, '12:00:00', '12:30:00', 1);

INSERT INTO public.enterprise_service_slot_template
VALUES(2, 1, 1, '12:30:00', '13:00:00', 1);

INSERT INTO public.enterprise_service_slot_template
VALUES(3, 1, 3, '13:00:00', '13:30:00', 1);

INSERT INTO public.enterprise_service_slot_template
VALUES(4, 1, 3, '13:30:00', '14:00:00', 1);

INSERT INTO public.enterprise_service_slot_template
VALUES(5, 2, 3, '8:00:00', '18:00:00', NULL);

INSERT INTO public.appointment (
    appointment_id,
    enterprise_service_id,
    customer_user_id,
    price,
    start_time,
    end_time,
    is_custom
) VALUES (
    1,
    1,
    2,
    100,
    '2025-12-01 12:00:00+00',
    '2025-12-01 12:30:00+00',
    FALSE
);

INSERT INTO public.appointment (
    appointment_id,
    enterprise_service_id,
    customer_user_id,
    price,
    start_time,
    end_time,
    is_custom,
    is_accepted,
    address,
    latitude,
    longitude
) VALUES (
    2,
    2,
    2,
    300,
    '2025-12-03 13:00:00+00',
    '2025-12-03 14:00:00+00',
    TRUE,
    TRUE,
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346
);

INSERT INTO public.review
VALUES (
    1,
    2,
    1,
    4,
    'It was alright',
    FALSE
);

INSERT INTO public.report
VALUES (
    1,
    2,
    1,
    NULL,
    NULL,
    FALSE
);