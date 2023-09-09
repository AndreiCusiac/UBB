#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "Domain.h"
#include "Utils.h"
#include "Repo.h"
#include "Service.h"
#include "UI.h"

void save(Lista* l, Medicament med)
{
	if (l->load <= l->nrElem)
	{
		Medicament* auxx = malloc(sizeof(Medicament) * (l->load * 2));

		for (int i = 0; i < l->nrElem; i++)
		{
			auxx[i] = l->v[i];
		}

		free(l->v);
		l->v = auxx;
		l->load = l->load * 2;
	}

	l->v[l->nrElem] = med;
	l->nrElem = l->nrElem + 1;

	//printf("%d %d %d\n", l->load, get_stoc(l->v[0]), l->nrElem);	
}

void test_save()
{
	Lista l;
	l.nrElem = 0;

	Medicament med;
	strcpy(med.nume, "Parasinus");
	med.cantitate = 20;
	med.concentratie = 10.5;

	save(&l, med);
	assert(l.nrElem == 1);
	assert(l.v[0].cantitate == 20);
}

int cauta(Lista* Da, int cod)
{
	int i;
	for (i = 0; i < Da->nrElem; i++)
	{
		if (cod == get_cod(Da->v[i]))
		{
			return i;
		}
	}
	return -1;
}

void test_cauta()
{
	Lista l;
	l.nrElem = 0;

	Medicament med, med1;
	strcpy(med.nume, "Parasinus");
	med.cod = 123;
	med.cantitate = 20;
	med.concentratie = 10.5;

	strcpy(med1.nume, "Avocalmin");
	med1.cod = 122;
	med1.cantitate = 35;
	med1.concentratie = 6;

	save(&l, med);
	save(&l, med1);
	assert(l.nrElem == 2);

	assert(cauta(&l, 122) == 1);
	assert(cauta(&l, 123) == 0);
	assert(cauta(&l, 12) == -1);
}



void actualizeaza_stoc(Lista* l, int stoc, int poz)
{
	l->v[poz].cantitate = stoc;
}

void actualizeaza_medicament(Lista* l, char nume[50], float concetratie, int poz)
{
	strcpy(l->v[poz].nume, nume);
	l->v[poz].concentratie = concetratie;
}

void sterge_stoc(Lista* l, int poz)
{
	l->v[poz].cantitate = 0;
}



void test_actualizeaza_stoc()
{
	Lista l;
	l.nrElem = 0;

	Medicament med, med1;
	strcpy(med.nume, "Parasinus");
	med.cod = 123;
	med.cantitate = 20;
	med.concentratie = 10.5;

	strcpy(med1.nume, "Avocalmin");
	med1.cod = 122;
	med1.cantitate = 35;
	med1.concentratie = 6;

	save(&l, med);
	save(&l, med1);
	assert(l.nrElem == 2);
	assert(l.v[0].cod == 123);
	assert(l.v[0].cantitate == 20);

	actualizeaza_stoc(&l, 30, 0);

	assert(l.v[0].cantitate == 30);
}



void test_actualizeaza_medicament()
{
	Lista l;
	l.nrElem = 0;

	Medicament med, med1;
	strcpy(med.nume, "Parasinus");
	med.cod = 123;
	med.cantitate = 20;
	med.concentratie = 10.5;

	strcpy(med1.nume, "Avocalmin");
	med1.cod = 122;
	med1.cantitate = 35;
	med1.concentratie = 6;

	save(&l, med);
	save(&l, med1);
	assert(l.nrElem == 2);
	assert(l.v[0].cod == 123);
	assert(l.v[0].cantitate == 20);

	actualizeaza_medicament(&l, "Paracetamol", 10, 0);
	actualizeaza_medicament(&l, "Nurofen", 20, 1);

	assert(l.v[0].concentratie == 10);
	assert(strcmp(l.v[1].nume, "Nurofen") == 0);
}



void test_sterge_stoc()
{
	Lista l;
	l.nrElem = 0;

	Medicament med;
	strcpy(med.nume, "Parasinus");
	med.cantitate = 20;
	med.concentratie = 10.5;

	save(&l, med);
	assert(l.nrElem == 1);
	assert(l.v[0].cantitate == 20);

	sterge_stoc(&l, 0);
	assert(l.v[0].cantitate == 0);
}

void test_repo()
{
	test_save();
	test_actualizeaza_stoc();
	test_actualizeaza_medicament();
	test_sterge_stoc();
	test_cauta();

}