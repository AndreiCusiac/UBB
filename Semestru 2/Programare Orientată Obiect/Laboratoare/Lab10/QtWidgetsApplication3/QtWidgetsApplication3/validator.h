#pragma once
#include "domain.h"

class ValidException
{
private:
	string erori;

public:

	ValidException(const string& e) : erori{e}
	{
		
	}

	string get_erori()
	{
		return erori;
	}
};

class Validator
{
private:

	string erori = "";

public:

	Validator() = default;

	void valideaza(const Product& p);

};

void test_validare();