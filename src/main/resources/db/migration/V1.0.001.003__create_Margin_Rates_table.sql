CREATE TABLE public.margin_rates (
	id integer NOT NULL,
	exchange varchar(255) NULL,
	sector varchar(255) NULL,
	"name" varchar(255) NULL,
	filepath varchar(255) NULL,
	clearingcode varchar(255) NULL,
	clearingorg varchar(255) NULL,
	productfamily varchar(255) NULL,
	startperiod varchar(255) NULL,
	endperiod varchar(255) NULL,
	maintenancerate varchar(255) NULL,
	volscanmaintenancerate varchar(255) NULL,
	currency varchar(255) NULL,
	startdate timestamptz NULL,
	enddate timestamptz NULL
)
WITH (
	OIDS=FALSE
) ;
CREATE INDEX margin_rates_clearingcode_idx ON public.margin_rates (clearingcode,startdate,enddate) ;
