#include <assert.h>
#include <iostream>
#include "../Proiect C++/MDO.h"
#include "../Proiect C++/IteratorMDO.h"

#include <exception>
#include <vector>

using namespace std;

bool relatie1(TCheie cheie1, TCheie cheie2) {
	if (cheie1 <= cheie2) {
		return true;
	}
	else {
		return false;
	}
}

void test_cheie_maxima()
{
    MDO dictOrd = MDO(relatie1);
    assert(dictOrd.dim() == 0);
    assert(dictOrd.vid());
    //cout << dictOrd.dim() << endl;

    assert(dictOrd.cheieMaxima() == NULL_TCHEIE);

    dictOrd.adauga(6, 2);

    assert(dictOrd.cheieMaxima() == 6);
	
    //cout << dictOrd.dim() << endl;
    dictOrd.adauga(1, 3);
    dictOrd.adauga(2, 3);
    dictOrd.adauga(2, 3);
    dictOrd.adauga(9, 3);
    dictOrd.adauga(10, 3);
    dictOrd.adauga(6, 3);
    dictOrd.adauga(10, 2);
    dictOrd.adauga(9, 33);
    dictOrd.adauga(4, 3);
    dictOrd.adauga(7, 3);
    dictOrd.adauga(8, 3);

    //cout << dictOrd.dim() << endl;

    assert(dictOrd.dim() == 12);

    assert(dictOrd.cheieMaxima() == 10);
}

void testAll(){
	MDO dictOrd = MDO(relatie1);
	assert(dictOrd.dim() == 0);
	assert(dictOrd.vid());
    //cout << dictOrd.dim() << endl;
	
    dictOrd.adauga(1,2);
    //cout << dictOrd.dim() << endl;
    dictOrd.adauga(1,3);

    //cout << dictOrd.dim() << endl;
	
    assert(dictOrd.dim() == 2);
    assert(!dictOrd.vid());
   vector<TValoare> v= dictOrd.cauta(1);
    assert(v.size()==2);
    v= dictOrd.cauta(3);
    assert(v.size()==0);
     IteratorMDO it = dictOrd.iterator();
    it.prim();
    while (it.valid()){
    	TElem e = it.element();
    	it.urmator();
    }
    assert(dictOrd.sterge(1, 2) == true);
    assert(dictOrd.sterge(1, 3) == true);
    assert(dictOrd.sterge(2, 1) == false);
    assert(dictOrd.vid());
}

