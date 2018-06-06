CREATE TABLE public.zone_info (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	scope_id integer NOT NULL,
	level_id integer NOT NULL,
	price float4 NULL,
	"timestamp" timestamptz NULL,
	price_calc float4 NULL,
	price_calc_shift float4 NULL,
	trade_count integer NULL,
	activated bool NULL,
	CONSTRAINT zone_info_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

-- Permissions
ALTER TABLE public.zone_info OWNER TO smartbot;
