-- Drop table

-- DROP TABLE public.priority

CREATE TABLE public.priority (
	id serial NOT NULL,
	"type" int4 NULL,
	start_date timestamp NULL,
	currency_id int4 NOT NULL,
	end_date timestamp NULL,
	subtype int4 NULL,
	CONSTRAINT priority_pk PRIMARY KEY (id),
	CONSTRAINT priority_currency_fk FOREIGN KEY (currency_id) REFERENCES currency(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT priority_priority_subtype_fk FOREIGN KEY (subtype) REFERENCES priority_subtype(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT priority_priority_type_fk FOREIGN KEY (type) REFERENCES priority_type(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Permissions
ALTER TABLE public.priority OWNER TO smartbot;
