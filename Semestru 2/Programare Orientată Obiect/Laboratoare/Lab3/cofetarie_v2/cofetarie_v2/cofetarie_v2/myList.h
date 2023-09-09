#pragma once
#include "domain.h"

/*
	Tip de functie pentru distrugerea unei structuri
*/
typedef void(*DestroyFunction) (void*);

/*
	Tip de functie pentru copierea unui element
*/
typedef void*(*CopyFunction) (void*);

typedef struct {
	void** lista;
	int nrElemente, maxElemente;
	DestroyFunction destroyF;
} ListMP;

/*
	Creeaza o lista vida
*/
ListMP* CreeazaListMP(DestroyFunction DF);

/*
	Distruge o lista de elemente
	pointerCofetarie - pointer la lista de materii prime
*/
void DistrugeListMP(ListMP* pointerCofetarie);

/*
	Adauga un element la o lista
	pointerCofetarie - pointer la lista de materii prime
	mp - elementul de adaugat
*/
void AdaugaMP(ListMP* pointerCofetarie, void* mp);

/*
	Functie de get a unui element din lista
	pointerCofetarie - pointer la lista de materii prime
	index - indexul elementului ce se vrea returnat
*/
void* GetMP(ListMP* pointerCofetarie, int index);

/*
	Functie de actualizare parametri element lista
	pointerCofetarie - pointer la lista de materii prime
	index - pozitia elementului de modificat
	mp - noii parametri ai elementului de pe pozitia index
*/
void* UpdateMP(ListMP* pointerCofetarie, int index, void* mp);

/*
	Functie de verificare necesitate realocare
	si realocare in caz afirmativ
	pointerCofetarie - pointer la lista de materii prime
*/
void Realloc(ListMP* pointerCofetarie);

/*
	Functie de distrugere un element din lista
	pointerCofetarie - pointerul catre lista de materii prime
	index - indexul elementului de sters
*/
void DistrugeUnMP(ListMP* pointerCofetarie, int index);

/*
	Functie de distrugere ultimul element din lista
	pointerCofetarie - pointerul catre lista de materii prime
*/
void* DistrugeLast(ListMP* pointerCofetarie);

/*
	Functie de copiere lista
	Returneaza o lista noua ce contine aceleasi elemente ca cea initiala
*/
ListMP* CopieListMP(ListMP* pointerCofetarie, CopyFunction CF);

/*
	Functie de testare my List
*/
void TestMyList();
