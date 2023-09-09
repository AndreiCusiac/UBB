CREATE DATABASE Seminar2_222;
GO
USE Seminar2_222;
CREATE TABLE Persoane 
(cod_p INT PRIMARY KEY IDENTITY,
nume VARCHAR(100),
prenume VARCHAR(100),
localitate VARCHAR(90)
);

INSERT INTO Persoane (nume, prenume, localitate) VALUES
('Pop','Mihai', 'Cluj-Napoca'), ('Pop','Oana','Brasov'), ('Pop','Andrei', NULL),
('Popescu','Ionut','Cluj-Napoca'), ('Ion','Maria', 'Sibiu');

SELECT * FROM Persoane;

INSERT INTO Persoane (nume, prenume) VALUES
('Pop','Mihai');

SELECT * FROM Persoane;

UPDATE Persoane SET nume='Pop' WHERE nume='Popescu';

SELECT * FROM Persoane;

DELETE FROM Persoane WHERE localitate IS NULL;

SELECT * FROM Persoane;

SELECT nume FROM Persoane;

SELECT DISTINCT nume FROM Persoane;

SELECT nume, localitate FROM Persoane;

SELECT DISTINCT nume, localitate FROM Persoane;

SELECT * FROM Persoane WHERE nume < 'p';

INSERT INTO Persoane (nume, prenume, localitate) VALUES (23454,'Ion','oras');

SELECT * FROM Persoane WHERE nume < 'p';

INSERT INTO Persoane (nume, prenume, localitate) VALUES ('23454','Ion','oras');

SELECT * FROM Persoane WHERE nume < 'p';
--interogare care contine o coloana cu valoare calculata
SELECT nume, prenume, cod_p, cod_p+1 AS [cod_p++] FROM Persoane;

SELECT * FROM Persoane;

--se afiseaza persoanele care au localitatea egala cu Cluj-Napoca, Sibiu sau Brasov
--varianta cu IN 
SELECT * FROM Persoane WHERE localitate IN ('Cluj-Napoca','Sibiu','Brasov');
--varianta cu OR
SELECT * FROM Persoane WHERE localitate='Cluj-Napoca' OR
localitate='Sibiu' OR localitate='Brasov';

--relatie one to many
CREATE TABLE Categorii
(cod_c INT PRIMARY KEY IDENTITY,
nume VARCHAR(100)
);
CREATE TABLE Produse
(cod_p INT PRIMARY KEY IDENTITY,
denumire VARCHAR(100),
pret FLOAT,
cod_c INT FOREIGN KEY REFERENCES Categorii(cod_c)
);

INSERT INTO Categorii (nume) VALUES ('mancare'),('bautura'),('flori'),('legume');
INSERT INTO Produse (denumire, pret, cod_c) VALUES
('cartofi prajiti',8,1), ('burger',25,1), ('salata greceasca',30,1),
('cola',5,2), ('fanta',5,2), ('apa plata cu lamaie',6,2), ('suc de mere',8,2),
('red bull',5.4,2), ('dovleac',6,4);
INSERT INTO Produse (denumire,pret, cod_c) VALUES ('tricou',50,NULL);
SELECT * FROM Categorii;
SELECT * FROM Produse;

--se vor afisa inregistrarile care au potriviri in celalalt tabel
SELECT * FROM Categorii C INNER JOIN Produse P ON C.cod_c=P.cod_c;
--se vor afisa toate inregistrarile din tabelul din partea stanga, indiferent
--daca au sau nu potriviri in tabelul din partea dreapta 
SELECT * FROM Categorii C LEFT JOIN Produse P ON C.cod_c=P.cod_c;
--se vor afisa toate inregistrarile din tabelul din partea dreapta, indiferent
--daca au sau nu potriviri in tabelul din partea stanga
SELECT * FROM Categorii C RIGHT JOIN Produse P ON C.cod_c=P.cod_c;
--se vor afisa toate inregistrarile din cele doua tabele, indiferent daca au sau nu
--potriviri in celalalt tabel
SELECT * FROM Categorii C FULL JOIN Produse P ON C.cod_c=P.cod_c;

--afisam numarul de produse si pretul mediu pentru fiecare cod_c
SELECT cod_c, COUNT(cod_p) AS [nr produse], AVG(pret) AS [pret mediu pe categorie]
FROM Produse
GROUP BY cod_c;
--afisam numarul de produse si pretul mediu pentru fiecare cod_c, cu conditia ca
--numarul de produse sa fie > 3
SELECT cod_c, COUNT(cod_p) AS [nr produse], AVG(pret) AS [pret mediu pe categorie]
FROM Produse
GROUP BY cod_c
HAVING COUNT(cod_p)>3;

--afisam categoriile care au produse
SELECT nume FROM Categorii WHERE cod_c IN (SELECT cod_c FROM Produse);
--varianta cu JOIN
SELECT DISTINCT C.nume FROM Categorii C INNER JOIN Produse P ON C.cod_c=P.cod_c;
--varianta cu EXISTS 
SELECT C.nume FROM Categorii C WHERE EXISTS(SELECT * FROM Produse P 
WHERE C.cod_c=P.cod_c);

--afisam categoriile care nu au produse
--varianta cu NOT IN
SELECT nume FROM Categorii WHERE cod_c NOT IN (SELECT cod_c FROM Produse
WHERE cod_c IS NOT NULL);
--varianta cu NOT EXISTS 
SELECT C.nume FROM Categorii C WHERE NOT EXISTS(SELECT * FROM Produse P
WHERE C.cod_c=P.cod_c);
--varianta cu EXCEPT
SELECT nume FROM Categorii
EXCEPT
SELECT C.nume FROM Categorii C INNER JOIN Produse P ON C.cod_c=P.cod_c;
--toate valorile cod_c din tabelele Produse si Categorii
SELECT cod_c FROM Categorii
UNION
SELECT cod_c FROM Produse;

