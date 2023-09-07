// Pr 5.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <algorithm>
#include <cassert>
#include <iostream>
#include <fstream>
#include <set>
#include <vector>

using namespace std;

struct muchie { int sursa = -1; int destinatie = -1; int cap = -1; int flow = -1; };

struct nod { int height = 0; int exces = 0; };

#define SOURCE 0
#define MAX 9999

class Graf
{
private:

	vector<muchie> adj;
	vector<nod> noduri;

	int n;
	int m;

	string name_input;
	string name_output;

public:

	Graf()
	{
		n = 0;
	}

	Graf(int no, int muchii, string in, string ou) : n{ no }, m{ muchii }, name_input{ in }, name_output{ ou }
	{
		noduri.resize(n + 1);
	}

	void add_muchie(int x, int y, int w)
	{
		adj.push_back(muchie{ x, y, w, 0});
	}

	void init()
	{
		noduri[SOURCE].height = n;

		for (auto i : adj)
		{
			if (i.sursa == SOURCE)
			{
				i.flow = i.cap;

				noduri[i.destinatie].exces += i.flow;

				adj.push_back(muchie{ i.destinatie, SOURCE, 0, -i.flow });
			}
		}
	}

	void inaltare(int d)
	{
		int minim = MAX;

		for (auto i : adj)
		{
			if (i.sursa == d && i.flow != i.cap && noduri[i.destinatie].height < minim)
			{
				minim = noduri[i.destinatie].height;

				noduri[d].height = minim + 1;
			}
		}
	}

	int minim(int a, int b)
	{
		if (a < b)
		{
			return a;
		}

		return b;
	}

	bool pompare(int d)
	{
		for (auto i : adj)
		{
			if (i.sursa == d && i.flow != i.cap && noduri[d].height == noduri[i.destinatie].height + 1)
			{
				auto f = minim(i.cap - i.flow, noduri[d].exces);

				noduri[d].exces -= f;

				noduri[i.destinatie].exces += f;

				i.flow += f;

				int OK{ 0 };
				
				for (auto j : adj)
				{
					if (j.sursa == i.destinatie && j.destinatie == i.sursa)
					{
						j.flow -= f;

						OK = 1;
					}
				}

				if (OK == 0)
				{
					adj.push_back(muchie{ i.destinatie, i.sursa, 0, f });
				}

				return true;
			}
		}

		return false;
	}

	int nod_cu_exces()
	{
		for (int i=1; i<n-1; i++)
		{
			if (noduri[i].exces > 0)
			{
				return i;
			}
		}

		return -1;
	}

	void pompare_preflux()
	{
		while (nod_cu_exces() > 0)
		{
			auto i = nod_cu_exces();
			
			if (pompare(i) == false)
			{
				inaltare(i);
			}
		}
	}

	void scrie_pe_ecran()
	{
		cout << "\Fluxul maxim este: " << endl;

		cout << noduri[n-1].exces << endl << endl;

		for (auto i : adj)
		{
			cout << i.sursa << " la " << i.destinatie << " cu flow " << i.flow << endl;
		}

		//cout << "Scrierea in fisierul " << f_name << " a rezultatelor s-a realizat cu succes!" << endl;
	}

	void scrie()
	{
		auto f_name = name_output;

		ofstream f(f_name);

	
		f << "\Fluxul maxim este: " << endl;

		f << noduri[n-1].exces;
		

		//cout << "Scrierea in fisierul " << f_name << " a rezultatelor s-a realizat cu succes!" << endl;
	}
};

int citire(Graf& G, const string& nume, const string& nume_out)
{
	int n, x, m, y, w;

	auto f_name = nume;

	ifstream f(f_name);

	f >> n >> m;/*noduri*/;

	if (n < 0)
	{
		return -1;
	}

	Graf F{ n, m, nume, nume_out };

	for (auto i = 0; i < m; i++)
	{
		f >> x >> y >> w;

		F.add_muchie(x, y, w);
	}

	G = F;

	return 0;
}

int consola()
{
	cout << endl;
	Graf G;

	string nume_in;
	string nume_out;

	cout << "Dati numele fisierului de intrare: ";

	cin >> nume_in;

	cout << "Dati numele fisierului de iesire: ";

	cin >> nume_out;

	auto i = citire(G, nume_in, nume_out);

	if (i == -1)
	{
		cout << "Eroare!" << endl;
		return -1;
	}

	G.pompare_preflux();

	G.scrie();
	G.scrie_pe_ecran();

	return 1;
}

int main()
{
	consola();

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

