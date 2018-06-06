CREATE TABLE public.scope_zone (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	currency_id integer NOT NULL,
	status integer NULL,
	CONSTRAINT scope_zone_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

-- Permissions
ALTER TABLE public.scope_zone OWNER TO smartbot;
