-- Drop table

-- DROP TABLE public.currency_rates

CREATE TABLE public.currency_rates (
	id serial NOT NULL,
	high numeric(10,5) NOT NULL,
	low numeric(10,5) NOT NULL,
	"open" numeric(10,5) NOT NULL,
	"close" numeric(10,5) NOT NULL,
	volume int4 NOT NULL,
	"timestamp" timestamp NOT NULL,
	currency_id int4 NOT NULL,
	scope_id int4 NULL,
	point_pips float4 NULL,
	point_price float4 NULL,
	CONSTRAINT currency_rates_currency_fk FOREIGN KEY (currency_id) REFERENCES currency(id),
	CONSTRAINT currency_rates_scope_fk FOREIGN KEY (scope_id) REFERENCES scope(id) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE INDEX currency_rates_currency_id_idx ON public.currency_rates USING btree (currency_id);

ALTER TABLE public.currency_rates OWNER TO smartbot;
ALTER TABLE public.currency_rates ADD CONSTRAINT currency_rates_scope_fk FOREIGN KEY (scope_id) REFERENCES public."scope"(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE public.currency_rates ALTER COLUMN high TYPE numeric(10, 5) USING (high::numeric(10, 5));
ALTER TABLE public.currency_rates ALTER COLUMN low TYPE numeric(10, 5) USING (low::numeric(10, 5));
ALTER TABLE public.currency_rates ALTER COLUMN "open" TYPE numeric(10, 5) USING ("open"::numeric(10, 5));
ALTER TABLE public.currency_rates ALTER COLUMN "close" TYPE numeric(10, 5) USING ("close"::numeric(10, 5));
