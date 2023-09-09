use P52022
go

--- second query

begin tran
print('Am început tranzacția phantom reads.')
waitfor delay '00:00:02'
print('Am așteptat 2 secunde.')
insert into Familii(nume_f) values
('fam noua')
print('Am introdus fam noua.')
commit tran
print('Am încheiat tranzacția.')