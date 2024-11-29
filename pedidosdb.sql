--
-- PostgreSQL database dump
--

-- Dumped from database version 15.5
-- Dumped by pg_dump version 15.5

-- Started on 2024-10-15 19:36:10

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
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
-- TOC entry 214 (class 1259 OID 16600)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    cat_id integer NOT NULL,
    cat_nome character varying(20)
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16603)
-- Name: categoria_cat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_cat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categoria_cat_id_seq OWNER TO postgres;

--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 215
-- Name: categoria_cat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_cat_id_seq OWNED BY public.categoria.cat_id;


--
-- TOC entry 216 (class 1259 OID 16604)
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa (
    emp_id integer NOT NULL,
    emp_razao character varying(60),
    emp_fantasia character varying(50),
    emp_cnpj character varying(18),
    emp_cep character varying(9),
    emp_rua character varying(50),
    emp_numero character varying(8),
    emp_bairro character varying(30),
    emp_cidade character varying(30),
    emp_uf character varying(2),
    emp_fone character varying(15),
    emp_email character varying(80),
    emp_vlremb numeric(10,2)
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16607)
-- Name: empresa_emp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empresa_emp_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.empresa_emp_id_seq OWNER TO postgres;

--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 217
-- Name: empresa_emp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.empresa_emp_id_seq OWNED BY public.empresa.emp_id;


--
-- TOC entry 218 (class 1259 OID 16608)
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item (
    pro_id integer NOT NULL,
    ped_id integer NOT NULL,
    it_quant integer,
    it_valor numeric(10,2)
);


ALTER TABLE public.item OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16611)
-- Name: pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedido (
    ped_id integer NOT NULL,
    ped_data date,
    ped_clinome character varying(50),
    ped_clifone character varying(15),
    ped_total numeric(10,2),
    ped_viagem character(1),
    tpg_id integer
);


ALTER TABLE public.pedido OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16614)
-- Name: pedido_ped_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedido_ped_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pedido_ped_id_seq OWNER TO postgres;

--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 220
-- Name: pedido_ped_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedido_ped_id_seq OWNED BY public.pedido.ped_id;


--
-- TOC entry 221 (class 1259 OID 16615)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    pro_id integer NOT NULL,
    pro_nome character varying(30),
    pro_descr text,
    pro_valor numeric(10,2),
    pro_foto bytea,
    cat_id integer
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16620)
-- Name: produto_pro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_pro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produto_pro_id_seq OWNER TO postgres;

--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 222
-- Name: produto_pro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_pro_id_seq OWNED BY public.produto.pro_id;


--
-- TOC entry 223 (class 1259 OID 16621)
-- Name: tipo_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_pagamento (
    tpg_id integer NOT NULL,
    tpg_nome character varying(20)
);


ALTER TABLE public.tipo_pagamento OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16624)
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_pagamento_tpg_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tipo_pagamento_tpg_id_seq OWNER TO postgres;

--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 224
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_pagamento_tpg_id_seq OWNED BY public.tipo_pagamento.tpg_id;


--
-- TOC entry 3197 (class 2604 OID 16625)
-- Name: categoria cat_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN cat_id SET DEFAULT nextval('public.categoria_cat_id_seq'::regclass);


--
-- TOC entry 3198 (class 2604 OID 16626)
-- Name: empresa emp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa ALTER COLUMN emp_id SET DEFAULT nextval('public.empresa_emp_id_seq'::regclass);


--
-- TOC entry 3199 (class 2604 OID 16627)
-- Name: pedido ped_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido ALTER COLUMN ped_id SET DEFAULT nextval('public.pedido_ped_id_seq'::regclass);


--
-- TOC entry 3200 (class 2604 OID 16628)
-- Name: produto pro_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN pro_id SET DEFAULT nextval('public.produto_pro_id_seq'::regclass);


--
-- TOC entry 3201 (class 2604 OID 16629)
-- Name: tipo_pagamento tpg_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento ALTER COLUMN tpg_id SET DEFAULT nextval('public.tipo_pagamento_tpg_id_seq'::regclass);


--
-- TOC entry 3360 (class 0 OID 16600)
-- Dependencies: 214
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3362 (class 0 OID 16604)
-- Dependencies: 216
-- Data for Name: empresa; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3364 (class 0 OID 16608)
-- Dependencies: 218
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3365 (class 0 OID 16611)
-- Dependencies: 219
-- Data for Name: pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3367 (class 0 OID 16615)
-- Dependencies: 221
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3369 (class 0 OID 16621)
-- Dependencies: 223
-- Data for Name: tipo_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 215
-- Name: categoria_cat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_cat_id_seq', 1, false);


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 217
-- Name: empresa_emp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.empresa_emp_id_seq', 1, false);


--
-- TOC entry 3383 (class 0 OID 0)
-- Dependencies: 220
-- Name: pedido_ped_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedido_ped_id_seq', 1, false);


--
-- TOC entry 3384 (class 0 OID 0)
-- Dependencies: 222
-- Name: produto_pro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_pro_id_seq', 1, false);


--
-- TOC entry 3385 (class 0 OID 0)
-- Dependencies: 224
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_pagamento_tpg_id_seq', 1, false);


--
-- TOC entry 3203 (class 2606 OID 16631)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (cat_id);


--
-- TOC entry 3205 (class 2606 OID 16633)
-- Name: empresa empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (emp_id);


--
-- TOC entry 3207 (class 2606 OID 16635)
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (pro_id, ped_id);


--
-- TOC entry 3209 (class 2606 OID 16637)
-- Name: pedido pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (ped_id);


--
-- TOC entry 3211 (class 2606 OID 16639)
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (pro_id);


--
-- TOC entry 3213 (class 2606 OID 16641)
-- Name: tipo_pagamento tipo_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento
    ADD CONSTRAINT tipo_pagamento_pkey PRIMARY KEY (tpg_id);


--
-- TOC entry 3214 (class 2606 OID 16642)
-- Name: item item_ped_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_ped_id_fkey FOREIGN KEY (ped_id) REFERENCES public.pedido(ped_id) NOT VALID;


--
-- TOC entry 3215 (class 2606 OID 16647)
-- Name: item item_pro_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pro_id_fkey FOREIGN KEY (pro_id) REFERENCES public.produto(pro_id);


--
-- TOC entry 3216 (class 2606 OID 16652)
-- Name: pedido pedido_tpg_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_tpg_id_fkey FOREIGN KEY (tpg_id) REFERENCES public.tipo_pagamento(tpg_id) NOT VALID;


--
-- TOC entry 3217 (class 2606 OID 16657)
-- Name: produto produto_cat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_cat_id_fkey FOREIGN KEY (cat_id) REFERENCES public.categoria(cat_id);


-- Completed on 2024-10-15 19:36:10

--
-- PostgreSQL database dump complete
--

