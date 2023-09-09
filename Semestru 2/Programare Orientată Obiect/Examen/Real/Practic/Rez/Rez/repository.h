#pragma once

#include "domain.h"
#include <fstream>

class RepoException
{
private:

	string msg;

public:

	/*
	 * Constructor
	 * 
	 */
	RepoException(const string& m) : msg{m}
	{
		
	}


	/*
	 * Returneaza string cu eroare
	 * 
	 */
	string get_eroare() const;
	
};

class Repository
{

private:

	vector<Task> all;

	string name;

	/*
	 * Incarca din fisierul name
	 *
	 */
	void load_from_file();

	/*
	 * Incarca in fisierul name
	 *
	 */
	void load_to_file();

public:

	/*
	 *Constructor
	 *
	 */
	Repository(const string& n) : name{n}
	{
		//load_from_file();
	}

	/*
	 * adauga un task in lista
	 * daca ID-ul exista deja, arunca eroare
	 *
	 */
	void adauga(const int& i, const string& desc, const vector<string>& v, const string& st);

	/*
	 * adauga un task in lista, fara sa arunce eroare
	 *
	 */
	void adauga_din_fisier(const int& i, const string& desc, const vector<string>& v, const string& st);

	/*
	 * returneaza toate task-urile curente din lista
	 *
	 */
	vector<Task> get_all() const;

	void incarca()
	{
		load_from_file();
	}
	
};

void test_repository();