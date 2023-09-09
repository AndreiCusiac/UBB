#pragma once
#include <assert.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
	char* nume;
	char* producator;
	int cantitate;
} MateriePrima;

/*
	Functia creeaza si initializeaza o materie prima
	nume - numele materiei prime
	producator - numele producatorului
	cantitate - cantitatea existenta in stoc
	Functia returneaza o materie prima
*/
MateriePrima* CreeazaMP(char* nume, char* producator, int cantitate);

/*
	Functie de copiere materie prima
	mp - materia prima de copiat
*/
MateriePrima* CopyMP(MateriePrima* mp);

/*
	Functia deallocates memoria ocupata de o materie prima
*/
void DistrugeMP(MateriePrima* mp);

/*
	Functie de testare create si memory deallocation pe o materie prima
*/
void TestDomain();
