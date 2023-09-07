
#include "Colectie.h"
#include <iostream>

using namespace std;

void creeazaColectie(Colectie& c) {
	c.adauga(2);
	c.adauga(4);
	c.adauga(2);
	c.adauga(5);
	c.adauga(4);
	c.adauga(1);
}

/*
void tiparesteColectie(Colectie& c) {
	Iterator it = c.iterator();
	while (it.valid()) {
		cout << it.element() << ' ';
		it.urmator();
	}
}*/

int main() {

	/*
	
	Colectia se reprezinta sub forma unui ABC(in care se memoreaza toate elementele din colectie) reprezentat inlantuit cu alocare dinamica.
	
	*/
	
	Colectie c;
    creeazaColectie(c);
	//tiparirea ar trebui facuta folosind un iterator
	//tiparesteColectie(c);
	return 0;
}
