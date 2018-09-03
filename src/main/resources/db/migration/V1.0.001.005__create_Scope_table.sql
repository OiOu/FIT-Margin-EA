-- Drop table

-- DROP TABLE public."scope"

CREATE TABLE public."scope" (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	"type" int4 NULL,
	currency_id int4 NOT NULL,
	timestamp_from timestamp NOT NULL,
	timestamp_to timestamp NULL,
	CONSTRAINT scope_zone_pk PRIMARY KEY (id),
	CONSTRAINT scope_currency_fk FOREIGN KEY (currency_id) REFERENCES currency(id)
);

-- Permissions
ALTER TABLE public."scope" OWNER TO smartbot;
ALTER TABLE public."scope" ADD CONSTRAINT scope_currency_fk FOREIGN KEY (currency_id) REFERENCES public.currency(id) ;
