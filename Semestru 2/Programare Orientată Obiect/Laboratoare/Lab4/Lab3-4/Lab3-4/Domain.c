#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "Domain.h"
#include "Utils.h"
#include "Repo.h"
#include "Service.h"
#include "UI.h"

Lista creeaza_lista()
{
	Lista l;
	l.load = 10;
	l.nrElem = 0;
	l.v = malloc(sizeof(Medicament) * l.load);

	return l;
}
	
void test_creeaza_lista()
{
	Lista l;
	l = creeaza_lista();

	assert(l.nrElem == 0);
	assert(l.load == 10);

	distruge_lista(&l);
}

void distruge_med(Medicament* med)
{
	free(med->nume);
}

void distruge_lista(Lista* l)
{
	for (int i=0; i < l->nrElem; i++)
	{
		distruge_med(&(l->v[i]));
	}

	free(l->v);

	l->v = NULL;
	l->nrElem = 0;
	
}

void set_cod(Medicament* med, int cod)
{
	med->cod = cod;
}

void set_concentratie(Medicament* med, float conc)
{
	med->concentratie = conc;
}

void set_stoc(Medicament* med, int stoc)
{
	med->cantitate = stoc;
}
/*
void creeaza_nume(Medicament med, int nr)
{
	med.nume = malloc(nr * sizeof(char));
}*/

void set_nume(Medicament* med, char num[50])
{
	/*
	Medicament m1;

	set_stoc(&m1, get_stoc(*med));
	set_cod(&m1, get_cod(*med));
	set_concentratie(&m1, get_concentratie(*med));

	//m1.nume = malloc(10 * sizeof(char));
	m1.nume = malloc((strlen(num) + 1) * sizeof(char));
	
	strcpy(m1.nume, num);
	
	distruge_med(med);

	printf("%d %d \n", get_cod(m1), get_stoc(m1));
	
	return m1;
	*/

	strcpy(med->nume, num);
}

int get_cod(Medicament med)
{
	return med.cod;
}

int get_stoc(Medicament med)
{
	return med.cantitate;
}

float get_concentratie(Medicament med)
{
	return med.concentratie;
}

void get_nume(Medicament med, char c[50])
{
	strcpy(c, med.nume);
}

void test_gettere_settere()
{
	Medicament m1;

	char c[50];

	set_cod(&m1, 123);

	assert(get_cod(m1) == 123);

	set_concentratie(&m1, 0.5);
	
	assert(get_concentratie(m1) == 0.5);

	m1.nume = malloc(10 * sizeof(char));
	set_nume(&m1, "alabala");	

	get_nume(m1, c);

	assert(strcmp(c, "alabala") == 0);
	
	set_stoc(&m1, 20);

	assert(get_stoc(m1) == 20);
	
}


int validare2(char cod[50], char cantitate[50], char nume[50], char concentratie[50], char erori[256])
{
	int c, s;
	float con;

	char* ptr;

	c = strtol(cod, &ptr, 10);
	s = strtol(cantitate, &ptr, 10);
	con = strtof(concentratie, &ptr);
	
	if (c <= 0)
	{
		strcat(erori, "\nCodul nu este valid!\n");
		return 0;;
	}

	
	if (strlen(cod) != numar_de_cifre(c)) 
	{
		strcat(erori,"\nCodul nu este valid!\n");
		return 0;;
	}

	if (s <= 0)
	{
		strcat(erori, "\nCantitatea nu este valida!\n");
		return 0;
	}

	if (strlen(cantitate) != numar_de_cifre(s))
	{
		strcat(erori, "\nCantitatea nu este valid!\n");
		return 0;;
	}

	
	if (con <= 0)
	{
		strcat(erori, "\nConcentratia nu este valida!\n");
		return 0;
	}

	if (strlen(nume) == 0)
	{
		strcat(erori, "\nNumele nu poate fi vid!\n");
		return 0;
	}
	
	return 1;
}

void test_validare2()
{
	char e[256]="\nErori:";
	
	assert(validare2("123", "20", "abc", "20.5", e) == 1);
	assert(validare2("123", "20", "", "20.5", e) == 0);
	assert(validare2("123", "20", "abc", "-20.5", e) == 0);
	assert(validare2("12.3", "20", "abc", "20.5", e) == 0);
	assert(validare2("1a23", "20", "abc", "20.5", e) == 0);
	assert(validare2("123", "-20", "abc", "20.5", e) == 0);
	//printf("%s", e);
}

void test_domain()
{
	test_validare2();
	test_gettere_settere();

	test_creeaza_lista();
}