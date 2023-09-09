#include "myList.h"
#include <stdio.h>

ListMP* CreeazaListMP(DestroyFunction DF) {
	ListMP* cofetarie = malloc(sizeof(ListMP));
	cofetarie->nrElemente = 0;
	cofetarie->maxElemente = 2;
	cofetarie->lista = malloc(cofetarie->maxElemente * sizeof(void*));
	cofetarie->destroyF = DF;
	return cofetarie;
}

void DistrugeListMP(ListMP* pointerCofetarie) {
	for (int i = 0; i < pointerCofetarie->nrElemente; i++)
		pointerCofetarie->destroyF(pointerCofetarie->lista[i]);
	free(pointerCofetarie->lista);
	free(pointerCofetarie);
}

void AdaugaMP(ListMP* pointerCofetarie, void* mp) {
	Realloc(pointerCofetarie);
	pointerCofetarie->lista[pointerCofetarie->nrElemente] = mp;
	pointerCofetarie->nrElemente++;
}

void* GetMP(ListMP* pointerCofetarie, int index) {
	return pointerCofetarie->lista[index];
}

void* UpdateMP(ListMP* pointerCofetarie, int index, void* mp) {
	void* mpAux = pointerCofetarie->lista[index];
	pointerCofetarie->lista[index] = mp;
	return mpAux;
}

void Realloc(ListMP* pointerCofetarie) {
	if (pointerCofetarie->nrElemente < pointerCofetarie->maxElemente) return;
	void** newLista = malloc(2 * (pointerCofetarie->maxElemente) * sizeof(void*));
	for (int i = 0; i < pointerCofetarie->nrElemente; i++)
		newLista[i] = pointerCofetarie->lista[i];
	free(pointerCofetarie->lista);
	pointerCofetarie->lista = newLista;
	pointerCofetarie->maxElemente *= 2;
}

void DistrugeUnMP(ListMP* pointerCofetarie, int index) {
	for (int i = index; i < pointerCofetarie->nrElemente - 1; i++) {
		pointerCofetarie->lista[i] = pointerCofetarie->lista[i + 1];
	}
	pointerCofetarie->nrElemente -= 1;
}

void* DistrugeLast(ListMP* pointerCofetarie) {
	void* mp = pointerCofetarie->lista[pointerCofetarie->nrElemente - 1];
	pointerCofetarie->nrElemente -= 1;
	return mp;
}

ListMP* CopieListMP(ListMP* pointerCofetarie, CopyFunction CF) {
	ListMP* listAux = CreeazaListMP(pointerCofetarie->destroyF);
	for (int i = 0; i < pointerCofetarie->nrElemente; i++) {
		void* mp = GetMP(pointerCofetarie, i);
		AdaugaMP(listAux, CF(mp));
	}
	return listAux;
}

void TestMyList() {

	ListMP* cofetarie = CreeazaListMP(DistrugeMP);
	assert(cofetarie->nrElemente == 0);

	AdaugaMP(cofetarie, CreeazaMP("1", "1", 10));
	AdaugaMP(cofetarie, CreeazaMP("2", "2", 20));
	AdaugaMP(cofetarie, CreeazaMP("3", "3", 30));
	AdaugaMP(cofetarie, CreeazaMP("5", "5", 50));
	MateriePrima* mp = GetMP(cofetarie, 1);
	assert(strcmp(mp->nume, "2") == 0);
	assert(strcmp(mp->producator, "2") == 0);
	assert(mp->cantitate == 20);

	MateriePrima* mpNew = CreeazaMP("4", "4", 40);
	MateriePrima* mpOld = UpdateMP(cofetarie, 1, mpNew);
	DistrugeMP(mpOld);
	mp = GetMP(cofetarie, 1);
	assert(strcmp(mp->nume, "4") == 0);
	assert(strcmp(mp->producator, "4") == 0);
	assert(mp->cantitate == 40);

	mp = DistrugeLast(cofetarie);
	assert(cofetarie->nrElemente == 3);
	DistrugeMP(mp);

	ListMP* copyCofetarie = CopieListMP(cofetarie, CopyMP);
	assert(copyCofetarie->nrElemente == 3);
	mp = GetMP(cofetarie, 1);
	assert(strcmp(mp->nume, "4") == 0);
	assert(strcmp(mp->producator, "4") == 0);
	assert(mp->cantitate == 40);

	mp = GetMP(copyCofetarie, 1);
	DistrugeMP(mp);
	DistrugeUnMP(copyCofetarie, 1);
	assert(copyCofetarie->nrElemente == 2);

	DistrugeListMP(cofetarie);
	DistrugeListMP(copyCofetarie);
}
