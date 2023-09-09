#include "domain.h"

MateriePrima* CreeazaMP(char* nume, char* producator, int cantitate) {
	MateriePrima* mp = malloc(sizeof(MateriePrima));

	mp->nume = malloc(sizeof(char) * (strlen(nume) + 1));
	strcpy_s(mp->nume, strlen(nume) + 1, nume);
	mp->producator = malloc(sizeof(char) * (strlen(producator) + 1));
	strcpy_s(mp->producator, strlen(producator) + 1, producator);
	mp->cantitate = cantitate;

	return mp;
}

MateriePrima* CopyMP(MateriePrima* mp) {
	return CreeazaMP(mp->nume, mp->producator, mp->cantitate);
}

void DistrugeMP(MateriePrima* mp) {
	free(mp->nume); 
	free(mp->producator); 
	free(mp);
}

void TestDomain() {
	MateriePrima* mp = CreeazaMP("Zahar", "Margaritar", 100);
	assert(strcmp(mp->nume, "Zahar") == 0);
	assert(strcmp(mp->producator, "Margaritar") == 0);
	assert(mp->cantitate == 100);
	MateriePrima* copyMP = CopyMP(mp);
	assert(strcmp(copyMP->nume, "Zahar") == 0);
	assert(strcmp(copyMP->producator, "Margaritar") == 0);
	assert(copyMP->cantitate == 100);
	DistrugeMP(mp); DistrugeMP(copyMP);
}
