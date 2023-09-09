#pragma once
#ifndef SORT_H_
#define SORT_H_

#include "repository.h"
#include "myList.h"

/*
	Tipul functie de comparare pentru 2 elemente
	mp1, mp2 - parametrii ce se compara
	Functia returneaza 0 daca mp1 == mp2, 1 daca mp1 > mp2, -1 altfel
*/
typedef int(*cmpType) (MateriePrima* mp1, MateriePrima* mp2);

/*
	Functia de sortare
	cmpFunction - functia dupa care se sorteaza
*/
void sort(ListMP* pointerCofetarie, cmpType cmpFunction);

#endif