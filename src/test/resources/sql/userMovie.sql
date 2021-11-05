insert into user (id, username, password, role, email, active, created)
values (10, 'Test1Username', 'Test1Password', 'ROLE_USER', 'test1@mail.ru', 1, now());

insert into profile (id, user_id,  created)
values (77, 10, now());

insert into user_movie (id, rating, notes, viewed, user_id, external_movie_id, created, updated)
values (99, 9, 'This is the best film !!!', false , 10, 999, now(), now())