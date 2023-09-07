#include "IteratorColectie.h"
#include "Colectie.h"

void IteratorColectie::deplasare()
{
	while (curent < col.m && col.v[curent].frecv == 0)
	{
		curent++;
	}
}

IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	/* de adaugat */

	/*
	 * Complexitate - O (m)
	 *
	 */

	curent = 0;

	deplasare();
}

void IteratorColectie::prim() {
	/* de adaugat */

	/*
	 * Complexitate - O (m)
	 *
	 */

	curent = 0;

	deplasare();
}


void IteratorColectie::urmator() {
	/* de adaugat */

	/*
	 * Complexitate - O (m)
	 *
	 */

	curent++;

	deplasare();
}


bool IteratorColectie::valid() const {
	/* de adaugat */

	/*
	 * Complexitate - Theta (1)
	 *
	 */
	
	return curent >=0 && curent < col.m;
}



TElem IteratorColectie::element() const {
	/* de adaugat */

	/*
	 * Complexitate - Theta (1)
	 *
	 */
	
	return col.v[curent].e;
}
