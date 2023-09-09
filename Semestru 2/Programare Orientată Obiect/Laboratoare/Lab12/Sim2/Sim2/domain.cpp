#include "domain.h"

void Doctor::set_cnp(const string& c)
{
	cnp = c;
}

void Doctor::set_nume(const string& n)
{
	nume = n;
}

void Doctor::set_prenume(const string& p)
{
	prenume = p;
}

void Doctor::set_sectie(const string& s)
{
	sectie = s;
}

void Doctor::set_concediu(const string& con)
{
	concediu = con;
}

string Doctor::get_cnp() const
{
	return cnp;
}

string Doctor::get_nume() const
{
	return nume;
}

string Doctor::get_prenume() const
{
	return prenume;
}

string Doctor::get_sectie() const
{
	return sectie;
}

string Doctor::get_concediu() const
{
	return concediu;
}

void test_domain()
{
	Doctor d{ "123", "ala", "bala", "onco", "1" };

	Doctor d1;

	assert(d.get_cnp() == "123");

	assert(d.get_nume() == "ala");

	d.set_nume("ALA");

	assert(d.get_nume() == "ALA");

	d1.set_concediu("0");

	assert(d1.get_concediu() == "0");

}

