#include <QtWidgets/QApplication>
#include <QLabel>

#include "gui.h"
#include "domain.h"
#include "repository.h"
#include "service.h"
#include <assert.h>

void test_scriere()
{
	string nume = "Ala/Bala/Cai";

	vector<string> v;

	string aici = "";

	for (auto i : nume)
	{
		if (i == '/')
		{
			v.push_back(aici);

			aici = "";
		}
		else if (std::isalpha(i))
		{
			aici.push_back(i);
		}
	}

	assert(v[0] == "Ala");

	assert(v[1] == "Bala");

	assert(v[2] == "Cai");

	assert(v.size() == 3);
}

void teste()
{
	test_domain();
	//test_scriere();

	test_repository();
	test_service();
	
	return;
}

int main(int argc, char *argv[])
{
	teste();
	
    QApplication a(argc, argv);

	QLabel* l = new QLabel("Salut");

	//l->show();

	Repository repo("F.txt");

	repo.incarca();
	
	Service serv{ repo };

	/*Task t0{ 4, "ala", vector<string>{"Ion", "Geo"}, "open" };
	Task t1{ 1, "ala", vector<string>{"Ion", "Geo"}, "open" };
	Task t3{ 3, "bala", vector<string>{"Iobn", "Gebo", "Chelo"}, "inprogress" };
	Task t2{ 2, "bala", vector<string>{"Vasi", "Geo", "Ala"}, "closed" };

	Task t4{ 234, "", vector<string>{"Vasi", "Geo", "Ala"}, "closed" };
	Task t5{ 23, "fafas", vector<string>{"Vasi", "Geo", "Ala"}, "closed1" };
	Task t6{ 24, "asadas", vector<string>{"Vasi", "Geo", "Ala", "Cala", "Dala"}, "closed" };

	serv.adauga(t0.get_id(), t0.get_descriere(), t0.get_programatori(), t0.get_stare());
	serv.adauga(t1.get_id(), t1.get_descriere(), t1.get_programatori(), t1.get_stare());
	serv.adauga(t2.get_id(), t2.get_descriere(), t2.get_programatori(), t2.get_stare());
	serv.adauga(t3.get_id(), t3.get_descriere(), t3.get_programatori(), t3.get_stare());*/
	
	MyGUI x{ serv };

	x.show();
	
    return a.exec();
}
