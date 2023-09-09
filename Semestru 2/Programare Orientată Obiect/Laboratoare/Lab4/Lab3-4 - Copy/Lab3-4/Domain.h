#pragma once

typedef struct { int cod, cantitate; char nume[50]; float concentratie; } Medicament;

typedef struct { Medicament* v; int nrElem; int load; } Lista;

Lista creeaza_lista();

void distruge_lista(Lista* l);

/*
 * Functia seteaza codul unui medicament
 * med - medicament
 * cod - int
 * Nu se returneaza nimic
 */
void set_cod(Medicament* med, int cod);

/*
 * Functia seteaza concentratia unui medicament
 * med - medicament
 * conc - float
 * Nu se returneaza nimic
 */
void set_concentratie(Medicament* med, float conc);

/*
 * Functia seteaza stocul unui medicament
 * med - medicament
 * stoc - int
 * Nu se returneaza nimic
 */
void set_stoc(Medicament* med, int stoc);

/*
 * Functia seteaza numele unui medicament
 * med - medicament
 * num - char
 * Nu se returneaza nimic
 */
void set_nume(Medicament* med, char num[50]);

/*
 * Functia returneaza codul unui medicament
 * med - medicament
 * Se returneaza codul - int
 */
int get_cod(Medicament med);

/*
 * Functia returneaza stocul unui medicament
 * med - medicament
 * Se returneaza stocul - int
 */
int get_stoc(Medicament med);

/*
 * Functia returneaza concentratia unui medicament
 * med - medicament
 * Se returneaza concentratia - float
 */
float get_concentratie(Medicament med);

/*
 * Functia returneaza numele unui medicament
 * med - medicament
 * c - char
 * Se returneaza numele prin variabila c
 */
void get_nume(Medicament med, char c[50]);

/*
 * Functia valideaza atributele unui medicament
 * cod, cantitate, nume, concentratie - char
 * erori - char, va contine eventualele mesaje de eroare
 * Returneaza 1, daca atributele sunt valide, 0 altfel (plus mesaje de eroare in e)
 */
int validare2(char cod[50], char cantitate[50], char nume[50], char concentratie[50], char erori[256]);



void test_domain();

