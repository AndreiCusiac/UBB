#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "Domain.h"
#include "Utils.h"
#include "Repo.h"
#include "Service.h"
#include "UI.h"


int adauga(Lista* l, char cod[50], char stoc[50], char nume[50], char concentratie[50])
{
	char e[256] = "\nErori: ";
	Medicament med;
	char* ptr;
	
	int i = validare2(cod, stoc, nume, concentratie, e);
	
	if (i == 1)
	{
		med.nume = malloc((strlen(nume) + 1) * sizeof(char));
		set_nume(&med, nume);
		set_cod(&med, strtol(cod, &ptr, 10));
		set_concentratie(&med, strtof(concentratie, &ptr));
		set_stoc(&med, strtol(stoc, &ptr, 10));

		i = cauta(l, get_cod(med));
		
		if (i == -1)
		{
			save(l, med);
			return 1;
		}
		else
		{
			actualizeaza_stoc(l, med.cantitate, i);
			return 2;
		}
	}
	else
	{
		printf(e);
		return 0;
	}
}

void test_adauga()
{
	Lista l = creeaza_lista();
	
	assert(adauga(&l, "123", "20", "abc", "20.5") == 1);
	/*assert(adauga(&l, "-123", "20", "abc", "20.5") == 0);
	assert(adauga(&l, "-123", "-2", "abc", "20.5") == 0);
	assert(adauga(&l, "123", "", "abc", "20.5") == 0);
	assert(adauga(&l, "123", "da", "", "20.5") == 0);
	assert(adauga(&l, "123", "21", "abc", "-2") == 0);
	assert(adauga(&l, "123", "21", "", "2.5") == 0);
	assert(adauga(&l, "123", "20", "abc", "50.3") == 2);
	assert(adauga(&l, "124", "20", "abc", "50.3") == 1);
	*/
	distruge_lista(&l);
}

int actualizare(Lista* l, char cod[50], char nume[50], char concentratie[50])
{
	char e[256] = "\nErori: ";
	Medicament med;
	char* ptr;

	int i = validare2(cod, "20", nume, concentratie, e);

	if (i == 1)
	{
		med.nume = malloc((strlen(nume) + 1) * sizeof(char));
		set_nume(&med, nume);
		set_cod(&med, strtol(cod, &ptr, 10));
		set_concentratie(&med, strtof(concentratie, &ptr));

		i = cauta(l, get_cod(med));

		if (i != -1)
		{
			actualizeaza_medicament(l, nume, get_concentratie(med), i);
			return 1;
		}
		else
		{
			return 2;
		}
		
	}
	else
	{
		return 0;
	}
}

void test_actualizare()
{
	Lista l = creeaza_lista();
	char c[50];

	adauga(&l, "123", "20", "abc", "20.5");

	assert(get_cod(l.v[0]) == 123);
	assert(get_concentratie(l.v[0]) == 20.5);
	
	assert(actualizare(&l, "123", "abc", "30.5") == 1);

	assert(get_concentratie(l.v[0]) == 30.5);

	assert(actualizare(&l, "124", "abc", "30.5") == 2);

	assert(actualizare(&l, "123", "abc", "-30.5") == 0);

	get_nume(l.v[0], c);
	assert(strcmp(c, "abc") == 0);
	
	assert(actualizare(&l, "123", "acc", "30.5") == 1);

	get_nume(l.v[0], c);
	assert(strcmp(c, "acc") == 0);

	distruge_lista(&l);
	
}

int stergere(Lista* l, char cod[50])
{
	char e[256] = "\nErori: ";
	Medicament med;
	char* ptr;

	int i = validare2(cod, "20", "abc", "20", e);

	if (i == 1)
	{
		set_cod(&med, strtol(cod, &ptr, 10));

		i = cauta(l, get_cod(med));

		if (i != -1)
		{
			sterge_stoc(l, i);
			return 1;
		}
		else
		{
			return 2;
		}

	}
	else
	{
		return 0;
	}
}

void test_stergere()
{
	Lista l = creeaza_lista();

	adauga(&l, "123", "40", "abc", "20.5");

	assert(get_cod(l.v[0]) == 123);
	assert(get_stoc(l.v[0]) == 40);

	assert(stergere(&l, "124") == 2);
	assert(stergere(&l, "-124") == 0);

	assert(stergere(&l, "12.4") == 0);

	assert(stergere(&l, "123") == 1);
	assert(get_stoc(l.v[0]) == 0);

	distruge_lista(&l);
}

int sortare_nume_cresc(Lista* l, Lista* afis)
{
	if (l->nrElem == 0)
	{
		return 2;
	}

	//Medicament med;
	//char s[50], c[50];
	
	for (int i=0; i < l-> nrElem; i++)
	{
		save(afis, (l->v[i]));
	}
	/*
	for (int i=0; i < l->nrElem - 1; i++)
	{
		for (int j=i+1;  j< l->nrElem; j++)
		{
			get_nume(afis->v[i], c);
			get_nume(afis->v[j], s);

			if (strcmp(c, s) > 0)
			{
				med = afis->v[i];
				afis->v[i] = afis->v[j];
				afis->v[j] = med;
			}
		}
	}
	*/
	return 1;
}

void testare_sortare_nume_cresc()
{
	Lista l = creeaza_lista();
	Lista afis = creeaza_lista();

	assert( sortare_nume_cresc(&l, &afis) == 2);

	adauga(&l, "123", "20", "abc", "20.5");
	adauga(&l, "124", "30", "ebc", "20.5");
	adauga(&l, "125", "55", "dbc", "20.5");
	adauga(&l, "126", "20", "Abc", "20.5");

	assert(sortare_nume_cresc(&l, &afis) == 1);

	/*assert(get_cod(afis.v[0]) == 126);
	assert(get_cod(afis.v[1]) == 123);
	assert(get_cod(afis.v[2]) == 125);
	assert(get_cod(afis.v[3]) == 124);
	*/
	distruge_lista(&l);
	distruge_lista(&afis);
	
}

int sortare_nume_descresc(Lista* l, Lista* afis)
{
	if (l->nrElem == 0)
	{
		return 2;
	}

	afis->nrElem = 0;
	int i, j;
	Medicament med;
	char s[50], c[50];

	for (i = 0; i < l->nrElem; i++)
	{
		save(afis, l->v[i]);
	}

	for (i = 0; i < l->nrElem - 1; i++)
	{
		for (j = i + 1; j < l->nrElem; j++)
		{
			get_nume(afis->v[i], c);
			get_nume(afis->v[j], s);

			if (strcmp(c, s) < 0)
			{
				med = afis->v[i];
				afis->v[i] = afis->v[j];
				afis->v[j] = med;
			}
		}
	}

	return 1;
}

void testare_sortare_nume_descresc()
{
	Lista l = creeaza_lista();
	Lista afis = creeaza_lista();

	assert(sortare_nume_descresc(&l, &afis) == 2);

	adauga(&l, "123", "20", "abc", "20.5");
	adauga(&l, "124", "30", "cbc", "20.5");
	adauga(&l, "125", "55", "dbc", "20.5");
	adauga(&l, "126", "20", "aac", "20.5");

	assert(sortare_nume_descresc(&l, &afis) == 1);

	/*for (int i = 0; i < afis.nrElem; i++)
	{
		printf("%d", get_cod(afis.v[i]));
	}*/

	assert(get_cod(afis.v[0]) == 125);
	assert(get_cod(afis.v[1]) == 124);
	assert(get_cod(afis.v[2]) == 123);
	assert(get_cod(afis.v[3]) == 126);

	distruge_lista(&l);
	distruge_lista(&afis);
}

int sortare_stoc_cresc(Lista* l, Lista* afis)
{
	if (l->nrElem == 0)
	{
		return 2;
	}

	afis->nrElem = 0;
	int i, j;
	Medicament med;

	for (i = 0; i < l->nrElem; i++)
	{
		save(afis, l->v[i]);
	}

	for (i = 0; i < l->nrElem - 1; i++)
	{
		for (j = i + 1; j < l->nrElem; j++)
		{
			if (get_stoc(afis->v[i]) > get_stoc(afis->v[j]))
			{
				med = afis->v[i];
				afis->v[i] = afis->v[j];
				afis->v[j] = med;
			}
		}
	}

	return 1;
}

void testare_sortare_stoc_cresc()
{
	Lista l = creeaza_lista();
	Lista afis = creeaza_lista();

	assert(sortare_stoc_cresc(&l, &afis) == 2);

	adauga(&l, "123", "20", "abc", "20.5");
	adauga(&l, "124", "30", "cbc", "20.5");
	adauga(&l, "125", "55", "dbc", "20.5");
	adauga(&l, "126", "20", "Abc", "20.5");

	assert(sortare_stoc_cresc(&l, &afis) == 1);

	/*for (int i = 0; i < afis.nrElem; i++)
	{
		printf("%d", get_cod(afis.v[i]));
	}*/

	assert(get_cod(afis.v[0]) == 123);
	assert(get_cod(afis.v[1]) == 126);
	assert(get_cod(afis.v[2]) == 124);
	assert(get_cod(afis.v[3]) == 125);

	distruge_lista(&l);
	distruge_lista(&afis);
}

int sortare_stoc_descresc(Lista* l, Lista* afis)
{
	if (l->nrElem == 0)
	{
		return 2;
	}

	afis->nrElem = 0;
	int i, j;
	Medicament med;

	for (i = 0; i < l->nrElem; i++)
	{
		save(afis, l->v[i]);
	}

	for (i = 0; i < l->nrElem - 1; i++)
	{
		for (j = i + 1; j < l->nrElem; j++)
		{
			if (get_stoc(afis->v[i]) < get_stoc(afis->v[j]))
			{
				med = afis->v[i];
				afis->v[i] = afis->v[j];
				afis->v[j] = med;
			}
		}
	}

	return 1;
}

void testare_sortare_stoc_descresc()
{
	Lista l = creeaza_lista();
	Lista afis = creeaza_lista();

	assert(sortare_stoc_descresc(&l, &afis) == 2);

	adauga(&l, "123", "20", "abc", "20.5");
	adauga(&l, "124", "30", "cbc", "20.5");
	adauga(&l, "125", "55", "dbc", "20.5");
	adauga(&l, "126", "20", "Abc", "20.5");

	assert(sortare_stoc_descresc(&l, &afis) == 1);

	/*for (int i = 0; i < afis.nrElem; i++)
	{
		printf("%d", get_cod(afis.v[i]));
	}
	*/

	assert(get_cod(afis.v[0]) == 125);
	assert(get_cod(afis.v[1]) == 124);
	assert(get_cod(afis.v[2]) == 123);
	assert(get_cod(afis.v[3]) == 126);

	distruge_lista(&l);
	distruge_lista(&afis);
}

int filtr_dupa_stoc(Lista* l, Lista* afis, char stoc[50])
{
	if (l->nrElem == 0)
	{
		return 2;
	}

	afis->nrElem = 0;
	
	char e[256] = "\nErori: ";
	Medicament med;
	char* ptr;

	int i = validare2("123", stoc, "abc", "20", e);

	if (i == 1)
	{
		set_stoc(&med, strtol(stoc, &ptr, 10));

		for (i=0; i < l->nrElem; i++)
		{
			if (get_stoc(l->v[i]) < get_stoc(med))
			{
				save(afis, l->v[i]);
			}
		}
		
		return 1;
	}
	else
	{
		return 0;
	}
}

void test_filtrare_dupa_stoc()
{
	Lista l = creeaza_lista();
	Lista afis = creeaza_lista();

	assert(filtr_dupa_stoc(&l, &afis, "20") == 2);

	adauga(&l, "123", "20", "abc", "20.5");

	assert(get_cod(l.v[0]) == 123);

	assert(filtr_dupa_stoc(&l, &afis, "10") == 1);

	assert(afis.nrElem == 0);

	assert(filtr_dupa_stoc(&l, &afis, "-10") == 0);

	adauga(&l, "124", "30", "abc", "20.5");
	adauga(&l, "125", "55", "abc", "20.5");
	adauga(&l, "126", "20", "abc", "20.5");

	assert(l.nrElem == 4);
	
	assert(filtr_dupa_stoc(&l, &afis, "25") == 1);
	
	assert(afis.nrElem == 2);

	assert(get_cod(afis.v[0]) == 123);
	assert(get_cod(afis.v[1]) == 126);

	distruge_lista(&l);
	distruge_lista(&afis);
}

int filtr_dupa_nume(Lista* l, Lista* afis, char c)
{
	if (l->nrElem == 0)
	{
		return 2;
	}

	afis->nrElem = 0;
	int i;
	char n[50];

	for (i = 0; i < l->nrElem; i++)
	{
		get_nume(l->v[i], n);
		if (c == n[0])
		{
			save(afis, l->v[i]);
		}
	}

	return 1;
}

void test_filtrare_dupa_nume()
{
	Lista l = creeaza_lista();
	Lista afis = creeaza_lista();

	assert(filtr_dupa_nume(&l, &afis, 'c') == 2);

	adauga(&l, "123", "20", "bbc", "20.5");

	assert(get_cod(l.v[0]) == 123);

	assert(filtr_dupa_nume(&l, &afis, 'x') == 1);

	assert(afis.nrElem == 0);

	assert(filtr_dupa_nume(&l, &afis, '1') == 1);

	assert(afis.nrElem == 0);
	

	adauga(&l, "124", "30", "Bbc", "20.5");
	adauga(&l, "125", "55", "bbc", "20.5");
	adauga(&l, "126", "20", "bbc", "20.5");
	adauga(&l, "127", "55", "abc", "20.5");

	assert(l.nrElem == 5);

	assert(filtr_dupa_nume(&l, &afis, 'b') == 1);

	assert(afis.nrElem == 3);

	assert(get_cod(afis.v[0]) == 123);
	assert(get_cod(afis.v[2]) == 126);

	distruge_lista(&l);
	distruge_lista(&afis);
}

void test_service()
{
	test_adauga();
	test_actualizare();
	test_stergere();

	testare_sortare_nume_cresc();
	/*testare_sortare_nume_descresc();
	testare_sortare_stoc_cresc();
	testare_sortare_stoc_descresc();

	test_filtrare_dupa_stoc();
	test_filtrare_dupa_nume();
	*/
}
