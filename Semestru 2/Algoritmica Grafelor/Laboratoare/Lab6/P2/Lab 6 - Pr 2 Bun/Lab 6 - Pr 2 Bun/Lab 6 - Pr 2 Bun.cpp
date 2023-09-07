// Lab 6 - Pr 1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <vector>
#include <fstream>
#include <list>
#include <queue>

using namespace std;

struct muchie { int dela = -1; int catre = -1; int cap = -1; int flow = -1; };

struct nod { int height = 0; int exces = 0; };

#define MAX 9999
#define SOURCE 0

class Graf
{
private:

	string nume;

	int sum = 0;

	int noduri;
	int muchii;

	int centre;

	vector<muchie> adj;
	vector<nod> gf;

	vector<int> start;

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

		gf[SOURCE].height = noduri + 1;
		gf[SOURCE].exces = MAX;

		for (int i = 1; i <= c; i++)
		{
			adj.push_back(muchie{ SOURCE, i, MAX, 0 });
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
		adj.push_back(muchie{ a + 1, b + 1, pon, 0 });
	}

	int get_nod_exces()
	{
		for (int i = 1; i < gf.size(); i++)
		{
			if (gf[i].exces > 0)
			{
				return i;
			}
		}

		return -1;
	}

	muchie get_muchie_u_si_v(int u, int v)
	{
		for (auto p : adj)
		{
			if (p.dela == u && p.catre == v)
			{
				return p;
			}
		}

		return muchie{ -1, -1, -1, -1 };
	}

	pair<int, muchie> get_nod_si_muchie_bun_pentru_pompare(int d)
	{
		for (auto p : adj)
		{
			if (p.dela == d)
			{
				if (p.cap > p.flow && gf[p.catre].height + 1 == gf[d].height)
				{
					return make_pair(p.catre, p);
				}
			}
		}

		return make_pair(-1, muchie{ -1, -1, -1, -1 });
	}

	void init()
	{
		auto size = adj.size();
		for (int i = 0; i < size; i++)
		{
			if (adj[i].dela == SOURCE)
			{

				adj[i].flow = adj[i].cap;

				gf[adj[i].catre].exces = adj[i].cap;

				adj.push_back(muchie{ adj[i].catre, SOURCE, 0, -adj[i].cap });

				gf[SOURCE].exces -= adj[i].cap;
			}
		}
	}

	void pompare(int u, int v, muchie m)
	{
		int delta = minim(gf[u].exces, m.cap - m.flow);

		m.flow += delta;

		gf[u].exces -= delta;
		gf[v].exces += delta;

		auto muc = get_muchie_u_si_v(v, u);

		if (muc.catre != -1)
		{
			m.flow -= delta;
		}
		else
		{
			adj.push_back(muchie{ v, u, delta, 0 });
		}
	}

	void actualizeaza_muchie_reziduala(int i, int flow)
	{
		int u = adj[i].catre, v = adj[i].dela;

		for (int j = 0; j < adj.size(); j++)
		{
			if (adj[j].catre == v && adj[j].dela == u)
			{
				adj[j].flow -= flow;
				return;
			}
		}

		muchie e = muchie{ u, v, flow, 0 };

		if (u == noduri)
		{
			sum += flow;
		}

		adj.push_back(e);
	}

	bool pompare_2(int u)
	{
		for (int i = 0; i < adj.size(); i++)
		{
			if (adj[i].dela == u)
			{
				if (adj[i].flow == adj[i].cap)
					continue;

				if (gf[u].height == gf[adj[i].catre].height + 1)
				{
					int flow = min(adj[i].cap - adj[i].flow,
						gf[u].exces);

					if (adj[i].catre == SOURCE && u <= centre)
					{
						auto x = MAX - flow;

						if (x < 1001)
						{
							start.push_back(x);
						}
					}

					gf[u].exces -= flow;

					gf[adj[i].catre].exces += flow;

					adj[i].flow += flow;

					actualizeaza_muchie_reziduala(i, flow);

					return true;
				}
			}
		}
		return false;
	}

	int minim(int a, int b)
	{
		if (a < b)
		{
			return a;
		}

		return b;
	}

	void actualizeaza_inaltime(int d, int val)
	{
		gf[d].height = val;
	}

	void inaltare(int d)
	{
		int min = MAX;

		for (auto p : adj)
		{
			if (p.dela == d)
			{
				if (p.flow == p.cap)
				{
					continue;
				}

				min = minim(min, gf[p.catre].height);
			}
		}

		actualizeaza_inaltime(d, min + 1);
	}

	bool gasim_vecin_cu_height_mai_mic(int u)
	{
		for (auto p : adj)
		{
			if (p.dela == u)
			{
				if (gf[u].height > gf[p.catre].height)
				{
					return false;
				}
			}
		}

		return true;
	}

	void pompare_de_preflux()
	{
		init();

		while (get_nod_exces() != -1)
		{
			auto u = get_nod_exces();

			if (!pompare_2(u))
			{
				inaltare(u);
			}
		}
	}

	void print_muchii()
	{
		cout << endl;

		for (int i = 0; i < adj.size(); i++)
		{
			cout << "De la " << adj[i].dela << " catre " << adj[i].catre << ", cap=" << adj[i].cap << ", flux=" << adj[i].flow << endl;
		}

		cout << endl;
		cout << endl;
	}

	void print_start()
	{
		cout << "Suma: " << sum;
		cout << endl << "Start: ";

		for (auto i : start)
		{
			cout << i << " ";
		}

		cout << endl;
	}

	void print_fisier()
	{
		string name = nume + "-out.txt";
		ofstream of(name);

		of << sum << endl;
		for (auto i : start)
		{
			of << i << " ";
		}
	}
};

int citire_graf(Graf& G, string nume)
{
	auto f_name = nume;

	ifstream f(f_name);

	int n, m, a, b, c;

	f >> n >> c >> m;

	if (n < 0 || m < 0 || c < 0)
	{
		return -1;
	}

	Graf F{ n, m, c, nume };
	
	for (int i = 1; i <= m; i++)
	{
		f >> a >> b >> c;
		F.adaugaMuchie(a, b, c);
	}

	G = F;

	return 1;
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

	//G.print_muchii();
	G.pompare_de_preflux();
	//G.print_muchii();

	G.print_fisier();

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
