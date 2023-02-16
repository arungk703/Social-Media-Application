insert into user_details(id, birthdate, name) 
values(1001, current_date(), 'Arun');

insert into user_details(id, birthdate, name) 
values(1002, current_date(), 'Ravi');

insert into user_details(id, birthdate, name) 
values(1003, current_date(), 'Rama');

insert into post(id, description, user_id)
values(2001, 'I am learning REST API', 1001);

insert into post(id, description, user_id)
values(2002, 'I am learning AWS', 1001);

insert into post(id, description, user_id)
values(2003, 'I am learning GCP', 1002);

insert into post(id, description, user_id)
values(2004, 'I am learning DevOps', 1003);