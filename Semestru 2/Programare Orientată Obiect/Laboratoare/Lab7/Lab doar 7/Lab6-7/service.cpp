#include "service.h"


#include <algorithm>
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

	val.validare(c);

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

void Service::actualizeaza(const string& tit, const string& aut, const string& g, const int& a, const string& tit1, const string& aut1, const string& g1, const int& a1)
{
	/*try
	{*/
	Carte c_initial;

	c_initial.set_titlu(tit1);
	c_initial.set_autor(aut1);
	c_initial.set_gen(g1);
	c_initial.set_an(a1);
	
	val.validare(c_initial);
	
	repo.sterge(tit, aut, g, a);

	repo.store(c_initial);

		//return true;
	/*}
	catch(RepoException& e)
	{
		throw RepoException(e);
	}*/
}

void Service::sterge(const string& tit, const string& aut, const string& g, const int& a)
{
	/*try
	{*/
		repo.sterge(tit, aut, g, a);

		/*return true;
	}
	catch(RepoException&)
	{
		return false;
	}*/
}

bool Service::cautare(const string& tit, const string& aut, const string& g, const int& a)
{
	Carte c_initial{ tit, aut, g, a };
	
	return repo.exista(c_initial);

}


const MyVectorT<Carte>& Service::get_carti() noexcept
{
	return repo.get_all();
}

MyVectorT<Carte> Service::filtreaza(std::function<bool(const Carte&)> func)
{
	MyVectorT<Carte> rez;

	auto v = repo.get_all();

	for (const auto i : v)
	{
		if (func(i))
		{
			rez.push_back(i);
		}
	}

	return rez;
}

MyVectorT<Carte> Service::sorteaza1(bool(*func)(const Carte&, const Carte&))
{
	MyVectorT<Carte> rez{ repo.get_all() };

	for (auto i = 0; i<rez.size(); i++)
	{
		for (auto j = i+1; j < rez.size(); j++)
		{
			if (! func(rez.get(i), rez.get(j)) )
			{
				Carte aux = rez.get(i);
				rez.set(i, rez.get(j));
				rez.set(j, aux);
			}
		}	
	}

	return rez;
}
/*
MyVectorT<Carte> Service::sorteaza2(bool(*func)(const Carte&, const Carte&))
{
	auto copyAll = repo.get_all();
	std::sort(copyAll.begin(), copyAll.end(), func);
	return copyAll;
}*/

bool cmpTitlu(const Carte& c1, const Carte& c2)
{
	return c1.get_titlu() < c2.get_titlu();
}

MyVectorT<Carte> Service::sorteaza_titlu()
{
	return sorteaza1(cmpTitlu);
}

MyVectorT<Carte> Service::sorteaza_autor()
{
	return sorteaza1([](const Carte& c1, const Carte& c2) {return c1.get_autor() < c2.get_autor(); });
}

bool cmpAnGen(const Carte& c1, const Carte& c2)
{
	if (c1.get_an() == c2.get_an())
	{
		return c1.get_gen() < c2.get_gen();
	}

	return c1.get_an() < c2.get_an();
}

MyVectorT<Carte> Service::sorteaza_an_gen()
{
	return sorteaza1(cmpAnGen);
}




void test_adaugare()
{
	Repository repo;
	Validator val;
	Service serv{ repo , val };

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
	Validator val;
	Service serv{ repo , val };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Blandiana", "Mere", 1901);

	serv.actualizeaza("Ana", "Blandiana", "Mere", 1900, "Tudor", "Vianu", "da", 1901);

	try
	{
		serv.actualizeaza("Ana", "Blandiana", "Mere", 1900, "Tudor", "Vianu", "da", 1902);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
}

void test_stergere()
{
	Repository repo;
	Validator val;
	Service serv{ repo , val };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Blandiana", "Mere", 1901);

	serv.sterge("Ana", "Blandiana", "Mere", 1900);

	try
	{
		serv.sterge("Ana", "Blandiana", "Mere", 1900);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
}

void test_cautare()
{
	Repository repo;
	Validator val;
	Service serv{ repo , val };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Blandiana", "Mere", 1901);

	assert(serv.cautare("Ana", "Blandiana", "Mere", 1900) == true);

	try
	{
		serv.sterge("Ana", "Blandiana", "Mere", 1902);
		assert(false);
	}
	catch (RepoException&)
	{
		assert(true);
	}
}

void test_get_carti()
{
	Repository repo;
	Validator val;
	Service serv{ repo , val };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana", "Tudor", "Mere", 1901);

	assert(std::size(serv.get_carti()) == 2);

	const auto& v = serv.get_carti();

	assert(v.get(0).get_an() == 1900);

	assert(v.get(1).get_autor() == "Tudor");
	
}

void test_filtrare()
{
	Repository repo;
	Validator val;
	Service serv{ repo , val };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana3", "Tudor", "Mere", 1901);
	serv.adauga("Ana", "Tudor", "Mere", 1902);
	serv.adauga("Ana", "Tudor2", "Mere", 1903);
	serv.adauga("Ana", "Tudor2", "Pere", 1903);

	assert(std::size(serv.get_carti()) == 5);

	const auto& v = serv.filtreaza_titlu("Ana");

	assert(std::size(v) == 4);

	assert(v.get(2).get_an() == 1903);
	assert(v.get(0).get_autor() == "Blandiana");

	const auto&v1 = serv.filtreaza_an(1903);

	assert(std::size(v1) == 2);

	assert(v1.get(0).get_autor() == "Tudor2");
	assert(v1.get(1).get_gen() == "Pere");

}

void test_sortare()
{
	Repository repo;
	Validator val;
	Service serv{ repo , val };

	serv.adauga("Ana", "Blandiana", "Mere1", 1900);
	serv.adauga("Ana3", "Tudor", "Mere1", 1901);
	serv.adauga("BAna", "Tudor", "Mere", 1902);
	serv.adauga("Ana2", "Tudor2", "Nere", 1903);
	serv.adauga("Ana", "Tudor4", "Pere", 1903);

	assert(std::size(serv.get_carti()) == 5);

	const auto& v = serv.sorteaza_titlu();

	assert(std::size(v) == 5);

	assert(v.get(v.size() - 1).get_titlu() == "BAna");

	/*for (int i=0; i < v.size(); i++)
	{
		std::cout << v.get(i).get_an() << std::endl;
	}*/
	
	assert(v.get(2).get_autor() == "Tudor2");

	const auto& v1 = serv.sorteaza_autor();

	assert(std::size(v1) == 5);

	assert(v1.get(0).get_autor() == "Blandiana");
	assert(v1.get(4).get_gen() == "Pere");

	const auto& v2 = serv.sorteaza_an_gen();

	assert(std::size(v2) == 5);

	assert(v2.get(0).get_autor() == "Blandiana");
	assert(v2.get(3).get_gen() == "Nere");
	assert(v2.get(4).get_gen() == "Pere");

}

void test_service()
{
	test_adaugare();
	
	test_actualizare();
	
	test_stergere();

	test_cautare();

	test_get_carti();

	test_filtrare();

	test_sortare();
}
