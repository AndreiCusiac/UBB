#pragma once
#include "domain.h"

#include <vector>

using std::vector;

class RepoException
{
public:

	/*
	 * Functie de constructor 
	 * 
	 */
	RepoException(const string& m);

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

class Repository
{
public:

	/*
	 * Constructor implicit
	 * 
	 */
	Repository() = default;

	/*
	 * Constructor de copiere
	 *
	 */
	Repository(const Repository& ot) = delete;

	/*
	 * Functia adauga in repository cartea c
	 * c - carte, parametru de intrare
	 * Functia adauga la finalul repository-ului obiectul c, altfel arunca eroare
	 *
	 */
	void store(const Carte& c);

	/*
	 * Functia stabileste daca cartea c exista in repository
	 * c - carte, parametru de intrare
	 * Functia returneaza true, daca gaseste c in repository, false altfel
	 *
	 */
	bool exista(const Carte& c);

	/*
	 * Functia returneaza obiectele memorate la un momentdat in repository
	 * Fara parametri de intrare
	 * Functia returneaza un vector de tip carte cu obiectele curente
	 *
	 */
	const vector<Carte>& get_all() const noexcept;

	/*
	 * Functia returneaza obiectul din repository care corespunde cu valorile parametrilor de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia returneaza un obiect carte, daca identificarea are loc, exceptie altfel
	 *
	 */
	const Carte& cauta(const string& tit, const string& aut, const string& g, const int& a);

	/*
	 * Functia sterge obiectul din repository care corespunde cu valorile parametrilor de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia sterge obiectul identificat din lista, exceptie altfel
	 *
	 */
	void sterge(const string& tit, const string& aut, const string& g, const int& a);

	void adauga_wishlist(const Carte& c);

	void goleste_wishlist();

	const vector<Carte>& get_wishlist() const noexcept;

	int get_wishlist_size() const noexcept;
	
private:

	vector<Carte> all;
	vector<Carte> wishlist;
	
};

void test_repo();

void test_wishlist();