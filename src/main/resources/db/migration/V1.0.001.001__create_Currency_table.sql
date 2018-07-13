CREATE TABLE public.currency (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	short_name varchar(255) NULL,
	clearing_code varchar(255) NULL,
	futures_code varchar(255) NULL,
	inverted bool NULL DEFAULT false,
	CONSTRAINT currency_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;
CREATE UNIQUE INDEX currency_shortname_idx ON public.currency USING btree (short_name) ;

-- Permissions

ALTER TABLE public.currency OWNER TO smartbot;
