#include "Multime.h"
#include "IteratorMultime.h"
#include <iostream>

using namespace std;

//o posibila relatie
bool rel(TElem e1, TElem e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}

Multime::Multime() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */
	
	this->nrElem = 0;
	this->load = 5;
	this->v = new TElem[this->load];
}

void Multime::Redim()
{
	/*
	 * Complexitate totala = Teta (n)
	 */
	
	TElem* Nou = new TElem[10 * this->load];

	for (int i = 0; i < this->nrElem; i++)
	{
		Nou[i] = this->v[i];
	}

	this->load = 10 * this->load;

	delete[] this->v;

	this->v = Nou;
}

bool Multime::adauga(TElem elem) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */
	
	
	int i, j;
	
	if (cauta(elem) == false)
	{
		if (this->nrElem == this->load)
		{
			Redim();
		}
		for (i = 0; i < this->nrElem; i++)
		{
			if (rel(elem, this->v[i]) == true)
			{
				for (j = this->nrElem; j > i; j--)
				{
					this->v[j] = this->v[j - 1];
				}
				break;
			}
		}

		this->v[i] = elem;
		this->nrElem++;

		return true;
	}
	return false;
}


bool Multime::sterge(TElem elem) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */
	
	int i, j;
	
	if (cauta(elem) == true)
	{
		for (i = 0; i < this->nrElem; i++)
		{
			if (this->v[i] == elem)
			{
				for (j = i; j < this->nrElem - 1; j++)
				{
					this->v[j] = this->v[j + 1];
				}

				this->nrElem--;
				return true;
			}
		}
	}
	return false;
}

bool Multime::cauta(TElem elem) const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (log n) (cautare binara)
	 */
	
	int l = 0, r = this->nrElem - 1, m;

	while(l <= r)
	{
		m = l + (r - l) / 2;

		if (this->v[m] == elem)
		{
			return true;
		}

		if (rel(elem, this->v[m]) == false)
		{
			l = m + 1;
		}
		else
		{
			r = m - 1;
		}
	}
	
	return false;
}


int Multime::dim() const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */
	
	return this->nrElem;
}



bool Multime::vida() const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */
	
	if (this->nrElem > 0)
	{
		return false;
	}
	return true;
}

IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}


Multime::~Multime() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */
	
	if(this->v != NULL)
	{
		delete[] this->v;
	}
}






