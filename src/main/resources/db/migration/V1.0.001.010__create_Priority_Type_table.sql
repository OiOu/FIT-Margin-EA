-- Drop table

-- DROP TABLE public.priority_type

CREATE TABLE public.priority_type (
	id serial NOT NULL,
	"type" int4 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT priority_type_pk PRIMARY KEY (id)
);

-- Permissions
ALTER TABLE public.priority_type OWNER TO smartbot;

INSERT INTO public.priority_type ("type", "name", priorityDistance) VALUES(1, 'Buy', 5);
INSERT INTO public.priority_type ("type", "name", priorityDistance) VALUES(-1, 'Sell', 5);
