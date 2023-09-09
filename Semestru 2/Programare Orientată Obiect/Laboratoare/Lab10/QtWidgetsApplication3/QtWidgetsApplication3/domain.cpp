#include "domain.h"

string Product::get_ID() const
{
	return ID;
}

string Product::get_nume() const
{
	return nume;
}

string Product::get_pret() const
{
	return pret;
}

void Product::set_ID(const string& i)
{
	ID = i;
}

void Product::set_nume(const string& n)
{
	nume = n;
}

void Product::set_pret(const string& p)
{
	pret = p;
}

void test_domain()
{
	Product p{ "1", "Dala", "120" };

	Product p1 = p;

	p1.set_nume("Aia");

	assert(p.get_pret() == "120");

	p.set_pret("130");

	assert(p.get_ID() == "1");
	assert(p.get_ID() == "1");

	assert(p.get_pret() == "130");
	assert(p1.get_pret() == "120");

	assert(p.get_nume() == "Dala");
	assert(p1.get_nume() == "Aia");
}


