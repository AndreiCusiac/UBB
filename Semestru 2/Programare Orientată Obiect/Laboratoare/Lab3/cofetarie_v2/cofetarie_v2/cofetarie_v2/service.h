#pragma once
#include "domain.h"
#include "repository.h"
#include "myList.h"
#include "validator.h"
#include "sort.h"

typedef struct {
	Repository* allMPs;
	ListMP* undoList;
	ListMP* redoList;
} Depozit;

/*
	Functie de create depozit
*/
Depozit CreateDepozit();

/*
	Functie distrugere depozit
*/
void DistrugeDepozit(Depozit* dep);

/*
	Functie de adaugare materie prima la depozit
*/
int AddMP(Depozit* dep, char* nume, char* producator, int cantitate);

/*
	Functie de actualizare parametri element lista
	pointerCofetarie - pointer la lista de materii prime
	index - pozitia elementului de modificat
	mp - noii parametri ai elementului de pe pozitia index
*/
void* ActualizareMP(Depozit* dep, int index, void* mp);

/*
	Functie de distrugere un element din lista
	pointerCofetarie - pointerul catre lista de materii prime
	index - indexul elementului de sters
*/
void StergeMP(Depozit* dep, int index);

/*
	Functie de filtrare nume materie prima dupa prima litera
	pointerCofetarie - pointer spre lista de materii prime
	pointerRezultat - pointer spre rezultatele filtrarii
	litera - prima litera ce se va verifica
*/
ListMP* FilterByFirstLetter(ListMP* pointerCofetarie, char litera);

/*
	Functie de filtrare cantitate materie prima
	pointerCofetarie - pointer spre lista de materii prime
	pointerRezultat - pointer spre rezultatele filtrarii
	Qty - cantitatea de referinta
*/
ListMP* FilterByQuantity(ListMP* pointerCofetarie, int Qty);

/*
	Functie de filtrare producator materie prima
	pointerCofetarie - pointer spre lista de materii prime
	pointerRezultat - pointer spre rezultatele filtrarii
	producator - producatorul de referinta
*/
ListMP* FilterByProducator(ListMP* pointerCofetarie, char* producator);

/*
	Functie de filtrare producator materie prima
	pointerCofetarie - pointer spre lista de materii prime
	pointerRezultat - pointer spre rezultatele filtrarii
*/
ListMP* FilterByQtyLessThan(ListMP* pointerCofetarie);

/*
	Functie de sortare lista materii prime
	pointerCofetarie - pointer spre lista de materii prime
	pointerRezultat - pointer spre rezultatele sortarii
*/
ListMP* SortListMP(Depozit* dep, int filter, int reverse);

/*
	Functie de facut undo pe lista
	Return 0 if all ok, 1 if undo unsuccessful
*/
int undo(Depozit* dep);

/*
	Functie de facut undo pe lista
	Return 0 if all ok, 1 if undo unsuccessful
*/
int redo(Depozit* dep);

/*
	Functie de comparare 2 nume materii prime
	mp1, mp2 - materiile prime
	Returneaza o valoare negativa daca mp1.nume < mp2.nume, 
		0 daca mp1.nume == mp2.nume, 
		o valoare pozitiva daca mp1.nume > mp2.nume
*/
int cmpNameFwd(MateriePrima* mp1, MateriePrima* mp2);

/*
	Functie de comparare 2 nume materii prime
	mp1, mp2 - materiile prime
	Returneaza 1 daca mp1.cantitate > mp2.cantitate
	Returneaza 0 daca mp1.cantitate < mp2.cantitate
*/
int cmpQtyFwd(MateriePrima* mp1, MateriePrima* mp2);

/*
	Functie de comparare 2 nume materii prime
	mp1, mp2 - materiile prime
	Returneaza o valoare negativa daca mp1.nume > mp2.nume,
	0 daca mp1.nume == mp2.nume,
	o valoare pozitiva daca mp1.nume < mp2.nume
*/
int cmpNameAft(MateriePrima* mp1, MateriePrima* mp2);

/*
	Functie de comparare 2 nume materii prime
	mp1, mp2 - materiile prime
	Returneaza 1 daca mp1.cantitate < mp2.cantitate
	Returneaza 0 daca mp1.cantitate > mp2.cantitate
*/
int cmpQtyAft(MateriePrima* mp1, MateriePrima* mp2);

/*
	Functie de testare service
*/
void TestService();
