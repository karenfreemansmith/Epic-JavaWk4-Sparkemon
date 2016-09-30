--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: pets; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE pets (
    id integer NOT NULL,
    name character varying,
    playerid integer,
    birthday timestamp without time zone,
    lastate timestamp without time zone,
    lastslept timestamp without time zone,
    lastplayed timestamp without time zone,
    lastspecial timestamp without time zone,
    type integer,
    foodlevel integer,
    sleeplevel integer,
    playlevel integer,
    speciallevel integer
);


ALTER TABLE pets OWNER TO "Guest";

--
-- Name: pets_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE pets_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE pets_id_seq OWNER TO "Guest";

--
-- Name: pets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE pets_id_seq OWNED BY pets.id;


--
-- Name: players; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE players (
    id integer NOT NULL,
    name character varying,
    email character varying
);


ALTER TABLE players OWNER TO "Guest";

--
-- Name: players_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE players_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE players_id_seq OWNER TO "Guest";

--
-- Name: players_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE players_id_seq OWNED BY players.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY pets ALTER COLUMN id SET DEFAULT nextval('pets_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY players ALTER COLUMN id SET DEFAULT nextval('players_id_seq'::regclass);


--
-- Data for Name: pets; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY pets (id, name, playerid, birthday, lastate, lastslept, lastplayed, lastspecial, type, foodlevel, sleeplevel, playlevel, speciallevel) FROM stdin;
16	Granite Smash	9	2016-09-29 16:32:00.037339	2016-09-29 16:32:33.041246	2016-09-29 16:32:57.74378	2016-09-29 16:32:56.439795	\N	16	6	9	9	\N
17	VarChar	9	2016-09-29 16:33:01.987585	2016-09-29 16:33:27.279574	2016-09-29 16:33:37.53697	2016-09-29 16:33:39.801917	\N	3	6	12	10	\N
9	BatMan	5	2016-09-29 16:11:13.412411	2016-09-29 16:11:37.271994	2016-09-29 16:12:09.471621	2016-09-29 16:11:54.975157	\N	5	6	12	16	\N
8	greg	4	2016-09-29 16:05:59.603834	2016-09-29 16:07:05.809653	2016-09-29 16:07:13.551189	2016-09-29 16:07:11.256922	\N	11	6	7	9	\N
18	marley	10	2016-09-29 16:35:46.49165	2016-09-29 16:36:06.839528	\N	\N	\N	16	6	6	8	\N
4	Rocky	1	2016-09-29 15:10:24.317793	2016-09-29 15:10:44.039565	2016-09-29 15:10:46.015614	2016-09-29 15:10:47.855053	\N	11	6	7	10	\N
6	petmon	3	2016-09-29 16:01:38.3255	\N	\N	\N	\N	9	3	6	8	\N
11	thing	6	2016-09-29 16:15:34.083867	2016-09-29 16:15:55.304482	2016-09-29 16:16:02.343267	2016-09-29 16:15:51.471724	\N	14	6	12	16	\N
10	HA	5	2016-09-29 16:12:29.228014	2016-09-29 16:12:39.494826	2016-09-29 16:12:52.734842	2016-09-29 16:15:06.168577	\N	8	6	12	14	\N
13	Fakeachu	8	2016-09-29 16:25:04.459307	2016-09-29 16:25:33.520292	2016-09-29 16:26:00.574996	2016-09-29 16:25:49.144478	\N	4	6	12	16	\N
14	Dirtcat	8	2016-09-29 16:26:13.16312	\N	\N	\N	\N	11	3	6	8	\N
7	joey	4	2016-09-29 16:04:59.651598	2016-09-29 16:05:26.064003	2016-09-29 16:05:37.719003	2016-09-29 16:05:23.17602	\N	2	6	9	10	\N
1	Shiva	1	2016-09-29 13:17:29.117029	2016-09-29 14:27:26.701335	2016-09-29 14:27:22.181242	2016-09-29 14:27:24.477789	\N	17	0	4	9	-11
5	Rusty	1	2016-09-29 15:11:07.939808	\N	2016-09-29 15:11:47.079742	\N	\N	17	3	8	8	\N
3	Fred	1	2016-09-29 13:46:32.85159	2016-09-29 14:31:41.693785	2016-09-29 16:04:30.44887	2016-09-29 16:04:22.841033	\N	14	6	12	16	-46
12	Falcor	7	2016-09-29 16:21:25.350508	2016-09-29 16:22:18.647839	2016-09-29 16:22:09.53644	2016-09-29 16:22:25.857665	\N	3	6	12	16	\N
15	Atreyu	7	2016-09-29 16:27:17.443533	2016-09-29 16:27:37.375748	2016-09-29 16:27:33.758942	2016-09-29 16:27:47.214498	\N	5	6	12	16	\N
2	Tako	2	2016-09-29 13:18:02.018891	\N	\N	\N	\N	2	3	6	8	\N
\.


--
-- Name: pets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('pets_id_seq', 18, true);


--
-- Data for Name: players; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY players (id, name, email) FROM stdin;
1	Karen	karenfreemansmith@gmail.com
2	Joanna	joanna.saerom.anderson@gmail.com
3	Sheena	sheena@sheena.com
4	Jeremy	fryd21@gmail.com
5	Nhate	hoangnh092185@gmail.com
6	Yusuf	blah@blah.com
7	Elysia	elysia.avery@gmail.com
8	Addison	addison.nishijima@gmail.com
9	Caleb	calebpaul21@gmail.com
10	david	dbethune1970@gmail.com
\.


--
-- Name: players_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('players_id_seq', 10, true);


--
-- Name: pets_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY pets
    ADD CONSTRAINT pets_pkey PRIMARY KEY (id);


--
-- Name: players_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY players
    ADD CONSTRAINT players_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

