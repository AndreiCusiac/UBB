#include "repository.h"
#include "domain.h"
#include <fstream>
#include <sstream>

using std::ifstream;
using std::ofstream;
using std::stringstream;
using std::getline;
using std::stoi;
using std::stod;

void Repository::load_from_file()
{
	ifstream fin{ file_name };
	if (!fin.is_open())
	{
		fin.close();
		ofstream fout{ file_name };
		fout.close();
		fin.open(file_name);
	}
	string line, id_str, nume, tip, pret_str;
	int id;
	double pret;
	while (!fin.eof())
	{
		getline(fin, line);
		if (line != "")
		{
			stringstream line_ss{ line };
			getline(line_ss, id_str, ',');
			getline(line_ss, nume, ',');
			getline(line_ss, tip, ',');
			getline(line_ss, pret_str, ',');
			id = stoi(id_str);
			pret = stod(pret_str);
			Produs p{ id, nume, tip, pret };
			all.push_back(p);
			if (tip_map.find(tip) == tip_map.end())
			{
				tip_map[tip] = 1;
			}
			else
			{
				tip_map[tip]++;
			}
		}
	}
	fin.close();
}

void Repository::store_into_file()
{
	ofstream fout{ file_name };
	for (const auto& p : all)
	{
		auto& id = p.get_id();
		auto& nume = p.get_nume();
		auto& tip = p.get_tip();
		auto& pret = p.get_pret();
		fout << id << ',' << nume << ',' << tip << ',' << pret << '\n';
	}
	fout.close();
}

void Repository::store(const Produs& p)
{
	for (const auto& pr : all)
	{
		if (pr.get_id() == p.get_id())
		{
			throw RepoError{ "Exista deja un produs cu acelasi id!" };
		}
	}
	all.push_back(p);
	auto& tip = p.get_tip();
	if (tip_map.find(tip) == tip_map.end())
	{
		tip_map[tip] = 1;
	}
	else
	{
		tip_map[tip]++;
	}
	store_into_file();
	notify();
}

void test_repo()
{
	ofstream fout{ "test_repo.txt" };
	fout << "1,paine,franzela,1.2\n";
	fout << "2,tv,oled,5000.99\n";
	fout.close();
	Repository r{ "test_repo.txt" };
	auto& all = r.get_all();
	assert(all.at(0).get_id() == 1);
	assert(all.at(1).get_pret() == 5000.99);
	r.store(Produs{ 3,"aaa","bbb",44.5 });
	assert(all.at(2).get_nume() == "aaa");
	try
	{
		r.store(Produs{ 1,"a","a",1 });
		assert(false);
	}
	catch (RepoError&)
	{
		assert(true);
	}
	ifstream fin{ "test_repo.txt" };
	string line;
	getline(fin, line);
	getline(fin, line);
	getline(fin, line);
	assert(line == "3,aaa,bbb,44.5");
	remove("test_repo.txt");
}