use Farma;
go

drop procedure if exists suma_money_to_float;
go
drop procedure if exists suma_float_to_money;
go
drop procedure if exists set_new_version;
go
drop procedure if exists go_to_version;
go
drop procedure if exists pozitie_default_farmacist;
go
drop procedure if exists pozitie_no_default_farmicist;
go
drop procedure if exists tabel_fictiv;
go
drop procedure if exists tabel_fictiv_bye;
go
drop procedure if exists adauga_un_camp_util;
go
drop procedure if exists elimina_un_camp_util;
go
drop procedure if exists creare_fk_constraint;
go
drop procedure if exists eliminare_fk_constraint;

go

create procedure suma_money_to_float
as
begin
	alter table [Tichete de salariu]
	alter column suma float;
	print 'Tocmai am trecut suma la float!'
end;

go

create procedure suma_float_to_money 
as
begin
	alter table [Tichete de salariu]
	alter column suma money;
	print 'Tocmai am trecut suma la money!'
end;

go

create procedure pozitie_default_farmacist
as
begin
	alter table Angajați
	add constraint d_poziție default 'Farmacist' for poziție;
	print 'Tocmai am setat pozitia default la Farmacist!'
end;

go
create procedure pozitie_no_default_farmicist 
as
begin
	alter table Angajați
	drop constraint d_poziție;
	print 'Tocmai am eliminat pozitia default de Farmacist!'
end;

go

create procedure tabel_fictiv
as
begin
	drop table if exists un_tabel_inefabil_de_util;
	create table un_tabel_inefabil_de_util
	(
		sinonime_pentru_inefabil nvarchar(50)
	);
	print 'Tocmai am creat un tabel inefabil de util!'
end;

go
create procedure tabel_fictiv_bye 
as
begin
	drop table if exists un_tabel_inefabil_de_util;
	print 'Tocmai am sters acel tabel nu tocmai atat de util, se pare...'
end;

go

create procedure adauga_un_camp_util
as
begin
	alter table	Clienți
	add ultima_achizitie datetime
	print 'Tocmai am creat un camp nou in tabela Clienti!'
end;

go
create procedure elimina_un_camp_util 
as
begin
	alter table	Clienți
	drop column ultima_achizitie
	print 'Tocmai am sters un camp din tabela Clienti!'
end;

go

create procedure creare_fk_constraint
as
begin
	alter table Stocuri 
	add id_distribuitor smallint;
	alter table Stocuri
	add constraint fk_Distribuitor foreign key (id_distribuitor)
	references Distribuitori(id_distribuitor);

	print 'Tocmai am adaugat o constrangere straina in Stocuri!'
end;

go
create procedure eliminare_fk_constraint 
as
begin
	alter table Stocuri
	drop constraint fk_Distribuitor;
	alter table Stocuri 
	drop column id_distribuitor;

	print 'Tocmai am eliminat o constrangere straina in Stocuri!'
end;

go

create procedure set_new_version
	@version tinyint
as
begin
	update Versiuni
	set versiune = @version
end;

go

create procedure go_to_version
	@demanded_version tinyint
as
begin
	declare @current_version tinyint
	set @current_version = (select versiune from Versiuni)

	print concat('Versiunea curenta este: ', @current_version)

	if (@current_version = @demanded_version)
		print 'Nu s-au efectuat modificari!'

	while ( @current_version < @demanded_version )
	begin

		if ( @current_version = 0)
		begin 
			exec suma_money_to_float
		end;

		if ( @current_version = 1)
		begin 
			exec pozitie_default_farmacist
		end;

		if ( @current_version = 2)
		begin 
			exec tabel_fictiv
		end;

		if ( @current_version = 3)
		begin 
			exec adauga_un_camp_util
		end;

		if ( @current_version = 4)
		begin 
			exec creare_fk_constraint
		end;

		set @current_version = @current_version + 1;
		
	end;

	while ( @current_version > @demanded_version )
	begin
		/*print 'Am intrat aici'*/
		if ( @current_version = 1)
		begin 
			exec suma_float_to_money
		end;

		if ( @current_version = 2)
		begin 
			exec pozitie_no_default_farmicist
		end;

		if ( @current_version = 3)
		begin 
			exec tabel_fictiv_bye
		end;

		if ( @current_version = 4)
		begin 
			exec elimina_un_camp_util
		end;

		if ( @current_version = 5)
		begin 
			exec eliminare_fk_constraint
		end;

		set @current_version = @current_version - 1;
		
	end;

	print concat('Versiunea actualizata este: ', @current_version)
	exec set_new_version @current_version	
end;

go

exec go_to_version 0;