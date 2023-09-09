

#include "repository.h"

Lista* creeaza_lista_participanti() {

	Lista* lista = malloc(sizeof(Lista));
	lista->lista_participanti = malloc(memorie_initializare * sizeof(ElemType));

	lista->numar_elemente = (int*)malloc(sizeof(int));
	if (lista->numar_elemente != NULL)*(lista->numar_elemente) = 0;

	lista->memorie_maxima = (int*)malloc(sizeof(int));
	if (lista->memorie_maxima != NULL)*(lista->memorie_maxima) = memorie_initializare;

	return lista;

}

Lista* realocare_memorie_lista(Lista* ls, int mem_max) {

	Lista* ls_copy = malloc(sizeof(Lista));
	ls_copy->lista_participanti = malloc(mem_max * sizeof(ElemType));
	
	for (int i = 0; i < get_numar_participanti(ls); i++)
		ls_copy->lista_participanti[i] = ls->lista_participanti[i];
	
	ls_copy->numar_elemente = (int*)malloc(sizeof(int));
	
	if (ls_copy->numar_elemente !=NULL)*(ls_copy->numar_elemente) = *(ls->numar_elemente);
	ls_copy->memorie_maxima = (int*)malloc(sizeof(int));
	
	if(ls_copy->memorie_maxima!=NULL)*(ls_copy->memorie_maxima) = mem_max;
	
	distruge_lista(ls);
	
	return ls_copy;
}

Lista* adauga_participant(Lista* ls, ElemType part) {
	if (get_numar_participanti(ls) == get_memorie_maxima(ls))
		ls = realocare_memorie_lista(ls, 2 * get_memorie_maxima(ls));
	
	ls->lista_participanti[get_numar_participanti(ls)] = part;
	
	*(ls->numar_elemente) = *(ls->numar_elemente) + 1;
	
	return ls;
}

int cauta_participant(Lista* ls, char nume[20], char prenume[20]) {
	/*
	*Aceasta functie cauta un participant in lista de participanti
	*ls este de tip Lista, nume si prenume de tip string
	*Programul returnezeaza pozitia in lista a participantului in cazul in care se afla in lista sau -1 in caz contrar
	*/

	int i;
	bool gasit = false;
	for (i = 0; i < get_numar_participanti(ls) && gasit == false; i++)
	{
		Participant* pt;
		pt = get_participant_adresa_cu_pozitie(ls, i);
		if (strcmp(get_nume(pt), nume) == 0 && strcmp(get_prenume(pt), prenume) == 0)
		{
			gasit = true;
			return i;
		}
	}
	return -1;
}

Lista* sterge_participant(Lista* ls, Participant* part) {
	int i, poz = -1;
	for (i = 0; i < get_numar_participanti(ls); i++)
	{

		Participant* pt;
		pt = get_participant_adresa_cu_pozitie(ls, i);
		if (strcmp(get_nume(pt), get_nume(part)) == 0 && strcmp(get_prenume(pt), get_prenume(part)) == 0)
		{
			poz = i;
		}
	}
	if (poz != -1)
	{
		for (i = poz; i < get_numar_participanti(ls); i++)
			ls->lista_participanti[i] = ls->lista_participanti[i + 1];
		*(ls->numar_elemente) = *(ls->numar_elemente) - 1;
	}

if ((get_numar_participanti(ls) < (get_memorie_maxima(ls) / 2)) && (get_memorie_maxima(ls) > memorie_initializare)) {
	ls = realocare_memorie_lista(ls, get_memorie_maxima(ls) / 2);
}
return ls;
}

Lista* citeste_fisier(Lista* ls, char* nume_fis) {
	FILE* fis;
	int ok = 0;
	fis = fopen(nume_fis, "r");
	int numar;
	ok = fscanf(fis, "%d", &numar);
	for (int i = 0; i < numar; i++) {
		char nume[20], prenume[20];
		int scor[20];
		ok = fscanf(fis, "%s", &nume);
		ok = fscanf(fis, "%s", &prenume);
		for (int j = 0; j < 10; j++) {
			ok = fscanf(fis, "%d", &scor[j]);
		}
		Participant* pt;
		pt = creeaza_participant(nume, prenume, scor);
		ls = adauga_participant(ls, pt);
	}
	fclose(fis);
	return ls;
}

Participant* get_participant_adresa_cu_pozitie(Lista* l, int poz) {
	return (l->lista_participanti[poz]);
}

///LISTA
int get_numar_participanti(Lista* l) {

	return *(l->numar_elemente);
}

int get_memorie_maxima(Lista* l) {
	return *(l->memorie_maxima);
}

Lista* set_memorie_maxima(Lista* ls, int memo_max) {
	*(ls->memorie_maxima) = memo_max;
	return ls;
}

Lista* copiaza_lista(Lista* ls)
{
	Lista* rez = creeaza_lista_participanti();

	for (int i=0; i< get_numar_participanti(ls); i++)
	{
		ElemType p = get_participant_adresa_cu_pozitie(ls, i);
		
		adauga_participant(rez, creeaza_participant(get_nume(p), get_prenume(p), get_scor(p)));
	}

	return rez;
}

void distruge_lista(Lista* list) {
	free(list->lista_participanti);
	free(list->numar_elemente);
	free(list->memorie_maxima);
	free(list);
}

void distruge_lista_participanti(Lista* ls) {
	for (int i = 0; i < get_numar_participanti(ls); i++) {
		distruge_participant(get_participant_adresa_cu_pozitie(ls, i));
	}
}

void distruge_lista_3(Lista* ls)
{
	distruge_lista_2(ls, distruge_participant);
}

void distruge_lista_2(Lista* ls, DestroyFunction destrF)
{
	for (int i = 0; i < get_numar_participanti(ls); i++) {
		destrF(ls->lista_participanti[i]);
	}

	free(ls->lista_participanti);
	free(ls->numar_elemente);
	free(ls->memorie_maxima);
	free(ls);
}

void distruge_lista_4(Lista* ls)
{
	distruge_lista_participanti(ls);
	distruge_lista(ls);
}
