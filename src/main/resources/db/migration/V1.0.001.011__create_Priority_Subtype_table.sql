CREATE TABLE public.priority_subtype (
	id serial NOT NULL,
	"subtype" int4 NOT NULL,
	"name" varchar(255) NOT NULL,
	priorityDistance int4 default 5
)
WITH (
	OIDS=FALSE
) ;
-- Permissions
ALTER TABLE public.priority_subtype OWNER TO smartbot;
ALTER TABLE public.priority_subtype ADD CONSTRAINT priority_subtype_pk PRIMARY KEY (id) ;

INSERT INTO public.priority_subtype ("subtype", "name", priorityDistance) VALUES(1, 'Local', 5);
INSERT INTO public.priority_subtype ("subtype", "name", priorityDistance) VALUES(2, 'Global', 5);
