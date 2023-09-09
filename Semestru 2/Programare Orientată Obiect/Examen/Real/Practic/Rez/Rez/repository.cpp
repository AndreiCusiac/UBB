#include "repository.h"

string RepoException::get_eroare() const
{
	return msg;
}

/*void Repository::load_from_file()
{
	ifstream file(name);

	string str;

	int i{ -1 };

	int id;
	int nr;
	string descr;
	vector<string> v;
	string nume;
	string aici;
	string st;

	while (!file.eof())
	{
		getline(file, str);

		id = stoi(str);

		getline(file, str);

		descr = str;

		getline(file, str);

		nr = stoi(str);

		for (int y = 0; y < nr; y++)
		{
			getline(file, str);

			nume = str;

			v.push_back(nume);
		}

		getline(file, str);
		
		st = str;
		
		adauga_din_fisier(id, descr, v, st);
	}

	file.close();
}*/

void Repository::load_from_file()
{
	ifstream file(name);

	string str;

	int i{ -1 };
	
	int id;
	int nr;
	string descr;
	vector<string> v;
	string nume;
	string aici;
	string st;

	while(getline(file, str))
	{
		i++;

		if (i % 4 == 0)
		{
			id = stoi(str);
		}
		else if (i % 4 == 1)
		{
			descr = str;
		}
		else if (i % 4 == 2)
		{
			nr = stoi(str);

			for (int y=0; y<nr; y++)
			{
				getline(file, str);

				nume = str;

				v.push_back(nume);
			}
		}
		else if (i % 4 == 3)
		{
			st = str;

			adauga_din_fisier(id, descr, v, st);
		}
	}

	file.close();
}

void Repository::load_to_file()
{
	ofstream file(name);

	for (const auto& p : all)
	{
		file << p.get_id() << endl;
		file << p.get_descriere() << endl;

		file << p.get_programatori().size() << endl;

		for (auto i : p.get_programatori())
		{
			file << i << endl;
		}
		
		file << p.get_stare() << endl;
	}

	file.close();
}


void Repository::adauga_din_fisier(const int& i, const string& desc, const vector<string>& v, const string& st)
{
	for (const auto& t : all)
	{
		if (t.get_id() == i)
		{
			throw RepoException{ "Task-ul cu acest ID exista deja!" };
		}
	}

	all.push_back(Task{ i, desc, v, st });
}

void Repository::adauga(const int& i, const string& desc, const vector<string>& v, const string& st)
{
	for (const auto& t : all)
	{
		if (t.get_id() == i)
		{
			throw RepoException{ "Task-ul cu acest ID exista deja!" };
		}
	}

	all.push_back(Task{ i, desc, v, st });

	load_to_file();
}


vector<Task> Repository::get_all() const
{
	return all;
}

/*
 *
1
info
3
Ion
Geo
Vasi
open
10
rom
2
Ion
And
closed

 * 
 */

void test_repository()
{
	Repository repo("te.txt");

	repo.incarca();

	auto v = repo.get_all();

	assert(v.size() == 2);

	assert(v[0].get_id() == 1);
	assert(v[1].get_stare() == "closed");

	assert(v[1].get_id() == 10);

	assert(v[1].get_descriere() == "rom");

	//assert(v[1].get_programatori().size() == 2);
	
	Repository repo1("te10.txt");

	Task t1{ 1, "ala", vector<string>{"Ion", "Geo"}, "open" };
	Task t3{ 1, "bala", vector<string>{"Iobn", "Gebo"}, "closed" };
	Task t2{ 2, "bala", vector<string>{"Vasi", "Geo", "Ala"}, "closed" };

	repo1.adauga(t1.get_id(), t1.get_descriere(), t1.get_programatori(), t1.get_stare());
	repo1.adauga(t2.get_id(), t2.get_descriere(), t2.get_programatori(), t2.get_stare());

	try
	{
		repo1.adauga(t3.get_id(), t3.get_descriere(), t3.get_programatori(), t3.get_stare());

		assert(false);
	}
	catch(RepoException&)
	{
		assert(true);
	}

	auto v1 = repo1.get_all();

	assert(v1.size() == 2);

	ifstream f2("te1.txt");

	f2.clear();
}
