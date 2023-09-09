#include "sort.h"

void sort(ListMP* pointerRezultat, cmpType cmpFunction) {
	int i, j;
	for (i = 0; i < pointerRezultat->nrElemente; i++)
		for (j = i + 1; j < pointerRezultat->nrElemente; j++) {
			void* mp1 = GetMP(pointerRezultat, i);
			void* mp2 = GetMP(pointerRezultat, j);
			if (cmpFunction(mp1, mp2) > 0) {
				UpdateMP(pointerRezultat, i, mp2);
				UpdateMP(pointerRezultat, j, mp1);
			}
		}
}
