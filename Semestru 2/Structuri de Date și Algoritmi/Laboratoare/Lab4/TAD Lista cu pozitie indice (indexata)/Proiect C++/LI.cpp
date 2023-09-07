#include <exception>
#include <iostream>
#include "LI.h"
#include "IteratorLI.h"

#include <iostream>
using namespace std;

LI::LI() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	int i;
	
	this->nrElem = 0;
	this->load = 10;

	this->v = new TElem[this->load];
	this->urm = new int[this->load];
	this->ant = new int[this->load];

	for (i = 0; i < this->load - 1; i++)
	{
		urm[i] = i + 1;
	}

	urm[this->load - 1] = -1;

	primLiber = 0;
	prim = ultim = -1;
}

/*int LI::eliminaDinKInK(int k)
{
	if (k <= 0)
	{
		throw std::exception();
	}

	int nr{ 0 };

	for (int i = prim; i<this->nrElem)
}*/

int LI::ultimulIndex(TElem elem) const
{
	/*
	 *
	 * Alg ultimulIndex
	 *
	 * intreg j
	 *
	 * TElem elem
	 *
	 * j <- ultim
	 * 
	 * cat timp (j != -1 si v[j] != elem) executa
	 *
	 *		j <- ant[j]
	 *
	 * returneaza j
	 *
	 * sf Alg ultimulIndex
	 *
	 * Complexitate favorabila - Teta(1)
	 * Complexitate nefavorabila = Complexitate medie = Teta(n)
	 * 
	 */

	
	auto j = ultim;

	while (j !=-1 && v[j] != elem)
	{
		j = ant[j];
	}

	return j;
}


void LI::redim()
{

	/*
	 * Complexitate totala = Teta (n)
	 */

	TElem* Nou = new TElem[10 * this->load];
	int* NouUrm = new int[10 * this->load];
	int* NouAnt = new int[10 * this->load];

	int i;

	for (i = 0; i < this->nrElem; i++)
	{
		Nou[i] = this->v[i];
		NouUrm[i] = this->urm[i];
		NouAnt[i] = this->ant[i];
	}

	this->load = 10 * this->load;
	
	for (i = this->nrElem; i < this->load - 1; i++)
	{
		NouUrm[i] = i + 1; 
	}

	NouUrm[this->load - 1] = -1;

	delete[] this->v;
	delete[] this->urm;
	delete[] this->ant;

	this->v = Nou;
	this->urm = NouUrm;
	this->ant = NouAnt;

	this->primLiber = this->nrElem;
}

int LI::dim() const {
 	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	return this->nrElem;
}


bool LI::vida() const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	return this->nrElem == 0;
}

TElem LI::element(int i) const {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	if (i < 0 || i >= this->nrElem)
	{
		throw std::exception();
	}

	int j = this->prim;
	int k{ 0 };

	while (k != i)
	{
		j = urm[j];
		k++;
	}

	return this->v[j];
}

TElem LI::modifica(int i, TElem e) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	if (i < 0 || i >= this->nrElem)
	{
		throw std::exception();
	}

	int j = this->prim;
	int k{ 0 };

	while (k != i)
	{
		j = urm[j];
		k++;
	}

	TElem x = this->v[j];

	this->v[j] = e;

	return x;
}

void LI::adaugaSfarsit(TElem e) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	if (this->nrElem == this->load)
	{
		redim();
	}

	int i = this->primLiber;

	//cout << i << endl << endl;

	primLiber = urm[primLiber];

	if (this->nrElem == 0)
	{
		prim = ultim = i;
		urm[prim] = urm[ultim] = ant[prim] = ant[ultim] = -1;

		this->v[prim] = e;

		//cout << prim <<", "<<ultim << endl << endl;
	}
	else
	{
		ant[i] = ultim;
		urm[i] = -1;
		urm[ultim] = i;
		ultim = i;

		this->v[ultim] = e;
	}

	this->nrElem++;
}

void LI::adauga(int i, TElem e) {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	if (i < 0 || i > this->nrElem)
	{
		throw std::exception();
	}

	if (this->nrElem == this->load)
	{
		redim();
	}

	if (i == this->nrElem)
	{
		adaugaSfarsit(e);
	}
	else
	{
		int j = this->primLiber;

		primLiber = urm[primLiber];
		
		if (i == 0)
		{
			ant[prim] = j;
			urm[j] = prim;

			ant[j] = -1;

			prim = j;
		}
		else
		{
			int k = prim;
			int l{ 0 };

			while (l != i-1)
			{
				k = urm[k];
				l++;
			}

			urm[j] = urm[k];
			urm[k] = j;
			ant[j] = k;
			ant[urm[j]] = j;
		}

		this->v[j] = e;
		this->nrElem++;
	}
	
}

TElem LI::sterge(int i) {
	/* de adaugat */

	/*
	 * Complexitate totala = O(n)
	 */

	if (i < 0 || i >= this->nrElem)
	{
		throw std::exception();
	}

	int j = prim;
	int k{ 0 };

	while(k != i)
	{
		j = urm[j];
		k++;
	}

	TElem e = this->v[j];
	
	if (j == prim)
	{
		if (urm[prim] != -1)
		{
			ant[urm[prim]] = -1;
			prim = urm[prim];
		}
		else
		{
			prim = ultim = -1;
		}
	}
	else if (j == ultim)
	{
		if (prim != ultim)
		{
			urm[ant[ultim]] = -1;
			ultim = ant[ultim];
		}
		else
		{
			prim = ultim = -1;
		}
	}
	else
	{
		ant[urm[j]] = ant[j];
		urm[ant[j]] = urm[j];
	}

	urm[j] = primLiber;
	primLiber = j;

	this->nrElem--;

	return e;
	
}

int LI::cauta(TElem e) const{
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (n)
	 */

	int j = this->prim;
	int k{ 0 };

	while (this->v[j] != e && j != -1)
	{
		j = urm[j];
		k++;
	}

	if (k == this->nrElem)
	{
		return -1;
	}

	return k;
}

IteratorLI LI::iterator() const {
	return  IteratorLI(*this);
}

LI::~LI() {
	/* de adaugat */

	/*
	 * Complexitate totala = Teta (1)
	 */

	if (this->v != NULL)
	{
		delete[] this->v;
		delete[] this->urm;
		delete[] this->ant;
	}
	
}
