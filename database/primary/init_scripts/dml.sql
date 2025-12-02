INSERT INTO public.app_user (user_group, username, first_name, last_name, password_hash)
VALUES (
    'HEAD_ADMIN',
    'michal.sobiech',
    'Michał',
    'Sobiech',
    '$2a$14$H3Xb1uyNVBoVq7aXZI.mQO/L.iavvGIGjqsrOhm/5HRT9D0A0xkOO'
);

INSERT INTO public.app_user (user_group, username, first_name, last_name, password_hash)
VALUES (
    'REGULAR_ADMIN',
    'andrzej.kowal',
    'Andrzej',
    'Kowal',
    '$2a$14$H3Xb1uyNVBoVq7aXZI.mQO/L.iavvGIGjqsrOhm/5HRT9D0A0xkOO'
);

INSERT INTO public.app_user (user_group, username, first_name, last_name, password_hash, iban)
VALUES (
    'ENTREPRENEUR',
    'adam.adamczyk@example.com',
    'Adam',
    'Adamczyk',
    '$2a$14$H3Xb1uyNVBoVq7aXZI.mQO/L.iavvGIGjqsrOhm/5HRT9D0A0xkOO',
    'PL10105000997603123456789123'
);

INSERT INTO public.app_user (user_group, username, first_name, last_name, password_hash)
VALUES (
    'CUSTOMER',
    'jan.kowalski@example.com',
    'Jan',
    'Kowalski',
    '$2a$14$H3Xb1uyNVBoVq7aXZI.mQO/L.iavvGIGjqsrOhm/5HRT9D0A0xkOO'
);

INSERT INTO public.enterprise (owner_user_id, name, description, address, latitude, longitude, suspended_by_admin)
VALUES (
    3,
    'The Good Hairdresser',
    'Description here',
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346,
    FALSE
);

INSERT INTO public.app_user (user_group, username, first_name, last_name, password_hash, enterprise_id)
VALUES (
    'EMPLOYEE',
    'anna.nowak.1',
    'Anna',
    'Nowak',
    '$2a$14$H3Xb1uyNVBoVq7aXZI.mQO/L.iavvGIGjqsrOhm/5HRT9D0A0xkOO',
    1
);

INSERT INTO public.enterprise_service (
    enterprise_id,
    name,
    description,
    address,
    latitude,
    longitude,
    time_zone,
    takes_custom_appointments,
    max_distance_km,
    cathegory,
    price,
    currency, 
    suspended_by_admin
) VALUES (
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

INSERT INTO public.enterprise_service (
    enterprise_id,
    name,
    description,
    address,
    latitude,
    longitude,
    time_zone,
    takes_custom_appointments,
    max_distance_km,
    cathegory,
    price,
    currency, 
    suspended_by_admin
) VALUES (
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

INSERT INTO public.enterprise_service_slot_template (enterprise_service_id, day_of_week, start_time, end_time, max_occupancy)
VALUES(1, 1, '12:00:00', '12:30:00', 1);

INSERT INTO public.enterprise_service_slot_template (enterprise_service_id, day_of_week, start_time, end_time, max_occupancy)
VALUES(1, 1, '12:30:00', '13:00:00', 1);

INSERT INTO public.enterprise_service_slot_template (enterprise_service_id, day_of_week, start_time, end_time, max_occupancy)
VALUES(1, 3, '13:00:00', '13:30:00', 1);

INSERT INTO public.enterprise_service_slot_template (enterprise_service_id, day_of_week, start_time, end_time, max_occupancy)
VALUES(1, 3, '13:30:00', '14:00:00', 1);

INSERT INTO public.enterprise_service_slot_template (enterprise_service_id, day_of_week, start_time, end_time)
VALUES(2, 3, '8:00:00', '18:00:00');

INSERT INTO public.appointment (
    enterprise_service_id,
    customer_user_id,
    price,
    currency,
    start_time,
    end_time,
    is_custom
) VALUES (
    1,
    4,
    100,
    'PLN',
    '2026-12-01 12:00:00+00',
    '2026-12-01 12:30:00+00',
    FALSE
);

INSERT INTO public.appointment (
    enterprise_service_id,
    customer_user_id,
    price,
    currency,
    start_time,
    end_time,
    is_custom
) VALUES (
    1,
    4,
    100,
    'PLN',
    '2024-12-01 12:00:00+00',
    '2024-12-01 12:30:00+00',
    FALSE
);

INSERT INTO public.appointment (
    enterprise_service_id,
    customer_user_id,
    price,
    currency,
    start_time,
    end_time,
    is_custom,
    is_accepted,
    address,
    latitude,
    longitude
) VALUES (
    2,
    4,
    300,
    'PLN',
    '2026-12-03 13:00:00+00',
    '2026-12-03 14:00:00+00',
    TRUE,
    TRUE,
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346
);

INSERT INTO public.appointment (
    enterprise_service_id,
    customer_user_id,
    price,
    currency,
    start_time,
    end_time,
    is_custom,
    is_accepted,
    address,
    latitude,
    longitude
) VALUES (
    2,
    4,
    300,
    'PLN',
    '2024-12-03 13:00:00+00',
    '2024-12-03 14:00:00+00',
    TRUE,
    TRUE,
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346
);

INSERT INTO public.appointment (
    enterprise_service_id,
    customer_user_id,
    price,
    currency,
    start_time,
    end_time,
    is_custom,
    is_accepted,
    address,
    latitude,
    longitude
) VALUES (
    2,
    4,
    300,
    'PLN',
    '2024-12-03 13:00:00+00',
    '2024-12-03 14:00:00+00',
    TRUE,
    NULL,
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346
);

INSERT INTO public.appointment (
    enterprise_service_id,
    customer_user_id,
    price,
    currency,
    start_time,
    end_time,
    is_custom,
    is_accepted,
    address,
    latitude,
    longitude
) VALUES (
    2,
    4,
    123,
    'PLN',
    '2026-12-03 14:00:00+00',
    '2026-12-03 14:30:00+00',
    TRUE,
    NULL,
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346
);

INSERT INTO public.appointment (
    enterprise_service_id,
    customer_user_id,
    price,
    currency,
    start_time,
    end_time,
    is_custom,
    is_accepted,
    rejection_message,
    address,
    latitude,
    longitude
) VALUES (
    2,
    4,
    9.99,
    'PLN',
    '2026-12-03 14:00:00+00',
    '2026-12-03 14:30:00+00',
    TRUE,
    FALSE,
    'Rejection reason here',
    '15/19, Nowowiejska, Koszyki, Śródmieście Południowe, Midtown, Warsaw, Masovian Voivodeship, 00-665, Poland',
    52.2189917,
    21.011346
);

INSERT INTO public.review (
    creator_customer_user_id,
    subject_enterprise_service_id,
    stars_out_of_5,
    content,
    suspended_by_admin
) VALUES (
    4,
    1,
    4,
    'It was alright',
    FALSE
);

INSERT INTO public.report (
    creator_user_id,
    enterprise_id,
    is_resolved
)
VALUES (
    4,
    1,
    FALSE
);