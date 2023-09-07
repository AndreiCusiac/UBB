#include "IteratorMultime.h"

#include <exception>

#include "Multime.h"


IteratorMultime::IteratorMultime(const Multime& m): mult(m) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */
	
	this->i = 0;
}

TElem IteratorMultime::element() const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */
	
	return mult.v[this->i];
}

bool IteratorMultime::valid() const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */
	
	if (this->i >= 0 && this->i < mult.dim())
	{
		return true;
	}
	
	return false;
}

void IteratorMultime::urmator() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	if (valid() == false)
	{
		throw std::exception();
	}

	this->i++;
}

void IteratorMultime::prim() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	this->i = 0;
}

