#include "IteratorMDO.h"
#include "MDO.h"

void IteratorMDO::inordine(int i)
{
	/*
	 * Complexitate = Theta(nrChei)
	 *
	 */
	
	if (i != -1)
	{
		inordine(dict.st[i]);
		v.push_back(dict.elem[i]);
		inordine(dict.dr[i]);
	}
}

void IteratorMDO::init()
{
	/*
	 * Complexitate = O(nrChei * nrElem)
	 *
	 */
	
	v.clear();
	x.clear();
	delete[] y;

	inordine(dict.rad);

	for (auto a : v)
	{
		for (auto p : a)
		{
			x.push_back(p);
		}
	}

	int size = x.size();

	y = new TElem[size];

	for (int j=0; j<size; j++)
	{
		y[j] = x.at(j);
	}
}

IteratorMDO::IteratorMDO(const MDO& d) : dict(d){
	/* de adaugat */

	/*
	 * Complexitate = O(nrChei * nrElem)
	 *
	 */

	init();

	i = 0;
}

void IteratorMDO::prim(){
	/* de adaugat */

	/*
	 * Complexitate = O(1)
	 *
	 */

	i = 0;
}

void IteratorMDO::urmator(){
	/* de adaugat */

	/*
	 * Complexitate = O(1)
	 *
	 */

	i++;
}

bool IteratorMDO::valid() const{
	/* de adaugat */

	/*
	 * Complexitate = O(1)
	 *
	 */

	return i >= 0 && i < dict.nrElem;
}

TElem IteratorMDO::element() const{
	/* de adaugat */

	/*
	 * Complexitate = O(1)
	 *
	 */

	return y[i];
	
	//return pair <TCheie, TValoare>  (-1, -1);
}


