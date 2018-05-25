CREATE TABLE public.margin_rates (
	id int4 NOT NULL,
	exchange varchar(255) NULL,
	sector varchar(255) NULL,
	"name" varchar(255) NULL,
	filepath varchar(255) NULL,
	clearingcode varchar(255) NULL,
	clearingorg varchar(255) NULL,
	productfamily varchar(255) NULL,
	startperiod varchar(255) NULL,
	endperiod varchar(255) NULL,
	maintenancerate float4 NULL,
	volscanmaintenancerate float4 NULL,
	currency_id int4 NOT NULL,
	startdate date NOT NULL DEFAULT date(now())
)
WITH (
	OIDS=FALSE
) ;
CREATE INDEX margin_rates_currency_id_idx ON public.margin_rates USING btree (currency_id, startdate) ;

-- Permissions

ALTER TABLE public.margin_rates OWNER TO smartbot;
