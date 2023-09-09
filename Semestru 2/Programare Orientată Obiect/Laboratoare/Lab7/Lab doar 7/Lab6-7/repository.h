#pragma once
#include "domain.h"

#include <vector>

#include "Template.h"

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
	const MyVectorT<Carte>& get_all() const noexcept;

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
	
private:

	MyVectorT<Carte> all;
	
};

class Iterator;

class MyVector
{
private:
	int load;
	int nrElem;

	Carte* v;

public:

	MyVector() : load{ 10 }, nrElem{ 0 }, v{ new Carte[load] }
	{
		
	}

	//Rule of 3

	// Constructor de copiere
	
	MyVector(const MyVector& ot);

	// Operator =

	MyVector& operator=(const MyVector& ot);
	
	void push_back(const Carte& c);

	// Destructor

	int size() const;

	~MyVector()
	{
		delete[] v;
	}

	Carte& get(const int poz) const;

	/*poate fara constul final*/
	void set(const int poz, const Carte& c);

	friend class Iterator;

	Iterator begin();

	Iterator end();
	
};

class Iterator
{
private:
	const MyVector& v;
	int poz = 0;

public:

	Iterator(const MyVector& v) noexcept : v{ v }
	{
		
	}
	
	Iterator(const MyVector& v, const int poz)noexcept : v{v}, poz{poz}
	{
		
	}

	bool valid() const;

	Carte& element() const;

	void next();
	
};

void test_repo();