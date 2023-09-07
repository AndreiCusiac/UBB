#include "IteratorColectie.h"

#include <exception>

#include "Colectie.h"


IteratorColectie::IteratorColectie(const Colectie& c): col(c) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	
	curent = c.prim;
}

void IteratorColectie::prim() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	curent = col.prim;
	
}


void IteratorColectie::urmator() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	if (valid() == false)
	{
		throw std::exception();
	}
	
	curent = curent->urmeaza();
}


bool IteratorColectie::valid() const {
	/* de adaugat */
	
	/*
	 * Complexitate totala = Teta (1)
	 */

	return curent != nullptr;
}



TElem IteratorColectie::element() const {
	/* de adaugat */
	
	/*
	 * Complexitate totala = Teta (1)
	 */

	return curent->element();
}
