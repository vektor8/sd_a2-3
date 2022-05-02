-- This script was generated by a beta version of the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.food
(
    id bigint NOT NULL,
    category integer,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    price double precision,
    restaurant_id bigint NOT NULL,
    CONSTRAINT food_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.ordered_food
(
    id bigint NOT NULL,
    quantity integer,
    food_id bigint,
    order_id bigint,
    CONSTRAINT ordered_food_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.orders
(
    id bigint NOT NULL,
    order_status integer,
    customer_id bigint,
    restaurant_id bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.restaurant
(
    id bigint NOT NULL,
    location character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    admin_id bigint,
    CONSTRAINT restaurant_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.restaurant_foods
(
    restaurant_id bigint NOT NULL,
    foods_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.restaurant_orders
(
    restaurant_id bigint NOT NULL,
    orders_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.users
(
    dtype character varying(31) COLLATE pg_catalog."default" NOT NULL,
    id bigint NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    password_hash character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users_orders
(
    customer_id bigint NOT NULL,
    orders_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.users_restaurants
(
    admin_id bigint NOT NULL,
    restaurants_id bigint NOT NULL
);

ALTER TABLE IF EXISTS public.food
    ADD CONSTRAINT fkm9xrxt95wwp1r2s7andom1l1c FOREIGN KEY (restaurant_id)
    REFERENCES public.restaurant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.ordered_food
    ADD CONSTRAINT fk4o5ymem1aghd4echyvrr7e2m3 FOREIGN KEY (order_id)
    REFERENCES public.orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.ordered_food
    ADD CONSTRAINT fkbmd6kulwkrenlll0s6tblji6j FOREIGN KEY (food_id)
    REFERENCES public.food (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT fki7hgjxhw21nei3xgpe4nnpenh FOREIGN KEY (restaurant_id)
    REFERENCES public.restaurant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT fksjfs85qf6vmcurlx43cnc16gy FOREIGN KEY (customer_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.restaurant
    ADD CONSTRAINT fkrk767nt9h14telxuh6tn5xff7 FOREIGN KEY (admin_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.restaurant_foods
    ADD CONSTRAINT fk4dbvvswitmtp26c2n425c9d12 FOREIGN KEY (restaurant_id)
    REFERENCES public.restaurant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.restaurant_foods
    ADD CONSTRAINT fk546hh7nv85x4dl0aefekixbm6 FOREIGN KEY (foods_id)
    REFERENCES public.food (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS uk_jcui1ncguod9vfe1qbpksy46j
    ON public.restaurant_foods(foods_id);


ALTER TABLE IF EXISTS public.restaurant_orders
    ADD CONSTRAINT fk21jwpdt4b37ymv5nkjjte0seg FOREIGN KEY (restaurant_id)
    REFERENCES public.restaurant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.restaurant_orders
    ADD CONSTRAINT fk8swg1vejobtj0ryb6fiwg264k FOREIGN KEY (orders_id)
    REFERENCES public.orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS uk_d1dqp0i886vmn4ue9eggj0d8j
    ON public.restaurant_orders(orders_id);


ALTER TABLE IF EXISTS public.users_orders
    ADD CONSTRAINT fk2lnf5jw8p8q0ytkr8dp0mlx6 FOREIGN KEY (orders_id)
    REFERENCES public.orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS uk_1njdfitph68mh7p7c6f3qc736
    ON public.users_orders(orders_id);


ALTER TABLE IF EXISTS public.users_orders
    ADD CONSTRAINT fk535gbkx5lnqoh2tinilnquojy FOREIGN KEY (customer_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.users_restaurants
    ADD CONSTRAINT fk8pqqnjtabbpyn66pbvtbp8q37 FOREIGN KEY (restaurants_id)
    REFERENCES public.restaurant (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;
CREATE INDEX IF NOT EXISTS uk_8h27sv3t26a2rsk4op3x43la7
    ON public.users_restaurants(restaurants_id);


ALTER TABLE IF EXISTS public.users_restaurants
    ADD CONSTRAINT fknxioxhqspamncuuigg98hksny FOREIGN KEY (admin_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION;

END;
