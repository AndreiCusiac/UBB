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
#include "smallGUI.h"
#include <string>
#include "service.h"
#include "repository.h"
#include "crud.h"
#include "crud2.h"
#include "read.h"
#include "observer.h"
#include <algorithm>
#include "guiModel.h"

class SmallGUI : public QWidget, public Observer
{
private:

    Service& serv;

    QListWidget* list = new QListWidget;

    QPushButton* btnAdd = new QPushButton("Adauga");
    QPushButton* btnGol = new QPushButton("Goleste");
    QPushButton* btnGen = new QPushButton("Genereaza");
    QPushButton* btnExp = new QPushButton("Export");

    QPushButton* btnCrud = new QPushButton("Cos CRUD");
    QPushButton* btnCrud2 = new QPushButton("Cos CRUD Tabel");
    QPushButton* btnRead = new QPushButton("Cos Read Only");

    QPushButton* btnExit = new QPushButton("Inchide");

    QLineEdit* txtTitlu = new QLineEdit;
    QLineEdit* txtAutor = new QLineEdit;
    QLineEdit* txtGen = new QLineEdit;
    QLineEdit* txtAn = new QLineEdit;
    QLineEdit* txtRandom = new QLineEdit;
    QLineEdit* txtExp = new QLineEdit;

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

        sec->addWidget(btnAdd);
        sec->addWidget(btnGol);
        sec->addWidget(btnGen);
        sec->addWidget(btnExp);

        sec->addStretch();
        sec->addWidget(btnCrud);
        sec->addWidget(btnRead);
        sec->addWidget(btnCrud2);
        sec->addStretch();

        sec->addWidget(btnExit);

        return sec;
    }

    QVBoxLayout* formular()
    {
        QVBoxLayout* ls = new QVBoxLayout;

        QFormLayout* form = new QFormLayout;

        form->addWidget(new QLabel{ "Atribute carte:" });
        form->addRow("Titlu:", txtTitlu);
        form->addRow("Autor:", txtAutor);
        form->addRow("Gen:", txtGen);
        form->addRow("An:", txtAn);

        ls->addLayout(form);

        ls->addStretch();

        QFormLayout* form1 = new QFormLayout;

        form->addRow("Numar random:", txtRandom);

        ls->addLayout(form1);

        ls->addStretch();

        QFormLayout* form2 = new QFormLayout;

        form->addRow("Fisier export:", txtExp);

        ls->addLayout(form1);

        ls->addStretch();

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

        main->addLayout(meniu());
        main->addStretch();
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

        QObject::connect(btnAdd, &QPushButton::clicked, [&]()
            {
                const auto titlu = txtTitlu->text().toStdString();
                const auto autor = txtAutor->text().toStdString();
                const auto gen = txtGen->text().toStdString();
                const auto an = txtAn->text().toStdString();

                try
                {
                    auto a = stoi(an);

                    try
                    {
                        serv.adauga_wishlist(titlu, autor, gen, a);
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
                    QMessageBox::warning(nullptr, "Atentionare!", "Format invalid pentru an!");
                }

                auto mes = stare_cos();

                QMessageBox::information(nullptr, "Stare cos", QString::fromStdString(mes));

                loadData(serv.get_wish());
            });

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

        QObject::connect(btnCrud, &QPushButton::clicked, [&]()
            {
                auto* t = new CrudGUI(serv);

                t->show();

            });

        QObject::connect(btnCrud2, &QPushButton::clicked, [&]()
            {
                auto* t = new Crud2GUI(serv);

                t->show();

            });

        QObject::connect(btnRead, &QPushButton::clicked, [&]()
            {
                auto* t = new ReadOnlyGUI(serv);

                t->show();

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

        QObject::connect(btnExp, &QPushButton::clicked, [&]()
            {
                const auto num = txtExp->text().toStdString();

                try
                {
                    serv.exportCSVSimplu(num);

                    string mes = "Exportul in fisierul ";

                    mes += num;

                    mes += " s-a realizat cu succes!";

                    QMessageBox::information(nullptr, "Atentionare!", QString::fromStdString(mes));

                }
                catch (ofstream::failure&)
                {
                    QMessageBox::warning(nullptr, "Stare cos", "A aparut o eroare la scrierea fisierului!");
                }

                auto mes = stare_cos();

                //QMessageBox::information(nullptr, "Stare cos", QString::fromStdString(mes));

                loadData(serv.get_wish());

            });


        QObject::connect(btnExit, &QPushButton::clicked, [&]()
            {
                //qDebug() << "Ati parasit aplicatia!";

                QMessageBox* msgBox = new QMessageBox;

                msgBox->setStandardButtons(QMessageBox::Ok | QMessageBox::Cancel);

                msgBox->setText("Sunteti sigur ca doriti sa inchideti cosul?");

                msgBox->show();

                int ret = msgBox->exec();

                if (ret == QMessageBox::Ok)
                {
                    this->close();
                    msgBox->close();
                }
                else
                {
                    msgBox->close();
                }

                //QMessageBox::information(nullptr, "Info", "Ati parasit aplicatia!");
                //QMessageBox::warning(nullptr, "Atentionare", "Atribute invalide!");

            });
    }

    ~SmallGUI()
    {
        serv.removeObserver(this);
    }

public:

    SmallGUI(Service& s) : serv{ s }
    {
        initSGUI();
        loadData(serv.get_wish());
        initConnect();
    }
};