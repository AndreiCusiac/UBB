#pragma once
#include <algorithm>
#include <functional>

#include "repository.h"
#include "validator.h"
#include "Undo.h"
#include <iostream>

#include <algorithm>
#include <random>
#include <chrono>
#include <map>
#include <fstream>
#include <iostream>

using namespace std;

class DTO
{
private:

	string gen;
	int nr;

public:

	DTO() {}

	DTO(const string& st) : gen{ st }, nr{ 1 } {}

	void increase()
	{
		nr++;
	}

	string get_gen_DTO() const
	{
		return gen;
	}

	int get_numar_DTO() const
	{
		return nr;
	}
};


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
	Service(AbstractRepo& rep, Validator& valid) : repo{ rep }, val{ valid }
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

	void adauga_aut(const string& tit, const string& aut, const string& g, const int& a);

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
	const vector<Carte> get_carti() noexcept;

	vector<Carte> filtreaza(std::function<bool(const Carte&)> func);

	vector<Carte> filtreaza_titlu(const string& tit) { return filtreaza([tit](const Carte& c) {return c.get_titlu() == tit; }); }

	vector<Carte> filtreaza_an(const int& a) { return filtreaza([a](const Carte& c) {return c.get_an() == a; }); }

	vector<Carte> sorteaza1(bool(*func)(const Carte&, const Carte&));

	//MyVectorT<Carte> sorteaza2(bool(*func)(const Carte&, const Carte&));

	vector<Carte> sorteaza_titlu();

	vector<Carte> sorteaza_autor();

	vector<Carte> sorteaza_an_gen();

	void adauga_wishlist(const string& tit, const string& aut, const string& g, const int& a);

	void adauga_random_wishlist(const int& nr);

	void sterge_wishlist();

	const vector<Carte>& get_wish() noexcept;

	map<string, DTO> getRaport() const;

	void exportCSV(const string& nume, const string& extensie);

	void exportCSVSimplu(const string& nume);

	void exportCSV_Carti(const string& nume, const string& extensie);

	void undo();

private:

	AbstractRepo& repo;
	Validator& val;
	vector<unique_ptr<ActiuneUndo>> undoActions;
};


void test_adaugare();

void test_actualizare();

void test_stergere();

void test_cautare();

void test_get_carti();

void test_filtrare();

void test_sortare();

void test_serv_wishlist();

void test_undo();

void test_service();