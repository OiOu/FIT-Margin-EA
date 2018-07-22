CREATE TABLE public.zone_level (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	height int4 NULL,
	k float4 NOT NULL,
	local_priority bool NULL,
	global_priority bool NULL,
	CONSTRAINT zone_level_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

-- Permissions
ALTER TABLE public.zone_level OWNER TO smartbot;

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('0.25', 5, 0.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('0.5', 10, 0.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('0.75', 5, 0.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('1.0', 20, 1.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('1.25', 5, 1.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('1.5', 10, 1.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('1.75', 5, 1.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('2.0', 20, 2.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('2.25', 5, 2.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('2.5', 10, 2.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('2.75', 5, 2.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('3.0', 20, 3.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('3.25', 5, 3.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('3.5', 10, 3.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('3.75', 5, 3.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('4.0', 20, 4.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('4.25', 5, 4.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('4.5', 10, 4.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('4.75', 5, 4.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('5.0', 20, 5.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('5.25', 5, 5.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('5.5', 10, 5.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('5.75', 5, 5.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('6.0', 20, 6.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('6.25', 5, 6.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('6.5', 10, 6.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('6.75', 5, 6.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('7.0', 20, 7.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('7.25', 5, 7.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('7.5', 10, 7.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('7.75', 5, 7.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('8.0', 20, 8.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('8.25', 5, 8.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('8.5', 10, 8.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('8.75', 5, 8.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('9.0', 20, 9.0, false, true);

INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('9.25', 5, 9.25, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('9.5', 10, 9.5, true, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('9.75', 5, 9.75, false, false);
INSERT INTO public.zone_level ("name", height, k, local_priority, global_priority) VALUES('10.0', 20, 10.0, false, true);
