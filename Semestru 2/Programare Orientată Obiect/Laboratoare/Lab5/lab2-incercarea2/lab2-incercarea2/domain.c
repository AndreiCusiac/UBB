
#include "repository.h"
#define _CRT_SECURE_NO_WARNINGS
Lista creeaza_lista_participanti() {

	Lista lista;
	lista.lista_participanti = malloc(memorie_initializare * sizeof(ElemType));

	lista.numar_elemente = (int*)malloc(sizeof(int));
	if (lista.numar_elemente != NULL)*(lista.numar_elemente) = 0;

	lista.memorie_maxima = (int*)malloc(sizeof(int));
	if (lista.memorie_maxima != NULL)*(lista.memorie_maxima) = memorie_initializare;

	return lista;

}
Participant* creeaza_participant(char* nume, char* prenume, int* scor) {
	Participant* participant = malloc(sizeof(Participant));

		size_t nr = strlen(nume)+1;
		participant->nume =(char*)malloc(nr * sizeof(char));
		if (participant->nume!=0)strcpy_s(participant->nume,nr,nume);

		nr = strlen(prenume)+1;
		participant->prenume = (char*)malloc(nr*sizeof(char));
		if (participant->prenume != 0)strcpy_s(participant->prenume,nr, prenume);

		participant->scor = (int*)malloc(10*sizeof(int));
		for (int i=0;i<10;i++)
			participant->scor[i] = scor[i];

		return participant;

}



char* get_nume(Participant* part) {
	return part->nume;
}

Participant* set_nume(Participant* part, char nume[20]) {

	Participant* pt1;
	pt1 = creeaza_participant(nume, get_prenume(part), get_scor(part));
	distruge_participant(part);
	return pt1;
}

char* get_prenume(Participant* part) {
	return part->prenume;
}

Participant* set_prenume(Participant* part, char prenume[20]) {
	Participant* pt1;
	pt1 = creeaza_participant(get_nume(part), prenume, get_scor(part));
	distruge_participant(part);
	return pt1;
}

int get_scor_problema(Participant* part, int problema) {
	
	return part->scor[problema - 1];
}

Participant* set_scor_problema(Participant* part, int problema, int scor_problema) {

	Participant* pt1;
	int* scor = get_scor(part);
	scor[problema - 1] = scor_problema;
	pt1 = creeaza_participant(get_nume(part), get_prenume(part), scor);
	distruge_participant(part);
	return pt1;
}

int* get_scor(Participant* part) {
	return part->scor;
}

Participant* set_scor(Participant* part, int scor[10]) {

	Participant* pt1;
	pt1 = creeaza_participant(get_nume(part), get_prenume(part), scor);
	distruge_participant(part);
	return pt1;
}

///LISTA
int get_numar_participanti(Lista* l) {

	return *(l->numar_elemente);
}

int get_memorie_maxima(Lista* l) {
	return *(l->memorie_maxima);
}

Participant* get_participant_adresa_cu_pozitie(Lista* l, int poz) {
	return (l->lista_participanti[poz]);
}

Lista set_memorie_maxima(Lista* ls, int memo_max) {
	*(ls->memorie_maxima) = memo_max;
	return *ls;
}


///DISTRUGERI
void distruge_participant(Participant* part) {
	free(part->nume);
	free(part->prenume);
	free(part->scor);
	free(part);
}



