#include "service.h"

vector<Doctor> Service::get_all()
{
	return r.get_all();
}

int Service::get_size()
{
	return r.get_size();
}

vector<Doctor> Service::filtreaza_nume(const string& n)
{
	vector<Doctor> rez;

	auto v = r.get_all();
	
	for (auto i : v)
	{
		if (i.get_nume() == n)
		{
			rez.push_back(i);
		}
	}

	return rez;
}

vector<Doctor> Service::filtreaza_sectie(const string& s)
{
	vector<Doctor> rez;

	auto v = r.get_all();

	for (auto i : v)
	{
		if (i.get_sectie() == s)
		{
			rez.push_back(i);
		}
	}

	return rez;
}

void test_service()
{
	Repository r("te1.txt");

	r.load_from_file();

	Service s(r);

	assert(s.get_size() == 2);

	assert(s.filtreaza_nume("ala").size() == 2);
	assert(s.filtreaza_sectie("onco").size() == 1);
}
