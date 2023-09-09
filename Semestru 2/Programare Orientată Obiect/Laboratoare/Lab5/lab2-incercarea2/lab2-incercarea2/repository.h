#pragma once
#define _CRT_SECURE_NO_DEPRECATE
#define _CRT_SECURE_NO_WARNINGS
#include "domain.h"
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>

/*
*Aceasta functie redimensioneaza capacitea unei liste
* ls este adresa obiectului lista
* mem_max de tip int
* Returneaza o lista cu capacitate mai mare
*/
Lista realocare_memorie_lista(Lista* ls, int mem_max);

/*
*Aceasta functie adauga un participant la sfarsitul listei
* ls este adresa obiectului lista
* part este adresa obiectului participant
* Returneaza obiectul lista cu participantul adaugat
*/
Lista adauga_participant(Lista* ls, Participant* part);

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
Lista sterge_participant(Lista* ls, Participant* part);

/*
*Aceasta functie citeste datele unor participanti din fisier si le pune in lista
* ls este adresa obiectului lista
* nume_fis de tip string
* Returneaza obiectul lista cu participantii adaugati
*/
Lista citeste_fisier(Lista ls, char* nume_fis);

/*
* Aceasta functie elibereaza din memorie lista cu datele unor participanti
* list este adresa obiectului lista
* Nu returneaza nimic
*/
void distruge_lista(Lista* list);

/*
* Aceasta functie elibereaza din memorie datele participantilor din lista
* ls este adresa obiectului lista
* Nu returneaza nimic
*/
void distruge_lista_participanti(Lista* ls);

