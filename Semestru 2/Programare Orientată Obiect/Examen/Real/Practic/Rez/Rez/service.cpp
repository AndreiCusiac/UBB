#include "service.h"

#include <algorithm>

void Service::valideaza(const int& i, const string& desc, const vector<string>& v, const string& st)
{
	string e = "";

	if (desc == "")
	{
		e += "Descriere vida!\n";
	}

	if (st != "open" && st != "closed" && st!= "inprogress")
	{
		e += "Stare nevalida!\n";
	}

	if (v.size() < 1 || v.size() > 4)
	{
		e += "Nr de programatori invalid!\n";
	}

	if (e != "")
	{
		throw ServException{ e };
	}
}


void Service::adauga(const int& i, const string& desc, const vector<string>& v, const string& st)
{
	valideaza(i, desc, v, st);

	repo.adauga(i, desc, v, st);
}


vector<Task> Service::get_all() const
{
	return repo.get_all();
}

vector<Task> Service::sort_stare()
{
	auto v = repo.get_all();

	sort(v.begin(), v.end(), [](const Task& t1, const Task& t2) {return t1.get_stare() < t2.get_stare(); });

	return v;
}

vector<Task> Service::get_nume(const string& n)
{
	auto v = repo.get_all();

	vector<Task> x;

	for (auto& i : v)
	{
		for (const auto& p : i.get_programatori())
		{
			if (p == n)
			{
				x.push_back(i);
			}
		}
	}

	return x;
}


void test_service()
{
	Repository repo("te2.txt");
	Service serv{ repo };

	Task t0{ 4, "ala", vector<string>{"Ion", "Geo"}, "open" };
	Task t1{ 1, "ala", vector<string>{"Ion", "Geo"}, "open" };
	Task t3{ 3, "bala", vector<string>{"Iobn", "Gebo", "Chelo"}, "inprogress" };
	Task t2{ 2, "bala", vector<string>{"Vasi", "Geo", "Ala"}, "closed" };
	
	Task t4{ 234, "", vector<string>{"Vasi", "Geo", "Ala"}, "closed" };
	Task t5{ 23, "fafas", vector<string>{"Vasi", "Geo", "Ala"}, "closed1" };
	Task t6{ 24, "asadas", vector<string>{"Vasi", "Geo", "Ala", "Cala", "Dala"}, "closed" };

	serv.adauga(t0.get_id(), t0.get_descriere(), t0.get_programatori(), t0.get_stare());
	serv.adauga(t1.get_id(), t1.get_descriere(), t1.get_programatori(), t1.get_stare());
	serv.adauga(t2.get_id(), t2.get_descriere(), t2.get_programatori(), t2.get_stare());
	serv.adauga(t3.get_id(), t3.get_descriere(), t3.get_programatori(), t3.get_stare());

	try
	{
		serv.adauga(t4.get_id(), t4.get_descriere(), t4.get_programatori(), t4.get_stare());
		assert(false);
	}
	catch(ServException&)
	{
		assert(true);
	}

	try
	{
		serv.adauga(t5.get_id(), t5.get_descriere(), t5.get_programatori(), t5.get_stare());
		assert(false);
	}
	catch (ServException&)
	{
		assert(true);
	}

	try
	{
		serv.adauga(t6.get_id(), t6.get_descriere(), t6.get_programatori(), t6.get_stare());
		assert(false);
	}
	catch (ServException&)
	{
		assert(true);
	}

	auto x = serv.sort_stare();

	assert(x.size() == 4);

	assert(x[0].get_id() == 2);

	assert(x[2].get_id() == 4);
	assert(x[3].get_id() == 1);

	auto x1 = serv.get_nume("Ion");

	assert(x1.size() == 2);

	auto x2 = serv.get_nume("Geo");

	assert(x2.size() == 3);

	auto x3 = serv.get_nume("Bpg");

	assert(x3.size() == 0);
}
