-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;
CREATE TABLE IF NOT EXISTS Company (
                           id uuid primary key DEFAULT uuid_generate_v4 (),
                           name VARCHAR(100),
                           address VARCHAR(100),
                           updated_at TIMESTAMP,
                           created_at TIMESTAMP
);
