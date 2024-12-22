-- public.user_id_seq definition

-- DROP SEQUENCE public.user_id_seq;

CREATE SEQUENCE public.user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;


-- public.customer_customer_id_seq definition

-- DROP SEQUENCE public.customer_customer_id_seq;

CREATE SEQUENCE public.customer_customer_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;


-- public.customer_independent_end_user_id_seq definition

-- DROP SEQUENCE public.customer_independent_end_user_id_seq;

CREATE SEQUENCE public.customer_independent_end_user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;


-- public.independent_end_user_independent_end_user_id_seq definition

-- DROP SEQUENCE public.independent_end_user_independent_end_user_id_seq;

CREATE SEQUENCE public.independent_end_user_independent_end_user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;


-- public.independent_end_user definition

-- Drop table

-- DROP TABLE public.independent_end_user;

CREATE TABLE public.independent_end_user (
	independent_end_user_id serial4 NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	password_hash varchar(100) NOT NULL,
	user_id int4 DEFAULT nextval('user_id_seq'::regclass) NOT NULL,
	CONSTRAINT independent_end_user_pk PRIMARY KEY (independent_end_user_id),
	CONSTRAINT independent_end_user_unique UNIQUE (email)
);


-- public.customer definition

-- Drop table

-- DROP TABLE public.customer;

CREATE TABLE public.customer (
	customer_id serial4 NOT NULL,
	independent_end_user_id serial4 NOT NULL,
	payment_method_id int4 NULL,
	CONSTRAINT customer_pk PRIMARY KEY (customer_id),
	CONSTRAINT customer_unique UNIQUE (independent_end_user_id),
	CONSTRAINT customer_unique_1 UNIQUE (payment_method_id),
	CONSTRAINT customer_independent_end_user_fk FOREIGN KEY (independent_end_user_id) REFERENCES public.independent_end_user(independent_end_user_id)
);