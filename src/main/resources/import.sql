insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'New Yourk'); 
insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'Prague'); 
insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'Paris'); 
insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'Tokyo'); 
insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'London');

insert into Flight (id, name, from_id, to_id, dateOfDeparture, seats) values (HIBERNATE_SEQUENCE.nextval, 'prag-ny', 2,1, '2013-07-08 02:00:00.0', 15);