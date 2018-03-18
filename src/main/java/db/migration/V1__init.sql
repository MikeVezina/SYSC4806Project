--
-- Name: product; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE product (
    id bigint NOT NULL,
    average_rating integer NOT NULL,
    category integer,
    number_of_ratings integer NOT NULL,
    rating_total integer NOT NULL,
    url character varying(255) NOT NULL
);


ALTER TABLE product OWNER TO admin;

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE product_id_seq OWNER TO admin;

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE product_id_seq OWNED BY product.id;


--
-- Name: review; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE review (
    id bigint NOT NULL,
    rating integer NOT NULL,
    author_id bigint,
    product_id bigint
);


ALTER TABLE review OWNER TO admin;

--
-- Name: review_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE review_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE review_id_seq OWNER TO admin;

--
-- Name: review_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE review_id_seq OWNED BY review.id;

--
-- Name: user_entity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE user_entity (
    id bigint NOT NULL,
    password character varying(255),
    username character varying(32)
);


ALTER TABLE user_entity OWNER TO admin;

--
-- Name: user_entity_id_seq; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE user_entity_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_entity_id_seq OWNER TO admin;

--
-- Name: user_entity_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: admin
--

ALTER SEQUENCE user_entity_id_seq OWNED BY user_entity.id;


--
-- Name: user_relations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE user_relations (
    following_id bigint NOT NULL,
    follower_id bigint NOT NULL
);


ALTER TABLE user_relations OWNER TO admin;

--
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY review ALTER COLUMN id SET DEFAULT nextval('review_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: admin
--

ALTER TABLE ONLY user_entity ALTER COLUMN id SET DEFAULT nextval('user_entity_id_seq'::regclass);
