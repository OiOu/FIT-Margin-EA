-- DROP TABLE public.zone_level
CREATE TABLE public.zone_level (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	height int4 NULL, -- Height of the zone
	k float4 NOT NULL,
	priority_type_id int4 NULL, -- SELL or BUY
	priority_subtype_id int4 NULL, -- LOCAL or GLOBAL
	priority_distance int4 NULL, -- Distance from outer border of zone where priority should be changed
	trade_allowed bool NULL DEFAULT false, -- Can we open order on this level?
	order_assignment_shift int4 NULL DEFAULT 0, -- Shift in Point from main zone border
	"enable" bool NULL DEFAULT true, -- Do we use this level in process?
	boost bool NULL DEFAULT false, -- Allow to open boost order from this zone
	CONSTRAINT zone_level_pk PRIMARY KEY (id)
)
WITH (
	OIDS=FALSE
) ;

-- Column comments

COMMENT ON COLUMN public.zone_level.height IS 'Height of the zone' ;
COMMENT ON COLUMN public.zone_level.priority_type_id IS 'SELL or BUY' ;
COMMENT ON COLUMN public.zone_level.priority_subtype_id IS 'LOCAL or GLOBAL' ;
COMMENT ON COLUMN public.zone_level.priority_distance IS 'Distance from outer border of zone where priority should be changed' ;
COMMENT ON COLUMN public.zone_level.trade_allowed IS 'Can we open order on this level?' ;
COMMENT ON COLUMN public.zone_level.order_assignment_shift IS 'Shift in Point from main zone border ' ;
COMMENT ON COLUMN public.zone_level."enable" IS 'Do we use this level in process?' ;
COMMENT ON COLUMN public.zone_level.boost IS 'Allow to open boost order from this zone' ;

-- Permissions
ALTER TABLE public.zone_level OWNER TO smartbot;
-- 1 - Buy; -1 - Sell
-- 1 - Local; 2 - Global
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('0.25', 5, 0.25, null, false, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, priority_distance, is_trade_allowed, "enable") VALUES('0.5', 10, 0.5, 1, 5, true, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('0.75', 5, 0.75, null, false, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, priority_distance, "enable") VALUES('1.0', 20, 1.0, 2, 5, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('1.25', 5, 1.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('1.5', 10, 1.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('1.75', 5, 1.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('2.0', 20, 2.0, null, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('2.25', 5, 2.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('2.5', 10, 2.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('2.75', 5, 2.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('3.0', 20, 3.0, null, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('3.25', 5, 3.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('3.5', 10, 3.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('3.75', 5, 3.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('4.0', 20, 4.0, null, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('4.25', 5, 4.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('4.5', 10, 4.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('4.75', 5, 4.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('5.0', 20, 5.0, null, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('5.25', 5, 5.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('5.5', 10, 5.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('5.75', 5, 5.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('6.0', 20, 6.0, null, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('6.25', 5, 6.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('6.5', 10, 6.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('6.75', 5, 6.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('7.0', 20, 7.0, null, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('7.25', 5, 7.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('7.5', 10, 7.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('7.75', 5, 7.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('8.0', 20, 8.0, null, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('8.25', 5, 8.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('8.5', 10, 8.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('8.75', 5, 8.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('9.0', 20, 9.0, null, true);

INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('9.25', 5, 9.25, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('9.5', 10, 9.5, null, true);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('9.75', 5, 9.75, null, false);
INSERT INTO public.zone_level ("name", height, k, priority_subtype_id, "enable") VALUES('10.0', 20, 10.0, null, true);

