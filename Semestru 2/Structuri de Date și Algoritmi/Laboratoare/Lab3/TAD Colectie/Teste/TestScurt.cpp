#include "TestScurt.h"
#include <assert.h>
#include <iostream>


#include "../Proiect C++/Colectie/Colectie.h"
#include "../Proiect C++/Colectie/IteratorColectie.h"


void testEliminaNrAparitii()
{
	Colectie c;
	assert(c.vida() == true);
	assert(c.dim() == 0); //adaug niste elemente
	c.adauga(5);
	c.adauga(1);
	c.adauga(10);
	c.adauga(7);
	c.adauga(1);
	c.adauga(1);
	c.adauga(1);
	c.adauga(11);
	c.adauga(-3);
	assert(c.dim() == 9);
	assert(c.cauta(10) == true);
	assert(c.cauta(16) == false);
	assert(c.nrAparitii(1) == 4);
	assert(c.nrAparitii(7) == 1);
	assert(c.sterge(1) == true);
	assert(c.sterge(6) == false);
	assert(c.dim() == 8);
	assert(c.nrAparitii(1) == 3);

	assert(c.eliminaAparitii(30, 10) == 1);
	assert(c.eliminaAparitii(30, 9) == 0);
	assert(c.eliminaAparitii(20, 1) == 3);

	assert(c.nrAparitii(1) == 0);
	
	c.adauga(1);
	c.adauga(1);
	c.adauga(1);
	c.adauga(1);
	c.adauga(1);
	c.adauga(1);

	assert(c.nrAparitii(1) == 6);
	assert(c.eliminaAparitii(4, 1) == 4);

	assert(c.nrAparitii(1) == 2);
}


void testAll() { //apelam fiecare functie sa vedem daca exista
	Colectie c;
	assert(c.vida() == true);
	assert(c.dim() == 0); //adaug niste elemente
	//std::cout << c.cauta(5) << std::endl;
	c.adauga(5);
	c.adauga(1);
	c.adauga(10);
	c.adauga(7);
	c.adauga(1);
	c.adauga(11);
	c.adauga(-3);
	/*
	IteratorColectie ic = c.iterator();
	ic.prim();
	while (ic.valid()) {
		TElem e = ic.element();
		std::cout << e << std::endl;
		ic.urmator();
	}
	std::cout << c.dim() << std::endl;*/
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
		//std::cout << e << std::endl;
		ic.urmator();
	}
	
}