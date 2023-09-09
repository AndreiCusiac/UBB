#pragma once
#include <stdbool.h>

/*
*Aceasta functie veirifica daca au aceleasi elemente
* v1,v2- vectori de tip int cu 10 elemnte
* Returneaza true, daca vectorii sunt egali, false in caz contrar
*/
bool compara_vectori(int v1[10], int v2[10]);

/*
*Aceasta functie calculeaza media elementelor unui vector de o anumita lungime
* v - vector de tip in
* n- de tip int
* Returneaza un float care reprezinta media elementelor din vectr
*/
float media(int v[], float n);