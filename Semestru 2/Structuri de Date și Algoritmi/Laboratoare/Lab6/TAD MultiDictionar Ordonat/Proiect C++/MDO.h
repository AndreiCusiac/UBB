#pragma once

#include <vector>

#define NULL_TCHEIE 0

typedef int TCheie;
typedef int TValoare;

#include <utility>
typedef std::pair<TCheie, TValoare> TElem;

using namespace std;

class IteratorMDO;

typedef bool(*Relatie)(TCheie, TCheie);

class MDO {
	friend class IteratorMDO;
    private:
		/* aici e reprezentarea */
	
		Relatie rel;

		int nrElem;
		int nrChei;
		int load;

		int rad;
		vector<TElem>* elem;

		int* st;
		int* dr;
		int primLiber;

		void resize();
		int adauga_cheie(TElem x);
		pair<int,pair<int, int>> pozitioneaza(TCheie c) const;
		pair<int, int> maxim(int i) const;

		int minim_absolut() const;
		int maxim_absolut() const;
	
    public:

	// constructorul implicit al MultiDictionarului Ordonat
	MDO(Relatie r);

	// gaseste si returneaza cheia maxima a multidictionarului
	// Daca  multidictionarul este vid, se returneaza NULL_TCHEIE
	TCheie cheieMaxima() const;

	// adauga o pereche (cheie, valoare) in MDO
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare 
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MDO 
	int dim() const;

	//verifica daca MultiDictionarul Ordonat e vid 
	bool vid() const;

	// se returneaza iterator pe MDO
	// iteratorul va returna perechile in ordine in raport cu relatia de ordine
	IteratorMDO iterator() const;

	// destructorul 	
	~MDO();

};
