CREATE TABLE public.zone_level (
	id serial NOT NULL,
	"name" varchar(255) NULL,
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

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(1, 0.25, '0.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(2, 0.5, '0.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(3, 0.75, '0.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(4, 1.0, '1.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(5, 1.25, '1.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(6, 1.5, '1.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(7, 1.75, '1.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(8, 2.0, '2.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(9, 2.25, '2.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(10, 2.5, '2.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(11, 2.75, '2.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(12, 3.0, '3.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(13, 3.25, '3.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(14, 3.5, '3.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(15, 3.75, '3.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(16, 4.0, '4.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(17, 4.25, '4.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(18, 4.5, '4.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(19, 4.75, '4.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(20, 5.0, '5.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(21, 5.25, '5.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(22, 5.5, '5.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(23, 5.75, '5.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(24, 6.0, '6.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(25, 6.25, '6.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(26, 6.5, '6.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(27, 6.75, '6.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(28, 7.0, '7.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(29, 7.25, '7.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(30, 7.5, '7.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(31, 7.75, '7.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(32, 8.0, '8.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(33, 8.25, '8.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(34, 8.5, '8.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(35, 8.75, '8.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(36, 9.0, '9.0', false, true);

INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(37, 9.25, '9.25', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(38, 9.5, '9.5', true, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(39, 9.75, '9.75', false, false);
INSERT INTO public.zone_level (id, "name", k, local_priority, global_priority) VALUES(40, 10.0, '10.0', false, true);
