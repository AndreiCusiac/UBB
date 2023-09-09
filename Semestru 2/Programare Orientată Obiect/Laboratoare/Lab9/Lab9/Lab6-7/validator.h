#pragma once
#include "domain.h"
#include <vector>

class ValidException;

class Validator;

class ValidException
{
public:

	/*
	 * Functie de constructor
	 *
	 */
	ValidException(const string& m);

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

class Validator
{
public:

	void validare(const Carte& c);
};



void test_validare();