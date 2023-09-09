CREATE DATABASE Seminar4222SGBD;
GO
USE Seminar4222SGBD;
CREATE TABLE Aeroporturi
(cod_a INT PRIMARY KEY IDENTITY,
nume VARCHAR(100),
tara VARCHAR(100)
);
INSERT INTO Aeroporturi (nume, tara) VALUES
('Heathrow','Anglia'),
('George Enescu Bacau','Romania'),
('Otopeni','Romania'),
('Cristiano Ronaldo','Portugalia');
SELECT * FROM Aeroporturi;
EXEC sp_lock;
SELECT * FROM sys.dm_tran_locks;
SELECT * FROM sys.dm_tran_active_transactions;
SELECT DB_NAME(135);
BEGIN TRAN;
SELECT * FROM Aeroporturi;
SELECT * FROM sys.dm_tran_locks;
UPDATE Aeroporturi SET nume='Cristiano Ronaldo Funchal Madeira'
WHERE tara='Portugalia';
SELECT * FROM sys.dm_tran_locks;
SELECT * FROM sys.dm_tran_active_transactions;
COMMIT TRAN;
SELECT * FROM sys.dm_tran_locks;
SELECT * FROM sys.dm_tran_active_transactions;
DBCC LOG (Seminar4222SGBD,2);
CREATE TABLE Filme
(cod_f INT PRIMARY KEY IDENTITY,
titlu VARCHAR(100),
durata INT,
AN INT
);
INSERT INTO Filme VALUES ('John Wick',120,NULL),
('John Wick',NULL,NULL),
('John Wick',NULL,2014);
SELECT * FROM Filme;
MERGE Filme
USING (SELECT MAX(cod_f) AS cod_F, titlu, MAX(durata) AS durata,
MAX(an) AS an FROM Filme GROUP BY titlu) TabelSursa
ON (Filme.cod_f=TabelSursa.cod_f)
WHEN MATCHED THEN UPDATE SET Filme.durata=TabelSursa.durata,
Filme.titlu=TabelSursa.titlu, Filme.an=TabelSursa.an
WHEN NOT MATCHED BY SOURCE THEN DELETE;
SELECT * FROM Filme;