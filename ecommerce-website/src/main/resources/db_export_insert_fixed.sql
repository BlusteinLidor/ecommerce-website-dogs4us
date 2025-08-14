--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5 (Debian 17.5-1.pgdg120+1)
-- Dumped by pg_dump version 17.5 (Debian 17.5-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: cart_item; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.cart_item (
    product_id uuid NOT NULL,
    user_id uuid NOT NULL,
    customization_preview character varying(255),
    quantity integer NOT NULL
);


ALTER TABLE public.cart_item OWNER TO admin;

--
-- Name: category; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.category (
    id uuid NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.category OWNER TO admin;

--
-- Name: customization; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.customization (
    id uuid NOT NULL,
    field_name character varying(255) NOT NULL,
    field_value character varying(255),
    order_id uuid,
    product_id uuid
);


ALTER TABLE public.customization OWNER TO admin;

--
-- Name: order_item; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.order_item (
    order_id uuid NOT NULL,
    product_id uuid NOT NULL,
    customization_reference character varying(255),
    quantity integer NOT NULL,
    unit_price double precision NOT NULL
);


ALTER TABLE public.order_item OWNER TO admin;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.orders (
    order_id uuid NOT NULL,
    order_date date NOT NULL,
    total_amount double precision NOT NULL,
    user_id uuid
);


ALTER TABLE public.orders OWNER TO admin;

--
-- Name: product; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.product (
    id uuid NOT NULL,
    description character varying(255),
    name character varying(255) NOT NULL,
    price double precision NOT NULL,
    stock_quantity integer,
    category_id uuid,
    imageurl character varying(255),
    customizable_fields character varying(255)[]
);


ALTER TABLE public.product OWNER TO admin;

--
-- Name: product_custom_fields; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.product_custom_fields (
    product_id uuid NOT NULL,
    field_name character varying(255)
);


ALTER TABLE public.product_custom_fields OWNER TO admin;

--
-- Name: product_customizable_fields; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.product_customizable_fields (
    product_id uuid NOT NULL,
    field_name character varying(255)
);


ALTER TABLE public.product_customizable_fields OWNER TO admin;

--
-- Name: users; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    email character varying(255) NOT NULL,
    is_admin boolean,
    name character varying(255) NOT NULL,
    password_hash character varying(255) NOT NULL
);


ALTER TABLE public.users OWNER TO admin;

--
-- Data for Name: cart_item; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.cart_item (product_id, user_id, customization_preview, quantity) VALUES ('423e4567-e89b-12d3-a456-426614174000', '123e4567-e89b-12d3-a456-426614174001', 'Standard package', '2');


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.category (id, name) VALUES ('323e4567-e89b-12d3-a456-426614174000', 'Dog Food');
INSERT INTO public.category (id, name) VALUES ('323e4567-e89b-12d3-a456-426614174001', 'Dog Toys');
INSERT INTO public.category (id, name) VALUES ('10480589-25b5-4e1b-9618-9445761a4ea8', 'Dog Mats');
INSERT INTO public.category (id, name) VALUES ('a15b0fcf-696a-499c-ad85-dc59074af4c2', 'Dog Pillows');
INSERT INTO public.category (id, name) VALUES ('427d638c-024f-4254-ba9b-5662a5994628', 'Dog Wearables');
INSERT INTO public.category (id, name) VALUES ('6838f338-62ea-484b-b45e-6149a789d9ca', 'Dog Blankets');
INSERT INTO public.category (id, name) VALUES ('323e4567-e89b-12d3-a456-426614174002', 'Dog Tags');


--
-- Data for Name: customization; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('523e4567-e89b-12d3-a456-426614174000', 'Color Choice', 'Blue', '223e4567-e89b-12d3-a456-426614174000', '423e4567-e89b-12d3-a456-426614174001');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('d7d493cd-1557-4a0c-8491-1a07d9c64cb7', 'Name', 'Billie', '307a2072-1e7c-482c-86a6-b59116663238', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('934c14d7-6d75-4d09-83fe-4a40b84eb450', 'Name', 'asd', '5ec73052-df80-4113-8e43-c871b228d0ab', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('7375e44a-a6bd-48b0-8df1-3fe8eef0b3c6', 'Name', 'asd', '0e6d5ac7-8957-4eda-88d2-f3e226ba62b0', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('e588362d-afd9-4d9f-80dd-901e1406d630', 'Name', '123', '80b0a25b-0fda-4bf8-ab33-75b49cba027d', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('d7b6517f-9e91-4c5f-8dcf-3b0c672a948a', 'Name', '34', 'a6cc2fbd-2c2e-403a-a2de-d4d53dc9ae5d', '31a1f8cc-4bde-45e2-847c-1ddca1ec5652');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('fa53bfb6-eead-4d06-8c82-2a22adc78900', 'Name', '564', '175c01f8-8aa4-41a1-a801-f5381aab2c6b', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('e8b11efb-a5a9-45b7-8651-e5702e4df0ed', 'Image', '-', '175c01f8-8aa4-41a1-a801-f5381aab2c6b', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('8168f202-c7bc-4a9a-9710-6c94daf50f46', 'Name', '34', 'c349b5d4-9b44-4212-9250-d94387865691', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('66e6d509-f106-4090-b2d2-bfe119d92f9e', 'Image', '-', 'c349b5d4-9b44-4212-9250-d94387865691', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('17159399-02f5-45da-b4e7-e4618faf305f', 'Name', '1', 'aa5bba90-8ad2-46c3-b5d8-4bffe5c47692', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('6abf5946-72d8-4eb0-8213-b05ec91fd7ac', 'Image', '/assets/img/product12.png', 'aa5bba90-8ad2-46c3-b5d8-4bffe5c47692', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('723784d1-8a81-4b8b-845d-f843f64714b1', 'Name', '-', '8b831f2e-115b-4a09-8d42-9800588499e7', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('4ee769e4-78d0-408c-96d7-0ee25321fe27', 'Image', '-', '8b831f2e-115b-4a09-8d42-9800588499e7', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('6d34fb19-e829-4b51-9ed6-6b6dcfa469eb', 'Image', '/assets/img/product10.png', '8b831f2e-115b-4a09-8d42-9800588499e7', '9aaa20aa-e9a5-49f6-b197-51b79fc338c3');
INSERT INTO public.customization (id, field_name, field_value, order_id, product_id) VALUES ('86eebaa4-1365-4229-938d-4d8952fffbbb', 'Name', '1', 'f0639ab6-f523-482f-b249-e429289aef61', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3');


--
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('223e4567-e89b-12d3-a456-426614174000', '423e4567-e89b-12d3-a456-426614174000', 'CUSTOM-REF-001', '2', '49.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('69cb6089-1526-4599-b968-2dfe9dd65f59', '423e4567-e89b-12d3-a456-426614174001', '', '1', '29.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('69cb6089-1526-4599-b968-2dfe9dd65f59', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3', '', '1', '45.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('e2848a3b-8724-4641-8e66-4a4051df0172', '423e4567-e89b-12d3-a456-426614174001', '', '1', '29.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('e2848a3b-8724-4641-8e66-4a4051df0172', '9aaa20aa-e9a5-49f6-b197-51b79fc338c3', '', '1', '35.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('e2848a3b-8724-4641-8e66-4a4051df0172', '423e4567-e89b-12d3-a456-426614174000', '', '3', '19.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('93387777-411a-439f-9d43-c1c907cea2ba', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1', '', '1', '15.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('93387777-411a-439f-9d43-c1c907cea2ba', '2322cf20-39b9-4fa1-9b94-e6f698c0ce42', '', '2', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('93387777-411a-439f-9d43-c1c907cea2ba', '31a1f8cc-4bde-45e2-847c-1ddca1ec5652', '', '4', '25.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('93387777-411a-439f-9d43-c1c907cea2ba', 'e6950282-8d1d-4409-8c8f-24a006883432', '', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('93387777-411a-439f-9d43-c1c907cea2ba', '423e4567-e89b-12d3-a456-426614174000', '', '1', '19.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('93387777-411a-439f-9d43-c1c907cea2ba', '423e4567-e89b-12d3-a456-426614174001', '', '11', '29.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('62ae05da-d59c-4627-b772-4d15cb48e377', '2322cf20-39b9-4fa1-9b94-e6f698c0ce42', '', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('fbb9c4e4-3d65-4643-b202-74b488d97078', '423e4567-e89b-12d3-a456-426614174001', '', '1', '29.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('149934fe-8da1-4c17-8680-6765944a19f5', '423e4567-e89b-12d3-a456-426614174001', '', '1', '29.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('f6542d0a-b460-4210-9152-d26fa9fc2b32', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3', '', '1', '45.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('06bc5d47-e747-4e33-bcbc-e2fdca934bc8', '423e4567-e89b-12d3-a456-426614174001', '', '1', '29.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('95290970-40e2-40dc-8b42-5113877c7f28', '2322cf20-39b9-4fa1-9b94-e6f698c0ce42', '', '4', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('be426900-f28a-4e5f-bd49-53fc7cccca63', '423e4567-e89b-12d3-a456-426614174001', 'Color: Black; Size: Medium;', '1', '29.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('580ef3a1-0e86-45af-8445-5bb3325997e3', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: White; Size: Small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('4723b510-2309-46e1-8b42-943745a33d24', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: White; Size: Small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('45f49034-2304-4b7d-b9ff-60dee6cd86fb', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: White; Size: Small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('9b6fd8ca-5f42-479a-9f0a-448432181157', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('30ebad34-6968-4804-ada0-63ebb5dff7b4', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('e1f4cc53-f69e-4767-937a-d02137cc6db5', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('e55bebe3-1252-499a-96da-8bf7ea6ccce9', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('43e0d52c-e3ab-41ea-a3ab-3dddb5ca7202', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('17e97324-1fa9-4746-9689-bcc2f1059e81', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('c7b98fc9-08d8-49ca-be10-3424cf9feeff', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('7777eae0-ebf1-4ddf-98e0-0b03ece21997', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('df6392eb-07d6-41d4-bfb6-e745b89de588', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('ad3ec6ce-35dd-4d2f-8317-ead02b4dfaaa', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('bb4ec610-7495-49da-84c2-e73a455a8389', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('a8f2a1e5-0219-42f7-a979-39d9fa951c9e', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('6ddbc216-b580-4cce-a372-00db4fa05dd1', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('ae61661f-580f-4c61-8851-79c4b0de6870', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('14f43481-3b6a-46aa-80ce-aa80b698a7b8', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: black; Size: small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('88eb5926-5e26-4319-ab19-f97a9ecf10a9', 'e6950282-8d1d-4409-8c8f-24a006883432', 'Color: White; Size: Small;', '1', '33.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('307a2072-1e7c-482c-86a6-b59116663238', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3', 'Name: Billie;', '1', '45.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('5ec73052-df80-4113-8e43-c871b228d0ab', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3', 'Name: asd;', '1', '45.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('0e6d5ac7-8957-4eda-88d2-f3e226ba62b0', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3', 'Name: asd;', '1', '45.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('80b0a25b-0fda-4bf8-ab33-75b49cba027d', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3', 'Name: 123;', '1', '45.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('a6cc2fbd-2c2e-403a-a2de-d4d53dc9ae5d', '31a1f8cc-4bde-45e2-847c-1ddca1ec5652', 'Name: 34;', '1', '25.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('175c01f8-8aa4-41a1-a801-f5381aab2c6b', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1', 'Name: 564; Image: -;', '1', '15.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('c349b5d4-9b44-4212-9250-d94387865691', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1', 'Name: 34; Image: -;', '1', '15.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('aa5bba90-8ad2-46c3-b5d8-4bffe5c47692', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1', 'Name: 1; Image: /assets/img/product12.png;', '1', '15.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('8b831f2e-115b-4a09-8d42-9800588499e7', 'e4ae9233-3693-467f-9af4-8afdbbcab3e1', 'Name: -; Image: -;', '1', '15.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('8b831f2e-115b-4a09-8d42-9800588499e7', '9aaa20aa-e9a5-49f6-b197-51b79fc338c3', 'Image: /assets/img/product10.png;', '1', '35.99');
INSERT INTO public.order_item (order_id, product_id, customization_reference, quantity, unit_price) VALUES ('f0639ab6-f523-482f-b249-e429289aef61', '41bf873a-34e5-4cb3-a099-4a9c7ed633c3', 'Name: 1;', '1', '45.99');


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('223e4567-e89b-12d3-a456-426614174000', '2025-06-10', '299.99', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('223e4567-e89b-12d3-a456-426614174001', '2025-06-11', '149.5', '123e4567-e89b-12d3-a456-426614174001');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('223e4567-e89b-12d3-a456-426614174002', '2025-06-09', '599.99', '123e4567-e89b-12d3-a456-426614174002');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('223e4567-e89b-12d3-a456-426614174003', '2025-06-11', '89.99', '123e4567-e89b-12d3-a456-426614174000');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('f6355e3a-b2b7-406a-ad94-b76b1c9b6b61', '2025-12-12', '999.99', '123e4567-e89b-12d3-a456-426614174002');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('69cb6089-1526-4599-b968-2dfe9dd65f59', '2025-07-16', '75.98', '71995f7f-3702-43a5-99fb-9a1e7f384bb3');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('e2848a3b-8724-4641-8e66-4a4051df0172', '2025-07-18', '125.95', '71995f7f-3702-43a5-99fb-9a1e7f384bb3');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('93387777-411a-439f-9d43-c1c907cea2ba', '2025-07-18', '571.8', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('62ae05da-d59c-4627-b772-4d15cb48e377', '2025-07-23', '33.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('fbb9c4e4-3d65-4643-b202-74b488d97078', '2025-07-23', '29.99', '71995f7f-3702-43a5-99fb-9a1e7f384bb3');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('149934fe-8da1-4c17-8680-6765944a19f5', '2025-07-23', '29.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('f6542d0a-b460-4210-9152-d26fa9fc2b32', '2025-07-27', '45.99', '77e664c2-9fb8-4ae6-94a6-0d3169abf366');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('06bc5d47-e747-4e33-bcbc-e2fdca934bc8', '2025-07-27', '29.99', '77e664c2-9fb8-4ae6-94a6-0d3169abf366');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('95290970-40e2-40dc-8b42-5113877c7f28', '2025-07-31', '139.96', '77e664c2-9fb8-4ae6-94a6-0d3169abf366');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('be426900-f28a-4e5f-bd49-53fc7cccca63', '2025-07-31', '29.99', '77e664c2-9fb8-4ae6-94a6-0d3169abf366');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('307a2072-1e7c-482c-86a6-b59116663238', '2025-08-03', '45.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('5ec73052-df80-4113-8e43-c871b228d0ab', '2025-08-03', '45.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('0e6d5ac7-8957-4eda-88d2-f3e226ba62b0', '2025-08-03', '45.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('80b0a25b-0fda-4bf8-ab33-75b49cba027d', '2025-08-03', '45.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('a6cc2fbd-2c2e-403a-a2de-d4d53dc9ae5d', '2025-08-03', '25.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('175c01f8-8aa4-41a1-a801-f5381aab2c6b', '2025-08-03', '15.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('c349b5d4-9b44-4212-9250-d94387865691', '2025-08-03', '15.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('aa5bba90-8ad2-46c3-b5d8-4bffe5c47692', '2025-08-04', '15.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('8b831f2e-115b-4a09-8d42-9800588499e7', '2025-08-06', '51.98', '77e664c2-9fb8-4ae6-94a6-0d3169abf366');
INSERT INTO public.orders (order_id, order_date, total_amount, user_id) VALUES ('f0639ab6-f523-482f-b249-e429289aef61', '2025-08-06', '45.99', '4744ef9c-bbe9-48f5-84ff-546b14863150');


--
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.product (id, description, name, price, stock_quantity, category_id, imageurl, customizable_fields) VALUES ('e6950282-8d1d-4409-8c8f-24a006883432', 'Mat', 'Customizable Dog Mat', '33.99', '19', '10480589-25b5-4e1b-9618-9445761a4ea8', 'assets/img/product2.jpg', NULL);
INSERT INTO public.product (id, description, name, price, stock_quantity, category_id, imageurl, customizable_fields) VALUES ('31a1f8cc-4bde-45e2-847c-1ddca1ec5652', 'Hoop', 'Customizable Dog Toy - Hoop', '25.99', '9', '323e4567-e89b-12d3-a456-426614174001', 'assets/img/product7.png', NULL);
INSERT INTO public.product (id, description, name, price, stock_quantity, category_id, imageurl, customizable_fields) VALUES ('e4ae9233-3693-467f-9af4-8afdbbcab3e1', 'Bandana', 'Customizable Dog Bandana', '15.99', '18', '427d638c-024f-4254-ba9b-5662a5994628', 'assets/img/product6.jpg', NULL);
INSERT INTO public.product (id, description, name, price, stock_quantity, category_id, imageurl, customizable_fields) VALUES ('9aaa20aa-e9a5-49f6-b197-51b79fc338c3', 'Blanket', 'Customizable Dog Blanket', '35.99', '4', '6838f338-62ea-484b-b45e-6149a789d9ca', 'assets/img/product8.jpg', NULL);
INSERT INTO public.product (id, description, name, price, stock_quantity, category_id, imageurl, customizable_fields) VALUES ('41bf873a-34e5-4cb3-a099-4a9c7ed633c3', 'Vest', 'Customizable Dog Vest', '45.99', '2', '427d638c-024f-4254-ba9b-5662a5994628', 'assets/img/product5.jpg', NULL);
INSERT INTO public.product (id, description, name, price, stock_quantity, category_id, imageurl, customizable_fields) VALUES ('423e4567-e89b-12d3-a456-426614174000', '', 'Customizable Dog Bowl', '19.99', '20', '323e4567-e89b-12d3-a456-426614174000', 'assets/img/product1.jpg', NULL);
INSERT INTO public.product (id, description, name, price, stock_quantity, category_id, imageurl, customizable_fields) VALUES ('423e4567-e89b-12d3-a456-426614174001', 'Personalized toy for your furry friend', 'Customizable Dog Toy', '29.99', '0', '323e4567-e89b-12d3-a456-426614174001', 'assets/img/product3.jpg', NULL);
INSERT INTO public.product (id, description, name, price, stock_quantity, category_id, imageurl, customizable_fields) VALUES ('2322cf20-39b9-4fa1-9b94-e6f698c0ce42', 'Pillow', 'Customizable Dog Pillow', '33.99', '2', 'a15b0fcf-696a-499c-ad85-dc59074af4c2', 'assets/img/product4.jpg', NULL);


--
-- Data for Name: product_custom_fields; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('2322cf20-39b9-4fa1-9b94-e6f698c0ce42', 'Image');
INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('423e4567-e89b-12d3-a456-426614174000', 'Size');
INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('423e4567-e89b-12d3-a456-426614174001', 'Color');
INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('423e4567-e89b-12d3-a456-426614174001', 'Size');
INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('e6950282-8d1d-4409-8c8f-24a006883432', '[[Color, Size]]');
INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('31a1f8cc-4bde-45e2-847c-1ddca1ec5652', '[Name]');
INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('e4ae9233-3693-467f-9af4-8afdbbcab3e1', '[[[[Name, Image]]]]');
INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('9aaa20aa-e9a5-49f6-b197-51b79fc338c3', '[Image]');
INSERT INTO public.product_custom_fields (product_id, field_name) VALUES ('41bf873a-34e5-4cb3-a099-4a9c7ed633c3', '[[[[[Name]]]]]');

--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: admin
--

INSERT INTO public.users (id, email, is_admin, name, password_hash) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'john.doe@example.com', 't', 'John Doe', '$2a$10$abcdefghijklmnopqrstuvwxyz123456789');
INSERT INTO public.users (id, email, is_admin, name, password_hash) VALUES ('123e4567-e89b-12d3-a456-426614174001', 'jane.smith@example.com', 'f', 'Jane Smith', '$2a$10$abcdefghijklmnopqrstuvwxyz987654321');
INSERT INTO public.users (id, email, is_admin, name, password_hash) VALUES ('123e4567-e89b-12d3-a456-426614174002', 'bob.wilson@example.com', 'f', 'Bob Wilson', '$2a$10$abcdefghijklmnopqrstuvwxyz456789123');
INSERT INTO public.users (id, email, is_admin, name, password_hash) VALUES ('cabc975d-9e07-495f-b593-70cecc4c5fbc', 'lidor1@example.com', 'f', 'Lidor', '$2a$10$9uUE0kPFDh7fPFIU206fHe3ELsqOOJclAWhOEyRgGjUWf0HUUTuNC');
INSERT INTO public.users (id, email, is_admin, name, password_hash) VALUES ('c26fdccb-3ae3-4a2b-83fb-ab0b5c0e935b', 'lidor11@example.com', 'f', 'dsdasds', '$2a$10$ZKJVu1clRFziMmRuiknMvurlzXhmY8rTndqBcrQsUHBOJB5TLXP/2');
INSERT INTO public.users (id, email, is_admin, name, password_hash) VALUES ('71995f7f-3702-43a5-99fb-9a1e7f384bb3', 'lidorblu@example.com', 'f', 'Lidor Blustein', '$2a$10$Zfj3MX0WWLKSk0YWONsQi.C1FZjTXkOHNZP8PtCZuB.BO7jey/sIW');
INSERT INTO public.users (id, email, is_admin, name, password_hash) VALUES ('4744ef9c-bbe9-48f5-84ff-546b14863150', 'lidor@example.com', 't', 'Lidor', '$2a$10$tp0tJ1ihWaFs7Y5OBm0hbu/KdA6YnOSnyh7WMJ5iB6oQ081S2uyJC');
INSERT INTO public.users (id, email, is_admin, name, password_hash) VALUES ('77e664c2-9fb8-4ae6-94a6-0d3169abf366', '123@gmail.com', 'f', 'dsad ', '$2a$10$rep7U6mVyfdVb2wCYgxL8uWpRESVpikwaFSimEOZ3KCXdmAzzI9Pm');


--
-- Name: cart_item cart_item_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_pkey PRIMARY KEY (product_id, user_id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: customization customization_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customization
    ADD CONSTRAINT customization_pkey PRIMARY KEY (id);


--
-- Name: order_item order_item_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.order_item
    ADD CONSTRAINT order_item_pkey PRIMARY KEY (order_id, product_id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (order_id);


--
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- Name: customization uk3dci4ekv26i22biulik5nmult; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customization
    ADD CONSTRAINT uk3dci4ekv26i22biulik5nmult UNIQUE (order_id, product_id, field_name);


--
-- Name: category uk46ccwnsi9409t36lurvtyljak; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT uk46ccwnsi9409t36lurvtyljak UNIQUE (name);


--
-- Name: users uk6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- Name: product_custom_fields ukor7xjntobmknc609ow73uxwtm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.product_custom_fields
    ADD CONSTRAINT ukor7xjntobmknc609ow73uxwtm UNIQUE (product_id, field_name);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: product fk1mtsbur82frn64de7balymq9s; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fk1mtsbur82frn64de7balymq9s FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: product_custom_fields fkawlsh4h7c2ggsmi9g1e5f3qh9; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.product_custom_fields
    ADD CONSTRAINT fkawlsh4h7c2ggsmi9g1e5f3qh9 FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: product_customizable_fields fkdpitcpyvi2r85rhd6w6jbklei; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.product_customizable_fields
    ADD CONSTRAINT fkdpitcpyvi2r85rhd6w6jbklei FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- Name: customization fkliyorqc57r3om26ph9recsaim; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customization
    ADD CONSTRAINT fkliyorqc57r3om26ph9recsaim FOREIGN KEY (order_id) REFERENCES public.orders(order_id);


--
-- Name: customization fkrw7p9xlu0ex4b1wvnr9m0k8eq; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.customization
    ADD CONSTRAINT fkrw7p9xlu0ex4b1wvnr9m0k8eq FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- PostgreSQL database dump complete
--

