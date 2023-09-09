CREATE DATABASE Caini;
GO
--USE se foloseste pentru a ne conecta la o anumita baza de date
USE Caini;
/* comentariu
pe mai multe
linii
*/
CREATE TABLE Rase
(cod_r INT PRIMARY KEY IDENTITY, --IDENTITY se foloseste pentru a genera in mod automat valori pentru coloana cod_r
nume VARCHAR(60),
durata_de_viata INT,
greutate FLOAT
);
--adaugarea unei coloane in tabelul Rase
ALTER TABLE Rase
ADD is_vaccinat BIT;
--redenumirea coloanei greutate din tabelul Rase in masa
EXEC sp_rename 'dbo.Rase.greutate','masa','COLUMN';
--schimbarea tipului de date al unei coloane
ALTER TABLE Rase
ALTER COLUMN masa INT;
--stergerea unei coloane din tabelul Rase
ALTER TABLE Rase
DROP COLUMN is_vaccinat;
--stergerea unui tabel
DROP TABLE Rase;
--modificarea numelui bazei de date
ALTER DATABASE Caini
MODIFY Name=Canguri;
--stergerea bazei de date
USE master;
DROP DATABASE Canguri;


