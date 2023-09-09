#pragma once
#include "repository.h"

class ServException
{
private:

	string msg;

public:

	/*
	 * Constructor
	 * 
	 */
	
	ServException(const string& m) : msg{ m }
	{

	}


	/*
	 * Returneaza string cu eroare
	 *
	 */
	
	string get_eroare() const
	{
		return msg;
	}

};

class Service
{
private:

	Repository& repo;

public:

	/*
	 * Constructor
	 *
	 */
	
	Service(Repository& r) : repo{r}
	{
		
	}


	/*
	 * Valideaza task-ul primit, arunca eroare daca e invalid
	 *
	 */
	void valideaza(const int& i, const string& desc, const vector<string>& v, const string& st);

	/*
	 * Adauga un task la repo, arunca eorare
	 *
	 */
	void adauga(const int& i, const string& desc, const vector<string>& v, const string& st);

	/*
	 * Constructor
	 *
	 */
	vector<Task> sort_stare();

	/*
	 * Constructor
	 *
	 */
	vector<Task> get_nume(const string& n);


	/*
	 * Constructor
	 *
	 */
	vector<Task> get_all() const;
	
};

void test_service();