insert into user (id, username, password, role, email, active, created)
values (100, 'TestUsername', 'TestPassword', 'ROLE_USER', 'test@mail.ru', 1, now());

insert into profile (id, user_id,  created)
values (1, 100, now());
