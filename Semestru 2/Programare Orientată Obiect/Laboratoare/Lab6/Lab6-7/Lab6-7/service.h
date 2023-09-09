#pragma once
#include "repository.h"

class ServicException
{
public:
	
	/*
	 * Functie de constructor
	 *
	 */
	ServicException(const string& m);

	/*
	 * Functia returneaza mesaul de eroare memorat
	 * Fara parametri de intrare
	 * Returneaza un string, mesaj de eroare
	 *
	 */
	string get_eroare() const;

private:

	string eroare;

};


class Service
{
public:

	/*
	 * Constructor implicit
	 *
	 */
	Service() = default;

	/*
	 * Constructor explicit
	 *
	 */
	Service(Repository& rep) : repo{ rep }
	{
		
	}
	
	/*
	 * Constructor de copiere
	 *
	 */
	Service(const Service& ot) = delete;

	/*
	 * Functia adauga in obiectul service cartea data prin parametrii de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia salveaza obiectul c obtinut prin creearea unei carti, eroare altfel
	 * 
	 */
	void adauga(const string& tit, const string& aut, const string& g, const int& a);

	/*
	 * Functia actualizeaza un obiect din service cu noile valori din parametri
	 * tit, aut, g - stringuri, atribute ale cartii initiale
	 * a - int, anul cartii initiale
	 * tit1, aut1, g1 - stringuri, atribute ale cartii actualizate
	 * a1 - int, anul cartii actualizte
	 * Functia actualizeaza obiectul c si returneaza true, false altfel
	 *
	 */
	bool actualizeaza(const string& tit, const string& aut, const string& g, const int& a, const string& tit1, const string& aut1, const string& g1, const int& a1);

	/*
	 * Functia sterge un obiect din service cu valorile din parametri
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia sterge obiectul c si returneaza true, false altfel
	 *
	 */
	bool sterge(const string& tit, const string& aut, const string& g, const int& a);

	/*
	 * Functia cauta un obiect din service cu valorile din parametri
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia sterge obiectul c si returneaza true, false altfel
	 *
	 */
	bool cautare(const string& tit, const string& aut, const string& g, const int& a);

	/*
	 * Functia returneaza toate obiectele ce se afla la un momentdat in service
	 * Fara parametri de intrare
	 * Functia un vector de tip Carte continand toate cartile existente
	 *
	 */
	const vector<Carte>& get_carti() noexcept;

private:

	Repository& repo;
	
};


void test_adaugare();

void test_actualizare();

void test_stergere();

void test_cautare();

void test_get_carti();

void test_service();