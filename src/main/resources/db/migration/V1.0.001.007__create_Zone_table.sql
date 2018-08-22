CREATE TABLE public."zone" (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	scope_id integer NOT NULL,
	level_id integer NOT NULL,
	price float4 NULL,
	"timestamp" timestamp NULL,
	price_calc float4 NULL,
	price_calc_shift float4 NULL,
	price_calc_order_detection_zone float4 NULL,
	price_stop_loss float4 NULL,
	price_take_profit float4 NULL,
	price_break_even_profit float4 NULL,
	price_trail_profit float4 NULL,
	trade_count integer NULL,
	activated bool NULL DEFAULT FALSE,
	touched bool NULL DEFAULT FALSE,
	CONSTRAINT zone_info_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

-- Permissions
ALTER TABLE public.zone OWNER TO smartbot;
ALTER TABLE public."zone" ADD CONSTRAINT zone_scope_fk FOREIGN KEY (scope_id) REFERENCES public."scope"(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE public."zone" ADD CONSTRAINT zone_zone_level_fk FOREIGN KEY (level_id) REFERENCES public.zone_level(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE public."zone" ALTER COLUMN "price" TYPE numeric(10, 5) USING ("price"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_calc" TYPE numeric(10, 5) USING ("price_calc"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_calc_shift" TYPE numeric(10, 5) USING ("price_calc_shift"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_calc_order_detection_zone" TYPE numeric(10, 5) USING ("price_calc_order_detection_zone"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_stop_loss" TYPE numeric(10, 5) USING ("price_stop_loss"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_take_profit" TYPE numeric(10, 5) USING ("price_take_profit"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_break_even_profit" TYPE numeric(10, 5) USING ("price_break_even_profit"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_trail_profit" TYPE numeric(10, 5) USING ("price_trail_profit"::numeric(10, 5));
