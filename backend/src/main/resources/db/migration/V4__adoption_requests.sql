create table ADOPTION_REQUESTS (
  id serial primary key,
  animal bigint not null,
  adopter_name varchar(255) not null,
  email varchar(255) not null,
  notes varchar(1000)
);