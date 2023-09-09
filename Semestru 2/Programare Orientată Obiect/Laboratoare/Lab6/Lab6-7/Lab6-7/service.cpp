#include "service.h"

#include <cassert>

ServicException::ServicException(const string& m) : eroare{ m }
{

}

string ServicException::get_eroare() const
{
	return this->eroare;
}

void Service::adauga(const string& tit, const string& aut, const string& g, const int& a)
{
	Carte c{ tit, aut, g, a };
	//validare

	repo.store(c);
	
	/*try
	{
		repo.store(c);
	}
	catch (RepoException& e)
	{
		throw e;
	}*/
}

bool Service::actualizeaza(const string& tit, const string& aut, const string& g, const int& a, const string& tit1, const string& aut1, const string& g1, const int& a1)
{
	try
	{
		repo.sterge(tit, aut, g, a);

		Carte c_initial;
		
		c_initial.set_titlu(tit1);
		c_initial.set_autor(aut1);
		c_initial.set_gen(g1);
		c_initial.set_an(a1);

		repo.store(c_initial);

		return true;
	}
	catch(RepoException&)
	{
		return false;
	}
}

bool Service::sterge(const string& tit, const string& aut, const string& g, const int& a)
{
	try
	{
		repo.sterge(tit, aut, g, a);

		return true;
	}
	catch(RepoException&)
	{
		return false;
	}
}

bool Service::cautare(const string& tit, const string& aut, const string& g, const int& a)
{
	Carte c_initial{ tit, aut, g, a };
	
	return repo.exista(c_initial);

}


const vector<Carte>& Service::get_carti() noexcept
{
	return repo.get_all();
}




void test_adaugare()
{
	Repository repo;
	Service serv{ repo };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Blandiana", "Mere", 1901);

	try
	{
		serv.adauga("Ana", "Blandiana", "Mere", 1901);
		assert(false);
	}
	catch(RepoException&)
	{
		assert(true);
	}
	
}

void test_actualizare()
{
	Repository repo;
	Service serv{ repo };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Blandiana", "Mere", 1901);

	assert(serv.actualizeaza("Ana", "Blandiana", "Mere", 1900, "Tudor", "Vianu", "da", 1901) == true);

	assert(serv.actualizeaza("Ana", "Blandiana", "Mere", 1900, "Tudor", "Vianu", "da", 1902) == false);
}

void test_stergere()
{
	Repository repo;
	Service serv{ repo };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Blandiana", "Mere", 1901);

	assert(serv.sterge("Ana", "Blandiana", "Mere", 1900) == true);
	assert(serv.sterge("Ana", "Blandiana", "Mere", 1900) == false);
}

void test_cautare()
{
	Repository repo;
	Service serv{ repo };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Blandiana", "Mere", 1901);

	assert(serv.cautare("Ana", "Blandiana", "Mere", 1900) == true);
	assert(serv.sterge("Ana", "Blandiana", "Mere", 1902) == false);
}

void test_get_carti()
{
	Repository repo;
	Service serv{ repo };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Tudor", "Mere", 1901);

	assert(size(serv.get_carti()) == 2);

	const auto& v = serv.get_carti();

	assert(v[0].get_an() == 1900);

	assert(v[1].get_autor() == "Tudor");
	
}

void test_service()
{
	test_adaugare();
	
	test_actualizare();
	
	test_stergere();

	test_cautare();

	test_get_carti();
	
}
