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

class Crud2GUI : public QWidget, public Observer
{
private:

    Service& serv;

    QTableWidget* tbl = new QTableWidget;

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
        auto x = f;
    	
        tbl->setRowCount(x.size());

        int cur = 0;

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

            tbl->setItem(cur, 0, new QTableWidgetItem(QString::fromStdString(i.get_titlu())));
            tbl->setItem(cur, 1, new QTableWidgetItem(QString::fromStdString(i.get_autor())));
            tbl->setItem(cur, 2, new QTableWidgetItem(QString::fromStdString(i.get_gen())));
            tbl->setItem(cur, 3, new QTableWidgetItem(QString::fromStdString(std::to_string(i.get_an()))));

            cur++;
        }
    }

    QVBoxLayout* tabel()
    {
        QVBoxLayout* ls = new QVBoxLayout;

        ls->addWidget(new QLabel("Tabelul cartilor:"));

        tbl->setRowCount(1);
        tbl->setColumnCount(4);

        tbl->setHorizontalHeaderItem(0, new QTableWidgetItem("Titlu"));
        tbl->setHorizontalHeaderItem(1, new QTableWidgetItem("Autor"));
        tbl->setHorizontalHeaderItem(2, new QTableWidgetItem("Gen"));
        tbl->setHorizontalHeaderItem(3, new QTableWidgetItem("An"));

        /*9auto* it = new QTableWidgetItem;

        it->setText("ban");

        tbl->setItem(1, 1, new QTableWidgetItem("Banane"));
        tbl->setItem(1, 2, new QTableWidgetItem("Mere"));
        tbl->setItem(2, 1, it);*/

        ls->addWidget(tbl);

        return ls;
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

    void initSGUI()
    {
        QHBoxLayout* main = new QHBoxLayout;

        this->setLayout(main);

        //main->addLayout(meniu());
        //main->addStretch();
        main->addLayout(formular());
        main->addLayout(tabel());
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

    ~Crud2GUI()
    {
        serv.removeObserver(this);
    }

public:

    Crud2GUI(Service& s) : serv{ s }
    {
        initSGUI();
        loadData(serv.get_wish());
        initConnect();
    }
};