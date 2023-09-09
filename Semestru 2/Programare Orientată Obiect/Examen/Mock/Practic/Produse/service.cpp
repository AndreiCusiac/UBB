#include "service.h"
#include <fstream>

using std::ofstream;

const vector<Produs> Controller::sort_by_price()
{
	vector<Produs> all{ repo.get_all() };
	sort(all.begin(), all.end(), [](const Produs& p1, const Produs& p2) {
		return p1.get_pret() < p2.get_pret(); });
	return all;
}

void Controller::add(const int& id, const string& nume, const string& tip, const double& pret)
{
	string error_msg;
	if (nume == "")
	{
		error_msg += "Numele nu poate fi vid!\n";
	}
	if (pret < 1 || pret>10000)
	{
		error_msg += "Pretul trebuie sa fie intre 1 si 10000 lei!\n";
	}
	if (error_msg != "")
	{
		throw ValidationError{ error_msg };
	}
	Produs p{ id, nume, tip, pret };
	repo.store(p);
}

void test_service()
{
	ofstream fout{ "test_repo.txt" };
	fout << "1,pc,gaming,2500\n";
	fout << "2,paine,franzela,1.2\n";
	fout << "3,tv,oled,5000.99\n";
	fout.close();
	Repository r{ "test_repo.txt" };
	Controller c{ r };
	auto& all = c.sort_by_price();
	assert(all.at(0).get_pret() == 1.2);
	assert(all.at(1).get_pret() == 2500);
	assert(all.at(2).get_pret() == 5000.99);
	try
	{
		c.add(1, "asa", "asda", 123);
		assert(false);
	}
	catch (RepoError&)
	{
		assert(true);
	}
	try
	{
		c.add(4, "", "asda", 123);
		assert(false);
	}
	catch (ValidationError&)
	{
		assert(true);
	}
	try
	{
		c.add(4, "asa", "asda", 0.5);
		assert(false);
	}
	catch (ValidationError&)
	{
		assert(true);
	}
	try
	{
		c.add(4, "", "asda", 999999);
		assert(false);
	}
	catch (ValidationError&)
	{
		assert(true);
	}
	c.add(4, "asas", "asda", 450.9);
	assert(r.get_all().at(3).get_pret() == 450.9);
	remove("test_repo.txt");
}