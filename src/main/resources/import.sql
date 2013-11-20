SET @ny = HIBERNATE_SEQUENCE.nextval;
insert into Destination (id,name,latitude,longitude) values (@ny,'New Yourk',40.7143528,-74.00597309999999);

SET @prag = HIBERNATE_SEQUENCE.nextval;
insert into Destination (id,name,latitude,longitude) values (@prag,'Prague',50.0755381,14.4378005);


insert into Destination (id,name,latitude,longitude) values (HIBERNATE_SEQUENCE.nextval,'Paris',48.856614,2.3522219);
insert into Destination (id,name,latitude,longitude) values (HIBERNATE_SEQUENCE.nextval,'Tokyo',35.6894875,139.6917064);
insert into Destination (id,name,latitude,longitude) values (HIBERNATE_SEQUENCE.nextval,'London',51.51121389999999,-0.1198244);

SET @flight = HIBERNATE_SEQUENCE.nextval;
insert into Flight (id, name, from_id, to_id, dateOfDeparture, seats, distance, price) values (@flight, 'prag-ny', @prag,@ny, '2013-07-08 02:00:00.0', 15,6572.01806640625,65720.1796875);



insert into Reservation (id, created, password, seats, state, flight_id) values (HIBERNATE_SEQUENCE.nextval, NOW(), 'il1jkjq41wgplcosozi1t25ouq5ruezp', 10, 'PAID', @flight);