#pragma once

typedef int TElem;
class IteratorLI;

class LI {
private:
    friend class IteratorLI;
    /* aici e reprezentarea */

	/* elemente */
	TElem* v;

	/* pozitii libere/ legaturi */
	int* urm;
	int* ant;

	int prim, ultim, primLiber;

	int load;
	int nrElem;

public:
 		// constructor implicit
		LI ();

		// returneaza ultimul index al unui element dat
		// daca elementul nu este in lista returneaza un TPosition nevalid
		int ultimulIndex(TElem elem) const;
		// Obs: depinzand daca aveti lista indexata sau cu pozitie iterator, inlocuiti TPosition cu int sau IteratorLista si
		// TPosition nevalid cu indexul - 1 sau iterator nevalid.
	
		void redim();
	
		// returnare dimensiune
		int dim() const;

		// verifica daca lista e vida
		bool vida() const;

		// returnare element
		//arunca exceptie daca i nu e valid
		TElem element(int i) const;

		// modifica element de pe pozitia i si returneaza vechea valoare
		//arunca exceptie daca i nu e valid
		TElem modifica(int i, TElem e);

		// adaugare element la sfarsit
		void adaugaSfarsit(TElem e);

		// adaugare element pe o pozitie i 
		//arunca exceptie daca i nu e valid
		void adauga(int i, TElem e);

		// sterge element de pe o pozitie i si returneaza elementul sters
		//arunca exceptie daca i nu e valid
		TElem sterge(int i);

		// cauta element si returneaza prima pozitie pe care apare (sau -1)
		int cauta(TElem e)  const;

		// returnare iterator
		IteratorLI iterator() const;

		//destructor
		~LI();

};
