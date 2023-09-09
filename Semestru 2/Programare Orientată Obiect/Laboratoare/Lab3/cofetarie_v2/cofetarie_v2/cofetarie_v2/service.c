#include "service.h"

Depozit CreateDepozit() {
	Depozit dep;
	dep.allMPs = CreeazaRepo(DistrugeMP);
	dep.undoList = CreeazaListMP(DistrugeListMP);
	dep.redoList = CreeazaListMP(DistrugeListMP);
	return dep;
}

void DistrugeDepozit(Depozit* dep) {
	DistrugeRepo(dep->allMPs);
	DistrugeListMP(dep->undoList);
	DistrugeListMP(dep->redoList);
}

int AddMP(Depozit* dep, char* nume, char* producator, int cantitate) {
	MateriePrima* mp = CreeazaMP(nume, producator, cantitate);
	ListMP* toUndo = CopieListMP(dep->allMPs->lista, CopyMP);
	AdaugaMP(dep->allMPs->lista, mp);
	AdaugaMP(dep->undoList, toUndo);
	DistrugeListMP(dep->redoList);
	dep->redoList = CreeazaListMP(DistrugeListMP);
	return 0;
}

void* ActualizareMP(Depozit* dep, int index, void* mp) {
	ListMP* toUndo = CopieListMP(dep->allMPs->lista, CopyMP);
	MateriePrima* mpOld = UpdateMP(dep->allMPs->lista, index, mp);
	AdaugaMP(dep->undoList, toUndo);
	DistrugeListMP(dep->redoList);
	dep->redoList = CreeazaListMP(DistrugeListMP);
	return mpOld;
}

void StergeMP(Depozit* dep, int index) {
	ListMP* toUndo = CopieListMP(dep->allMPs->lista, CopyMP);
	DistrugeMP(GetMP(dep->allMPs->lista, index));
	DistrugeUnMP(dep->allMPs->lista, index);
	AdaugaMP(dep->undoList, toUndo);
	DistrugeListMP(dep->redoList);
	dep->redoList = CreeazaListMP(DistrugeListMP);
}

ListMP* FilterByFirstLetter(ListMP* pointerCofetarie, char litera) {
	ListMP* rezultat = CreeazaListMP(DistrugeMP);
	for (int i = 0; i < pointerCofetarie->nrElemente; i++) {
		MateriePrima* mp = CopyMP(pointerCofetarie->lista[i]);
		if (mp->nume[0] == litera) {
			AdaugaMP(rezultat, mp);
		}
		else DistrugeMP(mp);
	}
	return rezultat;
}

ListMP* FilterByQuantity(ListMP* pointerCofetarie, int Qty) {
	ListMP* rezultat = CreeazaListMP(DistrugeMP);
	for (int i = 0; i < pointerCofetarie->nrElemente; i++) {
		MateriePrima* mp = GetMP(pointerCofetarie, i);
		if (mp->cantitate <= Qty) {
			MateriePrima* mp1 = CopyMP(mp);
			AdaugaMP(rezultat, mp1);
		}
	}
	return rezultat;
}

ListMP* FilterByProducator(ListMP* pointerCofetarie, char* producator) {
	ListMP* rezultat = CreeazaListMP(DistrugeMP);
	for (int i = 0; i < pointerCofetarie->nrElemente; i++) {
		MateriePrima* mp = GetMP(pointerCofetarie, i);
		if (strstr(mp->producator, producator) != NULL) {
			MateriePrima* mp1 = CopyMP(mp);
			AdaugaMP(rezultat, mp1);
		}
	}
	return rezultat;
}

ListMP* FilterByQtyLessThan(ListMP* pointerCofetarie) {
	ListMP* rezultat = CreeazaListMP(DistrugeMP);
	for (int i = 0; i < pointerCofetarie->nrElemente; i++) {
		MateriePrima* mp = GetMP(pointerCofetarie, i);
		if (mp->cantitate <= 50) {
			MateriePrima* mp1 = CopyMP(mp);
			AdaugaMP(rezultat, mp1);
		}
	}
	return rezultat;
}

ListMP* SortListMP(Depozit* dep, int filter, int reverse) {
	ListMP* rezultat = CopieListMP(dep->allMPs->lista, CopyMP);
	if (reverse == 1)
		if (filter == 1) sort(rezultat, cmpNameFwd);
		else sort(rezultat, cmpQtyFwd);
	else
		if (filter == 1) sort(rezultat, cmpNameAft);
		else sort(rezultat, cmpQtyAft);
	return rezultat;
}

int undo(Depozit* dep) {
	if (dep->undoList->nrElemente == 0) return 1;
	ListMP* copieLista = CopieListMP(dep->allMPs->lista, CopyMP);
	ListMP* listAux = DistrugeLast(dep->undoList);
	DistrugeListMP(dep->allMPs->lista);
	dep->allMPs->lista = listAux;
	AdaugaMP(dep->redoList, copieLista);
	return 0;
}

int redo(Depozit* dep) {
	if (dep->redoList->nrElemente == 0) return 1;
	ListMP* copieLista = CopieListMP(dep->allMPs->lista, CopyMP);
	AdaugaMP(dep->undoList, copieLista);
	DistrugeListMP(dep->allMPs->lista);
	dep->allMPs->lista = DistrugeLast(dep->redoList);
	return 0;
}

int cmpNameFwd(MateriePrima* mp1, MateriePrima* mp2) {
	return strcmp(mp1->nume, mp2->nume);
}

int cmpQtyFwd(MateriePrima* mp1, MateriePrima* mp2) {
	return mp1->cantitate > mp2->cantitate;
}

int cmpNameAft(MateriePrima* mp1, MateriePrima* mp2) {
	return (-1) * strcmp(mp1->nume, mp2->nume);
}

int cmpQtyAft(MateriePrima* mp1, MateriePrima* mp2) {
	return mp1->cantitate < mp2->cantitate;
}

void TestService() {
	Depozit dep = CreateDepozit();

	AddMP(&dep, "a", "c", 10);
	AddMP(&dep, "c", "b", 20);
	undo(&dep);
	assert(dep.allMPs->lista->nrElemente == 1);
	undo(&dep);
	redo(&dep);

	undo(&dep);
	assert(dep.allMPs->lista->nrElemente == 0);
	
	AddMP(&dep, "ana", "are", 10);
	AddMP(&dep, "bana", "bare", 20);
	AddMP(&dep, "ana", "nare", 30);

	ListMP* rezultat = FilterByFirstLetter(dep.allMPs->lista, 'a');
	MateriePrima* mp = GetMP(rezultat, 1);
	assert(strcmp(mp->nume, "ana") == 0);
	assert(strcmp(mp->producator, "nare") == 0);
	assert(mp->cantitate == 30);
	DistrugeListMP(rezultat);

	ListMP* rezultat2 = FilterByQuantity(dep.allMPs->lista, 20);
	mp = GetMP(rezultat2, 1);
	assert(strcmp(mp->nume, "bana") == 0);
	assert(strcmp(mp->producator, "bare") == 0);
	assert(mp->cantitate == 20);
	assert(rezultat2->nrElemente == 2);
	DistrugeListMP(rezultat2);
	
	ListMP* rezultat5 = FilterByProducator(dep.allMPs->lista, "b");
	mp = GetMP(rezultat5, 0);
	assert(strcmp(mp->nume, "bana") == 0);
	assert(strcmp(mp->producator, "bare") == 0);
	assert(mp->cantitate == 20);
	assert(rezultat5->nrElemente == 1);
	DistrugeListMP(rezultat5);

	ListMP* rezultat6 = FilterByQtyLessThan(dep.allMPs->lista);
	mp = GetMP(rezultat6, 0);
	assert(strcmp(mp->nume, "ana") == 0);
	assert(strcmp(mp->producator, "are") == 0);
	assert(mp->cantitate == 10);
	DistrugeListMP(rezultat6);

	ListMP* rezultat3 = SortListMP(&dep, 1, 1);
	mp = GetMP(rezultat3, 1);
	assert(strcmp(mp->nume, "ana") == 0);
	assert(strcmp(mp->producator, "nare") == 0);
	assert(mp->cantitate == 30);
	DistrugeListMP(rezultat3);

	DistrugeDepozit(&dep);

	Depozit dep2 = CreateDepozit();

	AddMP(&dep2, "ana", "are", 10);
	AddMP(&dep2, "bana", "bare", 30);
	AddMP(&dep2, "ana", "nare", 20);

	ListMP* rezultat4 = SortListMP(&dep2, 2, 1);
	mp = GetMP(rezultat4, 1);
	assert(strcmp(mp->nume, "ana") == 0);
	assert(strcmp(mp->producator, "nare") == 0);
	assert(mp->cantitate == 20);
	DistrugeListMP(rezultat4);

	ListMP* rezultat10 = SortListMP(&dep2, 1, -1);
	mp = GetMP(rezultat10, 1);
	assert(strcmp(mp->nume, "ana") == 0);
	assert(strcmp(mp->producator, "are") == 0);
	assert(mp->cantitate == 10);
	DistrugeListMP(rezultat10);

	ListMP* rezultat11 = SortListMP(&dep2, 2, -1);
	mp = GetMP(rezultat11, 1);
	assert(strcmp(mp->nume, "ana") == 0);
	assert(strcmp(mp->producator, "nare") == 0);
	assert(mp->cantitate == 20);
	DistrugeListMP(rezultat11);

	mp = ActualizareMP(&dep2, 0, CreeazaMP("nana", "nare", 100));
	DistrugeMP(mp);
	mp = GetMP(dep2.allMPs->lista, 0);
	assert(strcmp(mp->nume, "nana") == 0);
	assert(strcmp(mp->producator, "nare") == 0);
	assert(mp->cantitate == 100);

	DistrugeDepozit(&dep2);

	Depozit dep3 = CreateDepozit();
	AddMP(&dep3, "ana", "are", 10);
	StergeMP(&dep3, 0);
	DistrugeDepozit(&dep3);
}
