#include "repository.h"

Repository* CreeazaRepo() {
	Repository* r = malloc(sizeof(Repository));
	r->lista = CreeazaListMP(DistrugeMP);
	return r;
}

void DistrugeRepo(Repository* r) {
	DistrugeListMP(r->lista);
	free(r);
}

void* GetRepo(Repository* r, int poz) {
	return r->lista->lista[poz];
}

void* UpdateRepo(Repository* r, int poz, void* e) {
	return UpdateMP(r->lista, poz, e);
}

void AddRepo(Repository* r, void* e) {
	AdaugaMP(r->lista, e);
}

void TestRepository() {
	Repository* cofetarie = CreeazaRepo(DistrugeMP);
	AddRepo(cofetarie, CreeazaMP("1", "1", 10));
	AddRepo(cofetarie, CreeazaMP("2", "2", 20));
	AddRepo(cofetarie, CreeazaMP("3", "3", 30));
	MateriePrima* mp = GetRepo(cofetarie, 1);
	assert(strcmp(mp->nume, "2") == 0);
	assert(strcmp(mp->producator, "2") == 0);
	assert(mp->cantitate == 20);
	mp = UpdateRepo(cofetarie, 1, CreeazaMP("4", "4", 40));
	DistrugeMP(mp);
	DistrugeRepo(cofetarie);
}
