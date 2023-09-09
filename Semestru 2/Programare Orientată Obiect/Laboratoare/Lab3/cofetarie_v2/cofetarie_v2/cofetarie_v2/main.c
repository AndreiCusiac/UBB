#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include <stdio.h>
#include "domain.h"
#include "repository.h"
#include "myList.h"
#include "validator.h"
#include "service.h"

void TestAll() {
	TestDomain();
	TestMyList();
	TestRepository();
	TestValidator();
	TestService();
}

void afisareListaMP(ListMP* pointerCofetarie) {
	for (int i = 0; i < pointerCofetarie->nrElemente; i++) {
		printf("Index: %d\n", i);
		MateriePrima* mp = GetMP(pointerCofetarie, i);
		printf("Nume produs: %s\n", mp->nume);
		printf("Producator produs: %s\n", mp->producator);
		printf("Producator produs: %d\n", mp->cantitate);
		printf("\n");
	}
}

void console() {
	Depozit dep = CreateDepozit();

	printf("Aplicatie gestiune materii prime cofetarie\n");
	printf("1. Adaugare materie prima\n");
	printf("2. Modificare materie prima\n");
	printf("3. Stergere materie prima\n");
	printf("4. Filtrare materii prime\n");
	printf("5. Sortare materii prime\n");
	printf("6. Tiparire materii prime\n");
	printf("7. Undo\n");
	printf("8. Redo\n");
	printf("0. Exit\n\n");

	int cmd = -1;
	while (cmd) {
		printf("Dati comanda: ");
		scanf_s("%d", &cmd);
		printf("\n");
		
		if (cmd == 1) {
			char nume[100], producator[100]; int cantitate = 0;
			printf("Dati numele produsului: ");
			scanf_s("%s", nume, (int)sizeof(nume));
			printf("Dati producatorul produsului: ");
			scanf_s("%s", producator, (int)sizeof(producator));
			printf("Dati cantitatea produsului: ");
			scanf_s("%d", &cantitate);
			printf("\n");

			AddMP(&dep, nume, producator, cantitate);
		}

		if (cmd == 2) {
			afisareListaMP(dep.allMPs->lista);

			printf("Dati indexul produsului de modificat: ");
			int index = 0; scanf_s("%d", &index);
			printf("Dati noul nume al produsului: ");
			char nume[100]; scanf_s("%s", nume, (int)sizeof(nume));
			printf("Dati noul producator al produsului: ");
			char producator[100]; scanf_s("%s", producator, (int)sizeof(nume));
			printf("Dati noua cantitate a produsului: ");
			int cantitate = 0; scanf_s("%d", &cantitate);
			printf("\n");
			MateriePrima* mp = CreeazaMP(nume, producator, cantitate);

			int validare = ValidateUpdate(dep.allMPs->lista, index, mp);
			if (validare == 0) {
				MateriePrima* oldMP = ActualizareMP(&dep, index, mp);
				DistrugeMP(oldMP);
			}
			else
				if (validare == 1) printf("Eroare! Indexul este in afara range-ului posibil!\n");
				else printf("Eroare! Numele si producatorul produsului trebuie sa fie unice!\n");
		}

		if (cmd == 3) {
			afisareListaMP(dep.allMPs->lista);
			printf("Dati indexul produsului de sters: ");
			int index = 0; scanf_s("%d", &index);
			printf("\n");
			StergeMP(&dep, index);
		}
		
		if (cmd == 4) {
			printf("1. Filtrare dupa prima litera\n");
			printf("2. Filtrare dupa o cantitate data\n");
			printf("3. Filtrare dupa producator\n");
			printf("4. Filtrare dupa cantitate mai mica de 50\n");
			int filter; printf("\nDati comanda: "); scanf_s("%d", &filter);

			if (filter == 1) {
				char litera; printf("\nDati prima litera: "); scanf_s("\n%c", &litera, (int)sizeof(litera));
				ListMP* rezultat = FilterByFirstLetter(dep.allMPs->lista, litera);
				printf("\n");
				afisareListaMP(rezultat);
				DistrugeListMP(rezultat);
			}

			if (filter == 2) {
				int Qty = 0; printf("\nDati cantitatea dorita: "); scanf_s("%d", &Qty);
				ListMP* rezultat = FilterByQuantity(dep.allMPs->lista, Qty);
				printf("\n");
				afisareListaMP(rezultat);
				DistrugeListMP(rezultat);
			}

			if (filter == 3) {
				char* producator = malloc(10 * sizeof(char)); printf("\nDati producatorul: "); scanf_s("%s", producator, (int)sizeof(producator));
				ListMP* rezultat = FilterByProducator(dep.allMPs->lista, producator);
				printf("\n");
				free(producator);
				afisareListaMP(rezultat);
				DistrugeListMP(rezultat);
			}

			if (filter == 4) {
				ListMP* rezultat = FilterByQtyLessThan(dep.allMPs->lista);
				printf("\n");
				afisareListaMP(rezultat);
				DistrugeListMP(rezultat);
			}
		}

		if (cmd == 5) {
			printf("1. Dupa nume\n");
			printf("2. Dupa cantitate\n");
			int filter; printf("\nDati comanda: "); scanf_s("%d", &filter);
			printf("\n");
			printf("1. Crescator\n");
			printf("2. Descrescator\n");
			int reverse = 1;
			printf("\nDati comanda: "); scanf_s("%d", &reverse);
			printf("\n");
			if (reverse == 2) reverse = -1;
			ListMP* rezultat2 = SortListMP(&dep, filter, reverse);
			afisareListaMP(rezultat2);
			DistrugeListMP(rezultat2);
		}

		if (cmd == 6) afisareListaMP(dep.allMPs->lista);

		if (cmd == 7) undo(&dep);

		if (cmd == 8) redo(&dep);
	}
	DistrugeDepozit(&dep);
}

int main() {

	TestAll();
	console();
	_CrtDumpMemoryLeaks();
	return 0;
}
