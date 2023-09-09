// Lab3-4.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include <assert.h>
#include <stdio.h>
#include <string.h>

#include "Domain.h"
#include "Utils.h"
#include "Repo.h"
#include "Service.h"
#include "UI.h"


void citeste(Lista* l)
{
	char cod[50], concentratie[50], nume[50], stoc[50];
	int OK = 1;
	
	printf("Dati atributele unui medicament: ");
	
	printf("\nCod: ");
	scanf("%s", &cod);
	
	printf("Nume: ");
	scanf("%s", &nume);
	
	printf("Concentratie: ");
	scanf("%s", &concentratie);
	
	printf("Cantitate in stoc: ");
	scanf("%s", &stoc);

	int i = adauga(l, cod, stoc, nume, concentratie);
	
	if (i == 1)
	{
		printf("\nMedicamentul a fost adaugat cu succes!\n");
	}
	else if (i == 2)
	{
		printf("\nStocul medicamentului a fost actualizat cu succes!\n");
	}
	else
	{
		printf("\nSalvarea medicamentului nu s-a efectuat!\n");
	}
	
}

void modifica(Lista *l)
{
	char cod[50], concentratie[50], nume[50];
	int OK = 1;

	printf("Dati codul unui medicament: ");

	printf("\nCod: ");
	scanf("%s", &cod);

	printf("\nDati noul nume si/ sau concentratie: ");
	
	printf("\nNume: ");
	scanf("%s", &nume);

	printf("Concentratie: ");
	scanf("%s", &concentratie);

	int i = actualizare(l, cod, nume, concentratie);

	if (i == 1)
	{
		printf("\nMedicamentul a fost actualizat cu succes!\n");
	}
	else if (i == 2)
	{
		printf("\nMedicamentul nu a fost gasit in stoc!\n");
	}
	else
	{
		printf("\nActualizarea medicamentului nu s-a efectuat!\n");
	}
}

void sterge(Lista* l)
{
	char cod[50];
	int OK = 1;

	printf("Dati codul unui medicament: ");

	printf("\nCod: ");
	scanf("%s", &cod);

	int i = stergere(l, cod);

	if (i == 1)
	{
		printf("\nStocul medicamentului a fost sters cu succes!\n");
	}
	else if (i == 2)
	{
		printf("\nMedicamentul nu a fost gasit in stoc!\n");
	}
	else
	{
		printf("\nStergerea stocului medicamentului nu s-a efectuat!\n");
	}
}

void tipareste(Lista* l)
{
	int i;
	char c[50];

	if (l->nrElem == 0)
	{
		printf("Nu exista medicamente de afisat!\n");
	}
	
	for (i=0; i<l->nrElem; i++)
	{
		printf("Cod: %d\n", get_cod(l->v[i]));
		
		get_nume(l->v[i], c);
		printf("Nume: %s\n", c);

		printf("Concentratie: %f\n", get_concentratie(l->v[i]));

		printf("Cantitate: %d\n", get_stoc(l->v[i]));
		
		printf("\n\n");
	}
}

void filtreaza_stoc(Lista* l)
{
	Lista afis;
	char stoc[50];
	int OK = 1;

	printf("\nDati o valoare pentru stoc: ");
	printf("\nCantitate in stoc: ");
	scanf("%s", &stoc);

	int i = filtr_dupa_stoc(l, &afis, stoc);

	if (i == 1)
	{
		printf("\nMedicamentele cu stocul mai mic decat %s sunt: \n", stoc);
		tipareste(&afis);
	}
	else if (i == 2)
	{
		printf("\nNu exista medicamente in stoc!\n");
	}
	else
	{
		printf("\nFiltrarea medicamentelor nu s-a efectuat!\n");
	}
}

void filtreaza_nume(Lista* l)
{
	Lista afis;
	char c[2];
	int OK = 1;

	printf("\nDati prima litera din nume: ");
	printf("\nPrima litera: ");
	scanf("%s", &c);

	int i = filtr_dupa_nume(l, &afis, c[0]);

	if (i != 2)
	{
		printf("\nMedicamentele al caror nume incepe cu %c sunt: \n", c[0]);
		tipareste(&afis);
	}
	else if (i == 2)
	{
		printf("\nNu exista medicamente in stoc!\n");
	}
	else
	{
		printf("\nFiltrarea medicamentelor nu s-a efectuat!\n");
	}
}

void vizualizeaza_nume_crescator(Lista* l)
{
	Lista afis;

	int i = sortare_nume_cresc(l, &afis);

	if (i == 1)
	{
		printf("\nMedicamentele sortate crescator dupa nume sunt: \n");
		tipareste(&afis);
	}
	else
	{
		printf("\nNu exista medicamente in stoc!\n");
	}
}

void vizualizeaza_nume_descrescator(Lista* l)
{
	Lista afis;

	int i = sortare_nume_descresc(l, &afis);

	if (i == 1)
	{
		printf("\nMedicamentele sortate descrescator dupa nume sunt: \n");
		tipareste(&afis);
	}
	else
	{
		printf("\nNu exista medicamente in stoc!\n");
	}
}

void vizualizeaza_stoc_crescator(Lista* l)
{
	Lista afis;

	int i = sortare_stoc_cresc(l, &afis);

	if (i == 1)
	{
		printf("\nMedicamentele sortate crescator dupa stoc sunt: \n");
		tipareste(&afis);
	}
	else
	{
		printf("\nNu exista medicamente in stoc!\n");
	}
}

void vizualizeaza_stoc_descrescator(Lista* l)
{
	Lista afis;

	int i = sortare_stoc_descresc(l, &afis);

	if (i == 1)
	{
		printf("\nMedicamentele sortate descrescator dupa stoc sunt: \n");
		tipareste(&afis);
	}
	else
	{
		printf("\nNu exista medicamente in stoc!\n");
	}
}

void sub_adaugare(Lista* l)
{
	printf("\nAti ales optiunea: Adaugare\n\n");
	citeste(l);
}

void sub_stergere(Lista* l)
{
	printf("\nAti ales optiunea: Stergere\n\n");
	sterge(l);
}

void sub_modificare(Lista* l)
{
	printf("\nAti ales optiunea: Modificare\n\n");
	modifica(l);
}

void sub_vizualizare(Lista* l)
{
	printf("\nAti ales optiunea: Vizualizare\n\n");

	int cmd = -1;

	while (cmd != 0)
	{
		printf("\nDoriti sa: \n 1. Vizualizati medicamentele ordonate dupa nume, crescator - tasta 1 \n 2. Vizualizati medicamentele ordonate dupa nume, descrescator - tasta 2 \n 3. Vizualizati medicamentele ordonate dupa cantitate, crescator - tasta 3 \n 4. Vizualizati medicamentele ordonate dupa cantitate, descrescator - tasta 4 \n 5. Iesiti la meniul principal - tasta 0 \n");
		printf("Prescriptia dumneavoastra este: ");
		scanf("%d", &cmd);

		if (cmd == 1)
		{
			vizualizeaza_nume_crescator(l);
			break;
		}
		else if (cmd == 2)
		{
			vizualizeaza_nume_descrescator(l);
			break;
		}
		else if (cmd == 3)
		{
			vizualizeaza_stoc_crescator(l);
			break;
		}
		else if (cmd == 4)
		{
			vizualizeaza_stoc_descrescator(l);
			break;
		}
		else if (cmd != 0)
		{
			printf("\nReteta dumneavoastra nu este valida!\n");
		}
	}
}

void sub_filtrare(Lista* l)
{
	printf("\nAti ales optiunea: Filtrare\n");
	
	int cmd = -1;

	while (cmd != 0)
	{
		printf("\nDoriti sa: \n 1. Vizualizati medicamentele cu stocul mai mic decat o valoare data - tasta 1 \n 2. Vizualizati medicamentele al caror nume incepe cu o litera data - tasta 2 \n 3. Iesiti la meniul principal - tasta 0 \n");
		printf("Prescriptia dumneavoastra este: ");
		scanf("%d", &cmd);

		if (cmd == 1)
		{
			filtreaza_stoc(l);
			break;
		}
		else if (cmd == 2)
		{
			filtreaza_nume(l);
			break;
		}
		else if (cmd != 0)
		{
			printf("\nReteta dumneavoastra nu este valida!\n");
		}
	}
}

void sub_tiparire(Lista* l)
{
	printf("\nAti ales optiunea: Tiparire\n\n");
	printf("Medicamentele sunt:\n");
	tipareste(l);
}

void consola()
{
	Lista l;
	l.nrElem = 0;
	meniu(&l);
}

void meniu(Lista* l)
{
	int cmd = -1;
	
	while (cmd != 0)
	{
		printf("\n Doriti sa: \n 1. Adaugati un medicament - tasta 1 \n 2. Actualizati un medicament - tasta 2 \n 3. Stergeti stocul unui medicament - tasta 3 \n 4. Vizualizati medicamentele din stoc - tasta 4 \n 5. Filtrati lista de medicamente - tasta 5 \n 6. Tipariti medicamentele existente - tasta 6 \n 7. Iesiti din program - tasta 0 \n");
		printf("Prescriptia dumneavoastra este: ");
		scanf("%d", &cmd);
		
		if (cmd == 1)
		{
			sub_adaugare(l);
		}
		else if (cmd == 2)
		{
			sub_modificare(l);
		}
		else if (cmd == 3)
		{
			sub_stergere(l);
		}
		else if (cmd == 4)
		{
			sub_vizualizare(l);
		}
		else if (cmd == 5)
		{
			sub_filtrare(l);
		}
		else if (cmd == 6)
		{
			sub_tiparire(l);
		}
		else if (cmd != 0)
		{
			printf("\nReteta dumneavoastra nu este valida! Dati o noua optiune!\n");
		}
		else if (cmd == 0)
		{
			printf("\nSunteti sigur ca doriti sa incetati tratamentul? \n 1. Iesire - tasta 1 \n 2. Revenire - tasta 2\n");
			printf("Optiunea dumneavoastra este: ");
			scanf("%d", &cmd);
			if (cmd!=1)
			{
				meniu(l);
			}
			else
			{
				break;
			}
		}
	}
}

void test_all()
{
	test_domain();
	test_repo();
	test_service();
	printf("\nTestele automate au fost rulate cu succes!\n");
}

int main()
{
	test_all();
    printf("\n\n---Catena SRL--- \n Header bug-uit sau virusat? Parasinus ti-e aliat!\n");
	consola();
	printf("\nSfarsit tratament.\n");

	_CrtDumpMemoryLeaks();
	return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
