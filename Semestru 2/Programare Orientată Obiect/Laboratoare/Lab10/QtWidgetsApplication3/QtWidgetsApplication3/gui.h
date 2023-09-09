#pragma once
#include <QWidget>
#include <QListWidget>
#include <QPushButton>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFormLayout>
#include <QLabel>
#include <QLineEdit>
#include <QObject>
#include <QMessageBox> 
#include <QtWidgets/QApplication>

class MyGUI : public QWidget
{
private:

	QListWidget* l = new QListWidget;

	QPushButton* add = new QPushButton("Adauga");
	QPushButton* mod = new QPushButton("Modifica");
	QPushButton* del = new QPushButton("Sterge");
	QPushButton* sor = new QPushButton("Sort");
	QPushButton* exit = new QPushButton("Exit");

	QLineEdit* id = new QLineEdit;
	QLineEdit* nume = new QLineEdit;
	QLineEdit* pret = new QLineEdit;
	
	QVBoxLayout* meniu()
	{
		QVBoxLayout* men = new QVBoxLayout;

		men->addWidget(add);
		men->addWidget(mod);
		men->addWidget(del);
		men->addWidget(sor);
		men->addWidget(exit);

		return men;
	}

	QVBoxLayout* form()
	{
		QVBoxLayout* men = new QVBoxLayout;

		men->addWidget(new QLabel("Detalii: "));

		QFormLayout* f = new QFormLayout;

		f->addRow("ID: ", id);
		f->addRow("Nume: ", nume);
		f->addRow("Pret: ", pret);

		men->addLayout(f);

		return men;

	}

	QVBoxLayout* lista()
	{
		QVBoxLayout* men = new QVBoxLayout;

		men->addWidget(new QLabel("Lista: "));

		men->addWidget(l);

		return men;

	}

	void initGUI()
	{
		QHBoxLayout* main = new QHBoxLayout;

		this->setLayout(main);

		main->addLayout(meniu());
		main->addStretch();
		main->addLayout(form());
		main->addLayout(lista());
	}

	void connect()
	{
		QObject::connect(add, &QPushButton::clicked, [&]()
			{
				QMessageBox::information(nullptr, "Adaugare", "Ati apasat pe adaugare!");
			});
		
		QObject::connect(exit, &QPushButton::clicked, [&]()
			{
				this->close();

				QMessageBox::information(nullptr, "Status", "Ati parasit aplicatia!");
			});
	}

public:

	MyGUI()
	{
		initGUI();

		// loadData();

		connect();
	}
};