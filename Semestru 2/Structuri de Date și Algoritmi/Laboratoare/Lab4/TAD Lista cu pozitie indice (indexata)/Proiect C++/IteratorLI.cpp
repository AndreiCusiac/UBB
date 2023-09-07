#include "IteratorLI.h"
#include "LI.h"
#include <exception>

IteratorLI::IteratorLI(const LI& li): lista(li) {
 	/* de adaugat */

    /*
     * Complexitate totala = Teta (1)
     */

    this->i = li.prim;
}

void IteratorLI::prim(){
 	/* de adaugat */

    /*
     * Complexitate totala = Teta (1)
     */

    this->i = lista.prim;
}

void IteratorLI::urmator(){
 	/* de adaugat */

    /*
     * Complexitate totala = Teta (1)
     */

    if (valid() == false)
    {
        throw std::exception();
    }
	
    this->i = lista.urm[i];
}

bool IteratorLI::valid() const{
 	/* de adaugat */

    /*
     * Complexitate totala = Teta (1)
     */

	return this->i >=0 && this->i < lista.nrElem;
}

TElem IteratorLI::element() const{
 	/* de adaugat */

    /*
     * Complexitate totala = Teta (1)
     */

    if (valid() == false)
    {
        throw std::exception();
    }
	
    return lista.v[this->i];
}
