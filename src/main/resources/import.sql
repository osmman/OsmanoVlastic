insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'New Yourk'); 
insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'Prague'); 
insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'Paris'); 
insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'Tokyo'); 
insert into Destination (id,name) values (HIBERNATE_SEQUENCE.nextval,'London');

insert into Flight (id, name, from_id, to_id, seats) values (HIBERNATE_SEQUENCE.nextval, 'prag-ny', 2,1, 15);