CREATE TABLE public.currency (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	shortname varchar(255) NULL,
	clearingcode varchar(255) NULL,
	pricepercontract float4 NULL,
	isinverted bool NULL DEFAULT false,
	CONSTRAINT currency_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;
CREATE UNIQUE INDEX currency_shortname_idx ON public.currency USING btree (shortname) ;

-- Permissions

ALTER TABLE public.currency OWNER TO smartbot;
