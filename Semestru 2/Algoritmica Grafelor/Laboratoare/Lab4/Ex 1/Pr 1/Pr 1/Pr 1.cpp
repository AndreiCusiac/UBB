// Pr 5.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <algorithm>
#include <cassert>
#include <iostream>
#include <fstream>
#include <set>
#include <vector>

using namespace std;

class Graf
{
private:
	vector<int> parinte;
	vector<int> grad;
	vector<int> cod;

	int n;

public:

	Graf()
	{
		n = 0;
		grad = { 0 };
	}

	void set_Graf(const int& nr)
	{
		n = nr;
		parinte.resize(nr);
		grad.resize(nr);
		std::fill(grad.begin(), grad.end(), 0);
	}

	void set_parinte_nod(const int& nod, const int& papa)
	{
		parinte[nod] = papa;
	}

	int get_parinte_nod(const int& nod)
	{
		return parinte[nod];
	}

	void inc_grad(int nod)
	{
		grad[nod]++;
	}

	void dec_grad(int nod)
	{
		grad[nod]--;
	}

	int get_grad(const int& nod)
	{
		return grad[nod];
	}

	vector<int> get_cod() const
	{
		return cod;
	}

	vector<int> get_grade() const
	{
		return grad;
	}

	vector<int> get_parinti() const
	{
		return parinte;
	}

	void codare_Prufer()
	{
		auto itr = std::find(grad.begin(), grad.end(), 1);

		auto ptr = std::distance(grad.begin(), itr);
		
		int frunza = ptr;
		
		while(size(cod) != n-1)
		{
			if (parinte[frunza] != -1)
			{
				int ant = parinte[frunza];

				cod.push_back(ant);

				if (size(cod) == n-1)
				{
					break;
				}

				grad[ant]--;

				//cout << "Frunza: " << frunza << " Ptr: " << ptr << " Ant: " << ant << " Gr ant: " << grad[ant] << endl;

				if (grad[ant] == 1 && ant < ptr && parinte[ant] != -1)
				{
					frunza = ant;
				}
				else
				{
					ptr++;
					while (grad[ptr] != 1 || parinte[ptr] == -1)
					{
						ptr++;
					}

					frunza = ptr;
				}
			}
		}
	}

	void tipareste()
	{
		cout << size(cod) << endl;

		for (const auto& p : cod)
		{
			cout << p << " ";
		}

		cout << endl;

		//cout << "Scrierea in fisierul " << f_name << " a rezultatelor s-a realizat cu succes!" << endl;
	}
	
	void scrie(string nume)
	{
		auto f_name = nume + "-out.txt";

		ofstream f(f_name);

		f << size(cod) << endl;
		
		for (const auto& p : cod)
		{
			f << p << " ";
		}

		cout << "Scrierea in fisierul " << f_name << " a rezultatelor s-a realizat cu succes!" << endl;
	}
};

int citire(Graf &G, const string& nume)
{
	int n, x;

	auto f_name = nume + ".txt";

	ifstream f(f_name);

	f >> n /*noduri*/;

	if (n < 0)
	{
		return -1;
	}

	G.set_Graf(n);
	
	for (auto i=0; i < n; i++)
	{
		f >> x;

		G.set_parinte_nod(i, x);

		if (x != -1)
		{
			G.inc_grad(x);
			G.inc_grad(i);
		}
	}

	/*for (int i=0; i<n;i++)
	{
		cout << i << "-" << G.get_grad(i) << endl;
		
	}*/
	
	return 0;
}

int consola()
{
	cout << endl;
	Graf G;

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

	G.codare_Prufer();

	//G.tipareste();
	G.scrie(nume_fisier);

	return 1;
}

int main()
{
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
