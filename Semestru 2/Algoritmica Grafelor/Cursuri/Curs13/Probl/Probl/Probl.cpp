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

	vector<int> grad;

	int n;
	int m;

	string name_input;
	string name_output;

public:

	Graf()
	{
		n = 0;
		grad.resize(n + 1);
	}

	Graf(int noduri, int muchii, string in, string ou) : n{noduri}, m{muchii}, name_input{in}, name_output{ou}
	{
		grad.resize(n+1);

		cout << grad.size();

		std::fill(grad.begin(), grad.end(), 0);
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

	vector<int> get_grade() const
	{
		return grad;
	}

	void scrie_pe_ecran()
	{
		cout << "\nGradele sunt: " << endl;

		for (auto i = 0; i < n; i++)
		{
			cout << "Nodul: " << i << ": ";

			if (grad[i] > 0)
			{
				cout << grad[i] << endl;
			}
			else
			{
				cout << "Varf izolat!" << endl;
			}
		}

		//cout << "Scrierea in fisierul " << f_name << " a rezultatelor s-a realizat cu succes!" << endl;
	}
	
	void scrie()
	{
		auto f_name = name_output;

		ofstream f(f_name);

		f << "Gradele sunt: " << endl;

		for (auto i=0; i<n; i++)
		{
			f << "Nodul: " << i << ": ";

			if (grad[i] > 0)
			{
				f << grad[i] << endl;
			}
			else
			{
				f << "Varf izolat!" << endl;
			}
		}

		//cout << "Scrierea in fisierul " << f_name << " a rezultatelor s-a realizat cu succes!" << endl;
	}
};

int citire(Graf& G, const string& nume, const string& nume_out)
{
	int n, x, m, y;

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
		f >> x >> y;

		F.inc_grad(x);
		F.inc_grad(y);
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

