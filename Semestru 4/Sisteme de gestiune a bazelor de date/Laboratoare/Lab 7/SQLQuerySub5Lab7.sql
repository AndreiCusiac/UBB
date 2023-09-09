use P52022
go

--- first query

select * from Familii

set transaction isolation level repeatable read

begin tran
print('Am început tranzacția phantom reads.')
select * from Familii where cod_f > 1
print('Am citit Familii cu cod_f > 1.')
waitfor delay '00:00:07'
print('Am așteptat 7 secunde.')
select * from Familii where cod_f > 1
print('Am citit Familii cu cod_f > 1.')
commit tran
print('Am încheiat tranzacția.')

-- to solve this:

set transaction isolation level serializable

begin tran
print('Am început tranzacția phantom reads.')
select * from Familii where cod_f > 1
print('Am citit Familii cu cod_f > 1.')
waitfor delay '00:00:07'
print('Am așteptat 7 secunde.')
select * from Familii where cod_f > 1
print('Am citit Familii cu cod_f > 1.')
commit tran
print('Am încheiat tranzacția.')


--- index

select * from Castele

create index idx_camere_castele on Castele(nr_camere)

select * from Castele order by nr_camere
go

select * from Castele order by dimensiune
go