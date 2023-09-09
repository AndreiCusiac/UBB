#pragma once
#define _CRT_SECURE_NO_WARNINGS
#include "controller.h"
#include "domain.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "controller.h"


/*
*Aceasta functiee ste defineste meniul principal al aplicatiei
* Nu are paramtri
* Nu returneaza nimic
*/
void creeaza_meniu();

/*
*Aceasta functie este meniul principal al aplicatiei
* Nu are paramtri
* Nu returneaza nimic
*/
void meniu();

/*
*Aceasta functie este adauga un participant in lista
* ls este de tip lista
* Retunreaza lista modificata sau nu, in functie de codul validarii
*/
Lista* adauga(Lista* ls, Lista* undo);


/*
*Aceasta functie este sterge un participant din lista
* ls este de tip lista
* Retunreaza lista modificata sau nu, in functie de codul validarii
*/
Lista* sterge(Lista* ls, Lista* undo);

void afis_undo(Lista* undo);

/*
*Aceasta functie afiseaza intr-un format elegant datele participantilor din lista
* ls este adresa obiectuluide tip lista
* Nu returneaza nimic
*/
void afiseaza_toti(Lista* ls);

/*
*Aceasta functie afiseaza intr-un format elegant datele unui participant din lista
* ls este adresa obiectuluide tip lista
* Nu returneaza nimic
*/
void afiseaza_date(Lista* ls);

/*
*Aceasta functie este actualizeaza un participant din lista
* ls este de tip lista
* Retunreaza lista modificata sau nu, in functie de codul validarii
*/
Lista* actualizare(Lista* ls, Lista* undo);

/*
*Aceasta functie aplica un filtru asupra elementelor dintr-o lista
* ls de tip lista
* Nu returneaza nimic
*/
void aplica_filtru(Lista* ls);

/*
*Aceasta functie sorteaza elementele din lista
* ls de tip lista
* Nu returneaza nimic
*/
void sortare(Lista* ls);

Lista* fa_undo(Lista* undo, Lista* ls);