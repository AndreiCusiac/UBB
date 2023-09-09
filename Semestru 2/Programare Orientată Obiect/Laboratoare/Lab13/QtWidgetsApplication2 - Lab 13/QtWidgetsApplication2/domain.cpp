#include "domain.h"


#include <cassert>
#include <string>

using std::string;

string Carte::get_titlu() const
{
	return this->titlu;
}

string Carte::get_autor() const
{
	return this->autor;
}

string Carte::get_gen() const
{
	return this->gen;
}

int Carte::get_an() const
{
	return this->an;
}

bool Carte::egal(const Carte& c) const
{
	if (c.get_titlu() == titlu && c.get_autor() == autor && c.get_gen() == gen && c.get_an() == an)
	{
		return true;
	}

	return false;
}


void Carte::set_titlu(const string& tit)
{
	this->titlu = tit;
}

void Carte::set_autor(const string& aut)
{
	this->autor = aut;
}

void Carte::set_gen(const string& g)
{
	this->gen = g;
}

void Carte::set_an(const int& a)
{
	this->an = a;
}


void test_domain()
{
	Carte c{ "Ion", "Liviu Rebreanu", "drama", 2020 };
	Carte c2;
	Carte c3;

	c2.set_titlu("Morometii");
	c2.set_autor("M. Preda");
	c2.set_gen("Drama");
	c2.set_an(2019);

	c3.set_titlu("Ion");
	c3.set_autor("Liviu Rebreanu");
	c3.set_gen("drama");
	c3.set_an(2020);

	assert(c.get_titlu() == "Ion");
	assert(c.get_autor() == "Liviu Rebreanu");
	assert(c.get_gen() == "drama");
	assert(c.get_an() == 2020);

	assert(c2.get_titlu() == "Morometii");
	assert(c2.get_autor() == "M. Preda");
	assert(c2.get_gen() == "Drama");
	assert(c2.get_an() == 2019);

	assert(c.egal(c2) == false);
	assert(c.egal(c3) == true);
}
