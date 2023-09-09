#pragma once
#include "domain.h"
#include "myList.h"

typedef struct {
	ListMP* lista;
} Repository;

/*
	Creeaza o lista vida
*/
Repository* CreeazaRepo();

/*
	Distruge o lista de elemente
	pointerCofetarie - pointer la lista de materii prime
*/
void DistrugeRepo(Repository* r);

/*
	Functie de get a unui element din lista
	pointerCofetarie - pointer la lista de materii prime
	index - indexul elementului ce se vrea returnat
*/
void* GetRepo(Repository* r, int poz);

/*
	Functie de actualizare parametri element lista
	pointerCofetarie - pointer la lista de materii prime
	index - pozitia elementului de modificat
	mp - noii parametri ai elementului de pe pozitia index
*/
void* UpdateRepo(Repository* r, int poz, void* e);

/*
	Adauga un element la o lista
	pointerCofetarie - pointer la lista de materii prime
	mp - elementul de adaugat
*/
void AddRepo(Repository* r, void* e);

/*
	Functie de testare repository
*/
void TestRepository();
