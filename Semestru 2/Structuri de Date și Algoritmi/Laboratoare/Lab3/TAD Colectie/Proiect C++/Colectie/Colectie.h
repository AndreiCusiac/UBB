#pragma once

#define NULL_TELEM -1
typedef int TElem;

class IteratorColectie;

class Nod;

typedef Nod* PNod;

class Nod
{

public:

	friend class Colectie;

	Nod(TElem e, int frecv, PNod urm);

	TElem element();

	int frecventa();
	
	PNod urmeaza();

private:

	TElem e;

	int frecv;

	PNod urm;
	
};

class Colectie
{
	friend class IteratorColectie;

private:
	/* aici e reprezentarea */

	PNod prim;
	
public:
		//constructorul implicit
		Colectie();

		//adauga un element in colectie
		void adauga(TElem e);

		//sterge o aparitie a unui element din colectie
		//returneaza adevarat daca s-a putut sterge
		bool sterge(TElem e);

		//verifica daca un element se afla in colectie
		bool cauta(TElem elem) const;

		//returneaza numar de aparitii ale unui element in colectie
		int nrAparitii(TElem elem) const;

		// elimina nr aparitii ale elementului elem. In cazul in care elementul apare mai putin de nr ori, toate aparitiile sale vor fi eliminate.
		// returneaza numarul de aparitii eliminate ale elementului.
		// arunca exceptie in cazul in care este nr este negativ.
		int eliminaAparitii(int nr, TElem elem);

	
		//intoarce numarul de elemente din colectie;
		int dim() const;

		//verifica daca colectia e vida;
		bool vida() const;

		//returneaza un iterator pe colectie
		IteratorColectie iterator() const;

		// destructorul colectiei
		~Colectie();

};

