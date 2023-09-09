#pragma once
#include <assert.h>
#include "domain.h"
#include "repository.h"
#include "myList.h"

/*
	Functie de validare actualizare parametri materie prima
	pointerCofetarie - pointer la lista de materii prime
	index - indexul elementului de actualizat
	mp - materia prima de validat
*/
int ValidateUpdate(ListMP* pointerCofetarie, int index, MateriePrima* mp);

/*
	Functie de testare validator
*/
void TestValidator();
