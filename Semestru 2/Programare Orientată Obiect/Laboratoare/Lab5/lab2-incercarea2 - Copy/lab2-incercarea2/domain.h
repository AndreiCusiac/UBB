#pragma once

#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#define _CRT_SECURE_NO_WARNINGS
#define memorie_initializare 10



typedef struct {
	//int id;
	char* nume;
	char* prenume;
	int* scor;
}Participant;	

/*
* Aceasta functie aloca in memorie datele unui participant si creeaza entitatea in sine
* nume,prenume de tip string
* scor un vector cu 10 elemente de tip int
* Returneaza obiectul participant
*/
Participant* creeaza_participant(char* nume, char* prenume, int* scor);



/*
* Aceasta functie elibereaza din memorie datele unui participant 
* part este adresa obiectului participant
* Nu returneaza nimic
*/
void distruge_participant(Participant* part);

//ZONA GETTERE SI SETTERE
/*
* Aceasta functie returneaza numele unui participant
* part este adresa obiectului participant
* Returneaza o adresa catre zona in care este stocat numele participantului
*/
char* get_nume(Participant* part);

/*
* Aceasta functie actualizeaza numele unui participant
* part este adresa obiectului participant
* nume este un string
* Returneaza ebiectul participant cu numele modificat
*/
Participant* set_nume(Participant* part, char nume[20]);

/*
* Aceasta functie returneaza prenumele unui participant
* part este adresa obiectului participant
* Returneaza o adresa catre zona in care este stocat prenumele participantului
*/
char* get_prenume(Participant* part);

/*
* Aceasta functie actualizeaza prenumele unui participant
* part este adresa obiectului participant
* prenume este un string
* Returneaza ebiectul participant cu prenumele modificat
*/
Participant* set_prenume(Participant* part, char prenume[20]);

/*
* Aceasta functie returneaza nota la o problema a unui participant
* part este adresa obiectului participant
* prob de tip int
* Returneaza un int care reprezinta nota participantului la o problema
*/
int get_scor_problema(Participant* part, int problema);

/*
* Aceasta functie seteaza nota la o problema a unui participant
* part este adresa obiectului participant
* problema de tip int
* scor_problema de tip int
* Returneaza ebiectul participant 
*/
Participant* set_scor_problema(Participant* part, int problema, int scor_problema);

/*
* Aceasta functie returneaza scorul problemelor unui participant
* part este adresa obiectului participant
* Returneaza o adresa la care sunt stocate notele problemelor unui participant
*/
int* get_scor(Participant* part);





/*
* Aceasta functie actualizeaza notele problemelor unui participant
* part este adresa obiectului participant
* scor este un vector de 10 elemente
* Returneaza ebiectul participant cu scorul modificat
*/
Participant* set_scor(Participant* part, int scor[10]);


