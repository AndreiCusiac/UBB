CREATE DATABASE Seminar2222SGBD;
GO
USE Seminar2222SGBD;
CREATE TABLE Producatori
(cod_p INT PRIMARY KEY IDENTITY,
nume VARCHAR(100),
tara VARCHAR(100)
);
CREATE TABLE Laptopuri
(cod_l INT PRIMARY KEY IDENTITY,
model VARCHAR(100),
pret INT,
tip VARCHAR(100),
cod_p INT FOREIGN KEY REFERENCES Producatori(cod_p)
);
INSERT INTO Producatori (nume, tara) VALUES
('Asus','Taiwan'), ('Lenovo','China'), ('HP','US'), ('Dell','US');
INSERT INTO Laptopuri (model, pret, tip, cod_p) VALUES
('ROG',5000,'Gaming',1), ('OMEN',5000,'Gaming',3), 
('ThinkPad',3000,'Office',2), ('IdeaPad 5 Pro',3000,'Gaming',2),
('TUF',4299,'Office',1), ('Inspiron',3600,'Office',4);