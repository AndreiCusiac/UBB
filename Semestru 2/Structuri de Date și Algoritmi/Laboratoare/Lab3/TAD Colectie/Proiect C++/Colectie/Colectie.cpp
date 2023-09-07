#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;

Nod::Nod(TElem e, int frecv, PNod urm)
{
	/*
	 * Complexitate totala = Teta (1)
	 */
	
	this->e = e;
	this->frecv = frecv;
	this->urm = urm;
}

TElem Nod::element()
{
	/*
	 * Complexitate totala = Teta (1)
	 */
	
	return this->e;
}

int Nod::frecventa()
{
	/*
	 * Complexitate totala = Teta (1)
	 */

	return this->frecv;
}


PNod Nod::urmeaza()
{
	/*
	 * Complexitate totala = Teta (1)
	 */

	return this->urm;
}





Colectie::Colectie() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	this->prim = nullptr;
}


void Colectie::adauga(TElem elem) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	
	PNod q = this->prim;
	
	/*if (cauta(elem) == true)
	{
		while (q->e != elem)
		{
			q = q->urm;
		}

		q->frecv++;
		
	}
	else*/
	{
		if (q == nullptr)
		{
			this->prim = new Nod(elem, 1, nullptr);
		}
		else
		{
			while (q->urm != nullptr)
			{
				q = q->urm;
			}

			PNod nou = new Nod(elem, 1, nullptr);

			q->urm = nou;
		}
		
	}
}


bool Colectie::sterge(TElem elem) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */


	PNod q = this->prim;
	
	if (cauta(elem) == true)
	{
		if (this->prim->e == elem)
		{
			q = this->prim;

			this->prim = this->prim->urm;

			delete q;
		}
		else
		{
			while(q->urm->e != elem)
			{
				q = q->urm;
			}

			PNod p = q->urm;

			q->urm = p->urm;

			delete p;
		}
		
		return true;
	}
	
	return false;
}


bool Colectie::cauta(TElem elem) const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	
	//cout << "Hello";
	
	PNod q = this->prim;

	if (this->prim == nullptr)
	{
		return false;
	}
	
	do
	{
		
		if (q->e == elem)
		{
			return true;
		}

		q = q->urm;
		
	} while (q != nullptr);
	
	return false;
}

int Colectie::nrAparitii(TElem elem) const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */
	
	PNod q = this->prim;
	int i = 0;

	while(q != nullptr)
	{
		if (q->e == elem)
		{
			i++;
		}
		q = q->urm;
	}
	
	return i;
}

int Colectie::eliminaAparitii(int nr, TElem elem)
{
	/*
	 * daca (nr < 0) atunci
	 *		arunca exceptie;
	 *
	 * intreg i, j
	 *
	 * i <- 0
	 *
	 * bool k
	 *
	 * pentru j, 1, nr executa
	 *
	 *		k = sterge(elem)
	 *
	 *			- se apeleaza functia de stergere de nr ori
	 *			- k memoreaza valoarea de retur
	 *
	 *		daca k == true atunci
	 *			i = i +1
	 *
	 *			- daca elementul a fost sters cu adevarat, incrementam numarul total de stergeri memorat in i
	 *			
	 * sfarsit pentru
	 *
	 * returneaza i
	 *
	 * sfarsit functie
	 *
	 * Complexitate:
	 *
	 * complexitate favorabila = Teta (1) (nr < 0)
	 * complexitate nefavorabila = complexitate totala = Teta (n)
	 */

	
	if (nr < 0)
	{
		throw std::exception();
	}

	int i = 0, j;
	bool k;

	for (j = 1; j <= nr; j++)
	{
		k = sterge(elem);

		if (k == true)
		{
			i++;
		}
	}

	return i;
}


int Colectie::dim() const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	PNod q = this->prim;
	int i = 0;

	while (q != nullptr)
	{
		i += q->frecv;
		q = q->urm;
	}

	return i;
}


bool Colectie::vida() const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	PNod q = this->prim;

	if (q == nullptr)
	{
		return true;
	}
	
	return false;
}

IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	PNod q;

	while(this->prim != nullptr)
	{
		q = this->prim;

		this->prim = q->urm;

		delete q;
	}
	
}


