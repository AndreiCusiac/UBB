use Problema2
go
select * from Artisti
select * from Clasamente
select * from Melodii
select * from PozitieClasament
select * from Tari

-- b)
begin tran
update Artisti set nume_artist='schimbat3' where cod_artist=1
commit tran

set transaction isolation level read committed
--set transaction isolation level repeatable read
begin tran
select * from Artisti
waitfor delay '00:00:03'
select * from Artisti
commit tran

-- c)
create index idx_an on Melodii (an_lansare asc) include (titlu)
select titlu, an_lansare from Melodii order by an_lansare asc
