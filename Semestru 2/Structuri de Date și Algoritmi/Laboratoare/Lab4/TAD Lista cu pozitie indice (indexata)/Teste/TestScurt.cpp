#include <assert.h>
#include <exception>

#include "TestScurt.h"

#include <iostream>

#include "../Proiect C++/LI.h"
#include "../Proiect C++/IteratorLI.h"

using namespace std;


void testAll() {
    LI lista = LI();
    assert(lista.vida());
    lista.adaugaSfarsit(1);
    assert(lista.dim() == 1);
    lista.adauga(0,2);
    assert(lista.dim() == 2);
    IteratorLI it = lista.iterator();
    assert(it.valid());
    it.urmator();
    assert(it.element() == 1);
    it.prim();
    assert(it.element() == 2);
    assert(lista.cauta(1) == 1);
    assert(lista.modifica(1,3) == 1);
    assert(lista.element(1) == 3);
    assert(lista.sterge(0) == 2);
    assert(lista.dim() == 1);
}

void testUltimulIndex()
{
    LI lista = LI();
    assert(lista.vida());
    lista.adaugaSfarsit(1);
    lista.adaugaSfarsit(2);
    lista.adaugaSfarsit(3);
    lista.adaugaSfarsit(3);
    lista.adaugaSfarsit(1);
    lista.adaugaSfarsit(4);
    lista.adaugaSfarsit(3);

    assert(lista.dim() == 7);

    assert(lista.ultimulIndex(1) == 4);
    assert(lista.ultimulIndex(2) == 1);
    assert(lista.ultimulIndex(5) == -1);
}
