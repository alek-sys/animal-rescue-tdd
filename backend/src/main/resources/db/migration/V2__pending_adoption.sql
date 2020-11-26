alter table ANIMALS add column pending_adoption boolean default false;

update ANIMALS
    set pending_adoption = false
    where name = 'Georgie';
