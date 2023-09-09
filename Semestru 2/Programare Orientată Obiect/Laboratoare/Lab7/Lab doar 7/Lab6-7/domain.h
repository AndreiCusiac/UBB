#pragma once

#include <iostream>
#include <string>

#include <gsl/gsl_ieee_utils.h>

using std::string;

class Carte
{
public:
	
	/*
	 * Constructor implicit
	 */
	Carte() = default;

	/*
	 * Constructor explicit
	 * tit, aut, g - stringuri, atributele titlu, autor si gen ale cartii
	 * a - int, atributul an al cartii
	 */
	Carte(const string& tit, const string& aut, const string& g, const int& a): titlu{tit}, autor{aut}, gen{g}, an{a}
	{
		
	}

	/*
	 * Constructor de copiere
	 * tit, aut, g - stringuri, atributele titlu, autor si gen ale cartii
	 * a - int, atributul an al cartii
	 */
	Carte(const Carte& ot): titlu{ ot.titlu }, autor{ ot.autor }, gen{ ot.gen }, an{ ot.an }
	{
		
		std::cout << "!!!!!\n";
	}

	/*
	 * Functie ce returneaza titlul unei carti
	 * Fara parametri de intrare
	 * Returneaza string, titlul cartii
	 */
	string get_titlu() const;

	/*
	 * Functie ce returneaza autorul unei carti
	 * Fara parametri de intrare
	 * Returneaza string, autorul cartii
	 */
	string get_autor() const;

	/*
	 * Functie ce returneaza genul unei carti
	 * Fara parametri de intrare
	 * Returneaza string, genul cartii
	 */
	string get_gen() const;

	/*
	 * Functie ce returneaza anul unei carti
	 * Fara parametri de intrare
	 * Returneaza int, anul cartii
	 */
	int get_an() const;

	/*
	 * Functie ce verifica daca doua obiecte carte sunt identice
	 * c - carte, parametru de intrare
	 * Returneaza true, daca obiectele sunt identice, false altfel
	 */
	bool egal(const Carte& c) const;

	/*
	 * Functie ce seteaza titlul unei carti
	 * tit - string, titlul cartii
	 * Nu returneaza nimic
	 */
	void set_titlu(const string& tit);

	/*
	 * Functie ce seteaza autorul unei carti
	 * tit - string, autorul cartii
	 * Nu returneaza nimic
	 */
	void set_autor(const string& aut);

	/*
	 * Functie ce seteaza genul unei carti
	 * tit - string, genul cartii
	 * Nu returneaza nimic
	 */
	void set_gen(const string& g);

	/*
	 * Functie ce seteaza anul unei carti
	 * tit - string, anul cartii
	 * Nu returneaza nimic
	 */
	void set_an(const int& a);


private:
	string titlu;
	string autor;
	string gen;
	int an;
};


void test_domain();