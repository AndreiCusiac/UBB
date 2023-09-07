#pragma once

#define NULL_TELEM -1
typedef int TElem;

struct pereche { TElem e; int frecv; };

class IteratorColectie;

class Colectie
{
	friend class IteratorColectie;

private:
	/* aici e reprezentarea */

	pereche* v;

	int nrElem;
	int m;

	int* urm;

	int primLiber;

	void actualizeazaPrimLiber();

	int dispersie(TElem e) const;

public:
		//constructorul implicit
		Colectie();

		void rehash();

		// adauga nr aparitii ale elementului elem in colectie.
		// arunca exceptie in cazul in care este nr este negativ.
		void adaugaAparitiiMultiple(int nr, TElem elem);


		//adauga un element in colectie
		void adauga(TElem e);

		//sterge o aparitie a unui element din colectie
		//returneaza adevarat daca s-a putut sterge
		bool sterge(TElem e);

		//verifica daca un element se afla in colectie
		bool cauta(TElem elem) const;

		//returneaza numar de aparitii ale unui element in colectie
		int nrAparitii(TElem elem) const;


		//intoarce numarul de elemente din colectie;
		int dim() const;

		//verifica daca colectia e vida;
		bool vida() const;

		//returneaza un iterator pe colectie
		IteratorColectie iterator() const;

		// destructorul colectiei
		~Colectie();


		int getM();
};

