#pragma once
#include <assert.h>
#include <string>
#include <vector>

using namespace std;

class Task
{
private:

	int id;

	string descriere;

	vector<string> programatori;

	string stare;

public:

	Task(const int& i, const string& desc, const vector<string>& v, const string& st) : id{i}, descriere{desc}, programatori{v}, stare{st}
	{
		
	}

	/*
	 * Functie de setare
	 * Primeste un int
	 * id primeste i
	 * 
	 */
	void set_id(const int& i);

	/*
	 * Functie de setare
	 * Primeste un string
	 * descriere primeste d
	 *
	 */
	void set_descriere(const string& d);

	/*
	 * Functie de setare
	 * Primeste un vector de stringuri
	 * programatori primeste v
	 *
	 */
	void set_programatori(const vector<string>& v);

	/*
	 * Functie de setare
	 * Primeste un string
	 * stare primeste d
	 *
	 */
	void set_stare(const string& d);

	/*
	 * Functie de returnare
	 * returneaza ID-ul taskului
	 *
	 */
	int get_id() const;

	/*
	 * Functie de returnare
	 * returneaza descrierea taskului
	 *
	 */
	string get_descriere() const;

	/*
	 * Functie de returnare
	 * returneaza programatorii taskului
	 *
	 */
	vector<string> get_programatori() const;

	/*
	 * Functie de returnare
	 * returneaza starea taskului
	 *
	 */
	string get_stare() const;
	
};

void test_domain();