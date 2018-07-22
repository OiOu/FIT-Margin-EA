CREATE TABLE public.margin_rates (
    id serial not null,
	exchange varchar(255) NULL,
	sector varchar(255) NULL,
	"name" varchar(255) NULL,
	file_path varchar(255) NULL,
	clearing_code varchar(255) NULL,
	clearing_org varchar(255) NULL,
	product_family varchar(255) NULL,
	start_period varchar(255) NULL,
	end_period varchar(255) NULL,
	maintenance_rate float4 NULL,
	vol_scan_maintenance_rate varchar(255) NULL,
	currency_id int4 NOT NULL,
	start_date date NOT NULL DEFAULT date(now()),
	future_point float4 NULL,
	price_per_contract float4 NULL,
	CONSTRAINT margin_rates_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

CREATE INDEX margin_rates_currency_id_idx ON public.margin_rates USING btree (currency_id, start_date) ;

-- Permissions
ALTER TABLE public.margin_rates OWNER TO smartbot;
ALTER TABLE public.margin_rates ADD CONSTRAINT margin_rates_currency_fk FOREIGN KEY (currency_id) REFERENCES public.currency(id) ;
