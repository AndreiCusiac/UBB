#include "validator.h"

#include <cassert>

ValidException::ValidException(const string& m) : eroare{ m }
{

}

string ValidException::get_eroare() const
{
	return this->eroare;
}

void Validator::validare(const Carte& c)
{
	string msg;

	if (c.get_titlu().size() == 0)
	{
		msg += "\nTitlul nu poate fi vid!\n";
	}

	if (c.get_autor().size() == 0)
	{
		msg += "\nAutorul nu poate fi vid!\n";
	}

	/*for (unsigned j=0; j<size(c.get_autor()); j++)
	{
		auto i = c.get_autor()[j];

		if (!((i >= 'a' && i <= 'z') || (i >= 'A' && i <= 'Z') || (i == '-') || (i == ' ')))
		{
			msg += "\nNumele autorului poate contine doar litere!\n";
		}
	}*/

	/*/for (auto i : c.get_autor())
	{
		if (!( (i>='a' && i<='z') || (i>='A' && i<='Z') || (i =='-') || (i == '.') || (i == ' ')))
		{
			msg += "\nNumele autorului poate contine doar litere!\n";
		}
	}*/

	if (c.get_gen().size() == 0)
	{
		msg += "\nGenul nu poate fi vid!\n";
	}

	if (c.get_an() == NULL)
	{
		msg += "\nAnul nu poate fi vid!\n";
	}

	if (c.get_an() < 0)
	{
		msg += "\nAnul nu poate fi negativ!\n";
	}

	if (c.get_an() < 1900)
	{
		msg += "\nAnul nu poate fi mai mic decat 1900!\n";
	}

	if (c.get_an() > 2021)
	{
		msg += "\nAnul nu poate fi mai mare decat cel curent!\n";
	}

	if (msg.empty() == false)
	{
		throw ValidException(msg);
	}
}


void test_validare()
{
	Carte c{ "Ion", "Liviu Rebreanu", "drama", 2020 };
	Carte c2;
	Carte c3;

	Validator valid;

	c2.set_titlu("");
	c2.set_autor("M. Preda");
	c2.set_gen("Drama");
	c2.set_an(2019);

	c3.set_titlu("Ion");
	c3.set_autor("Liviu Rebreanu");
	c3.set_gen("drama");
	c3.set_an(-2020);

	valid.validare(c);

	try
	{
		valid.validare(c2);
		assert(false);
	}
	catch (const ValidException& e)
	{
		assert(e.get_eroare().find("vid") >= 0);
		assert(e.get_eroare().find("Titlu") >= 0);
	}

	try
	{
		valid.validare(c3);
		assert(false);
	}
	catch (const ValidException& e)
	{
		assert(e.get_eroare().find("negativ") >= 0);
		assert(e.get_eroare().find("Anul") >= 0);
	}
}