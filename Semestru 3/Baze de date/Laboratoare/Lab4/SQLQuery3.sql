GO 
USE Farma

/* clienți seniori*/
select * from Clienți where vârstă > 62

/* pozitii existente in cadrul angajaților */
select distinct poziție from Angajați 

/* anii cand au fost gestionate tichete de salariu */
select distinct an from [Tichete de salariu]

/* numele si cantitatea de medicamente luate de clienti cu varsta sub 55 de ani*/
select M.nume, R.cantitate, C.id_client from Clienți C 
inner join Rețete R on C.id_client = R.id_client
inner join Medicamente M on R.id_medicament = M.id_medicament
where C.vârstă < 55

/* stocu medicamentelor expirate din toate filiale */
select M.nume, M.dată_expirare, S.cantitate, F.localitate from Francize F
inner join Stocuri S on F.id_franciză = S.id_franciză
inner join Medicamente M on S.id_medicament = M.id_medicament
where M.dată_expirare < GETDATE()

/* filiale care au medicamente in cantitate mai mica decat 10 */
select M.nume, S.cantitate, M.producător, F.localitate, F.adresă from Francize F
inner join Stocuri S on F.id_franciză = S.id_franciză
inner join Medicamente M on S.id_medicament = M.id_medicament
where S.cantitate < 10

/* distribuitori cu contracte mai mari de 1000, cu localitatile filialelor aferente */
select D.nume, C.sumă, F.localitate from Distribuitori D
inner join [Contracte distribuitori] C on D.id_distribuitor = C.id_distribuitor
inner join Francize F on C.id_franciză = F.id_franciză
where C.sumă > 1000

/* distribuitori cu contracte trecute de termen si filialele corespunzatoare */
select D.nume, C.expirare_contract, F.id_franciză from Distribuitori D
inner join [Contracte distribuitori] C on D.id_distribuitor = C.id_distribuitor
inner join Francize F on C.id_franciză = F.id_franciză
where C.expirare_contract < GETDATE()

/* tipurile contractelor, firma contractoare si adresele lor */
select D.nume, C.tip, F.adresă from Distribuitori D
inner join [Contracte distribuitori] C on D.id_distribuitor = C.id_distribuitor
inner join Francize F on C.id_franciză = F.id_franciză
order by C.tip

/* total retete si suma achitata de catre un anumit client */
select C.nume, C.prenume, M.nume, R.cantitate, M.preț, R.cantitate*M.preț as [total] from Clienți C 
inner join Rețete R on C.id_client = R.id_client
inner join Medicamente M on R.id_medicament = M.id_medicament
where C.id_client = 1
order by C.nume, C.prenume, total

/* distribuitorii cu mai mult de 3 contracte */
select C.id_distribuitor, COUNT(C.id_franciză) as [Contracte] from [Contracte distribuitori] C 
group by C.id_distribuitor
having COUNT(C.id_franciză) >= 3

/* angajatii cu numarul tichetelor de salarii aferente lor */
select A.nume, COUNT(T.suma) as NrTichete from [Tichete de salariu] T
inner join Angajați A on T.id_angajat = A.id_angajat
group by A.nume

/* filialele cu mai putin de 10 angajati */
select F.localitate, COUNT(A.id_franciză) as NrAngajați from Angajați A
inner join Francize F on A.id_franciză = F.id_franciză
group by F.localitate
having COUNT(A.id_franciză) < 10