// Lab 6 - Pr 1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <vector>
#include <fstream>
#include <list>
#include <queue>

using namespace std;

struct muchie { int catre; int cap; int flow; };

struct nod { int height = 0; int exces = 0; };

#define MAX 9999
#define SOURCE 0

class Graf
{
private:

	string nume;

	int noduri;
	int muchii;

	int centre;

	vector<vector<muchie>> adj;
	vector<nod> gf;

	int curent_lista = 1;
	int curent_vecini = 1;
	int h_veche;
	
	vector<vector<pair<int, int>>> lista;

public:

	Graf()
	{
		noduri = muchii = 0;
	}

	Graf(const string& n) : nume{ n }
	{
		
	}

	Graf(int n, int m, int c, const string& ne) : noduri{ n }, muchii{ m }, centre{ c }, nume{ ne }
	{
		gf.resize(noduri + 1);
		adj.resize(noduri + 1);
		lista.resize(noduri + 1);

		gf[SOURCE].height = noduri + 1;
		gf[SOURCE].exces = 0;

		for (int i = 1; i <= c; i++)
		{
			adj[SOURCE].push_back(muchie{ i, MAX, 0 });

			lista[i].push_back(make_pair(i, SOURCE));
		}

	}

	void set_noduri(int n)
	{
		noduri = n;
	}

	void set_muchii(int m)
	{
		muchii = m;
	}

	void adaugaMuchie(int a, int b, int pon)
	{
		adj[a + 1].push_back(muchie{ b + 1, pon, 0 });

		lista[a + 1].push_back(make_pair(a+1, b+1));
		lista[b + 1].push_back(make_pair(b+1, a+1));
	}

	void sorteaza()
	{
		for (int i=1; i<noduri; i++)
		{
			sort(lista[i].begin(), lista[i].end());
		}
	}

	void init()
	{
		for (auto i : adj[SOURCE])
		{
			i.flow = i.cap;
			gf[i.catre].exces = i.cap;
			gf[SOURCE].exces -= i.cap;	

			// !!!

			adj[i.catre].push_back(muchie{ SOURCE, MAX, 0 });
		}

		adj[SOURCE].clear();
	}

	int minim(int a, int b)
	{
		if (a < b)
		{
			return a;
		}

		return b;
	}

	void pozitioneaza_lista(int x)
	{
		swap(lista[0], lista[x]);
		curent_lista = 1;
	}

	int get_nod_first(int x)
	{
		return lista[x][0].first;
	}

	nod get_nod_curent_din_lista(int x)
	{
		return gf[get_nod_first(x)];
	}

	int get_inaltime_nod_curent_din_lista(int x)
	{
		return get_nod_curent_din_lista(x).height;
	}

	int get_exces_nod_curent_din_lista(int x)
	{
		return get_nod_curent_din_lista(x).exces;
	}

	int get_exces_direct(int d)
	{
		return gf[d].exces;
	}

	int get_capacitate(int s, int d)
	{
		for (auto p : adj[s])
		{
			if (p.catre == d)
			{
				return p.cap;
			}
		}

		return -1;
	}
	
	void actualizeaza_inaltime_nod (int x, int h)
	{
		gf[get_nod_first(x)].height = h;
	}

	void inaltare(int x)
	{
		auto u = get_nod_first(x);

		int min=noduri;

		for (auto p : adj[u])
		{
			min = minim(min, gf[p.catre].height);
		}

		actualizeaza_inaltime_nod(x, min);
	}

	void incrementeaza_exces_direct(int d, int val)
	{
		gf[d].exces += val;
	}

	void decrementeaza_exces_direct(int d, int val)
	{
		gf[d].exces -= val;
	}
	
	int get_vecin_curent(int x, int c)
	{
		return lista[x][c].second;
	}

	void actualizeaza_flux(int s, int d, int m)
	{
		for (auto p : adj[s])
		{
			if (p.catre == d)
			{
				p.flow += m;

				int gasit = 0;

				for (auto l : adj[d])
				{
					if (l.catre == s)
					{
						gasit = 1;

						l.cap = p.flow;
					}
				}

				if (gasit == 0)
				{
					adj[d].push_back(muchie{ s, p.flow, 0 });
				}
			}
		}

	}

	void pompare(int s, int d)
	{
		auto min = minim(get_exces_direct(s), get_capacitate(s, d));

		actualizeaza_flux(s, d, min);

		decrementeaza_exces_direct(s, min);
		incrementeaza_exces_direct(d, min);
	}

	void descarca(int x)
	{
		while (get_exces_nod_curent_din_lista(x) > 0)
		{
			if (curent_vecini >= lista[x].size())
			{
				inaltare(x);
				curent_vecini = 1;
			}
			else if ( (get_capacitate(get_nod_first(x), get_vecin_curent(x,  curent_vecini)) > 0) && (get_inaltime_nod_curent_din_lista(x) == gf[get_vecin_curent(x, curent_vecini)].height + 1))
			{
				pompare(get_nod_first(x), get_vecin_curent(x, curent_vecini));
			}
			else
			{
				curent_vecini++;
			}
		}
	}

	void pompare_topologica()
	{
		init();

		sorteaza();

		curent_lista = 1;

		while (curent_lista < noduri)
		{
			h_veche = get_inaltime_nod_curent_din_lista(curent_lista);

			descarca(curent_lista);
			
			if (get_inaltime_nod_curent_din_lista(curent_lista) > h_veche)
			{
				pozitioneaza_lista(curent_lista);
			}
			
			curent_lista++;
		}
	}

	void print_graf()
	{
		cout << endl;

		string buffer = "          ";

		for (auto i = 0; i <= noduri; i++)
		{
			cout << "Nodul " << i << " - ";
			for (const auto& a : adj[i])
			{
				cout << "la " << a.catre << ", ponderea " << a.cap << endl << buffer;
			}
			cout << endl;
		}

		cout << endl;
	}

	void print_init()
	{
		init();

		print_graf();
	}

	void print_vecini()
	{
		sorteaza();

		for (auto i=1; i< noduri; i++)
		{
			cout << "Nodul: " << i << " - " << "Vecinii: ";
			for (auto a : lista[i])
			{
				cout << a.second << " ";
			}
			cout << endl;
		}
	}

	/*void print_fisier()
	{
		string name = nume + "-out.txt";
		ofstream of(name);

		of << col << endl;
		for (int i = 0; i < noduri; i++)
		{
			of << color[i] << " ";
		}
	}*/
};

int citire_graf(Graf& G, string nume)
{
	auto f_name = nume + ".txt";

	ifstream f(f_name);

	int n, m, a, b, c;

	f >> n >> c >> m;

	if (n < 0 || m < 0 || c < 0)
	{
		return -1;
	}

	Graf F{ n, m, c, nume };

	/*F.set_noduri(n);
	F.set_muchii(m);*/

	for (int i = 1; i <= m; i++)
	{
		f >> a >> b >> c;
		F.adaugaMuchie(a, b, c);
	}

	G = F;

	return 1;
}


void apel()
{

}

int consola()
{
	string nume;

	cout << "Dati numele fisierului: ";

	cin >> nume;

	if (nume == "stop")
	{
		return 0;
	}

	Graf G{ nume };

	auto i = citire_graf(G, nume);

	if (i == -1)
	{
		cout << "Eroare\n\n";
		return -1;
	}

	G.print_graf();
	//G.print_init();
	G.print_vecini();

	G.pompare_topologica();

	return 1;
}

int main()
{
	while (1)
	{
		if (consola() == 0)
		{
			break;
		}
	};

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
