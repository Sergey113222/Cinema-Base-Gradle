truncate table user_movie;
truncate table profile;
delete from user;
ALTER TABLE user_movie ALTER COLUMN id RESTART WITH 1;
ALTER TABLE profile ALTER COLUMN id RESTART WITH 1;
ALTER TABLE user ALTER COLUMN id RESTART WITH 1;
