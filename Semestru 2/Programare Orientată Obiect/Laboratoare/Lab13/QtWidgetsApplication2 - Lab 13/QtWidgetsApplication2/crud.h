#pragma once

#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qformlayout.h>
#include <QListWidget>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QLineEdit>
#include <QtWidgets/qlistview.h>
#include <QTableWidget>
#include <qmessagebox.h>
#include "gui.h"
#include <string>
#include "service.h"
#include "repository.h"
#include <algorithm>

class CrudGUI : public QWidget, public Observer
{
private:

    Service& serv;

    QListWidget* list = new QListWidget;

    QPushButton* btnGol = new QPushButton("Goleste");
    QPushButton* btnGen = new QPushButton("Genereaza");

    //QPushButton* btnExit = new QPushButton("Inchide");

    QLineEdit* txtRandom = new QLineEdit;

    typedef vector<Carte> fct;

    void update() override
    {
        loadData(serv.get_wish());
    }

    void loadData(fct f)
    {
        list->clear();

        auto x = f;

        //auto v = serv.get_carti();

        for (auto i : f)
        {
            QListWidgetItem* it = new QListWidgetItem(QString::fromStdString(i.get_titlu()));

            string info;

            info += i.get_autor();
            info += "_";
            info += i.get_gen();
            info += "_";
            info += to_string(i.get_an());

            it->setData(Qt::UserRole, QString::fromStdString(info));

            list->addItem(it);
        }
    }

    QVBoxLayout* meniu()
    {
        QVBoxLayout* sec = new QVBoxLayout;

        //sec->addWidget(new QLabel{ "Meniu" });

        sec->addWidget(btnGen);
    	
        sec->addWidget(btnGol);
        

        return sec;
    }

    QVBoxLayout* formular()
    {
        QVBoxLayout* ls = new QVBoxLayout;

        QFormLayout* form1 = new QFormLayout;

        ls->addWidget(btnGen);
        form1->addRow("Numar random:", txtRandom);

        ls->addLayout(form1);

        ls->addStretch();
    	
        ls->addWidget(btnGol);  

        return ls;
    }

    QVBoxLayout* lista()
    {
        QVBoxLayout* ls = new QVBoxLayout;

        ls->addWidget(new QLabel("Lista cartilor din cos:"));

        ls->addWidget(list);

        return ls;
    }

    void initSGUI()
    {
        QHBoxLayout* main = new QHBoxLayout;

        this->setLayout(main);

        //main->addLayout(meniu());
        //main->addStretch();
        main->addLayout(formular());
        main->addLayout(lista());
    }

    string stare_cos()
    {
        int s = serv.get_wish().size();

        string mes = "Momentan, se afla ";

        mes += to_string(s);

        mes += " carti in cos.";

        return mes;
    }

    void initConnect()
    {
        serv.addObserver(this);
    	
        QObject::connect(btnGen, &QPushButton::clicked, [&]()
            {
                const auto num = txtRandom->text().toStdString();

                try
                {
                    auto a = stoi(num);

                    try
                    {
                        serv.adauga_random_wishlist(a);

                        string mes = "Adaugarea celor ";
                        mes += num;
                        mes += " carti in cos s-a realizat cu succes!";

                        QMessageBox::information(nullptr, "Stare cos", QString::fromStdString(mes));
                    }
                    catch (RepoException& e)
                    {
                        QMessageBox::warning(nullptr, "Atentionare!", QString::fromStdString(e.get_eroare()));
                    }
                    catch (ValidException& e)
                    {
                        QMessageBox::warning(nullptr, "Atentionare!", QString::fromStdString(e.get_eroare()));
                    }

                }
                catch (exception)
                {
                    QMessageBox::warning(nullptr, "Atentionare!", "Format invalid pentru numar!");
                }

                auto mes = stare_cos();

                QMessageBox::information(nullptr, "Stare cos", QString::fromStdString(mes));

                loadData(serv.get_wish());

            });

        QObject::connect(btnGol, &QPushButton::clicked, [&]()
            {
                try
                {
                    try
                    {
                        serv.sterge_wishlist();
                        QMessageBox::information(nullptr, "Stare cos", "Golirea cosului s-a realizat cu succes!");
                    }
                    catch (RepoException& e)
                    {
                        QMessageBox::warning(nullptr, "Atentionare!", QString::fromStdString(e.get_eroare()));
                    }

                }
                catch (exception)
                {
                    QMessageBox::warning(nullptr, "Atentionare!", "Format invalid pentru an!");
                }

                auto mes = stare_cos();

                QMessageBox::information(nullptr, "Stare cos", QString::fromStdString(mes));

                loadData(serv.get_wish());

            });
    }

    ~CrudGUI()
    {
        serv.removeObserver(this);
    }

public:

    CrudGUI(Service& s) : serv{ s }
    {
        initSGUI();
        loadData(serv.get_wish());
        initConnect();
    }
};