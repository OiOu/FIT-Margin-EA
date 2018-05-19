CREATE SEQUENCE public.currency_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.user_settings_id_seq
  OWNER TO smartcore;

CREATE TABLE public.currency (
	id integer NOT NULL DEFAULT nextval('currency_id_seq'::regclass),
	"name" varchar(255) NULL,
	shortname varchar(255) NULL
)
WITH (
	OIDS=FALSE
) ;

CREATE UNIQUE INDEX currency_shortname_idx ON public.currency (shortname) ;
