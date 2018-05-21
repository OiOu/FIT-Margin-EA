CREATE SEQUENCE public.currency_rates_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

CREATE TABLE public.currency_rates (
	id integer NOT NULL DEFAULT nextval('currency_rates_id_seq'::regclass),
	currency int4 NOT NULL,
	bid float4 NOT NULL,
	ask float4 NOT NULL,
	"timestamp" timestamptz NOT NULL,
	CONSTRAINT currency_rates_currency_fk FOREIGN KEY (currency) REFERENCES currency(id)
)
WITH (
	OIDS=FALSE
) ;

CREATE INDEX currency_rates_currency_idx ON public.currency_rates USING btree (currency) ;

ALTER TABLE public.currency_rates OWNER TO smartbot;
