CREATE TABLE public.priority (
	id serial NOT NULL,
	"type" int4 NULL,
	"subtype" int4 NULL,
	start_date timestamp NULL,
	end_date timestamp NULL,
	currency_id int4 NOT NULL,
	CONSTRAINT priority_currency_fk FOREIGN KEY (currency_id) REFERENCES public.currency(id) ON DELETE CASCADE ON UPDATE CASCADE
)
WITH (
	OIDS=FALSE
) ;
-- Permissions
ALTER TABLE public.priority OWNER TO smartbot;
