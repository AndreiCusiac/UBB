#include "domain.h"
#include "repository.h"

#include <cassert>
#include <random>

RepoException::RepoException(const string& m) : eroare{ m }
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

void Repository::store_aut(const Carte& c)
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


const vector<Carte> Repository::get_all() const noexcept
{
	return all;
}

const Carte Repository::cauta(const string& tit, const string& aut, const string& g, const int& a)
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
	catch (RepoException&)
	{
		throw RepoException("\nNu exista cartea ceruta in lista!\n");
	}

	if (OK == 0)
	{
		throw RepoException("\nNu exista cartea ceruta in lista!\n");
	}
}


void Repository::adauga_wishlist(const Carte& c)
{
	for (const Carte& i : wishlist)
	{
		if (i.get_an() == c.get_an() && i.get_titlu() == c.get_titlu() && i.get_autor() == c.get_autor() && i.get_gen() == c.get_gen())
		{
			throw RepoException("\nCartea exista deja in lista!\n");
		}
	}

	wishlist.push_back(c);
}

void Repository::goleste_wishlist()
{
	wishlist.clear();
}

const vector<Carte>& Repository::get_wishlist() const noexcept
{
	return wishlist;
}

int Repository::get_wishlist_size() const noexcept
{
	return size(wishlist);
}



void FileRepository::store(const Carte& c)
{
	for (const Carte& i : all)
	{
		if (i.get_an() == c.get_an() && i.get_titlu() == c.get_titlu() && i.get_autor() == c.get_autor() && i.get_gen() == c.get_gen())
		{
			throw RepoException("\nCartea exista deja in lista!\n");
		}
	}
	all.push_back(c);

	load_to_file();
}

void FileRepository::store_aut(const Carte& c)
{
	for (const Carte& i : all)
	{
		if (i.get_an() == c.get_an() && i.get_titlu() == c.get_titlu() && i.get_autor() == c.get_autor() && i.get_gen() == c.get_gen())
		{
			throw RepoException("\nCartea exista deja in lista!\n");
		}
	}
	all.push_back(c);

	load_to_file();
}

bool FileRepository::exista(const Carte& c)
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


const vector<Carte> FileRepository::get_all() const noexcept
{
	return all;
}

const Carte FileRepository::cauta(const string& tit, const string& aut, const string& g, const int& a)
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

void FileRepository::sterge(const string& tit, const string& aut, const string& g, const int& a)
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
		load_to_file();
	}
	catch (RepoException&)
	{
		throw RepoException("\nNu exista cartea ceruta in lista!\n");
	}

	if (OK == 0)
	{
		throw RepoException("\nNu exista cartea ceruta in lista!\n");
	}


}


void FileRepository::adauga_wishlist(const Carte& c)
{
	for (const Carte& i : wishlist)
	{
		if (i.get_an() == c.get_an() && i.get_titlu() == c.get_titlu() && i.get_autor() == c.get_autor() && i.get_gen() == c.get_gen())
		{
			throw RepoException("\nCartea exista deja in lista!\n");
		}
	}

	wishlist.push_back(c);
}

void FileRepository::goleste_wishlist()
{
	wishlist.clear();
}

const vector<Carte>& FileRepository::get_wishlist() const noexcept
{
	return wishlist;
}

int FileRepository::get_wishlist_size() const noexcept
{
	return size(wishlist);
}



void UnNouRepository::store(const Carte& c)
{
	exceptie_prank();


	string key = c.get_titlu() + c.get_autor() + c.get_gen();

	string an = std::to_string(c.get_an());

	key += an;

	if (all.find(key) != all.end())
	{
		throw RepoException("\nCartea exista deja in lista!\n");
	}

	all.insert(std::make_pair(key, c));
}

void UnNouRepository::store_aut(const Carte& c)
{
	string key = c.get_titlu() + c.get_autor() + c.get_gen();

	string an = std::to_string(c.get_an());

	key += an;

	if (all.find(key) != all.end())
	{
		throw RepoException("\nCartea exista deja in lista!\n");
	}

	all.insert(std::make_pair(key, c));
}

bool UnNouRepository::exista(const Carte& c)
{
	exceptie_prank();

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


const vector<Carte> UnNouRepository::get_all() const noexcept
{
	// exceptie_prank();

	vector<Carte> y;
	for (const auto& i : all)
	{
		Carte x = i.second;
		y.push_back(x);
	}

	return y;
}

const Carte UnNouRepository::cauta(const string& tit, const string& aut, const string& g, const int& a)
{
	exceptie_prank();

	string key = tit + aut + g;

	string an = std::to_string(a);

	key += an;

	Carte x;

	if (all.find(key) != all.end())
	{
		x = all.find(key)->second;
	}

	if (all.find(key) == all.end())
	{
		throw RepoException("\nNu exista cartea ceruta in lista!\n");
	}

	return x;
}

void UnNouRepository::sterge(const string& tit, const string& aut, const string& g, const int& a)
{
	exceptie_prank();

	string key = tit + aut + g;

	string an = std::to_string(a);

	key += an;

	if (all.find(key) != all.end())
	{
		all.erase(key);
	}
	else
	{
		throw RepoException("\nNu exista cartea ceruta in lista!\n");
	}
}


void UnNouRepository::adauga_wishlist(const Carte& c)
{
	exceptie_prank();

	for (const Carte& i : wishlist)
	{
		if (i.get_an() == c.get_an() && i.get_titlu() == c.get_titlu() && i.get_autor() == c.get_autor() && i.get_gen() == c.get_gen())
		{
			throw RepoException("\nCartea exista deja in lista!\n");
		}
	}

	wishlist.push_back(c);
}

void UnNouRepository::goleste_wishlist()
{
	exceptie_prank();

	wishlist.clear();
}

const vector<Carte>& UnNouRepository::get_wishlist() const noexcept
{
	// exceptie_prank();

	return wishlist;
}

int UnNouRepository::get_wishlist_size() const noexcept
{
	// exceptie_prank();

	return size(wishlist);
}

const double UnNouRepository::da_mi_nr_random()
{
	constexpr int FLOAT_MIN = 0;
	constexpr int FLOAT_MAX = 1;

	std::random_device rd;
	std::default_random_engine eng(rd());
	std::uniform_real_distribution<float> distr(FLOAT_MIN, FLOAT_MAX);

	return distr(eng);

}

void UnNouRepository::exceptie_prank()
{
	auto y = da_mi_nr_random();

	if (y < threshold)
	{
		throw RepoException("\nGhinion!\n");
	}
}


void test_read()
{
	FileRepository repo{ "attempt.txt" };

	repo.load_from_file();




}


void test_repo()
{
	Repository repo;

	Carte carte{ "A1", "b1", "c1", 2020 };
	Carte carte1{ "A1", "b1", "c1", 2020 };
	Carte carte2{ "A1", "b1", "c1", 2002 };

	repo.store(carte);

	assert(size(repo.get_all()) == 1);
	//repo.store(carte1);
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

	test_read();

}



void test_wishlist()
{
	Repository repo;

	Carte carte{ "A2", "b1", "c1", 2020 };
	Carte carte1{ "A1", "d1", "c1", 2020 };
	Carte carte2{ "A3", "c1", "c1", 2002 };

	repo.adauga_wishlist(carte);
	repo.adauga_wishlist(carte1);
	repo.adauga_wishlist(carte2);

	assert(repo.get_wishlist_size() == 3);

	auto const v = repo.get_wishlist();

	assert(v[1].get_autor() == "d1");
	assert(v[2].get_an() == 2002);

	repo.goleste_wishlist();

	assert(repo.get_wishlist_size() == 0);

}

void test_da_mi_numar_random()
{
	UnNouRepository repo{ 0.5 };

	double m = 0;

	auto nr = 0;

	for (auto i = 0; i < 10000; i++)
	{
		auto x = repo.da_mi_nr_random();
		m += x;
		//std::cout << x << std::endl;
		nr++;
	}

	std::cout << "Media: " << m / nr << std::endl;
}