#include "validator.h"

int ValidateUpdate(ListMP* pointerCofetarie, int index, MateriePrima* mp) {
	if (index < 0 || index >= pointerCofetarie->nrElemente) return 1;
	for (int i = 0; i < pointerCofetarie->nrElemente; i++) {
		MateriePrima* currentMP = GetMP(pointerCofetarie, i);
		if (strcmp(currentMP->nume, mp->nume) == 0 && strcmp(currentMP->producator, mp->producator) == 0)
			return 2;
	}
	return 0;
}

void TestValidator() {

	ListMP* cofetarie = CreeazaListMP(DistrugeMP);

	AdaugaMP(cofetarie, CreeazaMP("1", "1", 10));
	AdaugaMP(cofetarie, CreeazaMP("2", "2", 20));
	AdaugaMP(cofetarie, CreeazaMP("3", "3", 30));

	MateriePrima* mp1 = CreeazaMP("4", "4", 40); assert(ValidateUpdate(cofetarie, 4, mp1) == 1); DistrugeMP(mp1);
	MateriePrima* mp2 = CreeazaMP("2", "2", 40); assert(ValidateUpdate(cofetarie, 2, mp2) == 2); DistrugeMP(mp2);
	MateriePrima* mp3 = CreeazaMP("4", "4", 40); assert(ValidateUpdate(cofetarie, 1, mp3) == 0); DistrugeMP(mp3);

	DistrugeListMP(cofetarie);
}
