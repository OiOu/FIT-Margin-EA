CREATE TABLE public.priority_type (
	id serial NOT NULL,
	"type" int4 NOT  NULL,
	"name" varchar(255) NOT  NULL,
	distance int4 default 5
)
WITH (
	OIDS=FALSE
) ;
-- Permissions
ALTER TABLE public.priority_type OWNER TO smartbot;
ALTER TABLE public.priority_type ADD CONSTRAINT priority_type_pk PRIMARY KEY (id) ;


INSERT INTO public.priority_type ("type", "name", distance) VALUES(1, 'Buy', 5);
INSERT INTO public.priority_type ("type", "name", distance) VALUES(-1, 'Sell', 5);
