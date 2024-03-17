delete from sensors;
delete from persons;
insert into sensors(id, purpose, min_value, max_value)
values(10000, 'sensor', 100, 200);
insert into persons(id, email, name, sensor_id)
values(123L, 'vasya@mail.ru', 'Vasya', 10000);
--insert into persons(id, email, name, sensor_id)
--values(124L, 'sara@gmail.com', 'Sara', 10000);