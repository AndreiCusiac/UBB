#include "repository.h"

void Repo::store(const Product& p)
{
	for (auto& i : all)
	{
		if (i.get_ID() == p.get_ID() && i.get_nume() == p.get_nume() && i.get_pret() == p.get_pret())
		{
			throw RepoException("\nProdusul exista deja!\n");
			return;
		}
	}

	all.push_back(p);
}


void Repo::sterge(const string& id, const string& n, const string& p)

{
	int j{ -1 };
	int OK{ 0 };
	for (auto& i : all)
	{
		j++;
		if (i.get_ID() == id && i.get_nume() == n && i.get_pret() == p)
		{
			OK = 1;
			all.erase(all.begin() + j);
		}
	}

	if (OK == 0)
	{
		throw RepoException("\nProdusul nu exista!\n");
	}
}


bool Repo::exista(const string& id, const string& n, const string& p)
{
	for (auto& i : all)
	{
		if (i.get_ID() == id && i.get_nume() == n && i.get_pret() == p)
		{
			return true;
		}
	}

	return false;
}

vector<Product> Repo::get_all() const
{
	return all;
}

int Repo::get_size() const
{
	return all.size();
}


void test_repo()
{
	Repo rep;

	Product p1{ "1", "Ab", "120" };
	Product p2{ "1", "Ab", "120" };
	Product p3{ "2", "Ab", "150" };
	Product p4{ "3", "Dab", "160" };
	Product p5{ "3", "Dab", "180" };

	rep.store(p1);

	try
	{
		rep.store(p1);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}

	assert(rep.get_size() == 1);

	rep.store(p3);
	rep.store(p4);

	assert(rep.get_size() == 3);

	assert(rep.exista("3", "Dab", "160") == true);
	assert(rep.exista("3", "Dab", "170") == false);

	rep.sterge("3", "Dab", "160");

	assert(rep.get_size() == 2);

	try
	{
		rep.sterge("3", "Dab", "160");
		assert(false);
	}
	catch (RepoException&)
	{
		assert(rep.get_size() == 2);
		assert(true);
	}
}
