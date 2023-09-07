#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>
#include <vector>

using namespace std;

int isPrime(int n)
{
	/*
	 * Complexitate - O(radical n)
	 *
	 */
	
	if (n <= 1)
		return 0;
	if (n <= 3)
		return 1;

	if (n % 2 == 0 || n % 3 == 0)
		return 0;

	for (int i = 5; i * i <= n; i = i + 6)
		if (n % i == 0 || n % (i + 2) == 0)
			return 0;

	return 1;
}

int getNextPrim(int m)
{
	
	//cout << m << endl;
	m *= 13;

	while (isPrime(m) == 0)
	{
		//cout << m << " ";
		m += 2;
	}

	//cout << m << " ";

	return m;
}

TElem hashCode(TElem e)
{
	/*
	 * Complexitate - Theta (1)
	 *
	 */
	
	return abs(e);
}

int Colectie::dispersie(TElem e) const 
{
	/*
	 * Complexitate - Theta (log n)
	 * 
	 */

	
	auto d = hashCode(e);

	int p = 19;

	int x = 0;

	while (d!=0)
	{
		x += d % 10 * p;
		p *= 19;

		d /= 10;
	}

	return x % m;
}

void Colectie::actualizeazaPrimLiber()
{
	/*
	 * Complexitate - O(m)
	 *
	 */

	
	primLiber++;

	while (primLiber < m && v[primLiber].frecv != 0)
	{
		primLiber++;
	}
}

Colectie::Colectie() {
	/* de adaugat */

	/*
	 * Complexitate - Theta (m)
	 *
	 */

	m = 113;
	
	v = new pereche[m];
	urm = new int[m];

	for (int i = 0; i < m; i++)
	{
		v[i] = pereche{ -1, 0 };
		urm[i] = -1;
	}

	primLiber = 0;
	nrElem = 0;
	
}

void Colectie::rehash()
{
	/*
	 * Complexitate - Theta (m)
	 *
	 */
	
	auto mVechi = m;
	
	m = getNextPrim(m);

	cout << "\nm=" << m << endl;

	auto vNou = new pereche[m];
	auto urmNou = new int[m];

	//cout << "\nm=" << m << endl;

	auto vcopie = new pereche[mVechi];
	auto urmcopie = new int[mVechi];

	//cout << "\n" << mVechi << endl;

	for (int i=0; i < mVechi; i++)
	{
		vcopie[i] = v[i];
		urmcopie[i] = urm[i];
	}

	//cout << "\nm=" << m << endl;
	
	delete[] v;
	delete[] urm;

	//cout << "\nm=" << m << endl;

	v = vNou;
	urm = urmNou;

	for (int i=0; i<m; i++)
	{
		v[i] = pereche{ -1, 0 };
		urm[i] = -1;
	}
	
	primLiber = 0;

	//cout << nrAparitii(0)<<endl;

	for (int i=0; i<mVechi; i++)
	{
		for (int j=0; j<vcopie[i].frecv; j++)
		{
			adauga(vcopie[i].e);
			nrElem--;
		}
	}

	//cout << nrAparitii(0)<<endl;
	
}

void Colectie::adaugaAparitiiMultiple(int nr, TElem elem)
{
	/*
	 * Alg adAparitiiMultiple
	 *
	 * daca nr < 0 atunci
	 *		arunca exceptie
	 *
	 * daca nr == 0 atunci
	 *		returneaza
	 *
	 * intreg d <- dispersie (elem)
	 *
	 * daca (nrAparitii(elem) == 0) atunci
	 *
	 *		daca (v[d].frecv == 0) atunci
		
				v[d].e <- elem
				v[d].frecv <- nr

				daca (d == primLiber) atunci
				
					actualizeazaPrimLiber()

					daca (primLiber >= m) atunci
					
						rehash()
					
			altfel
			
				intreg j <- -1

				cat timp (d != -1) executa
				
					j <- d
					d <- urm[d]
				
				v[primLiber].e <- elem
				v[primLiber].frecv <- nr

				urm[j] <- primLiber

				actualizeazaPrimLiber()

				daca (primLiber >= m) atunci
				
					rehash()
			
		atunci
		
			cat timp (v[d].e != elem) executa
			
				d <- urm[d]

			v[d].frecv <- v[d].frecv + nr
		}

		nrElem <- nrElem + nr

		sf algoritm
	 *
	 *
	 *  Complexitate favorabila - Theta (1) 
	 *  Complexitate nefavorabila = Theta (m)
	 *  Complexitate medie = O (m)
	 *  
	 */

	if (nr < 0)
	{
		throw std::exception();
	}

	if (nr == 0)
	{
		return;
	}

	
	
	int d = dispersie(elem);

	if (nrAparitii(elem) == 0)
	{
		if (v[d].frecv == 0)
		{
			v[d].e = elem;
			v[d].frecv = nr;

			if (d == primLiber)
			{
				actualizeazaPrimLiber();

				if (primLiber >= m)
				{
					rehash();
				}
			}
		}
		else
		{
			int j = -1;

			while (d != -1)
			{
				j = d;
				d = urm[d];
			}

			v[primLiber].e = elem;
			v[primLiber].frecv = nr;

			urm[j] = primLiber;

			actualizeazaPrimLiber();

			if (primLiber >= m)
			{
				rehash();
			}
		}
	}
	else
	{
		while (v[d].e != elem)
		{
			d = urm[d];
		}

		v[d].frecv+=nr;
	}

	nrElem+=nr;
}

void Colectie::adauga(TElem elem) {
	/* de adaugat */

	/*
	 * Complexitate - O (m)
	 *
	 */

	int d = dispersie(elem);

	if (nrAparitii(elem) == 0)
	{
		if (v[d].frecv == 0)
		{
			v[d].e = elem;
			v[d].frecv = 1;

			if (d == primLiber)
			{
				actualizeazaPrimLiber();

				if (primLiber >= m)
				{
					rehash();
				}
			}
		}
		else
		{
			int j = -1;

			while (d != -1)
			{
				j = d;
				d = urm[d];
			}

			v[primLiber].e = elem;
			v[primLiber].frecv = 1;

			urm[j] = primLiber;

			actualizeazaPrimLiber();

			if (primLiber >= m)
			{
				rehash();
			}
		}
	}
	else
	{
		while (v[d].e != elem)
		{
			d = urm[d];
		}

		v[d].frecv++;
	}

	nrElem++;
}


bool Colectie::sterge(TElem elem) {
	/* de adaugat */

	/*
	 * Complexitate - O (m)
	 *
	 */

	int d = dispersie(elem);

	while (v[d].e != elem && d!=-1)
	{
		d = urm[d];
	}

	if (d == -1 || (v[d].e == -1 && v[d].frecv == 0))
	{
		return false;
	}

	v[d].frecv--;

	if (v[d].frecv == 0)
	{
		do
		{
			int j = urm[d];

			while (dispersie(v[j].e) != d && j != -1)
			{
				j = urm[j];
			}

			if (j == -1)
			{
				break;
			}

			v[d].e = v[j].e;
			v[d].frecv = v[j].frecv;
			
			d = j;

		} while (true);

		v[d].e = -1;
		v[d].frecv = 0;
		
		auto e = dispersie(elem);

		if (e == d)
		{
			urm[d] = -1;
		}
		else
		{
			while (urm[e] != d)
			{
				e = urm[e];
			}

			urm[e] = urm[d];
		}
	}

	nrElem--;
	
	return true;
}


bool Colectie::cauta(TElem elem) const {
	/* de adaugat */

	/*
	 * Complexitate - O(m)
	 *
	 */

	auto d = dispersie(elem);

	while (v[d].e != elem && d != -1)
	{
		d = urm[d];
	}

	if (d == -1 || (v[d].e == -1 && v[d].frecv ==0))
	{
		return false;
	}

	return true;	
}

int Colectie::nrAparitii(TElem elem) const {
	/* de adaugat */

	/*
	 * Complexitate - O (m)
	 *
	 */
	
	auto d = dispersie(elem);

	while (v[d].e != elem && d != -1)
	{
		d = urm[d];
	}

	if (d == -1 || (v[d].e == -1 && v[d].frecv == 0))
	{
		return 0;
	}

	return v[d].frecv;
}

int Colectie::getM()
{
	/*
	 * Complexitate - Theta (1)
	 *
	 */
	
	return m;
}

int Colectie::dim() const {
	/* de adaugat */

	/*
	 * Complexitate - Theta (1)
	 *
	 */
	
	return nrElem;
}


bool Colectie::vida() const {
	/* de adaugat */
	
	/*
	 * Complexitate - Theta (1)
	 *
	 */
	
	return nrElem == 0;
}

IteratorColectie Colectie::iterator() const {
	/*
	 * Complexitate - Theta (1)
	 *
	 */
	
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	/* de adaugat */

	/*
	 * Complexitate - Theta (1)
	 *
	 */

	delete[] v;
	delete[] urm;
}


