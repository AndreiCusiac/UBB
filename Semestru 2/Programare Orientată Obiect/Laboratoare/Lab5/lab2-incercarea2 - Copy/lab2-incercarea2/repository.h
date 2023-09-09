#pragma once
#define _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_WARNINGS
#include "domain.h"
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>

typedef void* ElemType;

typedef struct {
	ElemType* lista_participanti;
	int* numar_elemente;
	int* memorie_maxima;
}Lista;

typedef void (*DestroyFunction)(ElemType);

/*
* Aceasta functie aloca in memorie spatiu pentru datele unui participant si creeaza un vector dinamic
* Nu are parametri
* Returneaza un vector dinamic
*/
Lista* creeaza_lista_participanti();

/*
* Aceasta functie elibereaza din memorie lista cu datele unor participanti
* list este adresa obiectului lista
* Nu returneaza nimic
*/
void distruge_lista(Lista* list);

/*
*Aceasta functie redimensioneaza capacitea unei liste
* ls este adresa obiectului lista
* mem_max de tip int
* Returneaza o lista cu capacitate mai mare
*/
Lista* realocare_memorie_lista(Lista* ls, int mem_max);

/*
*Aceasta functie adauga un participant la sfarsitul listei
* ls este adresa obiectului lista
* part este adresa obiectului participant
* Returneaza obiectul lista cu participantul adaugat
*/
Lista* adauga_participant(Lista* ls, ElemType part);

/*
*Aceasta functie cauta un participant in lista
* ls este adresa obiectului lista
* nume,prenume de tip string
* Returneaza pozitia la care se afla in lista
*/
int cauta_participant(Lista* ls, char nume[20], char prenume[20]);

/*
*Aceasta functie sterge un participant din lista 
* ls este adresa obiectului lista
* nume,prenume de tip string
* Returneaza obiectul lista cu participantul sters
*/
Lista* sterge_participant(Lista* ls, Participant* part);

/*
*Aceasta functie citeste datele unor participanti din fisier si le pune in lista
* ls este adresa obiectului lista
* nume_fis de tip string
* Returneaza obiectul lista cu participantii adaugati
*/
Lista* citeste_fisier(Lista* ls, char* nume_fis);

/*
* Aceasta functie returneaza un participant din lista care se afla la o pozitie specificata
* l este adresa obiectului lista
* poz de tip int
* Returneaza obiectul participant aflat la pozitia respectiva
*/
Participant* get_participant_adresa_cu_pozitie(Lista* l, int poz);
//uintptr_t get_participant_adresa_cu_pozitie(Lista l, int poz);

/*
* Aceasta functie returneaza numarul de participanti inscrisi
* l este adresa obiectului lista
* Returneaza un int cu semnificatia de mia sus
*/
int get_numar_participanti(Lista* l);

/*
* Aceasta functie returneaza numarul maxim de participanti care pot fi inscrisi in actuala lista
* l este adresa obiectului lista
* Returneaza un int cu semnificatia de mia sus
*/
int get_memorie_maxima(Lista* l);

/*
* Aceasta functie actualizeaza capacitatea maxima a unei liste
* l este adresa obiectului lista
* poz de tip int
* Returneaza ebiectul lista cu capacitatea modificata
*/
Lista* set_memorie_maxima(Lista* ls, int memo_max);

/*
 * Aceasta functie copiaza continutul unei liste intr-o noua lista
 * ls - lista initiala
 * Returneaza lista noua, copiata din cea primita ca parametru
 */
Lista* copiaza_lista(Lista* ls);

/*
* Aceasta functie elibereaza din memorie datele participantilor din lista
* ls este adresa obiectului lista
* Nu returneaza nimic
*/
void distruge_lista_participanti(Lista* ls);

void distruge_lista_3(Lista* ls);

void distruge_lista_2(Lista* ls, DestroyFunction destrF);

void distruge_lista_4(Lista* ls);