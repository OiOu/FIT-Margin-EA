-- Drop table

-- DROP TABLE public.zone_level

CREATE TABLE public.zone_level (
	id serial NOT NULL,
	"name" varchar(255) NULL,
	height int4 NULL,
	k numeric(4,2) NOT NULL,
	priority_type_id int4 NULL,
	priority_subtype_id int4 NULL,
	priority_distance int4 NULL,
	trade_allowed bool NULL DEFAULT false,
	order_assignment_shift int4 NULL DEFAULT 0,
	"enable" bool NULL DEFAULT true,
	boost bool NULL DEFAULT false,
	dynamic_stop_loss bool NULL DEFAULT false,
	risk_profit_min int4 NULL DEFAULT 3,
	stop_loss_size int4 NULL DEFAULT 30,
	break_even int4 NULL,
	trail int4 NULL,
	take_profit_percent float4 NULL,
	CONSTRAINT zone_level_pk PRIMARY KEY (id)
);

ALTER TABLE public."zone_level" ALTER COLUMN "k" TYPE numeric(2, 4) USING ("k"::numeric(2, 4));

-- Column comments

COMMENT ON COLUMN public.zone_level.height IS 'Height of the zone' ;
COMMENT ON COLUMN public.zone_level.priority_type_id IS 'SELL or BUY' ;
COMMENT ON COLUMN public.zone_level.priority_subtype_id IS 'LOCAL or GLOBAL' ;
COMMENT ON COLUMN public.zone_level.priority_distance IS 'Distance from outer border of zone where priority should be changed' ;
COMMENT ON COLUMN public.zone_level.trade_allowed IS 'Can we open order on this level?' ;
COMMENT ON COLUMN public.zone_level.order_assignment_shift IS 'Shift in Point from main zone border ' ;
COMMENT ON COLUMN public.zone_level."enable" IS 'Do we use this level in process?' ;
COMMENT ON COLUMN public.zone_level.take_profit_percent IS 'What part of order (in percent) we should close on this level' ;
COMMENT ON COLUMN public.zone_level.boost IS 'Allow to open boost order from this zone' ;
COMMENT ON COLUMN public.order_settings.dynamic_stop_loss IS 'Should we use fixed or dynamic SL';

-- NEW VERSION
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('0.5',20,0.50,1,1,2,true,15,true,false,true,3,40,200,250,100)
,('0.75',10,0.75,NULL,NULL,NULL,false,0,false,false,false,3,40,200,250,100)
,('0.25',10,0.25,NULL,NULL,NULL,false,0,false,false,false,3,40,200,250,100)
,('1.0',40,1.00,NULL,NULL,NULL,false,0,true,false,false,3,40,200,250,100)
;
-- OLD VERSION
-- Permissions
/*
ALTER TABLE public.zone_level OWNER TO smartbot;
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('0.25',5,0.25,NULL,NULL,NULL,false,0,false,false,false,3,40,50,100,NULL)
,('0.5',10,0.50,1,1,2,true,5,true,false,true,3,40,NULL,200,100)
,('0.75',5,0.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,NULL)
,('1.0',20,1.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('1.25',5,1.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,NULL)
,('1.5',10,1.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('1.75',5,1.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('2.0',20,2.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('2.25',5,2.25,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('2.5',10,2.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
;
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('2.75',5,2.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('3.0',20,3.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('3.25',5,3.25,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('3.5',10,3.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('3.75',5,3.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('4.0',20,4.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('4.25',5,4.25,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('4.5',10,4.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('4.75',5,4.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('5.0',20,5.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
;
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('5.25',5,5.25,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('5.5',10,5.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('5.75',5,5.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('6.0',20,6.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('6.25',5,6.25,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('6.5',10,6.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('6.75',5,6.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('7.0',20,7.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('7.25',5,7.25,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('7.5',10,7.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
;
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('7.75',5,7.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('8.0',20,8.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('8.25',5,8.25,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('8.5',10,8.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('8.75',5,8.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('9.0',20,9.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('9.25',5,9.25,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('9.5',10,9.50,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
,('9.75',5,9.75,NULL,NULL,NULL,false,0,false,false,false,3,30,50,100,100)
,('10.0',20,10.00,NULL,NULL,NULL,false,0,true,false,true,3,40,NULL,200,100)
;
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('10.25',5,10.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('10.5',10,10.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('10.75',5,10.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('11.0',20,11.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('11.25',5,11.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('11.5',10,11.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('11.75',5,11.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('12.0',20,12.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('12.25',5,12.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('12.5',10,12.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
;
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('12.75',5,12.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('13.0',20,13.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('13.25',5,13.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('13.5',10,13.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('13.75',5,13.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('14.0',20,14.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('14.25',5,14.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('14.5',10,14.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('14.75',5,14.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('15.0',20,15.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
;
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('15.25',5,15.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('15.5',10,15.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('15.75',5,15.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('16.0',20,16.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('16.25',5,16.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('16.5',10,16.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('16.75',5,16.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('17.0',20,17.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('17.25',5,17.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('17.5',10,17.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
;
INSERT INTO zone_level ("name",height,k,priority_type_id,priority_subtype_id,priority_distance,trade_allowed,order_assignment_shift,"enable",boost,dynamic_stop_loss,risk_profit_min,stop_loss_size,break_even,trail,take_profit_percent) VALUES
('17.75',5,17.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('18.0',20,18.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('18.25',5,18.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('18.5',10,18.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('18.75',5,18.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('19.0',20,19.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('19.25',5,19.25,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('19.5',10,19.50,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('19.75',5,19.75,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
,('20.0',20,20.00,NULL,NULL,NULL,false,0,false,false,false,3,30,NULL,NULL,100)
;
*/
