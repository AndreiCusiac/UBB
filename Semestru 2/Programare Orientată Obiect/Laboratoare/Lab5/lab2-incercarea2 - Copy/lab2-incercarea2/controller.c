#include "controller.h"


int valideaza(Lista* ls, char nume[20], char prenume[20], int scor[]) {
	///verificare litere
	for (unsigned int i = 0; i < strlen(nume); i++)
		if (strchr("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM", nume[i]) == NULL)
			return 1;

	for (unsigned int i = 0; i < strlen(prenume); i++)
		if (strchr("qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM- ", nume[i]) == NULL)
			return 2;
	for (int i = 0; i < 10; i++) {
		if (scor[i] < 1 || scor[i]>10)
			return 3;
	}
	int poz = cauta_participant(ls, nume, prenume);
	if (poz != -1) return 4;
	return 0;
}


Lista* adauga_in_lista(Lista* ls, char* nume, char* prenume, int* scor) {
	Participant* pt;
	pt = creeaza_participant(nume, prenume, scor);
	ls = adauga_participant(ls, pt);
	return ls;
}

int cauta_participant_in_lista(Lista* ls, char* nume, char* prenume) {
	int poz = cauta_participant(ls, nume, prenume);
	return poz;

}

Lista* sterge_din_lista(Lista* ls, int poz, DestroyFunction destF) {

	Participant* pt;
	pt = get_participant_adresa_cu_pozitie(ls, poz);
	ls = sterge_participant(ls, pt);
	destF(pt);
	//distruge_participant(pt);
	return ls;
}

Lista* comanda_undo(Lista* undo)
{
	Lista* copie = copiaza_lista(undo->lista_participanti[get_numar_participanti(undo) - 1]);
	sterge_din_lista(undo, get_numar_participanti(undo) - 1, distruge_lista_4);

	return copie;
}

Lista* actualizare_problema(Lista* ls,int poz, int prob,int nota) {
	Participant* pt;
	Participant* pt1;
	pt = get_participant_adresa_cu_pozitie(ls, poz);
	pt1 = creeaza_participant(get_nume(pt), get_prenume(pt), get_scor(pt));
	pt1 = set_scor_problema(pt1, prob, nota);
	ls = sterge_participant(ls, pt);
	distruge_participant(pt);
	ls = adauga_participant(ls, pt1);
	return ls;
}

Lista* actualizare_scor(Lista* ls, int poz, int* scor) {
	Participant *pt, *pt1;
	pt = get_participant_adresa_cu_pozitie(ls, poz);
	pt1 = creeaza_participant(pt->nume, pt->prenume, pt->scor);
	pt1 = set_scor(pt1, scor);
	ls = sterge_participant(ls, pt);
	distruge_participant(pt);
	ls = adauga_participant(ls, pt1);
	return ls;
}

Participant* get_date_participant_cu_pozitie(Lista* ls, int poz) {
	Participant* pt;
	pt = get_participant_adresa_cu_pozitie(ls, poz);
	return pt;
}

Lista* aplicare_filtru_scor(Lista* filt,Lista* ls, float scor) {
	for (int i = 0; i < get_numar_participanti(ls); i++) {
		Participant* pt = get_date_participant_cu_pozitie(ls, i);
		if (media(get_scor(pt),10) < scor)
			filt = adauga_participant(filt, pt);
	}
	return filt;
}

Lista* aplicare_filtru_scor_egal(Lista* filt, Lista* ls, float scor) {
	for (int i = 0; i < get_numar_participanti(ls); i++) {
		Participant* pt = get_date_participant_cu_pozitie(ls, i);
		if (media(get_scor(pt), 10) == scor)
			filt = adauga_participant(filt, pt);
	}
	return filt;
}

Lista* aplicare_filtru_litera(Lista* filt, Lista* ls, char litera) {
	for (int i = 0; i < get_numar_participanti(ls); i++) {
		Participant* pt = get_date_participant_cu_pozitie(ls, i);
		if (get_nume(pt)[0] == litera)
			filt = adauga_participant(filt, pt);
	}
	return filt;

}



Lista* sortare_lista_alfabetic(Lista* sort, Lista* ls, int mod, func_s functie) {
	for (int i = 0; i < get_numar_participanti(ls); i++) {
		Participant* pt = get_date_participant_cu_pozitie(ls, i);
		sort = adauga_participant(sort, pt);
	}
	///ordonare alfabetica
	if (mod == 1) {
		for ( int i=0;i<get_numar_participanti(sort)-1;i++)
			for (int j = i ; j < get_numar_participanti(sort); j++)
			{
				Participant* pt = get_date_participant_cu_pozitie(sort, i);
				Participant *pt1 = get_date_participant_cu_pozitie(sort, j);
				if((functie(get_nume(pt), get_nume(pt1))==1) || (functie(get_nume(pt), get_nume(pt1))==0 && functie(get_prenume(pt), get_prenume(pt1))==1)) {
					sort->lista_participanti[i] = pt1;
					sort->lista_participanti[j] = pt;
				}
			}
	}
	else if (mod == 0) {
		for (int i = 0; i < get_numar_participanti(ls) - 1; i++)
			for (int j = i ; j < get_numar_participanti(ls); j++)
			{
				Participant* pt = get_date_participant_cu_pozitie(sort, i);
				Participant *pt1 = get_date_participant_cu_pozitie(sort, j);
				if ((functie(get_nume(pt), get_nume(pt1))==-1) || (functie(get_nume(pt), get_nume(pt1)) == 0 && functie(get_prenume(pt), get_prenume(pt1))==-1)) {
					sort->lista_participanti[i] = pt1;
					sort->lista_participanti[j] = pt;
				}
			}
	}
	return sort;
}

Lista* sortare_lista_medie(Lista* sort, Lista* ls, int mod, func_m functie) {
	for (int i = 0; i < get_numar_participanti(ls); i++) {
		Participant* pt = get_date_participant_cu_pozitie(ls, i);
		sort = adauga_participant(sort, pt);
	}
	if (mod == 0) {
		for (int i = 0; i < get_numar_participanti(ls) - 1; i++)
			for (int j = i + 1; j < get_numar_participanti(ls); j++)
			{
				Participant* pt = get_date_participant_cu_pozitie(sort, i);
				Participant* pt1 = get_date_participant_cu_pozitie(sort, j);
				if (functie(media(get_scor(pt),10), media(get_scor(pt1),10),mod)) {
					sort->lista_participanti[i] = pt1;
					sort->lista_participanti[j] = pt;
				}
			}
	}
	else if (mod == 1) {
		for (int i = 0; i < get_numar_participanti(ls) - 1; i++)
			for (int j = i + 1; j < get_numar_participanti(ls); j++)
			{
				Participant* pt = get_date_participant_cu_pozitie(sort, i);
				Participant* pt1 = get_date_participant_cu_pozitie(sort, j);
				if (functie(media(get_scor(pt), 10), media(get_scor(pt1), 10),mod)) {
					sort->lista_participanti[i] = pt1;
					sort->lista_participanti[j] = pt;
				}
			}
	}
	return sort;
}



int compara_string(char* arg1, char* arg2) {
	if (strcmp(arg1, arg2) > 0) return 1;
	else if (strcmp(arg1, arg2) == 0) return 0;
	else return -1;

}

int compara_scor(float arg1, float arg2, int mod) {
	if (mod == 1) {
		if (arg1<arg2) return 1;
		return 0;}
	else {
		if (arg1 > arg2) return 1;
		return 0;
	}
}
