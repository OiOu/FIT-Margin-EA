-- Drop table

-- DROP TABLE public.currency

CREATE TABLE public.currency (
	id serial NOT NULL,
	name varchar(255) NULL,
	short_name varchar(255) NULL,
	clearing_code varchar(255) NULL,
	futures_code varchar(255) NULL,
	inverted bool NULL DEFAULT false,
	future_point float4 NULL,
	price_per_contract float4 NULL,
	k int4 NULL,
	zone_level_k int4 NULL DEFAULT 1,
	CONSTRAINT currency_pk PRIMARY KEY (id)
);
CREATE UNIQUE INDEX currency_shortname_idx ON public.currency USING btree (short_name);

ALTER TABLE public.currency OWNER TO smartbot;
COMMENT ON COLUMN public.currency.zone_level_k IS 'default value of zone height will be scaled by K ';
