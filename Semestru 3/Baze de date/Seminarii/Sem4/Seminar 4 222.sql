CREATE DATABASE Seminar4_222;
GO
USE Seminar4_222;
CREATE TABLE Mesaje
(cod_m INT PRIMARY KEY IDENTITY,
text_mesaj VARCHAR(500),
expeditor VARCHAR(100),
destinatar VARCHAR(100),
data_ora DATETIME
);
INSERT INTO Mesaje (text_mesaj, expeditor, destinatar, data_ora) VALUES
('ne intalnim pe Piezisa','Stefan','Edi','2021-11-15 20:00'),
('imi dai si mie tema?','Mircea','Stefan','2021-11-15 21:00'),
('hai la cantina','Andrei','Grupa 222','2021-11-16 12:00:00'),
('unavailable content','Codrin','Oana','2021-11-15 20:00:00'),
('unavailable content','Oana','Codrin','2021-11-15 20:05:00');
SELECT * FROM Mesaje;
GO
--functie scalara
CREATE FUNCTION ufCateNumeCareSeTerminaCuA()
RETURNS INT AS
BEGIN
DECLARE @nr INT=0;
SELECT @nr=COUNT(*) FROM (SELECT expeditor FROM Mesaje WHERE expeditor LIKE '%a'
UNION
SELECT destinatar FROM Mesaje WHERE destinatar LIKE '%a') AS A ;
RETURN @nr;
END;
GO
--apelul functiei
PRINT dbo.ufCateNumeCareSeTerminaCuA();
GO
--functie inline table valued
CREATE FUNCTION ufMesajePrimiteDeUtilizator(@destinatar VARCHAR(100))
RETURNS TABLE AS
RETURN SELECT * FROM Mesaje WHERE destinatar=@destinatar;
GO
--apelul functiei
SELECT * FROM dbo.ufMesajePrimiteDeUtilizator('Grupa 222');
GO
--functie multi statement table valued
CREATE FUNCTION ufMesajeleMaiRecenteDecat(@data_si_ora DATETIME)
RETURNS @Mesaje TABLE (text_mesaj VARCHAR(500), expeditor VARCHAR(100), 
destinatar VARCHAR(100), data_si_ora DATETIME) 
AS
BEGIN
INSERT INTO @Mesaje (text_mesaj,expeditor, destinatar, data_si_ora) SELECT
text_mesaj, expeditor, destinatar, data_ora FROM Mesaje WHERE
data_ora>@data_si_ora;
IF(@@ROWCOUNT=0)
	INSERT INTO @Mesaje (text_mesaj) VALUES 
	('nu s-au gasit mesaje atat de recente');
RETURN;
END;
GO
--apelul functiei
SELECT * FROM dbo.ufMesajeleMaiRecenteDecat('2021-12-01 00:12:12');
SELECT * FROM dbo.ufMesajeleMaiRecenteDecat('2021-11-11 00:12:12');
GO
--view
CREATE VIEW vw_Mesaje
AS
SELECT text_mesaj, expeditor, destinatar FROM Mesaje;
GO
--interogarea view-ului
SELECT * FROM vw_Mesaje;
--definitia unui view cu OBJECT_DEFINITION
PRINT OBJECT_DEFINITION(OBJECT_ID('dbo.vw_Mesaje'));
--definitia unui view cu sp_helptext 
EXEC sp_helptext 'dbo.vw_Mesaje';
GO
--trigger pentru insert
CREATE TRIGGER La_inserare_mesaj
ON Mesaje
FOR INSERT
AS
BEGIN
 SELECT expeditor, destinatar FROM inserted;
END;
GO
--declansarea trigger-ului
INSERT INTO Mesaje (text_mesaj,expeditor,destinatar,data_ora) VALUES
('mesaj de test','expeditor de test','destinatar de test','2021-11-15 20:50:00');
--trigger pentru delete
GO
CREATE TRIGGER La_stergere_mesaj
ON Mesaje
INSTEAD OF DELETE
AS
BEGIN
	PRINT 'Nu avem voie sa stergem mesaje';
END;
GO
--declansarea trigger-ului
DELETE FROM Mesaje WHERE expeditor='expeditor de test';
SELECT * FROM Mesaje;