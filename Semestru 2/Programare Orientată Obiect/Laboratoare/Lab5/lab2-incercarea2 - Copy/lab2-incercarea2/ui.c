
#define _CRT_SECURE_NO_WARNINGS
#include "ui.h"
void creeaza_meniu() {
	printf("\nIntroduceti una dintre optiunile de mai jos: \n");
	printf("1- pentru a adauga un participant \n");
	printf("2- pentru a sterge un participant \n");
	printf("3- pentru a afisa toti participantii \n");
	printf("4- pentru a vedea statusul unui participant \n");
	printf("5- pentru a modifica datele unui participant \n");
	printf("6- pentru a aplica un filtru \n");
	printf("7- pentru a sorta dupa un criteriu \n");
	printf("8- pentru a face undo \n");
	printf("9- afiseaza ce-i in undo \n");
	printf("0- pentru a iesi \n");
	printf("Optiunea dumneavoastra: ");
}
void meniu() {
	Lista* ls;
	Lista* undo = creeaza_lista_participanti();
	ls = creeaza_lista_participanti();
	ls = citeste_fisier(ls,"lista.txt");
	
	int optiune;
	while (1) {
		creeaza_meniu();
		scanf("%d", &optiune);
		if ((int)optiune != 10) {
			if (optiune < 0 || optiune>9) printf("Optiune invalida!\n");
			else {
				if (optiune == 1) {
					ls = adauga(ls, undo);
				}
				else if (optiune == 2)
					ls = sterge(ls, undo);
				else if (optiune == 3)
					afiseaza_toti(ls);
				else if (optiune == 4)
					afiseaza_date(ls);
				else if (optiune == 5)
					ls = actualizare(ls, undo);
				else if (optiune == 6)
					aplica_filtru(ls);
				else if (optiune == 7)
					sortare(ls);

				else if (optiune == 8)
					ls = fa_undo(undo, ls);

				else if (optiune == 9)
					afis_undo(undo);
				
				else if (optiune == 0) {
					distruge_lista_participanti(ls);
					distruge_lista(ls);

					distruge_lista_2(undo, distruge_lista_3);
					break;
				}
				else printf("In curs de constructie");
			}
		}
	}
}

Lista* adauga(Lista* ls, Lista* undo) {
	char nume[20], prenume[20];
	int scor[10];
	printf("Introduceti numele participantului (maxim 20 de caractere) :");
	scanf("%s", &nume);
	printf("Introduceti numele participantului (maxim 20 de caractere) :");
	scanf("%s", &prenume);
	printf("Introduceti cele 10 note ale participantului:");
	for (int i = 0; i < 10; i++) {
		int nota;
		scanf("%d", &nota);
		scor[i] = nota;
	}



	int verdict = valideaza(ls,nume, prenume, scor);
	if (verdict == 1) printf("Numele nu poate sa contina cifre sau alte caractere \n");
	else if (verdict == 2) printf("Prenumele nu poate sa contina cifre sau alte caractere \n");
	else if (verdict==3) printf("Notele trebuie sa fie cuprinse intre 1 si 10 \n");
	else if (verdict==4) printf("Participantul se afla deja in baza de date \n");
	else {
		int poz = cauta_participant_in_lista(ls, nume, prenume);
		if (poz != -1) printf("Participantul se afla deja in baza de date \n");
		else {

			adauga_participant(undo, copiaza_lista(ls));
			
			ls = adauga_in_lista(ls, nume, prenume, scor);

			
			printf("Participant adaugat cu succes\n");
		}
	}
	return ls;
}




Lista* sterge(Lista* ls, Lista* undo) {
	char nume[20], prenume[20];
	printf("Introduceti numele participantului (maxim 20 de caractere) :");
	scanf("%s", &nume);
	printf("Introduceti numele participantului (maxim 20 de caractere) :");
	scanf("%s", &prenume);
	int poz = cauta_participant_in_lista(ls, nume, prenume);
	if (poz == -1) printf("Participantul nu se afla in baza de date \n");
	else {
		adauga_participant(undo, copiaza_lista(ls));
		
		ls = sterge_din_lista(ls, poz, distruge_participant);

		printf("Participant sters cu succes\n");
	}
	return ls;
}

Lista* fa_undo(Lista* undo, Lista* ls)
{
	if (get_numar_participanti(undo) < 1)
	{
		printf("\nNu mai exista back-up-uri disponibile!\n");
	}
	else
	{
		distruge_lista_participanti(ls);
		distruge_lista(ls);
	
		ls = comanda_undo(undo);

		printf("\nLista curenta ar trebui sa fie:\n");

		afiseaza_toti(ls);
		
		printf("\nUndo realizat cu succes!\n");
	}

	return ls;
}

void afis_undo(Lista* undo)
{
	printf("\nComponentele liste undo sunt:\n");

	if (get_numar_participanti(undo) < 1)
	{
		printf("\nNu mai exista back-up-uri disponibile!\n");
	}
	else
	{
		for (int i = 0; i < get_numar_participanti(undo); i++)
		{
			printf("\nLista nr. %d:\n", i);
			afiseaza_toti(undo->lista_participanti[i]);
		}
	}
}

void afiseaza_toti(Lista* ls) {
	int i, j;
	if (get_numar_participanti(ls) != 0) {
		printf("\nSunt inscrisi %d participanti\n", get_numar_participanti(ls));
		for (i = 0; i < get_numar_participanti(ls); i++) {
			Participant* pt = get_date_participant_cu_pozitie(ls, i);
			printf("\nParticipantul cu numele %s si prenumele %s are urmatoarele note ", get_nume(pt), get_prenume(pt));
			for (j = 0; j < 10; j++)
				printf("%d ", get_scor_problema(pt, j + 1));
			printf("\n");
			printf("Media este %f", (float)media(get_scor(pt), 10));
			printf("\n");
		}
	}
	else printf("Nu exista participanti inscrisi\n");
}

void afiseaza_date(Lista* ls) {
	char nume[20], prenume[20];
	printf("Introduceti numele participantului (maxim 20 de caractere) :");
	scanf("%s", &nume);
	printf("Introduceti numele participantului (maxim 20 de caractere) :");
	scanf("%s", &prenume);
	int poz = cauta_participant(ls, nume, prenume);
	if (poz != -1) {
		Participant *pt = get_date_participant_cu_pozitie(ls, poz);
		printf("Participantul cu numele %s si prenumele %s are urmatoarele note ", get_nume(pt), get_prenume(pt));
		for (int j = 0; j < 10; j++)
			printf("%d ", get_scor_problema(pt, j + 1));
		printf("\n");
		printf("Media este %f", (float)media(get_scor(pt), 10));
		printf("\n");
	}
	else printf("Participantul nu se afla in baza de date \n");

}

Lista* actualizare(Lista* ls, Lista* undo) {
	char nume[20], prenume[20],optiune[3];
	printf("Introduceti numele participantului (maxim 20 de caractere) :");
	scanf("%s", &nume);
	printf("Introduceti numele participantului (maxim 20 de caractere) :");
	scanf("%s", &prenume);
	printf("Doriti sa actualizati numai o singura problema? ");
	scanf("%s", &optiune);
	if (strcmp(optiune, "da") == 0 || strcmp(optiune, "Da") == 0 || strcmp(optiune, "DA") == 0) {
		int prob, nota;
		printf("Introduceti numarul problemei : ");
		scanf("%d", &prob);
		printf("Introduceti nota problemei : ");
		scanf("%d", &nota);
		if (prob < 1 || prob>10) printf("Problema invalida!\n");
		else if (nota<1||nota >10)printf("Nota invalida!\n");
		else {
			int poz = cauta_participant_in_lista(ls, nume, prenume);
			if (poz == -1) printf("Participantul nu se afla in baza de date\n");
			else {
				adauga_participant(undo, copiaza_lista(ls));
				
				ls = actualizare_problema(ls, poz, prob, nota);

				
			}
		}
	}
	else if (strcmp(optiune, "nu") == 0 || strcmp(optiune, "Nu") == 0 || strcmp(optiune, "NU") == 0) {
		int scor[10],ok=0;
		for (int i = 0; i < 10; i++) {
			int nota;
			scanf("%d", &nota);
			if (nota < 1 || nota>10) {
				printf("Notele nu pot sa fie numere negative sau mai mari decat 10\n");
				ok = 1;
			}
			else scor[i] = nota;
		}
		if (ok == 0) {
			int poz = cauta_participant_in_lista(ls, nume, prenume);
			if (poz == -1) printf("Participantul nu se afla in baza de date\n");
			else {
				adauga_participant(undo, copiaza_lista(ls));
				
				ls = actualizare_scor(ls, poz, scor);

			}
		}
	}
	return ls;
}

void aplica_filtru(Lista* ls) {
	int optiune;
	printf("Introduceti :\n 1-pentru a filtra dupa un scor\n 2-pentru a filtra dupa o litera\n 3- pentru a filtra dupa o medie egala\n Optiunea dumneavoastra: ");
	scanf("%d", &optiune);
	if (optiune == 1) {
		float scor;
		printf("Introduceri scorul :");
		scanf("%f", &scor);
		Lista* filt;
		filt = creeaza_lista_participanti();
		filt = aplicare_filtru_scor(filt, ls, scor);
		afiseaza_toti(filt);
		distruge_lista(filt);
	}
	else if (optiune == 2) {
		char litera;
		scanf("%c", &litera);//pt a scapa de enter
		printf("Introduceri litera :");
		scanf("%c", &litera);
		Lista* filt;
		filt = creeaza_lista_participanti();
		filt = aplicare_filtru_litera(filt, ls, litera);
		afiseaza_toti(filt);
		distruge_lista(filt);
	}
	else if (optiune == 3)
	{
		float scor;
		printf("Introduceri scorul :");
		scanf("%f", &scor);
		Lista* filt;
		filt = creeaza_lista_participanti();
		filt = aplicare_filtru_scor_egal(filt, ls, scor);
		afiseaza_toti(filt);
		distruge_lista(filt);
	}
	else printf("Comanda invalida");
}

void sortare(Lista* ls) {
	int optiune;
	printf("Introduceti :\n 1-pentru a sorta alfabetic\n 2-pentru a sorta dupa medie\nOptiunea dumneavoastra: ");
	scanf("%d", &optiune);
	if (optiune == 1) {
		printf("Descrescator?(1-nu, 0-da)\nOptiunea dumneavoastra :");
		int alegere;
		scanf("%d", &alegere);
		Lista* sort;
		sort = creeaza_lista_participanti();
		sort = sortare_lista_alfabetic(sort, ls, alegere,compara_string);
		afiseaza_toti(sort);
		distruge_lista(sort);
	}
	else if (optiune == 2) {
		printf("Descrescator?(1-da, 0-nu)\nOptiunea dumneavoastra :");
		int alegere;
		scanf("%d", &alegere);
		Lista* sort;
		sort = creeaza_lista_participanti();
		sort = sortare_lista_medie(sort, ls, alegere,compara_scor);
		afiseaza_toti(sort);
		distruge_lista(sort);
	}
	else printf("Comanda invalida");
}