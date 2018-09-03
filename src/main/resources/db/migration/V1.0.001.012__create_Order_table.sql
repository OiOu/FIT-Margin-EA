-- Drop table

-- DROP TABLE public."order"

CREATE TABLE public."order" (
	id serial NOT NULL,
	"name" varchar NOT NULL,
	"type" int4 NOT NULL,
	subtype int4 NOT NULL,
	currency_id int4 NOT NULL,
	level_id int4 NOT NULL,
	open_timestamp timestamp NOT NULL,
	price numeric(10,5) NOT NULL,
	price_stop_loss numeric(10,5) NOT NULL,
	price_take_profit numeric(10,5) NOT NULL,
	close_reason int4 NULL,
	price_break_even_profit numeric(10,5) NULL,
	price_trail_profit numeric(10,5) NULL,
	break_even_activated bool NULL DEFAULT false,
	ticket int4 NULL,
	points int4 NULL,
	activated bool NULL DEFAULT false,
	close_timestamp timestamp NULL,
	trail_stop_activated bool NULL DEFAULT false,
	CONSTRAINT order_pk PRIMARY KEY (id),
	CONSTRAINT order_currency_fk FOREIGN KEY (currency_id) REFERENCES currency(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT order_zone_level_fk FOREIGN KEY (level_id) REFERENCES zone_level(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Permissions
ALTER TABLE public."order" OWNER TO smartbot;

ALTER TABLE public."order" ALTER COLUMN "price" TYPE numeric(10, 5) USING ("price"::numeric(10, 5));
ALTER TABLE public."order" ALTER COLUMN "price_stop_loss" TYPE numeric(10, 5) USING ("price_stop_loss"::numeric(10, 5));
ALTER TABLE public."order" ALTER COLUMN "price_take_profit" TYPE numeric(10, 5) USING ("price_take_profit"::numeric(10, 5));
ALTER TABLE public."order" ALTER COLUMN "price_break_even_profit" TYPE numeric(10, 5) USING ("price_break_even_profit"::numeric(10, 5));
ALTER TABLE public."order" ALTER COLUMN "price_trail_profit" TYPE numeric(10, 5) USING ("price_trail_profit"::numeric(10, 5));

COMMENT ON COLUMN public."order".break_even_activated IS 'Shows if order was moved to breakeven';
COMMENT ON COLUMN public."order".ticket IS 'Ticked number of oper order';
COMMENT ON COLUMN public."order".points IS 'Amount of points that was get by order. Can be negative for orders that were closed by SL';
COMMENT ON COLUMN public."order".activated IS 'Activate order (shows if order was really open)';
COMMENT ON COLUMN public."order".trail_stop_activated IS 'Trail was triggered';
