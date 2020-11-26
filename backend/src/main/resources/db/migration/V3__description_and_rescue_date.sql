alter table ANIMALS add column description varchar default '', add column rescue_date date;

update ANIMALS
    set description = 'Brody has lots of energy and loves the outdoors.', rescue_date = '2020-01-14'
    where name = 'Brody';

update ANIMALS
    set description = 'She is chubby, lazy, but always polite. She does not need much attention from you, but when she does, there is no way you can resist petting her.', rescue_date = '2019-12-25'
    where name = 'Chocobo';
