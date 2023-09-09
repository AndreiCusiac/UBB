#pragma once

/*
 * Functia adauga medicamentul cu atributele date in lista, actualizeaza cantitatea daca exista deja in lista, afiseaza erori altfel
 * l - lista de medicamente
 * cod, stoc, nume, concentratie - char
 * Returneaza 1, daca salvarea a avut loc, 2 daca medicamentul se afla deja in stoc, 0 daca apar erori
 */
int adauga(Lista* l, char cod[50], char stoc[50], char nume[50], char concentratie[50]);

/*
 * Functia modifica numele si concentratia pentru un medicament aflat in stoc
 * l - lista de medicamente
 * cod, nume, concentratie - char
 * Returneaza 1, daca actualizare are loc cu succes, 2 daca medicamentul nu se afla in stoc, 0 daca apar erori
 */
int actualizare(Lista* l, char cod[50], char nume[50], char concentratie[50]);

/*
 * Functia sterge cantitatea unui medicament aflat in stoc
 * l - lista de medicamente
 * cod - char
 * Returneaza 1, daca stergerea are loc cu succes, 2 daca medicamentul nu se afla in stoc, 0 daca apar erori
 */
int stergere(Lista* l, char cod[50]);

/*
 * Functia sorteaza crescator dupa nume medicamentele dintr-o lista
 * l - lista de medicamente
 * afis - lista de medicamente sortata
 * Returneaza 1, daca sortarea are loc, 2 daca lista initiala este vida
 */
int sortare_nume_cresc(Lista* l, Lista* afis);

/*
 * Functia sorteaza descrescator dupa nume medicamentele dintr-o lista
 * l - lista de medicamente
 * afis - lista de medicamente sortata
 * Returneaza 1, daca sortarea are loc, 2 daca lista initiala este vida
 */
int sortare_nume_descresc(Lista* l, Lista* afis);

/*
 * Functia sorteaza crescator dupa stoc medicamentele dintr-o lista
 * l - lista de medicamente
 * afis - lista de medicamente sortata
 * Returneaza 1, daca sortarea are loc, 2 daca lista initiala este vida
 */
int sortare_stoc_cresc(Lista* l, Lista* afis);

/*
 * Functia sorteaza descrescator dupa stoc medicamentele dintr-o lista
 * l - lista de medicamente
 * afis - lista de medicamente sortata
 * Returneaza 1, daca sortarea are loc, 2 daca lista initiala este vida
 */
int sortare_stoc_descresc(Lista* l, Lista* afis);

/*
 * Functia filtreaza acele medicamente dintr-o lista al caror stoc este mai mic decat un numar dat
 * l - lista de medicamente
 * afis - lista de medicamente filtrata
 * stoc - char
 * Returneaza 1, daca filtrarea are loc, 2 daca lista initiala este vida, 0 daca apar erori
 */
int filtr_dupa_stoc(Lista* l, Lista* afis, char stoc[50]);

/*
 * Functia filtreaza acele medicamente dintr-o lista al caror nume incepe cu o litera data
 * l - lista de medicamente
 * afis - lista de medicamente filtrata
 * c - char
 * Returneaza 1, daca filtrarea are loc, 2 daca lista initiala este vida
 */
int filtr_dupa_nume(Lista* l, Lista* afis, char c);

void test_service();
