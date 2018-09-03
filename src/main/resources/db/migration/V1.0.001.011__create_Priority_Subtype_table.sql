-- Drop table

-- DROP TABLE public.priority_subtype

CREATE TABLE public.priority_subtype (
	id serial NOT NULL,
	subtype int4 NOT NULL,
	"name" varchar(255) NOT NULL,
	CONSTRAINT priority_subtype_pk PRIMARY KEY (id)
);

-- Permissions
ALTER TABLE public.priority_subtype OWNER TO smartbot;

INSERT INTO public.priority_subtype ("subtype", "name", priorityDistance) VALUES(1, 'Local', 5);
INSERT INTO public.priority_subtype ("subtype", "name", priorityDistance) VALUES(2, 'Global', 5);
