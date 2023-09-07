#include "TestScurt.h"
#include <assert.h>
#include <exception>
#include <iostream>


#include "../Proiect C++/Colectie/Colectie.h"
#include "../Proiect C++/Colectie/IteratorColectie.h"

void testAll1() { //apelam fiecare functie sa vedem daca exista
	Colectie c;
	assert(c.vida() == true);
	assert(c.dim() == 0); //adaug niste elemente
	c.adauga(5);
	c.adauga(1);
	c.adauga(10);
	c.adauga(7);
	c.adauga(1);
	c.adauga(11);
	c.adauga(-3);
	assert(c.dim() == 7);
	assert(c.cauta(10) == true);
	assert(c.cauta(16) == false);
	assert(c.nrAparitii(1) == 2);
	assert(c.nrAparitii(7) == 1);
	assert(c.sterge(1) == true);
	assert(c.sterge(6) == false);	
	assert(c.dim() == 6);
	assert(c.nrAparitii(1) == 1);
	IteratorColectie ic = c.iterator();
	ic.prim();
	while (ic.valid()) {
		TElem e = ic.element();
		ic.urmator();
	}	 
}

void testAll2() { //apelam fiecare functie sa vedem daca exista
	Colectie c;
	assert(c.vida() == true);
	assert(c.dim() == 0); //adaug niste elemente
	c.adauga(5);
	c.adauga(1);
	c.adauga(1);
	c.adauga(1);
	c.adauga(1);
	c.adauga(10);
	c.adauga(7);
	c.adauga(1);
	c.adauga(11);
	c.adauga(-3);
	assert(c.dim() == 10);
	assert(c.cauta(10) == true);
	assert(c.cauta(16) == false);
	assert(c.nrAparitii(1) == 5);
	assert(c.nrAparitii(7) == 1);
	assert(c.sterge(1) == true);
	assert(c.sterge(6) == false);
	assert(c.dim() == 9);
	assert(c.nrAparitii(1) == 4);
	IteratorColectie ic = c.iterator();
	ic.prim();
	while (ic.valid()) {
		TElem e = ic.element();
		ic.urmator();
	}
}

void testNou()
{
	Colectie c;
	assert(c.vida() == true);
	assert(c.dim() == 0); //adaug niste elemente

	c.adaugaAparitiiMultiple(10, 1);
	c.adauga(1);
	c.adauga(10);
	c.adauga(7);

	assert(c.dim() == 13);
	
	c.adaugaAparitiiMultiple(0, 20);

	assert(c.dim() == 13);

	try
	{
		c.adaugaAparitiiMultiple(-1, 2);
		assert(false);
	}
	catch (std::exception)
	{
		assert(true);
	}

	assert(c.dim() == 13);

	c.adaugaAparitiiMultiple(1, 1);

	assert(c.dim() == 14);

	assert(c.nrAparitii(1) == 12);

	c.adaugaAparitiiMultiple(200, 2);

	assert(c.dim() == 214);
}

void testAll()
{
	testAll1();
	testAll2();

	testNou();
}