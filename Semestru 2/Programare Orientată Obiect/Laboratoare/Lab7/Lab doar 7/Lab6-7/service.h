#pragma once
#include <functional>

#include "repository.h"
#include "validator.h"

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
	Service(Repository& rep, Validator& vald) : repo{ rep }, val{ vald }
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
	void actualizeaza(const string& tit, const string& aut, const string& g, const int& a, const string& tit1, const string& aut1, const string& g1, const int& a1);

	/*
	 * Functia sterge un obiect din service cu valorile din parametri
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia sterge obiectul c si returneaza true, false altfel
	 *
	 */
	void sterge(const string& tit, const string& aut, const string& g, const int& a);

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
	const MyVectorT<Carte>& get_carti() noexcept;

	MyVectorT<Carte> filtreaza(std::function<bool(const Carte&)> func);

	MyVectorT<Carte> filtreaza_titlu(const string& tit) { return filtreaza([tit](const Carte& c) {return c.get_titlu() == tit; }); }
	
	MyVectorT<Carte> filtreaza_an(const int& a) { return filtreaza([a](const Carte& c) {return c.get_an() == a; }); }

	MyVectorT<Carte> sorteaza1(bool(*func)(const Carte&, const Carte&));

	//MyVectorT<Carte> sorteaza2(bool(*func)(const Carte&, const Carte&));

	MyVectorT<Carte> sorteaza_titlu();
	
	MyVectorT<Carte> sorteaza_autor();

	MyVectorT<Carte> sorteaza_an_gen();

private:

	Repository& repo;
	Validator& val;
	
};


void test_adaugare();

void test_actualizare();

void test_stergere();

void test_cautare();

void test_get_carti();

void test_service();

void test_filtrare();