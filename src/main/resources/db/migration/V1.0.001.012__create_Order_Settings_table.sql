CREATE TABLE public.order_settings (
	id serial NOT NULL,
	currency_id int4 NOT NULL,
	sl_size int4 NULL,
	risk_profit_min int4 NULL DEFAULT 300,
	trail int4 NULL DEFAULT 0,
	break_even int4 NULL DEFAULT 0,
	CONSTRAINT order_settings_pk PRIMARY KEY (id),
	CONSTRAINT order_settings_currency_fk FOREIGN KEY (currency_id) REFERENCES currency(id) ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
	OIDS=FALSE
) ;

-- Permissions
ALTER TABLE public.order_settings OWNER TO smartbot;

ALTER TABLE public.order_settings ADD CONSTRAINT order_settings_un UNIQUE (currency_id) ;

INSERT INTO public.order_settings (currency_id, sl_size, risk_profit_min, trail, break_even) VALUES (unnest(ARRAY(SELECT id from currency)), 30, 5, 100, 50) on conflict (currency_id) do nothing;
