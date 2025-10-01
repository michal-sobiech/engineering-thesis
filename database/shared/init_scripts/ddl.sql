-- DROP SCHEMA public;

-- CREATE SCHEMA public AUTHORIZATION pg_database_owner;

-- DROP SEQUENCE public.user_id_seq;

CREATE SEQUENCE public.user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;-- public.independent_end_user definition

-- Drop table

-- DROP TABLE public.independent_end_user;

CREATE TABLE public.independent_end_user (
	independent_end_user_id int8 NOT NULL,
	user_id int8 DEFAULT nextval('user_id_seq'::regclass) NOT NULL,
	first_name text NOT NULL,
	last_name text NOT NULL,
	email text NOT NULL,
	password_hash text NOT NULL,
	CONSTRAINT independent_end_user_pk PRIMARY KEY (independent_end_user_id),
	CONSTRAINT independent_end_user_unique UNIQUE (user_id),
	CONSTRAINT independent_end_user_unique_1 UNIQUE (email)
);


-- public.customer definition

-- Drop table

-- DROP TABLE public.customer;

CREATE TABLE public.customer (
	customer_id int8 NOT NULL,
	independent_end_user_id int8 NOT NULL,
	CONSTRAINT customer_pk PRIMARY KEY (customer_id),
	CONSTRAINT customer_unique UNIQUE (independent_end_user_id),
	CONSTRAINT customer_independent_end_user_fk FOREIGN KEY (independent_end_user_id) REFERENCES public.independent_end_user(independent_end_user_id) ON DELETE CASCADE
);


-- public.entrepreneur definition

-- Drop table

-- DROP TABLE public.entrepreneur;

CREATE TABLE public.entrepreneur (
	entrepreneur_id int8 NOT NULL,
	independent_end_user_id int8 NOT NULL,
	CONSTRAINT entrepreneur_pk PRIMARY KEY (entrepreneur_id),
	CONSTRAINT entrepreneur_unique UNIQUE (independent_end_user_id),
	CONSTRAINT entrepreneur_independent_end_user_fk FOREIGN KEY (independent_end_user_id) REFERENCES public.independent_end_user(independent_end_user_id) ON DELETE CASCADE
);


-- public.enterprise definition

-- Drop table

-- DROP TABLE public.enterprise;

CREATE TABLE public.enterprise (
	enterprise_id int8 NOT NULL,
	entrepreneur_id int8 NOT NULL,
	"name" text NOT NULL,
	description text NOT NULL,
	"location" text NOT NULL,
	CONSTRAINT enterprise_pk PRIMARY KEY (enterprise_id),
	CONSTRAINT enterprise_unique UNIQUE (entrepreneur_id),
	CONSTRAINT enterprise_entrepreneur_fk FOREIGN KEY (entrepreneur_id) REFERENCES public.entrepreneur(entrepreneur_id) ON DELETE CASCADE
);


-- public.employee definition

-- Drop table

-- DROP TABLE public.employee;

CREATE TABLE public.employee (
	employee_id int8 NOT NULL,
	user_id int8 DEFAULT nextval('user_id_seq'::regclass) NOT NULL,
	enterprise_id int8 NOT NULL,
	first_name text NOT NULL,
	last_name text NOT NULL,
	username text NOT NULL,
	passwordhash text NOT NULL,
	CONSTRAINT employee_pk PRIMARY KEY (employee_id),
	CONSTRAINT employee_unique UNIQUE (user_id),
	CONSTRAINT employee_unique_1 UNIQUE (enterprise_id),
	CONSTRAINT employee_unique_2 UNIQUE (username),
	CONSTRAINT employee_enterprise_fk FOREIGN KEY (enterprise_id) REFERENCES public.enterprise(enterprise_id)
);