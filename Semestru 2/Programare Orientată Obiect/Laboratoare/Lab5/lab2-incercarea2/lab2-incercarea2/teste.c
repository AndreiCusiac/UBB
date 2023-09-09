#include "teste.h"


void test_creeaza_participant() {
	Participant* pt;
	int v[] = { 10, 10, 9, 9, 8, 8, 7, 7, 6, 6 };
	pt = creeaza_participant("maricel", "costel", v);
	assert(strcmp(pt->nume, "maricel") == 0);
	assert(strcmp(pt->prenume, "costel") == 0);
	assert(compara_vectori(pt->scor, v) == true);
	distruge_participant(pt);
}

void test_creeaza_lista() {
	Lista l;
	l = creeaza_lista_participanti();
	assert(*(l.numar_elemente) == 0);
	assert(*(l.memorie_maxima) == 10);
	assert(get_memorie_maxima(&l) == 10);
	assert(get_numar_participanti(&l) == 0);
	l=set_memorie_maxima(&l, 20);
	assert(get_memorie_maxima(&l) == 20);

	distruge_lista(&l);

}


void test_getter_setter_participant() {
	Participant* pt;
	int v[] = { 10, 10, 9, 9, 8, 8, 7, 7, 6, 6 };
	pt = creeaza_participant("maricel", "costel", v);
	///Getter
	assert(strcmp((char*)(get_nume(pt)), "maricel") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "costel") == 0);
	assert(get_scor_problema(pt, 5) == 8);
	assert(compara_vectori(get_scor(pt), v));
	///Setter

	pt=set_nume(pt, "andrei");
	assert(strcmp((char*)get_nume(pt), "andrei") == 0);
	pt=set_prenume(pt, "marica");
	assert(strcmp((char*)(get_prenume(pt)), "marica") == 0);
	pt=set_scor_problema(pt, 5, 10);
	assert(get_scor_problema(pt, 5) == 10);
	int v1[] = { 10,10,1,1,2,2,3,3,4,4 };
	pt=set_scor(pt, v1);
	assert(compara_vectori(get_scor(pt), v1) == true);
	distruge_participant(pt);
}

void test_adauga_participant() {
	Participant* pt;
	int v[] = { 10,5,6,3,7,9,2,10,5,8 };
	pt = creeaza_participant("marin", "cornel", v);
	Lista ls;
	ls = creeaza_lista_participanti();
	ls = adauga_participant(&ls, pt);
	assert(get_numar_participanti(&ls) == 1);
	assert(get_memorie_maxima(&ls) == 10);
	Participant* pt1;
	pt = get_participant_adresa_cu_pozitie(&ls, 0);
	assert(strcmp((char*)(get_nume(pt)), "marin") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "cornel") == 0);
	assert(get_scor_problema(pt, 5) == 7);
	assert(compara_vectori(get_scor(pt), v));

	pt1 = get_participant_adresa_cu_pozitie(&ls, 0);
	assert(strcmp((char*)(get_nume(pt1)), "marin") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "cornel") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));
	/// adaug alt participant
	
	Participant* pt2;
	pt2 = creeaza_participant("andrei", "burca", v);
	ls = adauga_participant(&ls, pt2);
	assert(get_numar_participanti(&ls) == 2);
	///primul participant
	pt1 = get_participant_adresa_cu_pozitie(&ls, 0);
	assert(strcmp((char*)(get_nume(pt1)), "marin") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "cornel") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));

	///al doilea participant
	pt1 = get_participant_adresa_cu_pozitie(&ls, 1);
	assert(strcmp((char*)(get_nume(pt1)), "andrei") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "burca") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));

	/// adaug alti 8 participanti
	Participant *pt3, *pt4, *pt5, *pt6, *pt7, *pt8, *pt9, *pt10, *pt11;
	pt3 = creeaza_participant("iulia", "bira", v);
	ls = adauga_participant(&ls, pt3);
	pt4 = creeaza_participant("carmen", "burca", v);
	ls = adauga_participant(&ls, pt4);
	pt5 = creeaza_participant("vio", "ica", v);
	ls = adauga_participant(&ls, pt5);
	pt6 = creeaza_participant("caine", "mort", v);
	ls = adauga_participant(&ls, pt6);

	pt1 = get_participant_adresa_cu_pozitie(&ls, get_numar_participanti(&ls) - 1);

	assert(strcmp((char*)(get_nume(pt1)), "caine") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "mort") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));

	pt7 = creeaza_participant("baba", "mea", v);
	ls = adauga_participant(&ls, pt7);
	pt8 = creeaza_participant("maine", "nu", v);
	ls = adauga_participant(&ls, pt8);
	pt9 = creeaza_participant("pop", "andreea", v);
	ls = adauga_participant(&ls, pt9);
	pt10 = creeaza_participant("baciu", "lucian", v);
	ls = adauga_participant(&ls, pt10);

	///teste critice

	assert(get_numar_participanti(&ls) == 10);
	assert(get_memorie_maxima(&ls) == 10);


	///date ultim participant
	pt1 = get_participant_adresa_cu_pozitie(&ls, get_numar_participanti(&ls)-1);
	assert(strcmp((char*)(get_nume(pt1)), "baciu") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "lucian") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));


	///TEST CRITIC PT ALOCARE MEMORIE
	pt11 = creeaza_participant("marcica", "vasilica", v);
	ls = adauga_participant(&ls, pt11);
	assert(get_numar_participanti(&ls) == 11);
	assert(get_memorie_maxima(&ls) == 20);

	///date ultim participant
	pt1 = get_participant_adresa_cu_pozitie(&ls, get_numar_participanti(&ls) - 1);
	assert(strcmp((char*)(get_nume(pt1)), "marcica") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "vasilica") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));

	///date participant 6
	pt1 = get_participant_adresa_cu_pozitie(&ls, 5);
	assert(strcmp((char*)(get_nume(pt1)), "caine") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "mort") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));

	for (int i=0; i < get_numar_participanti(&ls); i++)
	{
		printf("%s\n", get_nume(get_participant_adresa_cu_pozitie(&ls, i)));
	}
	
	distruge_lista_participanti(&ls);
	distruge_lista(&ls);
}

void test_cauta_participant() {
	Participant* pt;
	int v[] = { 10,5,6,3,7,9,2,10,5,8 };
	pt = creeaza_participant("marin", "cornel", v);
	Lista ls;
	ls = creeaza_lista_participanti();
	ls = adauga_participant(&ls, pt);
	int poz = cauta_participant(&ls, "marin", "cornel");
	assert(poz != -1);

	///Cauta participant existent
	Participant* pt1;
	pt1 = get_participant_adresa_cu_pozitie(&ls, poz);
	assert(strcmp((char*)(get_nume(pt1)), "marin") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "cornel") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));

	///Cauta participant inexistent
	poz = cauta_participant(&ls, "florin", "gigel");
	assert(poz == -1);

	///Adaug alti 10 participanti
	Participant *pt2,*pt3, *pt4, *pt5, *pt6, *pt7, *pt8, *pt9, *pt10, *pt11;
	pt2 = creeaza_participant("bolgo", "miste", v);
	ls = adauga_participant(&ls, pt2);
	pt3 = creeaza_participant("iulia", "bira", v);
	ls = adauga_participant(&ls, pt3);
	pt4 = creeaza_participant("carmen", "burca", v);
	ls = adauga_participant(&ls, pt4);
	pt5 = creeaza_participant("vio", "ica", v);
	ls = adauga_participant(&ls, pt5);
	pt6 = creeaza_participant("caine", "mort", v);
	ls = adauga_participant(&ls, pt6);
	pt7 = creeaza_participant("baba", "mea", v);
	ls = adauga_participant(&ls, pt7);
	pt8 = creeaza_participant("maine", "nu", v);
	ls = adauga_participant(&ls, pt8);
	pt9 = creeaza_participant("pop", "andreea", v);
	ls = adauga_participant(&ls, pt9);
	pt10 = creeaza_participant("baciu", "lucian", v);
	ls = adauga_participant(&ls, pt10);
	int v1[] = {1,1,1,1,1,1,1,1,1,1 };
	pt11 = creeaza_participant("marcica", "vasilica", v1);
	ls = adauga_participant(&ls, pt11);


	poz = cauta_participant(&ls, "marcica", "vasilica");
	pt1 = get_participant_adresa_cu_pozitie(&ls, poz);
	assert(strcmp((char*)(get_nume(pt1)), "marcica") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "vasilica") == 0);
	assert(get_scor_problema(pt1, 10) == 1);
	assert(compara_vectori(get_scor(pt1), v1));
	distruge_lista_participanti(&ls);
	distruge_lista(&ls);

}

void test_sterge_participant() {
	Participant *pt;
	int v[] = { 10,5,6,3,7,9,2,10,5,8 };
	pt = creeaza_participant("marin", "cornel", v);
	Lista ls;
	ls = creeaza_lista_participanti();
	ls = adauga_participant(&ls, pt);

	///Prima Stergere
	ls = sterge_participant(&ls, pt);
	assert(get_memorie_maxima(&ls) == 10);
	assert(get_numar_participanti(&ls) == 0);

	///Adaugam 11 participanti
	ls = adauga_participant(&ls, pt);
	Participant *pt2, *pt3, *pt4, *pt5, *pt6, *pt7, *pt8, *pt9, *pt10, *pt11;
	pt2 = creeaza_participant("bolgo", "miste", v);
	ls = adauga_participant(&ls, pt2);
	pt3 = creeaza_participant("iulia", "bira", v);
	ls = adauga_participant(&ls, pt3);
	pt4 = creeaza_participant("carmen", "burca", v);
	ls = adauga_participant(&ls, pt4);
	pt5 = creeaza_participant("vio", "ica", v);
	ls = adauga_participant(&ls, pt5);
	pt6 = creeaza_participant("caine", "mort", v);
	ls = adauga_participant(&ls, pt6);
	pt7 = creeaza_participant("baba", "mea", v);
	ls = adauga_participant(&ls, pt7);
	pt8 = creeaza_participant("maine", "nu", v);
	ls = adauga_participant(&ls, pt8);
	pt9 = creeaza_participant("pop", "andreea", v);
	ls = adauga_participant(&ls, pt9);
	pt10 = creeaza_participant("baciu", "lucian", v);
	ls = adauga_participant(&ls, pt10);
	int v1[] = { 10,10,10,10,10,10,10,10,10,1 };
	pt11 = creeaza_participant("marcica", "vasilica", v1);
	ls = adauga_participant(&ls, pt11);
	assert(get_numar_participanti(&ls) == 11);
	assert(get_memorie_maxima(&ls) == 20);


	ls = sterge_participant(&ls, pt9);
	distruge_participant(pt9);
	assert(get_numar_participanti(&ls) == 10);
	assert(get_memorie_maxima(&ls) == 20);
	int poz = cauta_participant(&ls, "pop", "andreea");
	assert(poz == -1);
	ls = sterge_participant(&ls, pt10);
	distruge_participant(pt10);
	poz = cauta_participant(&ls, "baciu", "lucian");
	assert(poz == -1);
	assert(get_numar_participanti(&ls) == 9);
	assert(get_memorie_maxima(&ls) == 10);
	distruge_lista_participanti(&ls);
	distruge_lista(&ls);
	
}

void test_get_date_participant() {
	Participant *pt, *pt1;
	int v[] = { 10,5,6,3,7,9,2,10,5,8 };
	pt = creeaza_participant("marin", "cornel", v);
	Lista ls;
	ls = creeaza_lista_participanti();
	ls = adauga_participant(&ls, pt);
	pt1 = get_date_participant_cu_pozitie(&ls, 0);

	assert(strcmp((char*)(get_nume(pt1)), "marin") == 0);
	assert(strcmp((char*)(get_prenume(pt1)), "cornel") == 0);
	assert(get_scor_problema(pt1, 5) == 7);
	assert(compara_vectori(get_scor(pt1), v));
	distruge_participant(pt);
	distruge_lista(&ls);
}
void test_media() {
	int v[] = { 10,10,10,10 };
	assert((int)media(v, 4) == 10);
	int v1[] = { 10,1,1,10 };
	assert((int)media(v1, 4) == 5);
}

void test_valideaza() {
	Lista ls;
	ls = creeaza_lista_participanti();
	char nume[20] = "mama";
	char prenume[20] = "sat";
	int v[] = { 1,1,1,1,1,1,1,1,1,1 };
	assert(valideaza(&ls,nume, prenume, v) == 0);
	strcpy_s(nume, sizeof(nume), "pap1");
	assert(valideaza(&ls,nume, prenume, v) == 1);
	strcpy_s(nume, sizeof(nume), "pap");
	strcpy_s(prenume, sizeof(prenume), "mama1");
	assert(valideaza(&ls,nume, prenume, v) == 2);
	strcpy_s(nume, sizeof(nume), "pap1");
	assert(valideaza(&ls,nume, prenume, v) == 1);
	int v1[] = { 1,1,1,1,1,-1,1,1,1,1 };
	strcpy_s(nume, sizeof(nume), "pap");
	strcpy_s(prenume, sizeof(prenume), "mama");
	assert(valideaza(&ls,nume, prenume, v1) == 3);
	Participant* pt;
	pt = creeaza_participant(nume, prenume, v1);
	assert(valideaza(&ls, nume, prenume, v1) == 3);
	distruge_participant(pt);
	distruge_lista(&ls);
}

void test_citeste_fisier() {
	Lista ls;
	ls = creeaza_lista_participanti();
	ls = citeste_fisier(ls, "test.txt");
	int v[] = { 1,2,3,4,5,6,7,8,9,10 };
	Participant *pt = get_date_participant_cu_pozitie(&ls, 0);
	assert(strcmp((char*)(get_nume(pt)), "mama") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "mea") == 0);
	assert(get_scor_problema(pt, 5) == 5);
	assert(compara_vectori(get_scor(pt), v));
	distruge_lista_participanti(&ls);
	distruge_lista(&ls);
}

void test_adauga_cautare_sterge_controller() {
	Lista ls;
	ls = creeaza_lista_participanti();
	int v[] = { 1,2,1,2,1,2,1,2,1,2 };
	ls = adauga_in_lista(ls, "mada", "carmen", v);
	int poz = cauta_participant_in_lista(ls, "mada", "carmen");
	Participant* pt = get_participant_adresa_cu_pozitie(&ls, poz);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	assert(get_scor_problema(pt, 5) == 1);
	assert(compara_vectori(get_scor(pt), v));
	ls = sterge_din_lista(ls, poz);
	poz = cauta_participant_in_lista(ls, "mada", "carmen");
	assert(poz == -1);
	distruge_lista(&ls);
}
void test_actualizare() {
	Lista ls;
	ls = creeaza_lista_participanti();
	int v[] = { 1,2,1,2,1,2,1,2,1,2 };
	ls = adauga_in_lista(ls, "mada", "carmen", v);
	ls = actualizare_problema(ls, 0, 4, 9);
	int v1[] = { 1,2,1,9,1,2,1,2,1,2 };
	Participant *pt = get_participant_adresa_cu_pozitie(&ls, 0);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	assert(get_scor_problema(pt, 4) == 9);
	assert(compara_vectori(get_scor(pt), v1));
	int v2[] = { 1,1,1,1,1,1,1,1,1,1 };
	ls = actualizare_scor(ls, 0, v2);
	pt = get_participant_adresa_cu_pozitie(&ls, 0);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	assert(get_scor_problema(pt, 4) == 1);
	assert(compara_vectori(get_scor(pt), v2));
	distruge_lista_participanti(&ls);
	distruge_lista(&ls);
}

void test_filtru() {
	Lista ls;
	ls = creeaza_lista_participanti();
	int v[] = { 1,2,1,2,1,2,1,2,1,2 };//1,5
	ls = adauga_in_lista(ls, "mada", "carmen", v);
	int v1[] = { 3, 1, 4, 5, 6, 8, 2, 9, 10, 1 };//4,9
	ls = adauga_in_lista(ls, "andreea", "madam", v1);
	int v2[] = {5, 5, 5, 5, 5, 5, 5, 5, 5, 5};//5.0
	ls = adauga_in_lista(ls, "mandre", "ahim", v2);
	int v3[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };//10.0
	ls = adauga_in_lista(ls, "mihnea", "stefan", v3);
	Lista filt;
	///scor
	filt = creeaza_lista_participanti();
	filt = aplicare_filtru_scor(filt, ls,(float)5.1);
	assert(get_numar_participanti(&filt) == 3);
	Participant *pt = get_date_participant_cu_pozitie(&filt, 0);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	pt = get_date_participant_cu_pozitie(&filt, 1);
	assert(strcmp((char*)(get_nume(pt)), "andreea") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "madam") == 0);
	pt = get_date_participant_cu_pozitie(&filt, 2);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "ahim") == 0);
	distruge_lista(&filt);

	///litera
	filt = creeaza_lista_participanti();
	filt = aplicare_filtru_litera(filt, ls, 'm');
	assert(get_numar_participanti(&filt) == 3);
	pt = get_date_participant_cu_pozitie(&filt, 0);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	pt = get_date_participant_cu_pozitie(&filt, 1);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "ahim") == 0);
	pt = get_date_participant_cu_pozitie(&filt, 2);
	assert(strcmp((char*)(get_nume(pt)), "mihnea") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "stefan") == 0);
	distruge_lista(&filt);
	distruge_lista_participanti(&ls);
	distruge_lista(&ls);
}
void test_sort() {
	Lista ls;
	ls = creeaza_lista_participanti();
	int v[] = { 1,2,1,2,1,2,1,2,1,2 };//1,5
	ls = adauga_in_lista(ls, "mada", "carmen", v);
	int v1[] = { 3, 1, 4, 5, 6, 8, 2, 9, 10, 1 };//4,9
	ls = adauga_in_lista(ls, "andreea", "madam", v1);
	int v3[] = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };//10.0
	ls = adauga_in_lista(ls, "mandre", "stefan", v3);
	int v2[] = { 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };//5.0
	ls = adauga_in_lista(ls, "mandre", "ahim", v2);
	Lista sort;
	sort = creeaza_lista_participanti();
	Participant *pt;
	/// alfabetic crescator
	sort = sortare_lista_alfabetic(sort, ls, 1,compara_string);
	assert(get_numar_participanti(&sort) == 4);
	pt = get_date_participant_cu_pozitie(&sort, 0);
	assert(strcmp((char*)(get_nume(pt)), "andreea") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "madam") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 1);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 2);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "ahim") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 3);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "stefan") == 0);
	distruge_lista(&sort);


	/// alfabetic descrescator
	sort = creeaza_lista_participanti();
	sort = sortare_lista_alfabetic(sort, ls, 0,compara_string);
	assert(get_numar_participanti(&sort) == 4);
	pt = get_date_participant_cu_pozitie(&sort, 0);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "stefan") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 1);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "ahim") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 2);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 3);
	assert(strcmp((char*)(get_nume(pt)), "andreea") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "madam") == 0);
	distruge_lista(&sort);

	///medie descrescator
	sort = creeaza_lista_participanti();
	sort = sortare_lista_medie(sort, ls, 1,compara_scor);
	assert(get_numar_participanti(&sort) == 4);
	pt = get_date_participant_cu_pozitie(&sort, 0);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "stefan") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 1);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "ahim") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 2);
	assert(strcmp((char*)(get_nume(pt)), "andreea") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "madam") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 3);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	distruge_lista(&sort);

	///medie crescator
	sort = creeaza_lista_participanti();
	sort = sortare_lista_medie(sort, ls, 0,compara_scor);
	assert(get_numar_participanti(&sort) == 4);
	pt = get_date_participant_cu_pozitie(&sort, 0);
	assert(strcmp((char*)(get_nume(pt)), "mada") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "carmen") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 1);
	assert(strcmp((char*)(get_nume(pt)), "andreea") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "madam") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 2);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "ahim") == 0);
	pt = get_date_participant_cu_pozitie(&sort, 3);
	assert(strcmp((char*)(get_nume(pt)), "mandre") == 0);
	assert(strcmp((char*)(get_prenume(pt)), "stefan") == 0);
	distruge_lista(&sort);

	distruge_lista_participanti(&ls);
	distruge_lista(&ls);
}

void toate_testele() {
	
	//printf("da2\n");
	test_creeaza_participant();
	//printf("done1\n");
	test_getter_setter_participant();
	//printf("done2\n");
	test_creeaza_lista();
	//printf("done3\n");
	test_adauga_participant();
	//printf("done4\n");
	/*test_cauta_participant();
	//printf("done5\n");
	test_sterge_participant();
	//printf("done6\n");
	test_get_date_participant();
	//printf("done7\n");
	test_media();
	//printf("done8\n");
	test_valideaza();
	//printf("done9\n");
	test_citeste_fisier();
	//printf("done10\n");
	test_adauga_cautare_sterge_controller();
	//printf("done11\n");
	test_actualizare();
	//printf("done12\n");
	test_filtru();
	//printf("done13\n");
	test_sort();
	//printf("done14\n");
	*/
}