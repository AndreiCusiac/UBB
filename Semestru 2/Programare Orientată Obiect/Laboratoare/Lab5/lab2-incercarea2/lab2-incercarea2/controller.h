#pragma once

#include "domain.h"
#include "repository.h"
#include "utils.h"
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>

typedef int(*func)(void*);
typedef int(*func_s)(char*, char*);
typedef int(*func_m)(float, float, int);
/*
* Aceasta functie valideaza datele introduse de la tastatura
* ls este adresa obiectului lista
* nume,prenume de tip string
* scor un vector cu 10 elemente de tip int
* Returneaza un cod cu urm semnificare: 1-nume invalid, 2-prenume invalid, 3-note invalide,4- participant existent,0- nicio eroare
*/
int valideaza(Lista* ls, char nume[20], char prenume[20], int scor[]);

/*
*Aceasta functie adauga un participant la sfarsitul listei
* ls este adresa obiectului lista
* nume,prenume de tip string
* scor un vector cu 10 elemente de tip int
* Returneaza obiectul lista cu participantul adaugat
*/
Lista adauga_in_lista(Lista ls, char* nume, char* prenume, int* scor);

/*
*Aceasta functie cauta un participant in lista
* ls este adresa obiectului lista
* nume,prenume de tip string
* Returneaza pozitia la care se afla in lista
*/
int cauta_participant_in_lista(Lista ls, char* nume, char* prenume);

/*
*Aceasta functie sterge un participant din lista de pe o anumita pozitie
* ls este adresa obiectului lista
* poz de tip int
* Returneaza obiectul lista cu participantul sters
*/
Lista sterge_din_lista(Lista ls, int poz);

/*
*Aceasta functie actualizeaza nota unui probleme unui participant din lista
* ls este adresa obiectului lista
* poz,prob,nota de tip int
* Returneaza obiectul lista cu participantul modificat
*/
Lista actualizare_problema(Lista ls, int poz, int prob, int nota);

/*
*Aceasta functie actualizeaza scorul unui participant din lista
* ls este adresa obiectului lista
* poz de tip int
* scor un vector cu 10 elemente de tip int
* Returneaza obiectul lista cu participantul modificat
*/
Lista actualizare_scor(Lista ls, int poz, int* scor);

/*
*Aceasta functie returneaza un participant din lista de pe o anumita pozitie
* ls este adresa obiectului lista
* poz de tip int
* Returneaza obiectul participant de pe pozitia respectiva
*/
Participant* get_date_participant_cu_pozitie(Lista* ls, int poz);

/*
*Aceasta functie aplica un filtru asupra unei liste si returneaza o alta lista ce contie elemente care satisfac acel filtru,
*in acest caz, participantii cu un scor mai mic decat unul dat
* filt,ls - de tip lista
* scor de tip float
* Returneaza o lista cu filtrul aplicat
*/
Lista aplicare_filtru_scor(Lista filt, Lista ls, float scor);

/*
*Aceasta functie aplica un filtru asupra unei liste si returneaza o alta lista ce contie elemente care satisfac acel filtru,
*in acest caz, participantii a caror nume incepe cu o litera data
* filt,ls - de tip lista
* litera de tip char
* Returneaza o lista cu filtrul aplicat
*/
Lista aplicare_filtru_litera(Lista filt, Lista ls, char litera);

/*
*Aceasta functie sorteaza o lista si returneaza o alta lista ce contie elementele sortate,
*in acest caz, soatarea este alfabetica
* sort,ls - de tip lista
* mod de tip int ( 1-crescator, 0- descrescator) 
* Returneaza o lista sortata
*/
Lista sortare_lista_alfabetic(Lista sort, Lista ls, int mod, func_s functie);

/*
*Aceasta functie sorteaza o lista si returneaza o alta lista ce contie elementele sortate,
*in acest caz, soatarea este dupa media notelor problemelor
* sort,ls - de tip lista
* mod de tip int ( 1-crescator, 0- descrescator)
* Returneaza o lista sortata
*/
Lista sortare_lista_medie(Lista sort, Lista ls, int mod, func_m functie);


int compara_string(char* arg1, char* arg2);

int compara_scor(float arg1, float arg2, int mod);