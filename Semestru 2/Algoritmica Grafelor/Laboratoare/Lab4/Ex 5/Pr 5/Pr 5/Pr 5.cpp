// Pr 5.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <algorithm>
#include <cassert>
#include <iostream>
#include <fstream>
#include <vector>

using namespace std;

struct muchie { int sursa, destinatie, pondere; muchie* urm; };

class Graf
{
private:
	int n;
	int m;

	int s;

	int nrMuchii;
	int load;

	vector<muchie> prim;

public:

	Graf()
	{
		n = m = nrMuchii = s = 0;
		load = 10;
	}

	muchie get_poz(int i)
	{
		return prim[i];
	}

	auto get_all()
	{
		return prim;
	}

	void set_noduri(const int& nr)
	{
		n = nr;
	}

	int get_noduri()
	{
		return n;
	}

	int get_suma()
	{
		return s;
	}

	int get_nrMuchii()
	{
		return nrMuchii;
	}

	void set_muchii(const int& nr)
	{
		m = nr;
	}

	int get_muchii()
	{
		return m;
	}
	/*
	void add(int x, int y, int z)
	{
		auto* p = new muchie;

		//cout << "Am intrat cu " << z << endl;

		p->sursa = x;
		p->destinatie = y;
		p->pondere = z;

		p->urm = nullptr;

		if (prim == nullptr)
		{
			prim = p;
			ultim = p;
		}
		else
		{
			auto* q = prim;

			//cout << "Prim: " << prim->pondere << endl;

			if (prim->pondere > z)
			{
				//cout << "Aici!" << endl;
				p->urm = prim;
				prim = p;
			}
			else if (ultim->pondere < z)
			{
				ultim->urm = p;
				ultim = p;
			}
			else
			{
				//cout << "Aici " << z << endl;
				while (q != ultim)
				{
					if (q->urm->pondere >= z)
					{
						p->urm = q->urm;
						q->urm = p;
						break;
					}

					q = q->urm;
				}
			}
		}

		s += z;
		nrMuchii++;
		//cout << "Finalizat cu " << z << endl;
	}

void afiseaza_muchii()
	{
		//cout << "Da" << endl;
		auto* p = prim;
		//cout << p;



		while (p != nullptr)
		{
			cout << "Sursa " << p->sursa << ", Destinatia " << p->destinatie << ", Ponderea " << p->pondere << std::endl;
			p = p->urm;
		}
	}

	void tipareste()
	{
		//cout << "Da" << endl;
		auto* p = prim;
		//cout << p;

		cout << s << endl << nrMuchii << endl;

		while (p != nullptr)
		{
			cout << "Sursa " << p->sursa << ", Destinatia " << p->destinatie << std::endl;
			p = p->urm;
		}
	}

	*/

	void add(int x, int y, int z)
	{
		muchie m;
		m.sursa = x;
		m.destinatie = y;
		m.pondere = z;

		auto it = find_if(prim.begin(), prim.end(), [z](muchie m) {return m.pondere > z; });

		prim.insert(it, m);

		s += z;
		nrMuchii++;
	}

	void afiseaza_muchii()
	{
		for (auto p : prim)
		{
			cout << "Sursa " << p.sursa << ", Destinatia " << p.destinatie << ", Ponderea " << p.pondere << std::endl;
		}
	}

	void tipareste()
	{
		cout << s << endl << nrMuchii << endl;

		auto v = prim;

		sort(v.begin(), v.end(), [](muchie& m1, muchie& m2) {if (m1.sursa == m2.sursa) return m1.destinatie < m2.destinatie;  return m1.sursa < m2.sursa; });

		for (const auto& p : v)
		{
			cout << p.sursa << ", " << p.destinatie << std::endl;
		}

		/*for (auto p : prim)
		{
			cout << "Sursa " << p.sursa << ", Destinatia " << p.destinatie << std::endl;
		}*/

		cout << s << endl << nrMuchii << endl;
	}

	void scrie(string nume)
	{
		auto f_name = nume + "-out.txt";

		ofstream f(f_name);

		f << s << endl << nrMuchii << endl;

		auto v = prim;

		sort(v.begin(), v.end(), [](muchie& m1, muchie& m2) {if (m1.sursa == m2.sursa) return m1.destinatie < m2.destinatie;  return m1.sursa < m2.sursa; });

		for (const auto& p : v)
		{
			f << p.sursa << ", " << p.destinatie << std::endl;
		}

		cout << "Scrierea in fisierul " << f_name << " a rezultatelor s-a realizat cu succes!" << endl;
	}
};

class Union
{
private:

	vector<int> parinte;
	vector<int> sz;

public:

	Union(const int n)
	{
		parinte.resize(n);
		sz.resize(n);

		for (int i = 0; i < size(parinte); i++)
		{
			parinte[i] = i;
			sz[i] = 1;
		}
	}

	int radacina(int q)
	{
		while (q != parinte[q])
		{
			parinte[q] = parinte[parinte[q]];
			q = parinte[q];
		}

		return q;
	}

	bool conectat(const int p, const int q)
	{
		return radacina(p) == radacina(q);
	}

	void unifica(int p, int q)
	{
		int i = radacina(p);
		int j = radacina(q);

		if (i == j)
		{
			return;
		}

		if (sz[p] < sz[q])
		{
			parinte[i] = j;
			sz[j] += sz[i];
		}
		else
		{
			parinte[j] = i;
			sz[i] += sz[j];
		}
	}

};

int citire(Graf& G, const string& nume)
{
	int n, m, i{ 0 }, x, y, z;

	auto f_name = nume + ".txt";

	ifstream f(f_name);

	f >> n /*noduri*/ >> m /*muchii*/;

	if (n < 0)
	{
		return -1;
	}

	G.set_noduri(n);

	G.set_muchii(m);

	//cout << n << " " << m << endl;

	for (i = 1; i <= m; i++)
	{
		f >> x >> y >> z;

		G.add(x, y, z);
	}

	return 0;
}

void Kruscal(Graf G, Graf& F)
{
	Union u{ G.get_noduri() };

	auto i{ 0 };

	while (F.get_nrMuchii() != G.get_muchii() - 1 && i < G.get_muchii())
	{
		auto m = G.get_poz(i);

		if (u.conectat(m.sursa, m.destinatie) == false)
		{
			u.unifica(m.sursa, m.destinatie);

			F.add(m.sursa, m.destinatie, m.pondere);
		}

		i++;
	}
}

int consola()
{
	cout << endl;
	Graf G;
	Graf F;

	string nume_fisier;

	cout << "Dati numele fisierului de intrare: ";

	cin >> nume_fisier;

	if (nume_fisier == "stop")
	{
		return 0;
	}

	auto i = citire(G, nume_fisier);

	if (i == -1)
	{
		cout << "Eroare!" << endl;
		return -1;
	}

	Kruscal(G, F);

	//G.afiseaza_muchii();
	//F.tipareste();
	F.scrie(nume_fisier);

	return 1;
}

void test_citire()
{
	Graf G;

	citire(G, "date");

	assert(G.get_noduri() == 5);
	assert(G.get_suma() == 47);
	assert(G.get_nrMuchii() == 8);
}

void test_all()
{
	test_citire();

	cout << "Testele au rulat cu succes!" << endl;
}

int main()
{
	test_all();

	while (true)
	{
		auto i = consola();
		if (i == 0)
		{
			break;
		}
	}

	return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file

