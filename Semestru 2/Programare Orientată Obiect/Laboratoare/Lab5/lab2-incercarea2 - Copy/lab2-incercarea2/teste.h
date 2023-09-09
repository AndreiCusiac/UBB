#pragma once
#include "domain.h"
#include "controller.h"
#include "utils.h"
#include <stdio.h>
#include <stdint.h>
#include <stdbool.h>
#include <assert.h>

/*
*Aceasta functie testeaza creearea unui participant
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_creeaza_participant();

/*
*Aceasta functie testeaza creearea unei liste
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_creeaza_lista();

/*
*Aceasta functie testeaza accesarea si setarea datelor unui participant
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_getter_setter_participant();

/*
*Aceasta functie testeaza adaugarea unui participant in lista
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_adauga_participant();

/*
*Aceasta functie testeaza cautarea unui participant in lista
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_cauta_participant();

/*
*Aceasta functie testeaza stergerea unui participant din lista
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_sterge_participant();

/*
*Aceasta functie testeaza accesarea unui participant din lista
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_get_date_participant();
/*
*Aceasta functie testeaza functia media din utils
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/

void test_media();

/*
*Aceasta functie testeaza functia valideaza din controller
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_valideaza();

/*
*Aceasta functie testeaza citeste_fisier din repository
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_citeste_fisier();

/*
*Aceasta functie testeaza adaugarea,cautarea si stergrea unui participant din lista folosind functiile din controller
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_adauga_cautare_sterge_controller();

/*
*Aceasta functie testeaza actualizarea datelor unui participant din lista
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_actualizare();

/*
*Aceasta functie testeaza aplicarea unui filtru asupra unei liste
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_filtru();

/*
*Aceasta functie testeaza sortarea elementelor unei liste
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_sort();

/*
*Aceasta functie testeaza functionalitatea listei de undo
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void test_undo();

/*
*Aceasta functie testeaza ruleaza toate testele de mai sus
* Nu are paramtri
* Nu returneaza nimic
* AssertionError daca testele pica
*/
void toate_testele(); 

