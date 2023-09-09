#pragma once

/*
 * Functia cauta pozitia unui medicament intr-o lista in functie de codul sau
 * Da - lista de medicamente
 * cod - int
 * Returneaza pozitia din lista la care se afla medicamentul cu codul cod, -1 altfel
 */
int cauta(Lista* Da, int cod);

/*
 * Functia salveaza un medicament intr-o lista
 * l - lista de medicamente
 * med - medicament
 * Nu returneaza nimic
 */
void save(Lista* l, Medicament med);

/*
 * Functia actualizeaza stocul un medicament dintr-o lista
 * l - lista de medicamente
 * stoc - int
 * poz - int, pozitia medicamentului
 * Nu returneaza nimic
 */
void actualizeaza_stoc(Lista* l, int stoc, int poz);

/*
 * Functia actualizeaza numele si concentratia un medicament dintr-o lista
 * l - lista de medicamente
 * nume - char
 * concentratie - float
 * Nu returneaza nimic
 */
void actualizeaza_medicament(Lista* l, char nume[50], float concetratie, int poz);

/*
 * Functia sterge stocului un medicament dintr-o lista
 * l - lista de medicamente
 * poz - int, pozitia medicamentului
 * Nu returneaza nimic
 */
void sterge_stoc(Lista* l, int poz);


void test_repo();