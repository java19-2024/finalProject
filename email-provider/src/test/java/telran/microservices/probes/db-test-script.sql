delete from persons;
delete from sensors;
insert into sensors(id, purpose, min_value, max_value)
values(100000, 'oxygen_sensor', 100, 200);
insert into persons(id, email, name, sensor_id)
values (123, 'vasya@mail.ru', 'Vasya', 100000),
(124, 'sara@gmail.com', 'Sara', 100000);