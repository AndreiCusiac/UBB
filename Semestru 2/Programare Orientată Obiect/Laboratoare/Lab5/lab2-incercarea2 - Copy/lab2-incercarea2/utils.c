#include "utils.h"


bool compara_vectori(int v1[10], int v2[10]) {
	bool egal = true;
	for (int i = 0; i < 10; i++)
		if (v1[i] != v2[i])egal = false;
	return egal;
}
	
float media(int v[], float n) {
		float suma = 0;
		for (int i = 0; i < n; i++)
			suma += v[i];
		suma =suma/n;
		return suma;
	}

