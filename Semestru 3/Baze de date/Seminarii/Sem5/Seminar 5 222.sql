CREATE DATABASE Seminar5_222;
GO
USE Seminar5_222;
CREATE TABLE Persoane
(cod_p INT NOT NULL,
nume VARCHAR(100),
prenume VARCHAR(100),
localitate VARCHAR(100)
);
INSERT  INTO Persoane (cod_p, nume, prenume, localitate) VALUES
(1,'Pop','Ana','Sibiu'),(-8, 'Pop','Mihai','Floresti'),
(20,'Popescu','Ioana','Craiova'), (344, 'Ion','Diana','Brasov');

SELECT * FROM Persoane;
SELECT nume, prenume FROM Persoane WHERE nume='Pop';

GO
CREATE CLUSTERED INDEX IX_Persoane_cod_p_asc ON Persoane 
(cod_p ASC);
GO
SELECT * FROM Persoane;

INSERT INTO Persoane (cod_p, nume, prenume, localitate) VALUES
(-1,'Ion','Anda','Brasov');
SELECT * FROM Persoane;
GO
DROP INDEX IX_Persoane_cod_p_asc ON Persoane;
GO
SELECT * FROM Persoane;
INSERT INTO Persoane (cod_p, nume, prenume, localitate) VALUES
(2,'Ion','Andrada','Arad');
SELECT * FROM Persoane;
GO
CREATE CLUSTERED INDEX IX_Persoane_cod_p_asc ON Persoane 
(cod_p ASC);
GO
SELECT * FROM Persoane;
ALTER TABLE Persoane
ADD CONSTRAINT pk_Persoane PRIMARY KEY (cod_p)
;
DROP TABLE Persoane;
CREATE TABLE Persoane
(cod_p INT PRIMARY KEY IDENTITY,
nume VARCHAR(100),
prenume VARCHAR(100),
localitate VARCHAR(100),
popularitate INT
);
INSERT INTO Persoane (nume, prenume, localitate, popularitate) VALUES
('Pop','Mihai','Oradea',10), ('Pop', 'Adina','Oradea',6),
('Pop','Marcel','Arad',9), ('Ion','Andreea','Cluj-Napoca',3),
('Alb','Alina','Arad',2), ('Ion','Andrei','Floresti',10),
('Popescu','Daniel','Brasov',9);

SELECT * FROM Persoane;
GO
CREATE UNIQUE INDEX IX_Persoane_nume_asc_prenume_asc ON Persoane
(nume ASC, prenume ASC);
GO
SELECT nume, prenume  FROM Persoane WHERE nume='Pop';
SELECT nume, prenume FROM Persoane;
INSERT INTO Persoane (nume, prenume, localitate, popularitate) VALUES
('Pop','Mihai','Floresti',3);

INSERT INTO Persoane (nume, prenume, localitate, popularitate) VALUES
('Pop','Angela','Arad',10), ('Andrei','Ioana','Arad',3),
('Pop','Mihai','Floresti',3);
SELECT * FROM Persoane;
GO
DROP INDEX IX_Persoane_nume_asc_prenume_asc ON Persoane;
GO
CREATE UNIQUE INDEX IX_Persoane_nume_asc_prenume_asc_uq ON Persoane
(nume ASC, prenume ASC) WITH (IGNORE_DUP_KEY=ON);
INSERT INTO Persoane (nume, prenume, localitate, popularitate) VALUES
('Popescu','Ionel','Arad',5), ('Pop','Daniela','Braila',5),
('Pop','Mihai','Oradea',10);
SELECT * FROM Persoane;
INSERT INTO Persoane (nume, prenume, localitate, popularitate) VALUES
('Pop',NULL,'Oradea',10), (NULL,'Andrei','Oradea',10), 
(NULL, NULL, 'Floresti',4);
SELECT * FROM Persoane;
GO
DROP INDEX IX_Persoane_nume_asc_prenume_asc_uq ON Persoane;
GO
INSERT INTO Persoane (nume, prenume, localitate, popularitate) VALUES
('Pop','Mihai','Oradea',10);
GO

CREATE INDEX IX_Persoane_nume_asc_prenume_asc_include_popularitate ON Persoane
(nume ASC, prenume ASC) INCLUDE (popularitate);
GO
SELECT nume, prenume, popularitate FROM Persoane WHERE nume='Popescu';
GO
DROP INDEX IX_Persoane_nume_asc_prenume_asc_include_popularitate ON Persoane;
GO
CREATE INDEX IX_Persoane_nume_asc_prenume_asc ON Persoane
(nume ASC, prenume ASC);
GO
--se foloseste indexul pentru sortare
SELECT nume, prenume FROM Persoane ORDER BY nume ASC, prenume ASC;
SELECT nume, prenume FROM Persoane ORDER BY nume DESC, prenume DESC;
SELECT nume, prenume FROM Persoane ORDER BY nume ASC;
SELECT nume, prenume FROM Persoane ORDER BY nume DESC;
--nu se va folosi indexul pentru sortare
SELECT nume, prenume FROM Persoane ORDER BY nume ASC, prenume DESC;
SELECT nume, prenume FROM Persoane ORDER BY nume DESC, prenume ASC;
SELECT nume, prenume FROM Persoane ORDER BY prenume ASC, nume ASC;
SELECT nume, prenume FROM Persoane ORDER BY prenume DESC, nume DESC;
SELECT nume, prenume FROM Persoane ORDER BY prenume ASC, nume DESC;
SELECT nume, prenume FROM Persoane ORDER BY prenume DESC, nume ASC;
SELECT nume, prenume FROM Persoane ORDER BY prenume ASC;
SELECT nume, prenume FROM Persoane ORDER BY prenume DESC;
--index filtered
GO

CREATE INDEX IX_Persoane_localitate_filtered ON Persoane
(localitate ASC, nume ASC, prenume ASC) 
WHERE localitate IN ('Oradea','Cluj-Napoca','Arad');
--se poate folosi indexul
SELECT nume, prenume, localitate FROM Persoane
WHERE localitate IN ('Oradea','Cluj-Napoca','Arad');
SELECT nume, prenume, localitate FROM Persoane WHERE localitate='Arad';
SELECT nume, prenume, localitate FROM Persoane WHERE localitate IN
('Oradea','Arad');
SELECT nume, prenume, localitate FROM Persoane WHERE localitate='Arad'
AND nume<>'Pop';
--nu se poate indexul filtrat
SELECT nume, prenume, localitate FROM Persoane;
SELECT nume, prenume, localitate FROM Persoane WHERE localitate NOT IN
('Oradea','Cluj-Napoca','Arad');
SELECT nume, prenume, localitate FROM Persoane WHERE localitate<>'Arad';
SELECT nume, prenume, localitate FROM Persoane WHERE localitate
NOT IN ('Arad','Oradea','Braila');
--index pentru delete (ne ajuta sa determinam mai repede daca sunt sau nu
--inregistrari dependente de cea pe care vrem sa o stergem)
CREATE TABLE Categorii
(cod_c INT PRIMARY KEY IDENTITY,
nume VARCHAR(100)
);
CREATE TABLE Produse
(cod_p INT PRIMARY KEY IDENTITY,
nume VARCHAR(100),
cod_c INT FOREIGN KEY REFERENCES Categorii(cod_c)
);
INSERT INTO Categorii (nume) VALUES ('mancare'),('bauturi'),
('haine'), ('pantofi');
INSERT INTO Produse (nume, cod_c) VALUES ('popcorn',1), ('ciocolata',1),
('cola',2), ('sprite',2);
SELECT * FROM Categorii;
SELECT * FROM Produse;
DELETE FROM Categorii WHERE nume='haine';
GO
CREATE INDEX IX_Produse_cod_c_asc ON Produse (cod_c ASC);
GO
DELETE FROM Categorii WHERE nume='pantofi';


