#include "validator.h"

void Validator::valideaza(const Product& p)
{
	if (p.get_ID() == "")
	{
		erori += "\nID-ul nu poate fi vid!\n";
	}

	if(p.get_nume() == "")
	{
		erori += "\nNumele nu poate fi vid!\n";
	}

	if (p.get_pret() == "")
	{
		erori += "\nPretul nu poate fi vid!\n";
	}

	try
	{
		stof(p.get_pret());

		auto f = stof(p.get_pret());

		if (f < 0)
		{
			erori += "\nPretul nu poate fi negativ!\n";
		}
	}
	catch (exception)
	{
		erori += "\nPretul nu poate fi interpretat drept numar real!\n";
	}
	
	if (erori.size() > 0)
	{
		throw ValidException(erori);
	}
}

void test_validare()
{
	Validator v;

	Product p1{ "1", "Ab", "120" };
	Product p2{ "", "Ab", "120" };
	Product p3{ "2", "", "150" };
	Product p4{ "3", "Dab", "" };
	Product p5{ "3", "Dab", "18v0" };
	Product p6{ "1", "Ab", "-120" };

	v.valideaza(p1);

	try
	{
		v.valideaza(p2);
		assert(false);
	}
	catch (ValidException& e)
	{
		auto m = e.get_erori();

		assert(m.find("ID") >= 0);
		assert(m.find("vid") >=0);
		
		assert(true);
	}

	try
	{
		v.valideaza(p3);
		assert(false);
	}
	catch (ValidException& e)
	{
		auto m = e.get_erori();

		assert(m.find("Pretul") >= 0);
		assert(m.find("vid") >= 0);

		assert(true);
	}

	try
	{
		v.valideaza(p4);
		assert(false);
	}
	catch (ValidException& e)
	{
		auto m = e.get_erori();

		//assert(m.find("Pretul") >= 0);
		//assert(m.find("vid") >= 0);

		assert(true);
	}

	try
	{
		v.valideaza(p5);
		assert(false);
	}
	catch (ValidException& e)
	{
		auto m = e.get_erori();

		//assert(m.find("Pretul") >= 0);
		//assert(m.find("real") >= 0);

		assert(true);
	}

	try
	{
		v.valideaza(p6);
		assert(false);
	}
	catch (ValidException& e)
	{
		auto m = e.get_erori();

		//assert(m.find("Pretul") >= 0);
		//assert(m.find("negativ") >= 0);

		assert(true);
	}
}
