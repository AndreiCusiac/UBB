#pragma once
#include <map>

#include "domain.h"

#include <vector>

#include <algorithm>
#include <random>
#include <chrono>
#include <map>
#include <fstream>
#include <iostream>

class Repository;
using std::vector;

class AbstractRepo
{

private:

	

public:

	virtual void store(const Carte& c) = 0;

	virtual void store_aut(const Carte& c) = 0;

	virtual bool exista(const Carte& c) = 0;

	virtual const vector<Carte> get_all() const noexcept = 0;

	virtual const Carte cauta(const string& tit, const string& aut, const string& g, const int& a) = 0;

	virtual void sterge(const string& tit, const string& aut, const string& g, const int& a) = 0;

	virtual void adauga_wishlist(const Carte& c) = 0;

	virtual void goleste_wishlist() = 0;

	virtual const vector<Carte>& get_wishlist() const noexcept = 0;

	virtual int get_wishlist_size() const noexcept = 0;

	bool incarcat_din_fisier{ false };

	virtual bool incarcat_fisier() = 0;
	
	virtual ~AbstractRepo() = default;
};

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

class Repository : public AbstractRepo
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

	bool incarcat_fisier() override
	{
		return incarcat_din_fisier;
	}

	/*
	 * Functia adauga in repository cartea c
	 * c - carte, parametru de intrare
	 * Functia adauga la finalul repository-ului obiectul c, altfel arunca eroare
	 *
	 */
	void store(const Carte& c) override;

	void store_aut(const Carte& c) override;

	/*
	 * Functia stabileste daca cartea c exista in repository
	 * c - carte, parametru de intrare
	 * Functia returneaza true, daca gaseste c in repository, false altfel
	 *
	 */
	bool exista(const Carte& c)  override;

	/*
	 * Functia returneaza obiectele memorate la un momentdat in repository
	 * Fara parametri de intrare
	 * Functia returneaza un vector de tip carte cu obiectele curente
	 *
	 */
	const vector<Carte> get_all() const noexcept override;

	/*
	 * Functia returneaza obiectul din repository care corespunde cu valorile parametrilor de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia returneaza un obiect carte, daca identificarea are loc, exceptie altfel
	 *
	 */
	const Carte cauta(const string& tit, const string& aut, const string& g, const int& a) override;

	/*
	 * Functia sterge obiectul din repository care corespunde cu valorile parametrilor de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia sterge obiectul identificat din lista, exceptie altfel
	 *
	 */
	void sterge(const string& tit, const string& aut, const string& g, const int& a) override;

	void adauga_wishlist(const Carte& c) override;

	void goleste_wishlist() override;

	const vector<Carte>& get_wishlist() const noexcept override;

	int get_wishlist_size() const noexcept override;

private:

	vector<Carte> all;
	vector<Carte> wishlist;

};

class FileRepository : public AbstractRepo
{
public:

	/*
	 * Constructor implicit
	 *
	 */
	FileRepository(const string& name)
	{
		nume = name;
	}

	bool incarcat_fisier() override
	{
		return incarcat_din_fisier;
	}
	

	void load_from_file()
	{
		std::ifstream inputFile(nume);

		string str;

		while (std::getline(inputFile, str))
		{

			std::stringstream ss(str);

			string substr;

			string nume, autor, gen;

			int an;

			for (int i = 1; i <= 4; i++)
			{
				getline(ss, substr, ',');

				if (i==1)
				{
					nume = substr;
				}
				else if (i==2)
				{
					autor = substr;
				}
				else if (i == 3)
				{
					gen = substr;
				}
				else
				{
					an = std::stoi(substr);
				}
			}

			store(Carte(nume, autor, gen, an));
		}

		incarcat_din_fisier = true;

		inputFile.close();	

	}

	void load_to_file()
	{
		std::ofstream outputFile;

		outputFile.open(nume);

		for (auto& i : all)
		{
			outputFile << i.get_titlu() << "," << i.get_autor() << "," << i.get_gen() << "," << i.get_an() << std::endl;
		}

		outputFile.close();
	}

	/*
	 * Constructor de copiere
	 *
	 */
	FileRepository(const FileRepository& ot) = delete;

	/*
	 * Functia adauga in repository cartea c
	 * c - carte, parametru de intrare
	 * Functia adauga la finalul repository-ului obiectul c, altfel arunca eroare
	 *
	 */
	void store(const Carte& c) override;

	void store_aut(const Carte& c) override;

	/*
	 * Functia stabileste daca cartea c exista in repository
	 * c - carte, parametru de intrare
	 * Functia returneaza true, daca gaseste c in repository, false altfel
	 *
	 */
	bool exista(const Carte& c)  override;

	/*
	 * Functia returneaza obiectele memorate la un momentdat in repository
	 * Fara parametri de intrare
	 * Functia returneaza un vector de tip carte cu obiectele curente
	 *
	 */
	const vector<Carte> get_all() const noexcept override;

	/*
	 * Functia returneaza obiectul din repository care corespunde cu valorile parametrilor de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia returneaza un obiect carte, daca identificarea are loc, exceptie altfel
	 *
	 */
	const Carte cauta(const string& tit, const string& aut, const string& g, const int& a) override;

	/*
	 * Functia sterge obiectul din repository care corespunde cu valorile parametrilor de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia sterge obiectul identificat din lista, exceptie altfel
	 *
	 */
	void sterge(const string& tit, const string& aut, const string& g, const int& a) override;

	void adauga_wishlist(const Carte& c) override;

	void goleste_wishlist() override;

	const vector<Carte>& get_wishlist() const noexcept override;

	int get_wishlist_size() const noexcept override;

private:

	vector<Carte> all;
	vector<Carte> wishlist;

	string nume;

};

class UnNouRepository : public AbstractRepo
{
public:

	/*
	 * Constructor implicit
	 *
	 */
	UnNouRepository() = default;

	UnNouRepository(const double& f)
	{
		threshold = f;
	}

	/*
	 * Constructor de copiere
	 *
	 */
	UnNouRepository(const UnNouRepository& ot) = delete;

	/*
	 * Functia adauga in repository cartea c
	 * c - carte, parametru de intrare
	 * Functia adauga la finalul repository-ului obiectul c, altfel arunca eroare
	 *
	 */
	void store(const Carte& c) override;

	bool incarcat_fisier() override
	{
		return incarcat_din_fisier;
	}

	void store_aut(const Carte& c) override;

	/*
	 * Functia stabileste daca cartea c exista in repository
	 * c - carte, parametru de intrare
	 * Functia returneaza true, daca gaseste c in repository, false altfel
	 *
	 */
	bool exista(const Carte& c)  override;

	/*
	 * Functia returneaza obiectele memorate la un momentdat in repository
	 * Fara parametri de intrare
	 * Functia returneaza un vector de tip carte cu obiectele curente
	 *
	 */
	const vector<Carte> get_all() const noexcept override;

	/*
	 * Functia returneaza obiectul din repository care corespunde cu valorile parametrilor de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia returneaza un obiect carte, daca identificarea are loc, exceptie altfel
	 *
	 */
	const Carte cauta(const string& tit, const string& aut, const string& g, const int& a) override;

	/*
	 * Functia sterge obiectul din repository care corespunde cu valorile parametrilor de intrare
	 * tit, aut, g - stringuri, atribute ale cartii
	 * a - int, anul cartii
	 * Functia sterge obiectul identificat din lista, exceptie altfel
	 *
	 */
	void sterge(const string& tit, const string& aut, const string& g, const int& a) override;

	void adauga_wishlist(const Carte& c) override;

	void goleste_wishlist() override;

	const vector<Carte>& get_wishlist() const noexcept override;

	int get_wishlist_size() const noexcept override;

	const double da_mi_nr_random();

	void exceptie_prank();

private:

	std::map<string, Carte> all;
	vector<Carte> wishlist;
	double threshold{ 0 };

	vector<Carte> all_vector;

};

void test_repo();

void test_wishlist();

void test_da_mi_numar_random();