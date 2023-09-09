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

	undoActions.push_back(make_unique<UndoAdauga>(repo, c));

	/*try
	{
		repo.store(c);
	}
	catch (RepoException& e)
	{
		throw e;
	}*/
}

void Service::adauga_aut(const string& tit, const string& aut, const string& g, const int& a)
{
	Carte c{ tit, aut, g, a };
	//validare

	val.validare(c);

	repo.store_aut(c);

	undoActions.push_back(make_unique<UndoAdauga>(repo, c));
}

void Service::actualizeaza(const string& tit, const string& aut, const string& g, const int& a, const string& tit1, const string& aut1, const string& g1, const int& a1)
{
	Carte c_initial;

	c_initial.set_titlu(tit1);
	c_initial.set_autor(aut1);
	c_initial.set_gen(g1);
	c_initial.set_an(a1);

	val.validare(c_initial);

	repo.sterge(tit, aut, g, a);

	repo.store(c_initial);

	undoActions.push_back(make_unique<UndoModifica>(repo, Carte{ tit, aut, g, a }, c_initial));

}

void Service::sterge(const string& tit, const string& aut, const string& g, const int& a)
{
	repo.sterge(tit, aut, g, a);

	undoActions.push_back(make_unique<UndoSterge>(repo, Carte(tit, aut, g, a)));
}

bool Service::cautare(const string& tit, const string& aut, const string& g, const int& a)
{
	Carte c_initial{ tit, aut, g, a };

	return repo.exista(c_initial);

}


const vector<Carte> Service::get_carti() noexcept
{
	return repo.get_all();
}


vector<Carte> Service::filtreaza(std::function<bool(const Carte&)> func)
{
	vector<Carte> rez;

	auto v = repo.get_all();

	copy_if(v.begin(), v.end(), back_inserter(rez), func);

	/*

   for (const auto i : v)
   {
	   if (func(i))
	   {
		   rez.push_back(i);
	   }
   }

	*/

	return rez;
}


vector<Carte> Service::sorteaza1(bool(*func)(const Carte&, const Carte&))
{
	vector<Carte> rez{ repo.get_all() };

	sort(rez.begin(), rez.end(), func);

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

vector<Carte> Service::sorteaza_titlu()
{
	return sorteaza1(cmpTitlu);
}

vector<Carte> Service::sorteaza_autor()
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

vector<Carte> Service::sorteaza_an_gen()
{
	return sorteaza1(cmpAnGen);
}



void Service::adauga_wishlist(const string& tit, const string& aut, const string& g, const int& a)
{
	Carte c{ tit, aut, g, a };
	//validare

	val.validare(c);

	repo.cauta(tit, aut, g, a);

	repo.adauga_wishlist(c);

	notify();
}

void Service::adauga_random_wishlist(const int& nr)
{
	repo.goleste_wishlist();

	auto v = repo.get_all();

	auto num = chrono::system_clock::now().time_since_epoch().count();
	shuffle(v.begin(), v.end(), default_random_engine(unsigned(num)));

	// auto seed = std::chrono::system_clock::now().time_since_epoch().count();

	/*std::random_device rd;
	std::mt19937 g(rd());

	std::shuffle(v.begin(), v.end(), g);*/

	auto k{ 0 };

	for (const auto& i : v)
	{
		repo.adauga_wishlist(i);
		k++;

		if (k == nr)
		{
			break;
		}
	}

	notify();
}

void Service::sterge_wishlist()
{
	repo.goleste_wishlist();

	notify();
}

const vector<Carte>& Service::get_wish() noexcept
{
	return repo.get_wishlist();
}

map<string, DTO> Service::getRaport() const
{
	auto v = repo.get_all();

	map<string, DTO> m;

	for (auto& i : v)
	{
		//cout << i.get_gen()<<endl;
		if (m.find(i.get_gen()) == m.end())
		{
			DTO d{ i.get_gen() };
			//m.insert(std::make_pair(i.get_gen(), d));
			m[i.get_gen()] = d;
		}
		else
		{
			m[i.get_gen()].increase();
		}
	}

	return m;
}


void Service::exportCSV(const string& nume, const string& extensie)
{
	ofstream outputFile;

	const auto dest = nume + extensie;

	try
	{
		outputFile.open(dest);

		auto v = repo.get_wishlist();

		outputFile << "Titlu" << ", " << "Autor" << ", " << "Gen" << ", " << "An" << endl;

		for (auto& i : v)
		{
			outputFile << i.get_titlu() << ", " << i.get_autor() << ", " << i.get_gen() << ", " << i.get_an() << endl;
		}

		if (size(v) == 0)
		{
			outputFile << "Lista vida!" << endl;
		}
		else
		{
			outputFile << "Lista contine " << size(v) << " carti!" << endl;
		}
	}
	catch (ofstream::failure& e)
	{
		throw e;
	}

	outputFile.close();
}

void Service::exportCSVSimplu(const string& nume)
{
	ofstream outputFile;

	const auto dest = nume;

	try
	{
		outputFile.open(dest);

		auto v = repo.get_wishlist();

		outputFile << "Titlu" << ", " << "Autor" << ", " << "Gen" << ", " << "An" << endl;

		for (auto& i : v)
		{
			outputFile << i.get_titlu() << ", " << i.get_autor() << ", " << i.get_gen() << ", " << i.get_an() << endl;
		}

		if (size(v) == 0)
		{
			outputFile << "Lista vida!" << endl;
		}
		else
		{
			outputFile << "Lista contine " << size(v) << " carti!" << endl;
		}
	}
	catch (ofstream::failure& e)
	{
		throw e;
	}

	outputFile.close();
}

void Service::exportCSV_Carti(const string& nume, const string& extensie)
{
	ofstream outputFile;

	const auto dest = nume + extensie;

	try
	{
		outputFile.open(dest);

		auto v = repo.get_all();

		outputFile << "Titlu" << ", " << "Autor" << ", " << "Gen" << ", " << "An" << endl;

		for (auto& i : v)
		{
			outputFile << i.get_titlu() << ", " << i.get_autor() << ", " << i.get_gen() << ", " << i.get_an() << endl;
		}

		if (size(v) == 0)
		{
			outputFile << "Lista vida!" << endl;
		}
		else
		{
			outputFile << "Lista contine " << size(v) << " carti!" << endl;
		}
	}
	catch (ofstream::failure& e)
	{
		throw e;
	}

	outputFile.close();
}

void Service::undo()
{
	if (size(undoActions) == 0)
	{
		throw ServicException("\nNu se mai poate efectua undo!\n\n");
	}

	undoActions.back()->doUndo();
	undoActions.pop_back();
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
	catch (RepoException&)
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

	assert(size(serv.get_carti()) == 2);

	const auto& v = serv.get_carti();

	assert(v[0].get_an() == 1900);

	assert(v[1].get_autor() == "Tudor");

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

	assert(v[2].get_an() == 1903);
	assert(v[0].get_autor() == "Blandiana");

	const auto& v1 = serv.filtreaza_an(1903);

	assert(std::size(v1) == 2);

	assert(v1[0].get_autor() == "Tudor2");
	assert(v1[1].get_gen() == "Pere");

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

	assert(v[v.size() - 1].get_titlu() == "BAna");

	/*for (int i=0; i < v.size(); i++)
	{
		std::cout << v.get(i).get_an() << std::endl;
	}*/

	assert(v[2].get_autor() == "Tudor2");

	const auto& v1 = serv.sorteaza_autor();

	assert(std::size(v1) == 5);

	assert(v1[0].get_autor() == "Blandiana");
	assert(v1[4].get_gen() == "Pere");

	const auto& v2 = serv.sorteaza_an_gen();

	assert(std::size(v2) == 5);

	assert(v2[0].get_autor() == "Blandiana");
	assert(v2[3].get_gen() == "Nere");
	assert(v2[4].get_gen() == "Pere");

}


void test_serv_wishlist()
{
	Repository repo;
	Validator val;
	Service serv{ repo , val };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana3", "Tudor", "Mere", 1901);
	serv.adauga("Ana", "Tudor", "Mere", 1902);
	serv.adauga("Ana", "Tudor2", "Mere", 1903);
	serv.adauga("Ana", "Tudor2", "Pere", 1903);

	assert(size(serv.get_carti()) == 5);
	assert(size(serv.get_wish()) == 0);

	serv.adauga_wishlist("Ana", "Blandiana", "Mere", 1900);
	serv.adauga_wishlist("Ana3", "Tudor", "Mere", 1901);

	assert(size(serv.get_wish()) == 2);

	const auto v = serv.get_wish();

	assert(v[1].get_an() == 1901);

	serv.sterge_wishlist();
	assert(size(serv.get_wish()) == 0);

	serv.adauga_random_wishlist(3);
	assert(size(serv.get_wish()) == 3);

	serv.adauga_random_wishlist(20);
	assert(size(serv.get_wish()) == 5);

}

void test_undo()
{
	Repository repo;
	Validator val;
	Service serv{ repo , val };

	serv.adauga("Ana", "Blandiana", "Mere", 1900);
	serv.adauga("Ana3", "Tudor", "Mere", 1901);
	serv.adauga("Ana", "Tudor", "Mere", 1902);
	serv.adauga("Ana", "Tudor2", "Mere", 1903);
	serv.adauga("Ana", "Tudor2", "Pere", 1903);

	assert(serv.get_carti().size() == 5);


	serv.undo();

	assert(serv.get_carti().size() == 4);


	serv.sterge("Ana3", "Tudor", "Mere", 1901);

	assert(serv.get_carti().size() == 3);

	auto v = serv.get_carti();

	assert(v[1].get_an() == 1902);

	serv.undo();

	assert(serv.get_carti().size() == 4);



	serv.actualizeaza("Ana3", "Tudor", "Mere", 1901, "Ana14", "7udor", "M3re", 1914);

	auto v1 = serv.get_carti();

	assert(v1[3].get_an() == 1914);

	serv.undo();

	auto v2 = serv.get_carti();

	assert(v2[3].get_an() == 1901);
}


void test_service()
{
	test_adaugare();

	test_actualizare();

	test_stergere();

	test_cautare();

	test_get_carti();

	test_filtrare();

	test_serv_wishlist();

	test_undo();

	test_sortare();
}
