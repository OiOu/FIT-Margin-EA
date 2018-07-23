CREATE TABLE public."scope" (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	"type" integer NULL,
	currency_id integer NOT NULL,
	timestamp_from timestampt NOT NULL,
	CONSTRAINT scope_zone_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

-- Permissions
ALTER TABLE public."scope" OWNER TO smartbot;
ALTER TABLE public."scope" ADD CONSTRAINT scope_currency_fk FOREIGN KEY (currency_id) REFERENCES public.currency(id) ;
