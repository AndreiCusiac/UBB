#pragma once
#include "service.h"
#include <QLabel>
#include <QWidget>
#include <QPushButton>
#include <QLineEdit>
#include <QFormLayout>
#include <QTableWidget>
#include <QVBoxLayout>
#include <QHBoxLayout>
#include <QObject>
#include <QMessageBox>

class MyGUI : public QWidget
{
private:

	Service& serv;

	QTableWidget* tab = new QTableWidget;

	QLineEdit* TXTid = new QLineEdit;
	QLineEdit* TXTdesc = new QLineEdit;
	QLineEdit* TXTnr = new QLineEdit;
	QLineEdit* TXTprog = new QLineEdit;
	QLineEdit* TXTst = new QLineEdit;

	QPushButton* add = new QPushButton{"Adauga"};
	QPushButton* cauta = new QPushButton{ "Cauta" };
	QPushButton* reload = new QPushButton{ "Reload" };

	void init()
	{
		QHBoxLayout* l = new QHBoxLayout;

		this->setLayout(l);

		QVBoxLayout* v = new QVBoxLayout;

		QFormLayout* f = new QFormLayout;

		f->addRow(new QLabel{ "Atribute:" });
		f->addRow("Id: ", TXTid);
		f->addRow("Descriere: ", TXTdesc);
		f->addRow("Nr prog: ", TXTnr);
		f->addRow("Programatori: ", TXTprog);
		f->addRow("Stare: ", TXTst);

		v->addLayout(f);

		v->addStretch();

		v->addWidget(add);
		v->addWidget(cauta);
		v->addWidget(reload);

		l->addLayout(v);
		l->addWidget(tab);
	}

	void set_tabel()
	{
		tab->setRowCount(1);
		tab->setColumnCount(4);

		tab->setHorizontalHeaderItem(0, new QTableWidgetItem("ID"));
		tab->setHorizontalHeaderItem(1, new QTableWidgetItem("Descriere"));
		tab->setHorizontalHeaderItem(2, new QTableWidgetItem("Stare"));
		tab->setHorizontalHeaderItem(3, new QTableWidgetItem("Nr prog"));
	}
	
	void loadData(vector<Task> v)
	{
		tab->setRowCount(v.size());

		int cur = 0;
		
		for (auto i : v)
		{
			tab->setItem(cur, 0, new QTableWidgetItem(QString::fromStdString(to_string(i.get_id()))));
			tab->setItem(cur, 1, new QTableWidgetItem(QString::fromStdString(i.get_descriere())));
			tab->setItem(cur, 2, new QTableWidgetItem(QString::fromStdString(i.get_stare())));
			tab->setItem(cur, 3, new QTableWidgetItem(QString::fromStdString(to_string(i.get_programatori().size()))));

			cur++;
		}

	}

	void connect()
	{
		QObject::connect(add, &QPushButton::clicked, [&]()
			{
				const auto id = TXTid->text().toInt();
				const auto desc = TXTdesc->text().toStdString();
				const auto stare = TXTst->text().toStdString();
				const auto nrprog = TXTnr->text().toInt();
				const auto prog = TXTprog->text().toStdString();

				try
				{
					serv.adauga(id, desc, vector<string>{prog}, stare);
				}
				catch (RepoException& e)
				{
					QMessageBox::warning(nullptr, "Atentie!", QString::fromStdString(e.get_eroare()));
				}
				catch (ServException& e)
				{
					QMessageBox::warning(nullptr, "Atentie!", QString::fromStdString(e.get_eroare()));
				}

				loadData(serv.sort_stare());
			});

		QObject::connect(cauta, &QPushButton::clicked, [&]()
			{
				const auto prog = TXTprog->text().toStdString();

				loadData(serv.get_nume(prog));
			});
		
		QObject::connect(reload, &QPushButton::clicked, [&]()
			{
				loadData(serv.sort_stare());
			});
	}
	
public:

	MyGUI(Service& s) : serv{s}
	{
		init();
		set_tabel();
		loadData(serv.sort_stare());

		connect();
	}
	
};