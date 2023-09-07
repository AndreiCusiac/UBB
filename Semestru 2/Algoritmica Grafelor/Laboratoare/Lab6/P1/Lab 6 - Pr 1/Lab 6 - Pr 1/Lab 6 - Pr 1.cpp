// Lab 6 - Pr 1.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>
#include <vector>
#include <fstream>
#include <queue>

using namespace std;

struct muchie { int x; int y;};

struct nod { int v = -1; int grad = 0; };

bool cmp(const nod& n1, const nod& n2)
{
	return n1.grad > n2.grad;
}

class Graf
{
private:

	string nume;
	
	int noduri;
	int muchii;
	
	vector<vector<int>> adj;
	vector<nod> g;
	vector<int> color;

	int col = 0;

public:

	Graf()
	{
		noduri = muchii = 0;
	}

	Graf(const string& n) : nume{n}
	{

	}
	
	Graf(int n, int m, const string& ne): noduri{n}, muchii{m}, nume{ne}
	{
		g.resize(noduri);
		adj.resize(noduri);
		color.resize(noduri);

		fill(color.begin(), color.end(), -1);
	}
	
	void set_noduri(int n)
	{
		noduri = n;
	}

	void set_muchii(int m)
	{
		muchii = m;
	}

	void adaugaMuchie(int a, int b)
	{
		adj[a].push_back(b);
		adj[b].push_back(a);

		g[a].v = a;
		g[b].v = b;

		g[a].grad++;
		g[b].grad++;
	}

	bool sim_color(int i, int col)
	{
		for (auto p : adj[i])
		{
			if (color[p] == col)
			{
				//cout << "Nodul " << i << " este vecin cu " << p << ", care are culoarea " << color[p] << endl;
				return true;
			}
		}

		return false;
	}

	void sort_dupa_grad(bool(*func)(const nod&, const nod&))
	{
		sort(g.begin(), g.end(), func);
	}

	void colorare()
	{
		sort_dupa_grad(cmp);

		color[g[0].v] = col;

		g.erase(g.begin());

		while (!g.empty())
		{
			int i = -1;

			int OK = -1;

			for (int p = 0; p<g.size(); p++)
			{
				i++;

				if (sim_color(g[p].v, col) == false)
				{
					color[g[p].v] = col;

					OK = 1;

					g.erase(g.begin() + p);

					p--;
				}
			}

			if (OK== -1)
			{
				//break;
			}
			
			col++;
		}
	}

	
	void print_sort()
	{
		cout << endl;

		//sort_dupa_grad(cmp);
		
		for (auto i : g)
		{
			cout << "Nodul " << i.v << " - " << "Gradul " << i.grad << endl;
		}
		
		cout << endl;
	}

	void print_graf()
	{
		cout << endl;
		
		for (auto i=0; i<noduri; i++)
		{
			cout << "Nodul " << i << " - ";
			for (auto a : adj[i])
			{
				cout << a << " ";
			}
			cout << endl;
		}
		
		cout << endl;
	}

	void print_col()
	{
		cout << endl;

		cout << "Culori necesare: " << col << endl;

		for (int i=0; i < noduri; i++)
		{
			cout << "Nodul " << i << " - " << "Culoarea " << color[i] << endl;
		}
		
		cout << endl;
	}

	void print_minimal()
	{
		cout << endl << col << endl;

		for (int i = 0; i < noduri; i++)
		{
			cout << color[i] << " ";
		}

		cout << endl;
	}

	void print_fisier()
	{
		string name = nume + "-out.txt";
		ofstream of(name);

		of << col << endl;
		for (int i = 0; i < noduri; i++)
		{
			of << color[i] << " ";
		}
	}
};

int citire_graf(Graf& G, string nume)
{
	auto f_name = nume + ".txt";

	ifstream f(f_name);

	int n, m, a, b;

	f >> n >> m;

	if (n < 0 || m < 0)
	{
		return -1;
	}

	Graf F{ n, m, nume};

	F.set_noduri(n);
	F.set_muchii(m);

	for (int i=1; i<=m; i++)
	{
		f >> a >> b;
		F.adaugaMuchie(a, b);
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

	Graf G{nume};
	
	auto i = citire_graf(G, nume);

	if (i == -1)
	{
		cout << "Eroare\n\n";
		return -1;
	}

	//G.print_graf();
	//
	//G.sort_dupa_grad(cmp);
	//G.print_sort();

	G.colorare();
	//G.print_col();
	//G.print_minimal();
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
