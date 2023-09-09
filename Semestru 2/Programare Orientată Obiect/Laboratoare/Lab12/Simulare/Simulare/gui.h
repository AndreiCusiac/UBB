#pragma once
#include <QtWidgets/QtWidgets>
#include <QtWidgets/QApplication>
#include <QListWidget>
#include <QPushButton>
#include <QLineEdit>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QFormLayout>
#include <QLabel> 

class MyGUI : public QWidget
{
private:

	QListWidget* list = new QListWidget;

	QPushButton* filtrS = new QPushButton("Filtr Sectie");
	QPushButton* filtrN = new QPushButton("Filtr Nume");
	QPushButton* filtrT = new QPushButton("Filtr Toate");

	QLineEdit* cnp = new QLineEdit;
	QLineEdit* nume = new QLineEdit;
	QLineEdit* sectie = new QLineEdit;

	QVBoxLayout* meniu()
	{
		QVBoxLayout* m = new QVBoxLayout;

		QFormLayout* f1 = new QFormLayout;

		f1->addRow("CNP: ", cnp);

		m->addLayout(f1);

		m->addStretch();

		QFormLayout* f2 = new QFormLayout;

		f2->addWidget(filtrS);

		f2->addRow("Sectie: ", sectie);

		f2->addWidget(filtrN);

		f2->addRow("Nume: ", nume);

		f2->addWidget(filtrT);

		m->addLayout(f2);

		return m;
	}

	QVBoxLayout* lista()
	{
		QVBoxLayout* xa = new QVBoxLayout;

		xa->addWidget(new QLabel("Lista doctorilor: "));

		xa->addWidget(list);

		return xa;
	}
	
	void initGUI()
	{
		QHBoxLayout* main = new QHBoxLayout;

		this->setLayout(main);

		main->addLayout(meniu());
		main->addLayout(lista());
	}


public:

	MyGUI()
	{
		initGUI();
	}
	
};