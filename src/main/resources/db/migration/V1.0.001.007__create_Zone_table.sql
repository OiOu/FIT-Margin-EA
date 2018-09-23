-- Drop table

-- DROP TABLE public."zone"

CREATE TABLE public."zone" (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	scope_id int4 NOT NULL,
	level_id int4 NOT NULL,
	price numeric(10,5) NULL,
	"timestamp" timestamp NULL,
	price_calc numeric(10,5) NULL,
	price_calc_shift numeric(10,5) NULL,
	trade_count int4 NULL,
	activated bool NULL DEFAULT false,
	price_calc_order_detection_zone numeric(10,5) NULL,
	price_stop_loss numeric(10,5) NULL,
	price_take_profit numeric(10,5) NULL,
	touched bool NULL DEFAULT false,
	price_break_even_profit numeric(10,5) NULL,
	price_trail_profit numeric(10,5) NULL,
	floor int4 NULL,
	price_order numeric(10,5) NULL,
	price_atr numeric(10,5) NULL,
	CONSTRAINT zone_info_pk PRIMARY KEY (id),
	CONSTRAINT zone_scope_fk FOREIGN KEY (scope_id) REFERENCES scope(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT zone_zone_level_fk FOREIGN KEY (level_id) REFERENCES zone_level(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Permissions
ALTER TABLE public.zone OWNER TO smartbot;

COMMENT ON COLUMN public."zone".floor IS 'From what floor is zone';

ALTER TABLE public."zone" ALTER COLUMN "price" TYPE numeric(10, 5) USING ("price"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_calc" TYPE numeric(10, 5) USING ("price_calc"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_calc_shift" TYPE numeric(10, 5) USING ("price_calc_shift"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_calc_order_detection_zone" TYPE numeric(10, 5) USING ("price_calc_order_detection_zone"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_stop_loss" TYPE numeric(10, 5) USING ("price_stop_loss"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_take_profit" TYPE numeric(10, 5) USING ("price_take_profit"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_break_even_profit" TYPE numeric(10, 5) USING ("price_break_even_profit"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_trail_profit" TYPE numeric(10, 5) USING ("price_trail_profit"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_order" TYPE numeric(10, 5) USING ("price_order"::numeric(10, 5));
ALTER TABLE public."zone" ALTER COLUMN "price_atr" TYPE numeric(10, 5) USING ("price_atr"::numeric(10, 5));
