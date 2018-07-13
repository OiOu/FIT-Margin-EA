CREATE TABLE public.currency_rates (
	id serial NOT NULL,
	period int4 NOT NULL,
	priority int4 NOT NULL,
	high float4 NOT NULL,
	low float4 NOT NULL,
	"open" float4 NOT NULL,
	"close" float4 NOT NULL,
	volume int4 NOT NULL,
	"timestamp" timestamptz NOT NULL,
	currency_id integer NOT NULL,
	CONSTRAINT currency_rates_currency_fk FOREIGN KEY (currency_id) REFERENCES currency(id)
)
WITH (
	OIDS=FALSE
) ;

CREATE INDEX currency_rates_currency_id_idx ON public.currency_rates (currency_id) ;

ALTER TABLE public.currency_rates OWNER TO smartbot;
