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

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(1, '0.25', 10, 0.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(2, '0.5', 10, 0.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(3, '0.75', 10, 0.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(4, '1.0', 10, 1.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(5, '1.25', 10, 1.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(6, '1.5', 10, 1.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(7, '1.75', 10, 1.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(8, '2.0', 10, 2.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(9, '2.25', 10, 2.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(10, '2.5', 10, 2.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(11, '2.75', 10, 2.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(12, '3.0', 10, 3.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(13, '3.25', 10, 3.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(14, '3.5', 10, 3.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(15, '3.75', 10, 3.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(16, '4.0', 10, 4.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(17, '4.25', 10, 4.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(18, '4.5', 10, 4.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(19, '4.75', 10, 4.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(20, '5.0', 10, 5.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(21, '5.25', 10, 5.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(22, '5.5', 10, 5.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(23, '5.75', 10, 5.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(24, '6.0', 10, 6.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(25, '6.25', 10, 6.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(26, '6.5', 10, 6.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(27, '6.75', 10, 6.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(28, '7.0', 10, 7.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(29, '7.25', 10, 7.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(30, '7.5', 10, 7.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(31, '7.75', 10, 7.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(32, '8.0', 10, 8.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(33, '8.25', 10, 8.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(34, '8.5', 10, 8.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(35, '8.75', 10, 8.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(36, '9.0', 10, 9.0, false, true);

INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(37, '9.25', 10, 9.25, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(38, '9.5', 10, 9.5, true, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(39, '9.75', 10, 9.75, false, false);
INSERT INTO public.zone_level (id, "name", height, k, local_priority, global_priority) VALUES(40, '10.0', 10, 10.0, false, true);
