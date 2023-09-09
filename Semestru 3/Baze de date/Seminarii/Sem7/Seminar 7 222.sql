CREATE DATABASE Seminar7_222;
GO
USE Seminar7_222;
CREATE TABLE Produse
(cod_p INT PRIMARY KEY IDENTITY,
nume VARCHAR(100),
pret INT,
producator VARCHAR(100)
);
INSERT INTO Produse (nume, pret, producator) VALUES
('ciocolata Milka',4,'Mondelez'), ('Ferrero rocher',20,'Ferrero'),
('cozonac',15,'Boromir'), ('vin', 20,'Purcari');
GO
CREATE VIEW vw_Produse_preturi WITH SCHEMABINDING
AS
SELECT nume, pret, pret/2 AS [pret redus cu 50%], pret/4 AS [pret redus cu 75%]
FROM dbo.Produse;
GO
CREATE UNIQUE CLUSTERED INDEX IX_vw_Produse_preturi ON vw_Produse_preturi
(nume ASC);
GO
CREATE TABLE Trenuri
(cod_t INT PRIMARY KEY IDENTITY,
denumire VARCHAR(100),
tip VARCHAR(100)
);
INSERT INTO Trenuri (denumire, tip) VALUES
('IR324','TGV'), ('IR365','tren cu aburi'),
('R356','electric'), ('IR344','tren cu motorina');
INSERT INTO Trenuri (denumire, tip) VALUES ('Thomas','tren fericit');
SELECT denumire, tip, detaliere_tip=CASE tip
WHEN 'tren cu aburi' THEN 'tren foarte oldschool'
WHEN 'electric' THEN 'tren prietenos cu mediul'
WHEN 'tren cu motorina' THEN 'tren neprietenos cu mediu'
WHEN 'tren fericit' THEN 'Codrin e fericit'
ELSE '404 Not found'
END FROM Trenuri;

SELECT denumire, tip, categorie=CASE 
WHEN denumire LIKE 'IR%' THEN 'interregio'
WHEN denumire LIKE 'R%' THEN 'regio'
--ELSE 'tren fericit'
END FROM Trenuri;