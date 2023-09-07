#include "IteratorMDO.h"
#include "MDO.h"
#include <iostream>
#include <vector>

#include <exception>
using namespace std;

MDO::MDO(Relatie r) {
	/* de adaugat */

	rel = r;
	
	nrChei = 0;
	nrElem = 0;
	load = 10;

	rad = -1;
	elem = new vector<TElem>[load];

	st = new int[load];
	dr = new int[load];

	for (int i=0; i<load; i++)
	{
		st[i] = i + 1;
		dr[i] = -1;
	}

	st[load - 1] = -1;
	
	primLiber = 0;
	
}

void MDO::resize()
{
	/*
	 * Complexitate = Theta(nrChei)
	 * 
	 */
	
	int l = load * 10;

	vector<TElem>* vNew = new vector<TElem>[l];
	int* stNew = new int[l];
	int* drNew = new int[l];

	for (int i=0; i<load; i++)
	{
		vNew[i] = elem[i];
		stNew[i] = st[i];
		drNew[i] = dr[i];
	}

	for (int i=load; i<l; i++)
	{
		stNew[i] = i + 1;
		drNew[i] = -1;
	}

	stNew[l-1] = -1;

	primLiber = load;

	load *= 10;

	delete[] elem;
	delete[] st;
	delete[] dr;

	elem = vNew;
	st = stNew;
	dr = drNew;
}

pair<int, pair<int, int>> MDO::pozitioneaza(TCheie c) const
{
	/*
	 * Complexitate = O(nrChei)
	 *
	 */
	
	auto i = rad;
	int prec = rad;
	int cheie = -1;

	if (rad != -1)
	{
		cheie = elem[rad][0].first;
	}

	while (i >= 0 && elem[i].empty() != true && elem[i][0].first != c)
	{
		prec = i;
		cheie = elem[prec][0].first;
		if (rel(elem[i][0].first, c))
		{
			i = dr[i];
		}
		else
		{
			i = st[i];
		}
	}

	auto m = make_pair(prec, cheie);
	
	return make_pair(i, m);
}


int MDO::adauga_cheie(TElem x)
{
	/*
	 * Complexitate = O(1)
	 *
	 */
	
	elem[primLiber].push_back(x);

	nrChei++;

	auto p = primLiber;

	primLiber = st[primLiber];

	st[p] = -1;
	dr[p] = -1;

	return p;
}

pair<int, int> MDO::maxim(int i) const
{
	/*
	 * Complexitate = O(nrChei)
	 *
	 */
	
	auto j = i;
	while (i != -1)
	{
		j = i;
		i = dr[i];
	}

	return make_pair(j, elem[j][0].first);
}

int MDO::minim_absolut() const
{
	/*
	 * Complexitate = O(nrChei)
	 *
	 */
	
	int i = rad;
	int j = i;
	while (i != -1)
	{
		j = i;
		i = st[i];
	}

	return j;
}

int MDO::maxim_absolut() const
{
	/*
	 * Complexitate = O(nrChei)
	 *
	 */
	
	int i = rad;
	int j = i;
	while (i != -1)
	{
		j = i;
		i = dr[i];
	}

	return elem[j][0].first;
}




void MDO::adauga(TCheie c, TValoare v) {
	/* de adaugat */

	/*
	 * Complexitate = O(nrChei)
	 *
	 */

	if ((nrChei == load) || (primLiber == -1))
	{
		resize();
	}

	if (rad == -1)
	{
		rad = primLiber;
		adauga_cheie({ c, v });
	}
	else
	{
		auto m = pozitioneaza(c);
		auto i = m.first;
		auto prec = m.second.first;
		auto ch = m.second.second;

		if (i!=-1 && elem[i].empty() == false && elem[i][0].first == c)
		{
			elem[i].push_back(TElem(c, v));
		}
		else
		{
			auto p = adauga_cheie({ c, v });

			if (c != rad)
			{
				if (rel(ch, c))
				{
					dr[prec] = p;
				}
				else
				{
					st[prec] = p;
				}
			}
		}
	}

	nrElem++;
}

vector<TValoare> MDO::cauta(TCheie c) const {
	/* de adaugat */

	/*
	 * Complexitate = O(nrChei)
	 *
	 */

	auto m = pozitioneaza(c);
	auto i = m.first;

	vector<TValoare> v;

	if (elem[i].empty() == true || rad == -1 || i == -1)
	{
		return v;
	}

	for (auto a : elem[i])
	{
		v.push_back(a.second);
	}

	return v;

	
	//return vector<TValoare>  ();
}

TCheie MDO::cheieMaxima() const
{
	/*
	 * daca (rad = -1)
	 *
	 *		returneaza NULL_TCHEIE
	 *
	 * altfel
	 *
	 *		intreg i <- rad
	 *		intreg j <- i;
	 *
	 *		cat timp (i != -1) executa
	 *
	 *			j <- i
	 *			i <- dr[i]
	 *
	 *		returneaza elem[j][0].first
	 *
	 *	Complexitate favorabila: O(1)
	 *	Complexitate devaforabila: O(nrChei)
	 *	Complexitate medie: O(nrChei)
	 */
	
	
	if (rad == -1)
	{
		return NULL_TCHEIE;
	}
	else
	{
		return maxim_absolut();
	}
}


bool MDO::sterge(TCheie c, TValoare v) {
	/* de adaugat */

	/*
	 * Complexitate = O(nrChei + nrElem)
	 *
	 */

	auto m = pozitioneaza(c);
	auto i = m.first;

	if (elem[i].empty() == true || rad == -1 || i == -1)
	{
		return false;
	}

	int j{ -1 };

	int OK{ 0 };
	
	for (auto k : elem[i])
	{
		
		j++;
		if (k.second == v)
		{
			elem[i].erase(elem[i].begin() + j);
			nrElem--;

			OK = 1;
			
			break;
		}
	}

	if (OK == 0)
	{
		return false;
	}
	
	if (elem[i].empty() == true)
	{
		nrChei--;
		
		do
		{
			if (dr[i] == -1 || st[i] == -1)
			{
				auto prec = m.second.first;
				auto ch = m.second.second;

				//cout << "Aici " << c << "-" << v << endl;
				
				if (dr[i] != -1)
				{
					if (rel(ch, c))
					{
						dr[prec] = dr[i];
					}
					else
					{
						st[prec] = dr[i];
					}

					
				}
				else if (st[i] != -1)
				{
					if (rel(ch, c))
					{
						dr[prec] = st[i];
					}
					else
					{
						st[prec] = st[i];
					}
				}

				//cout << "Aici " << c << "-" << v << endl;
				
				st[i] = primLiber;
				primLiber = i;

				//cout << "Aici " << c << "-" << v << endl;
				
				break;
			}
			else
			{
				auto l = maxim(st[i]);
				auto poz_max = l.first;
				c = l.second;
				
				elem[i] = elem[poz_max];

				m = pozitioneaza(c);
				i = m.first;
			}
			
		} while (true);
	}

	//cout << "Aici " << c << "-" << v << endl;
	
	if (nrElem == 0)
	{
		rad = -1;
	}

	//cout << "Aici " << c << "-" << v << " " << rad << endl;
	
	return true;
}

int MDO::dim() const {
	/* de adaugat */

	/*
	 * Complexitate = O(1)
	 *
	 */

	return nrElem;
}

bool MDO::vid() const {
	/* de adaugat */

	/*
	 * Complexitate = O(1)
	 *
	 */
	
	return nrChei == 0;
}

IteratorMDO MDO::iterator() const {
	return IteratorMDO(*this);
}

MDO::~MDO() {

	/*
	 * Complexitate = O(1)
	 *
	 */

	delete[] elem;
	delete[] st;
	delete[] dr;
	
	/* de adaugat */
}
