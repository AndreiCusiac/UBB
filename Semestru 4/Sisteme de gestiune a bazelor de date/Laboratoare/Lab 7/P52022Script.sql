USE master
GO
IF(EXISTS(SELECT * FROM sys.databases WHERE name='P52022'))
	DROP DATABASE P52022;
GO
CREATE DATABASE P52022;
GO
USE P52022;
GO
CREATE TABLE Regate
(cod_r INT PRIMARY KEY IDENTITY,
nume_r VARCHAR(100),
descriere_r VARCHAR(100)
);
CREATE TABLE Castele 
(cod_c INT PRIMARY KEY IDENTITY,
nume_c VARCHAR(100),
dimensiune VARCHAR(100),
valoare_estimata INT,
nr_camere INT,
cod_r INT FOREIGN KEY REFERENCES Regate(cod_r)
ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE Familii
(cod_f INT PRIMARY KEY IDENTITY,
nume_f VARCHAR(100)
);
CREATE TABLE Persoane
(cod_p INT PRIMARY KEY IDENTITY,
nume_p VARCHAR(100),
cod_f INT FOREIGN KEY REFERENCES Familii(cod_f)
ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE Chirii
(cod_c INT FOREIGN KEY REFERENCES Castele(cod_c)
ON UPDATE CASCADE ON DELETE CASCADE,
cod_p INT FOREIGN KEY REFERENCES Persoane(cod_p)
ON UPDATE CASCADE ON DELETE CASCADE,
suma_chirie INT,
CONSTRAINT pk_Chirii PRIMARY KEY (cod_c,cod_p)
);
--Regate
INSERT INTO Regate (nume_r,descriere_r) VALUES 
('regat1','descriere regat1');
INSERT INTO Regate (nume_r,descriere_r) VALUES 
('regat2','descriere regat2');
INSERT INTO Regate (nume_r,descriere_r) VALUES 
('regat3','descriere regat3');
--Castele regat1
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel1 regat1','mare',30000,400,1);
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel2 regat1','mic',4000,40,1);
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel3 regat1','mediu',12000,200,1);
--Castele regat2
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel4 regat2','mic',5000,60,2);
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel5 regat2','mare',20000,170,2);
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel6 regat2','mediu',14000,110,2);
--Castele regat3
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel7 regat3','mare',30000,220,3);
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel8 regat3','mic',4600,30,3);
INSERT INTO Castele (nume_c, dimensiune,valoare_estimata,nr_camere,
cod_r) VALUES ('castel9 regat3','mediu',9800,77,3);
--Familii
INSERT INTO Familii (nume_f) VALUES ('familie1');
INSERT INTO Familii (nume_f) VALUES ('familie2');
INSERT INTO Familii (nume_f) VALUES ('familie3');
--Persoane
INSERT INTO Persoane (nume_p, cod_f) VALUES ('persoana1',1);
INSERT INTO Persoane (nume_p, cod_f) VALUES ('persoana2',2);
INSERT INTO Persoane (nume_p, cod_f) VALUES ('persoana3',3);
--Chirii
INSERT INTO Chirii (cod_c,cod_p,suma_chirie) VALUES (1,1,1000);
INSERT INTO Chirii (cod_c,cod_p,suma_chirie) VALUES (2,2,1400);
INSERT INTO Chirii (cod_c,cod_p,suma_chirie) VALUES (4,3,800);