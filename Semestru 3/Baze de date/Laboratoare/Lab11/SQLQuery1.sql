use Farma
go

create or alter procedure insert_francize(@NoRows int)
as
begin
	declare @i int
	while @i < @NoRows begin
		insert into Francize values (@i, concat('Localitate', @i), concat('Adresa', @i))
		set @i = @i + 1
	end

end
go

create or alter procedure insert_distribuitori(@NoRows int)
as
begin
	declare @i int
	while @i < @NoRows begin
		insert into Distribuitori values (@i, concat('Nume', @i))
		set @i = @i + 1
	end

end
go

create or alter procedure insert_contracte_distribuitori(@NoRows int)
as
begin
	declare @i int
	while @i < @NoRows begin
		insert into [Contracte distribuitori] values (@i, @i, '2021-12-31', '1000', 'aprovizionare')
		set @i = @i + 1
	end

end
go

create or alter procedure insert_stocuri(@NoRows int)
as
begin
	declare @i int
	while @i < @NoRows begin
		insert into Stocuri values (@i, @i, '10')
		set @i = @i + 1
	end

end
go

create or alter procedure insert_medicamente(@NoRows int)
as
begin
	declare @i int
	while @i < @NoRows begin
		insert into Medicamente values (@i, concat('Nume', @i), concat('Producător', @i), '2021-12-31', '20')
		set @i = @i + 1
	end

end
go

create or alter procedure insert_rețete(@NoRows int)
as
begin
	declare @i int
	while @i < @NoRows begin
		insert into Rețete values (@i, @i, 2)
		set @i = @i + 1
	end

end
go

create or alter procedure insert_clienți(@NoRows int)
as
begin
	declare @i int
	while @i < @NoRows begin
		insert into Clienți values (@i, concat('Nume', @i), concat('Prenume', @i), '50')
		set @i = @i + 1
	end

end
go

create or alter procedure Lab11
as
begin

	declare @TestID int, @TestRunID int, @TableID int, @NoOfRows int, @ViewID int
	declare @NameTest nvarchar(50), @NameTable nvarchar(50), @NameView nvarchar(50)
	declare @StartTime datetime

	declare cursor_teste cursor for select TestID, Name from Tests
	open cursor_teste

	fetch next from cursor_teste into @TestID, @NameTest

	while @@FETCH_STATUS = 0 begin
		
		--delete-uri

		declare cursor_tabele_din_teste cursor scroll for 
		select T.TableID, Name, NoOfRows
		from Tables T inner join TestTables TT on T.TableID = TT.TableID
		where TestID = @TestID
		order by Position

		open cursor_tabele_din_teste

		fetch next from cursor_tabele_din_teste into @TableID, @NameTable, @NoOfRows
		
		while @@FETCH_STATUS = 0 begin
			
			exec('delete from ' + @NameTable)

			fetch next from cursor_tabele_din_teste into @TableID, @NameTable, @NoOfRows
		end


		--insert-uri

		insert into TestRuns values(@NameTest, GETDATE(), null)
		set @TestRunID = @@IDENTITY

		fetch prior from cursor_tabele_din_teste into @TableID, @NameTable, @NoOfRows
		
		while @@FETCH_STATUS = 0 begin
			
			set @StartTime = getdate()

			if (@NameTable = 'Francize')
			begin
				exec insert_francize @NoOfRows
			end

			if (@NameTable = 'Distribuitori')
			begin
				exec insert_distribuitori @NoOfRows
			end

			if (@NameTable = 'Contracte distribuitori')
			begin
				exec insert_contracte_distribuitori @NoOfRows
			end

			if (@NameTable = 'Stocuri')
			begin
				exec insert_stocuri @NoOfRows
			end

			if (@NameTable = 'Medicamente')
			begin
				exec insert_medicamente @NoOfRows
			end

			if (@NameTable = 'Rețete')
			begin
				exec insert_rețete @NoOfRows
			end

			if (@NameTable = 'Clienți')
			begin
				exec insert_clienți @NoOfRows
			end

			insert into TestRunTables values (@TestRunID, @TableID, @StartTime, GETDATE())

			fetch prior from cursor_tabele_din_teste into @TableID, @NameTable, @NoOfRows
		end
		
		close cursor_tabele_din_teste
		deallocate cursor_tabele_din_teste
		

		

		--print @TestID
		--print @NameTest

		--view-uri

		declare cursor_views cursor scroll for 
		select V.ViewID, Name
		from Views V inner join TestViews VV on V.ViewID = VV.ViewID
		where TestID = @TestID
		open cursor_views

		fetch next from cursor_views into @ViewID, @NameView
		
		while @@FETCH_STATUS = 0 begin 
			set @StartTime = GETDATE()

			exec('select * from ' + @NameView)

			insert into TestRunViews values (@TestRunID, @ViewID, @StartTime, GETDATE())

			fetch next from cursor_views into @ViewID, @NameView
		end

		close cursor_views
		deallocate cursor_views


		update TestRuns set EndAt = GETDATE() where TestRunID = @TestRunID
		fetch next from cursor_teste into @TestID, @NameTest

	end	

	close cursor_teste
	deallocate cursor_teste

end

--exec Lab11