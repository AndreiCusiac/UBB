#include "ui.h"
#include <iostream>

using std::cin;
using std::cout;

void UI::adaugaUI()
{
	cout << "\nAti ales optiunea Adaugare.\n";
	
	string titlu, autor, gen;
	int an;

	cout << "\n Dati atributele cartii: \n";

	std::getline(cin, titlu);
	
	cout << "Dati titlul: ";
	getline(std::cin, titlu);

	cout << "Dati autorul: ";
	getline(std::cin, autor);

	cout << "Dati genul: ";
	getline(std::cin, gen);

	cout << "Dati anul: ";
	cin >> an;

	try
	{
		serv.adauga(titlu, autor, gen, an);
		cout << "\nAdaugarea s-a realizat cu succes!\n";
	}
	catch (RepoException& e)
	{
		cout << e.get_eroare() << std::endl;
	}
	catch (ValidException& e)
	{
		cout << e.get_eroare() << std::endl;
	}
}

void UI::actualizeazaUI()
{
	cout << "\nAti ales optiunea Actualizare.\n";

	string titlu, autor, gen;
	int an;

	cout << "\n Dati atributele cartii existente: \n";

	std::getline(cin, titlu);

	cout << "Dati titlul: ";
	getline(std::cin, titlu);

	cout << "Dati autorul: ";
	getline(std::cin, autor);

	cout << "Dati genul: ";
	getline(std::cin, gen);

	cout << "Dati anul: ";
	cin >> an;

	string titlu1, autor1, gen1;
	int an1;

	cout << "\n Dati atributele cartii actualizate: \n";
	
	std::getline(cin, titlu1);

	cout << "Dati titlul: ";
	getline(std::cin, titlu1);

	cout << "Dati autorul: ";
	getline(std::cin, autor1);

	cout << "Dati genul: ";
	getline(std::cin, gen1);

	cout << "Dati anul: ";
	cin >> an1;

	try
	{
		serv.actualizeaza(titlu, autor, gen, an, titlu1, autor1, gen1, an1);

		cout << "\nActualizarea s-a realizat cu succes!\n\n";
	}
	catch (RepoException& e)
	{
		cout << e.get_eroare() << std::endl;
	}
	catch (ValidException& e)
	{
		cout << e.get_eroare() << std::endl;
	}
}

void UI::stergeUI()
{
	cout << "\nAti ales optiunea Stergere.\n";

	string titlu, autor, gen;
	int an;

	cout << "\n Dati atributele cartii: \n";

	std::getline(cin, titlu);

	cout << "Dati titlul: ";
	getline(std::cin, titlu);

	cout << "Dati autorul: ";
	getline(std::cin, autor);

	cout << "Dati genul: ";
	getline(std::cin, gen);

	cout << "Dati anul: ";
	cin >> an;

	try
	{
		serv.sterge(titlu, autor, gen, an);
		
		cout << "\nStergerea s-a realizat cu succes!\n\n";
		
	}
	catch (RepoException& e)
	{
		cout << e.get_eroare() << std::endl;
	}
}

void UI::cautaUI()
{
	cout << "\nAti ales optiunea Cautare.\n";

	string titlu, autor, gen;
	int an;

	cout << "\n Dati atributele cartii: \n";

	std::getline(cin, titlu);

	cout << "Dati titlul: ";
	getline(std::cin, titlu);

	cout << "Dati autorul: ";
	getline(std::cin, autor);

	cout << "Dati genul: ";
	getline(std::cin, gen);

	cout << "Dati anul: ";
	cin >> an;

	try
	{
		const auto t = serv.cautare(titlu, autor, gen, an);
		
		if (t == true)
		{
			cout << "\nCartea cautata exista in lista!\n\n";
		}
		else
		{
			cout << "\nCartea cautata nu exista in lista!\n\n";
		}
	}
	catch (RepoException& e)
	{
		cout << e.get_eroare();
	}
}

void UI::afiseazaUI()
{
	cout << "\n Ati ales optiunea Afisare\n";

	auto v = serv.get_carti();

	if (std::size(v) == 0)
	{
		cout << "\nNu exista carti in lista!\n\n";
	}
	else
	{
		cout << "\nCartile existente in lista (" << std::size(v) << ") sunt: \n";
		for (auto& i : v)
		{
			cout << "Titlu: " << i.get_titlu() << std::endl;
			
			cout << "Autor: " << i.get_autor() << std::endl;
			
			cout << "Gen: " << i.get_gen() << std::endl;
			
			cout << "An: " << i.get_an() << std::endl;

			cout << std::endl;
		}
	}
}

void UI::filtreazaUI()
{
	cout << "\n Ati ales optiunea Filtrare\n";

	string cmd{ "1" };

	cout << "\nDoriti sa filtrati cartile existente dupa: \n 1. Titlu - tasta 1 \n 2. Anul aparitiei - tasta 2\n";
	cout << "Optiunea este: ";

	cin >> cmd;

	if (cmd == "1")
	{
		//get_carti_filtrat_titlu

		string titlu;

		cout << "\n Dati atributul cartii: \n";

		std::getline(cin, titlu);

		cout << "Dati titlul: ";
		getline(std::cin, titlu);

		auto v = serv.filtreaza_titlu(titlu);

		if (std::size(v) == 0)
		{
			cout << "\nNu exista carti cu titlul "<< titlu<< "!\n";
		}
		else
		{
			cout << "\nCartile cu titlul " << titlu << " existente in lista (" << std::size(v) << ") sunt: \n\n";
			for (auto& i : v)
			{
				cout << "Titlu: " << i.get_titlu() << std::endl;

				cout << "Autor: " << i.get_autor() << std::endl;

				cout << "Gen: " << i.get_gen() << std::endl;

				cout << "An: " << i.get_an() << std::endl;

				cout << std::endl;
			}
		}
		
	}
	else if (cmd == "2")
	{
		// get_carti_filtrat_an

		int an;

		cout << "\n Dati atributele cartii: \n";

		cout << "Dati anul: ";
		
		cin >> an;

		auto v = serv.filtreaza_an(an);

		if (std::size(v) == 0)
		{
			cout << "\nNu exista carti cu anul " << an << "!\n\n";
		}
		else
		{
			cout << "\nCartile cu anul " << an << " existente in lista (" << std::size(v) << ") sunt: \n";
			for (auto& i : v)
			{
				cout << "Titlu: " << i.get_titlu() << std::endl;

				cout << "Autor: " << i.get_autor() << std::endl;

				cout << "Gen: " << i.get_gen() << std::endl;

				cout << "An: " << i.get_an() << std::endl;

				cout << std::endl;
			}
		}
	}
	else
	{
		cout << "\nOptiunea nu este valida!\n\n";
	}
}

void UI::sorteazaUI()
{
	cout << "\n Ati ales optiunea Sortare\n";

	string cmd{ "1" };

	cout << "\nDoriti sa sortati cartile existente dupa: \n 1. Titlu - tasta 1 \n 2. Autor - tasta 2 \n 3. Anul aparitiei + gen - tasta 3\n";
	cout << "Optiunea este: ";

	cin >> cmd;

	if (cmd == "1")
	{
		auto v = serv.sorteaza_titlu();

		if (std::size(v) == 0)
		{
			cout << "\nNu exista carti in lista!\n\n";
		}
		else
		{
			cout << "\nCartile existente in lista (" << std::size(v) << "), sortate dupa titlu, sunt: \n";
			for (auto& i : v)
			{
				cout << "Titlu: " << i.get_titlu() << std::endl;

				cout << "Autor: " << i.get_autor() << std::endl;

				cout << "Gen: " << i.get_gen() << std::endl;

				cout << "An: " << i.get_an() << std::endl;

				cout << std::endl;
			}
		}
	}
	else if (cmd == "2")
	{
		auto v = serv.sorteaza_autor();

		if (std::size(v) == 0)
		{
			cout << "\nNu exista carti in lista!\n\n";
		}
		else
		{
			cout << "\nCartile existente in lista (" << std::size(v) << "), sortate dupa autor, sunt: \n";
			for (auto& i : v)
			{
				cout << "Titlu: " << i.get_titlu() << std::endl;

				cout << "Autor: " << i.get_autor() << std::endl;

				cout << "Gen: " << i.get_gen() << std::endl;

				cout << "An: " << i.get_an() << std::endl;

				cout << std::endl;
			}
		}
	}
	else if (cmd == "3")
	{
		auto v = serv.sorteaza_an_gen();

		if (std::size(v) == 0)
		{
			cout << "\nNu exista carti in lista!\n\n";
		}
		else
		{
			cout << "\nCartile existente in lista (" << std::size(v) << "), sortate dupa an si gen, sunt: \n";
			for (auto& i : v)
			{
				cout << "Titlu: " << i.get_titlu() << std::endl;

				cout << "Autor: " << i.get_autor() << std::endl;

				cout << "Gen: " << i.get_gen() << std::endl;

				cout << "An: " << i.get_an() << std::endl;

				cout << std::endl;
			}
		}
	}
	else
	{
		cout << "\nOptiunea nu este valida!\n\n";
	}
}


void UI::adauga_automat()
{
	serv.adauga("Ion", "Liviu Rebreanu", "Roman", 2020);
	serv.adauga("Moara cu noroc", "Ioan Slavici", "Nuvela", 2020);
	serv.adauga("Poezii", "Nichita Stanescu", "Poezii", 2021);
	serv.adauga("Dictionar", "Alfa Beta", "Lingvistic", 2020);
	serv.adauga("Ion", "Liviu Rebreanu", "Roman", 2019);
}


void UI::start_ui()
{
	cout << "\nInceput program.\n\n";
	
	string cmd{ "1" };

	adauga_automat();

	cout << "\nAdaugare automata a unor carti.\n\n";

	while (cmd != "0")
	{
		cout << "Doriti sa: \n 1. Adaugati o carte - tasta 1 \n 2. Actualizati o carte - tasta 2 \n 3. Stergeti o carte - tasta 3 \n 4. Cautati o carte - tasta 4 \n 5. Afisati cartile - tasta 5 \n 6. Filtrati cartile - tasta 6 \n 7. Sortati cartile - tasta 7 \n Iesiti din aplicatia - tasta 0\n";

		cout << "Optiunea este: ";
		
		cin >> cmd;

		if (cmd == "1")
		{
			adaugaUI();
		}
		else if (cmd == "2")
		{
			actualizeazaUI();
		}
		else if (cmd == "3")
		{
			stergeUI();
		}
		else if (cmd == "4")
		{
			cautaUI();
		}
		else if (cmd == "5")
		{
			afiseazaUI();
		}
		else if (cmd == "6")
		{
			filtreazaUI();
		}
		else if (cmd == "7")
		{
			sorteazaUI();
		}
		else if (cmd == "0")
		{
			cout << "\nSunteti sigur ca doriti sa iesiti?\n 1. Da - tasta 1 \n 2. Nu - tasta 0";
			cout << "\nOptiunea este: ";

			cin >> cmd;

			if (cmd == "1")
			{
				cout << "\nSfarsit program.\n";
				break;
			}
			cout << std::endl;
		}
		else
		{
			cout << "\nOptiunea nu este valida!\n\n";
		}
	}
}
