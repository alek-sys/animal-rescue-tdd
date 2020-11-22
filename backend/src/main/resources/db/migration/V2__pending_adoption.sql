alter table ANIMALS add column (pending_adoption bit default 0);

update ANIMALS
    set pending_adoption = 1
    where name = 'Georgie';
