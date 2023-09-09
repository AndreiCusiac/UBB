#pragma once
#include <string>
#include <cassert>

using std::string;

class Produs
{
private:
	int id;
	string nume, tip;
	double pret;
public:
	Produs(const int& newid, const string& newname, const string& newtip, const double& newpret) :
		id{ newid }, nume{ newname }, tip{ newtip }, pret{ newpret } {}
	const int& get_id() const
	{
		return id;
	}
	const string& get_nume() const
	{
		return nume;
	}
	const string& get_tip() const
	{
		return tip;
	}
	const double& get_pret() const
	{
		return pret;
	}
	const int get_nr_vocale() const
	{
		int n{ 0 };
		for (auto& c : nume)
		{
			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U')
			{
				n++;
			}
		}
		return n;
	}
};

void test_domain();