#include "domain.h"
#include "repository.h"

#include <cassert>

RepoException::RepoException(const string& m): eroare{ m }
{

}

string RepoException::get_eroare() const
{
	return this->eroare;
}



void Repository::store(const Carte& c)
{
	for (const Carte& i : all)
	{
		if (i.get_an() == c.get_an() && i.get_titlu() == c.get_titlu() && i.get_autor() == c.get_autor() && i.get_gen() == c.get_gen())
		{
			throw RepoException("\nCartea exista deja in lista!\n");
		}
	}
	all.push_back(c);
}

bool Repository::exista(const Carte& c)
{
	try
	{
		cauta(c.get_titlu(), c.get_autor(), c.get_gen(), c.get_an());
		return true;
	}
	catch (RepoException&)
	{
		return false;
	}
}


const vector<Carte>& Repository::get_all() const noexcept
{
	return all;
}

const Carte& Repository::cauta(const string& tit, const string& aut, const string& g, const int& a)
{
	for (const auto& i : all)
	{
		if (i.get_an() == a && i.get_titlu() == tit && i.get_autor() == aut && i.get_gen() == g)
		{
			return i;
		}
	}

	throw RepoException("\nNu exista cartea ceruta in lista!\n");
}

void Repository::sterge(const string& tit, const string& aut, const string& g, const int& a)
{
	int OK{ 0 };
	try
	{
		int j{ -1 };
		for (const auto& i : all)
		{
			j++;
			if (i.get_an() == a && i.get_titlu() == tit && i.get_autor() == aut && i.get_gen() == g)
			{
				OK = 1;
				all.erase(all.begin() + j);
			}
		}
	}
	catch(RepoException&)
	{
		throw RepoException("\nNu exista cartea ceruta in lista!\n");
	}
	
	if (OK == 0)
	{
		throw RepoException("\nNu exista cartea ceruta in lista!\n");
	}
}




void test_repo()
{
	Repository repo;

	Carte carte{ "A1", "b1", "c1", 2020 };
	Carte carte1{ "A1", "b1", "c1", 2020 };
	Carte carte2{ "A1", "b1", "c1", 2002 };

	repo.store(carte);

	assert(size(repo.get_all()) == 1);

	try
	{
		repo.store(carte1);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}

	repo.store(carte2);

	assert(size(repo.get_all()) == 2);

	assert(repo.exista(carte1) == true);

	repo.sterge("A1", "b1", "c1", 2020);

	assert(size(repo.get_all()) == 1);
	
	try
	{
		repo.sterge("A1", "b1", "c1", 2020);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}

	try
	{
		repo.cauta(carte1.get_titlu(), carte1.get_autor(), carte1.get_gen(), carte1.get_an());
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
	
	try
	{
		const auto c = repo.cauta(carte2.get_titlu(), carte2.get_autor(), carte2.get_gen(), carte2.get_an());

		assert(c.egal(carte2) == true);
		assert(true);
	}
	catch (RepoException&)
	{
		assert(false);
	}
	
}