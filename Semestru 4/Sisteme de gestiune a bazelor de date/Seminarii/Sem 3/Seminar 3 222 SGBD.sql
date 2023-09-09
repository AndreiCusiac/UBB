CREATE DATABASE Seminar3222SGBD;
GO
USE Seminar3222SGBD;
CREATE TABLE Seriale
(cod_s INT PRIMARY KEY IDENTITY,
nume VARCHAR(100),
nr_sezoane INT,
producator VARCHAR(100)
);
INSERT INTO Seriale (nume, nr_sezoane, producator) VALUES
('Fiica Ambasadorului',4,'ceva turc (nu ne mai amintim numele)'),
('Euphoria',2,'Zendaya'),
('Friends',10,'Bill');
--tranzactie autocommit
SELECT * FROM Seriale;
--tranzactii implicite
SET IMPLICIT_TRANSACTIONS ON;
SELECT * FROM Seriale;
SELECT @@TRANCOUNT;
INSERT INTO Seriale (nume, nr_sezoane, producator) VALUES
('True Blood',7,'HBO');
SELECT * FROM Seriale;
ROLLBACK TRAN;
SELECT @@TRANCOUNT;
SELECT * FROM Seriale;
SELECT @@TRANCOUNT;
INSERT INTO Seriale (nume, nr_sezoane, producator) VALUES
('Sherlock',4,'John Silver');
SELECT * FROM Seriale;
COMMIT TRAN;
SELECT @@TRANCOUNT;
SET IMPLICIT_TRANSACTIONS OFF;
SELECT * FROM Seriale;
SELECT @@TRANCOUNT;
--tranzactii explicite
BEGIN TRAN
SELECT @@TRANCOUNT;
SELECT * FROM Seriale;
INSERT INTO Seriale (nume,nr_sezoane, producator) VALUES 
('Serial no name',3,'producator no name');
SELECT @@TRANCOUNT;
COMMIT TRAN;
SELECT @@TRANCOUNT;
SELECT * FROM Seriale;
--imbricarea tranzactiilor
BEGIN TRAN;
SELECT * FROM Seriale;
SELECT @@TRANCOUNT;
BEGIN TRAN;
SELECT @@TRANCOUNT;
INSERT INTO Seriale (nume, nr_sezoane, producator) VALUES
('serialul care nu ramane',0,'nu avem');
SELECT * FROM Seriale;
COMMIT TRAN;
SELECT @@TRANCOUNT;
SELECT * FROM Seriale;
ROLLBACK TRAN;
SELECT @@TRANCOUNT;
SELECT * FROM Seriale;