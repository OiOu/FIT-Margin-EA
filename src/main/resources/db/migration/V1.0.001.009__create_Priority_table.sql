CREATE TABLE public.priority (
	id serial NOT NULL,
	"type" int4 NULL,
	"subtype" int4 NULL,
	start_date timestamp NULL,
	currency_id int4 NOT NULL,
	CONSTRAINT priority_currency_fk FOREIGN KEY (currency_id) REFERENCES public.currency(id) ON DELETE CASCADE ON UPDATE CASCADE
)
WITH (
	OIDS=FALSE
) ;
-- Permissions
ALTER TABLE public.priority OWNER TO smartbot;
ALTER TABLE public.priority ADD CONSTRAINT priority_pk PRIMARY KEY (id) ;
ALTER TABLE public.priority ADD CONSTRAINT priority_priority_type_fk FOREIGN KEY (type) REFERENCES public.priority_type(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE public.priority ADD CONSTRAINT priority_priority_subtype_fk FOREIGN KEY (subtype) REFERENCES public.priority_subtype(id) ON DELETE CASCADE ON UPDATE CASCADE;
