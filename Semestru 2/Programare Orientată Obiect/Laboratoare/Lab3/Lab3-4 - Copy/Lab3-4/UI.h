#pragma once

void test_all();

/*
 * Functia initializeaza o lista si apeleaza meniul
 */
void consola();

/*
 * Functia permite alegerea unor modificiari asupra listei de medicamente
 * l - lista de medicamente
 */
void meniu(Lista* l);

/*
 * Functia este un submeniu al functiei de adaugare
 * l - lista de medicamente
 */
void sub_adaugare(Lista* l);

/*
 * Functia permite citirea unor atribute ale unui medicament si adaugarea sa in lista
 * l - lista de medicamente
 * Afiseaza un mesaj de confirmare, daca adaugare are loc, erori altfel
 */
void citeste(Lista* l);

/*
 * Functia este un submeniu al functiei de stergere
 * l - lista de medicamente
 */
void sub_stergere(Lista* l);

/*
 * Functia permite stergerea stocului unui medicament din lista
 * l - lista de medicamente
 * Afiseaza un mesaj de confirmare, daca stergerea are loc, erori altfel
 */
void sterge(Lista* l);

/*
 * Functia este un submeniu al functiei de modificare
 * l - lista de medicamente
 */
void sub_modificare(Lista* l);

/*
 * Functia permite modificarea anumitor atribute ale unui medicament din lista
 * l - lista de medicamente
 * Afiseaza un mesaj de confirmare, daca adaugare are loc, erori altfel
 */
void modifica(Lista* l);

/*
 * Functia este un submeniu al functiei de afisare
 * l - lista de medicamente
 */
void sub_vizualizare(Lista* l);

/*
 * Functia permite afisarea medicamentelor din lista ordonate crescator dupa nume
 * l - lista de medicamente
 * Afiseaza lista ordonata, un mesaj de atentionare altfel
 */
void vizualizeaza_nume_crescator(Lista* l);

/*
 * Functia permite afisarea medicamentelor din lista ordonate descrescator dupa nume
 * l - lista de medicamente
 * Afiseaza lista ordonata, un mesaj de atentionare altfel
 */
void vizualizeaza_nume_descrescator(Lista* l);

/*
 * Functia permite afisarea medicamentelor din lista ordonate crescator dupa stoc
 * l - lista de medicamente
 * Afiseaza lista ordonata, un mesaj de atentionare altfel
 */
void vizualizeaza_stoc_crescator(Lista* l);

/*
 * Functia permite afisarea medicamentelor din lista ordonate descrescator dupa stoc
 * l - lista de medicamente
 * Afiseaza lista ordonata, un mesaj de atentionare altfel
 */
void vizualizeaza_stoc_descrescator(Lista* l);

/*
 * Functia este un submeniu al functiei de filtrare
 * l - lista de medicamente
 */
void sub_filtrare(Lista* l);

/*
 * Functia permite afisarea medicamentelor din lista al caror nume incepe cu o litera data
 * l - lista de medicamente
 * Afiseaza lista filtrata, un mesaj de atentionare altfel
 */
void filtreaza_nume(Lista* l);

/*
 * Functia permite afisarea medicamentelor din lista al caror stoc este mai mic decat un numar dat
 * l - lista de medicamente
 * Afiseaza lista filtrata, un mesaj de atentionare altfel
 */
void filtreaza_stoc(Lista* l);

/*
 * Functia este un submeniu al functiei de tiparire
 * l - lista de medicamente
 */
void sub_tiparire(Lista* l);

/*
 * Functia permite afisarea tutror medicamentelor din lista
 * l - lista de medicamente
 * Afiseaza lista, un mesaj de atentionare altfel
 */
void tipareste(Lista* l);
