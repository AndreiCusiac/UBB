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
#include "model.h"
#include <string>
#include "service.h"
#include "repository.h"
#include "crud.h"
#include "crud2.h"
#include "read.h"
#include "observer.h"
#include <QItemDelegate>
#include <QVariant> 
#include <algorithm>

using std::string;

/*class Delegate : QItemDelegate
{
public:
	
    Delegate(QWidget* parent = 0) : QItemDelegate(parent) {}
	
    void paint(QPainter* painter, const QStyleOptionViewItem& option, const QModelIndex& index) const
    {
        QStyleOptionViewItem opt(option);

        if (index.column() == _colorColumn)
        {
            opt.palette.setColor(
                QPalette::Base,
                QColor(255, 255, 204));

            opt.palette.setColor(
                QPalette::Window,
                QColor(255, 255, 204));
        }

        return QItemDelegate::paint(painter, opt, index);
    }
};*/

class SmallGUIModel : public QWidget, public Observer
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

    ~SmallGUIModel()
    {
        serv.removeObserver(this);
    }

public:

    SmallGUIModel(Service& s) : serv{ s }
    {
        initSGUI();
        loadData(serv.get_wish());
        initConnect();
    }
};

class MyGUIModel : public QWidget
{
private:    

    Service& serv;

    QListView* list = new QListView;
    QTableView* tbl = new QTableView;

    MyListModel* model;

    QListWidgetItem* j = new QListWidgetItem;
    QWidget* x = new QWidget;

    QPushButton* btnAdd = new QPushButton("Adauga");
    QPushButton* btnDel = new QPushButton("Sterge");
    QPushButton* btnModif = new QPushButton("Modifica");
    QPushButton* btnCauta = new QPushButton("Cauta");

    QPushButton* btnCos = new QPushButton("Acceseaza cos");

    QPushButton* btnSort = new QPushButton("Sorteaza");
    QPushButton* btnSortT = new QPushButton("Sorteaza dupa titlu");
    QPushButton* btnSortA = new QPushButton("Sorteaza dupa autor");
    QPushButton* btnSortAG = new QPushButton("Sorteaza dupa an si gen");

    QPushButton* btnFiltr = new QPushButton("Filtreaza");
    QPushButton* btnFiltrT = new QPushButton("Filtreaza dupa titlu");
    QPushButton* btnFiltrA = new QPushButton("Filtreaza dupa an");

    QPushButton* btnUndo = new QPushButton("Undo");
    QPushButton* btnRefresh = new QPushButton("Refresh");
    QPushButton* btnExit = new QPushButton("Iesire");

    QLineEdit* txtTitlu = new QLineEdit;
    QLineEdit* txtAutor = new QLineEdit;
    QLineEdit* txtGen = new QLineEdit;
    QLineEdit* txtAn = new QLineEdit;

    QLineEdit* txtTitluF = new QLineEdit;
    QLineEdit* txtAnF = new QLineEdit;

    void adauga_automat()
    {
        if (serv.fisier_accesat() == true)
        {
            return;
        }

        serv.adauga_aut("Ion", "Liviu Rebreanu", "Roman", 2020);
        serv.adauga_aut("Moara cu noroc", "Ioan Slavici", "Nuvela", 2020);
        serv.adauga_aut("Poezii", "Nichita Stanescu", "Poezii", 2021);
        serv.adauga_aut("Dictionar", "Alfa Beta", "Lingvistic", 2020);
        serv.adauga_aut("Ion", "Liviu Rebreanu", "Roman", 2019);
    }

    typedef vector<Carte> fct;

    void loadData(fct f)
    {
        model->setCarte(f);
    }

    QVBoxLayout* meniu()
    {
        QVBoxLayout* sec = new QVBoxLayout;

        //sec->addWidget(new QLabel{ "Meniu" });

        sec->addWidget(btnAdd);
        sec->addWidget(btnDel);
        sec->addWidget(btnModif);
        sec->addWidget(btnCauta);
        sec->addWidget(btnCos);
        sec->addWidget(btnUndo);
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

        return ls;
    }

    QVBoxLayout* lista()
    {
        QVBoxLayout* ls = new QVBoxLayout;

        ls->addWidget(new QLabel("Lista cartilor:"));

        list->setModel(model);

        ls->addWidget(list);

        QHBoxLayout* btn = new QHBoxLayout;

        btn->addWidget(btnSort);
        btn->addWidget(btnFiltr);

        ls->addLayout(btn);

        ls->addWidget(btnRefresh);


        return ls;
    }

    QVBoxLayout* tabel()
    {
        QVBoxLayout* ls = new QVBoxLayout;

        ls->addWidget(new QLabel("Tabelul cartilor:"));

        tbl->setModel(model);
    	
        /*9auto* it = new QTableWidgetItem;

        it->setText("ban");

        tbl->setItem(1, 1, new QTableWidgetItem("Banane"));
        tbl->setItem(1, 2, new QTableWidgetItem("Mere"));
        tbl->setItem(2, 1, it);*/

        ls->addWidget(tbl);

        return ls;
    }

    void initGUI()
    {
        QHBoxLayout* main = new QHBoxLayout;

        this->setLayout(main);

        main->addLayout(meniu());
        main->addStretch();
        main->addLayout(formular());
        main->addLayout(lista());
        //main->addWidget(lista());
        //main->addLayout(tabel());
    }


    void initConnect()
    {
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
                        serv.adauga(titlu, autor, gen, a);
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

                loadData(serv.get_carti());
            });

        QObject::connect(btnDel, &QPushButton::clicked, [&]()
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
                        serv.sterge(titlu, autor, gen, a);
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

                loadData(serv.get_carti());

            });

        QObject::connect(btnCauta, &QPushButton::clicked, [&]()
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
                        auto t = serv.cautare(titlu, autor, gen, a);
                        if (t == true)
                        {
                            QMessageBox::information(nullptr, "Rezultat", "Cartea cautata exista in lista!");
                        }
                        else
                        {
                            QMessageBox::information(nullptr, "Rezultat", "Cartea cautata nu exista in lista!");
                        }
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

                loadData(serv.get_carti());

            });

        QObject::connect(btnRefresh, &QPushButton::clicked, [&]()
            {
                loadData(serv.get_carti());

            });

        QObject::connect(btnSort, &QPushButton::clicked, [&]()
            {

                QWidget* s = new QWidget;

                QVBoxLayout* sor = new QVBoxLayout;

                s->setLayout(sor);

                sor->addWidget(btnSortT);
                sor->addWidget(btnSortA);
                sor->addWidget(btnSortAG);
                QPushButton* close = new QPushButton{ "Inchide" };
                sor->addWidget(close);

                s->show();

                QObject::connect(btnSortT, &QPushButton::clicked, [&, s]()
                    {
                        loadData(serv.sorteaza_titlu());
                        s->close();
                    });

                QObject::connect(btnSortA, &QPushButton::clicked, [&, s]()
                    {
                        loadData(serv.sorteaza_autor());
                        s->close();
                    });

                QObject::connect(btnSortAG, &QPushButton::clicked, [&, s]()
                    {
                        loadData(serv.sorteaza_an_gen());
                        s->close();
                    });

                QObject::connect(close, &QPushButton::clicked, [s]()
                    {

                        s->close();
                    });
            });

        QObject::connect(btnFiltr, &QPushButton::clicked, [&]()
            {

                QWidget* s = new QWidget;

                QListWidget* l = new QListWidget;

                QHBoxLayout* main = new QHBoxLayout;

                QVBoxLayout* sor = new QVBoxLayout;

                s->setLayout(main);

                QFormLayout* form1 = new QFormLayout;
                QFormLayout* form2 = new QFormLayout;

                sor->addWidget(btnFiltrT);
                form1->addRow("Titlu:", txtTitluF);
                sor->addLayout(form1);

                sor->addWidget(btnFiltrA);
                form2->addRow("An:", txtAnF);
                sor->addLayout(form2);

                QPushButton* close = new QPushButton{ "Inchide" };
                sor->addWidget(close);

                main->addLayout(sor);
                main->addWidget(l);

                s->show();

                QObject::connect(btnFiltrT, &QPushButton::clicked, [&, s, l]()
                    {
                        const auto titlu = txtTitluF->text().toStdString();

                        l->clear();

                        auto x = serv.filtreaza_titlu(titlu);

                        for (auto i : x)
                        {
                            QListWidgetItem* it = new QListWidgetItem(QString::fromStdString(i.get_titlu()));

                            l->addItem(it);
                        }

                        //s->close();
                    });

                QObject::connect(btnFiltrA, &QPushButton::clicked, [&, s]()
                    {
                        try
                        {
                            const auto an = txtAnF->text().toStdString();

                            const int a = stoi(an);

                            l->clear();

                            auto x = serv.filtreaza_an(a);

                            for (auto i : x)
                            {
                                QListWidgetItem* it = new QListWidgetItem(QString::fromStdString(i.get_titlu()));

                                l->addItem(it);
                            }
                        }
                        catch (exception)
                        {
                            QMessageBox::warning(nullptr, "Atentionare!", "Format invalid pentru an!");
                        }


                        //s->close();
                    });

                QObject::connect(close, &QPushButton::clicked, [s]()
                    {

                        s->close();
                    });
            });

        QObject::connect(btnUndo, &QPushButton::clicked, [&]()
            {

                try
                {
                    serv.undo();

                    QMessageBox::information(nullptr, "Undo", "Operatia de undo s-a realizat cu succes!");
                }
                catch (ServicException& e)
                {
                    QMessageBox::warning(nullptr, "Undo", QString::fromStdString(e.get_eroare()));
                }

                loadData(serv.get_carti());

            });

        QObject::connect(btnCos, &QPushButton::clicked, [&]()
            {
                SmallGUIModel* cos = new SmallGUIModel(serv);

                cos->show();

            });

        QObject::connect(btnModif, &QPushButton::clicked, [&]()
            {
                QWidget* updateW = new QWidget;

                QVBoxLayout* big = new QVBoxLayout;

                QHBoxLayout* formulare = new QHBoxLayout;

                updateW->setLayout(big);

                QVBoxLayout* ls1 = new QVBoxLayout;

                QFormLayout* formVechi = new QFormLayout;

                QLineEdit* txtTitluVechi = new QLineEdit;
                QLineEdit* txtAutorVechi = new QLineEdit;
                QLineEdit* txtGenVechi = new QLineEdit;
                QLineEdit* txtAnVechi = new QLineEdit;

                formVechi->addWidget(new QLabel{ "Atribute initiale:" });
                formVechi->addRow("Titlu:", txtTitluVechi);
                formVechi->addRow("Autor:", txtAutorVechi);
                formVechi->addRow("Gen:", txtGenVechi);
                formVechi->addRow("An:", txtAnVechi);

                ls1->addLayout(formVechi);

                formulare->addLayout(ls1);

                formulare->addStretch();



                QVBoxLayout* ls2 = new QVBoxLayout;

                QFormLayout* formNou = new QFormLayout;

                QLineEdit* txtTitluNou = new QLineEdit;
                QLineEdit* txtAutorNou = new QLineEdit;
                QLineEdit* txtGenNou = new QLineEdit;
                QLineEdit* txtAnNou = new QLineEdit;

                formNou->addWidget(new QLabel{ "Atribute noi:" });
                formNou->addRow("Titlu:", txtTitluNou);
                formNou->addRow("Autor:", txtAutorNou);
                formNou->addRow("Gen:", txtGenNou);
                formNou->addRow("An:", txtAnNou);

                ls2->addLayout(formNou);

                formulare->addLayout(ls2);

                big->addLayout(formulare);

                QPushButton* actualModif = new QPushButton{ "Modifica" };

                big->addWidget(actualModif);

                updateW->show();

                QObject::connect(actualModif, &QPushButton::clicked, [&, updateW, txtTitluNou, txtTitluVechi, txtAutorNou, txtAutorVechi, txtGenNou, txtGenVechi, txtAnNou, txtAnVechi]()
                    {
                        const auto titlu = txtTitluVechi->text().toStdString();
                        const auto autor = txtAutorVechi->text().toStdString();
                        const auto gen = txtGenVechi->text().toStdString();
                        const auto an = txtAnVechi->text().toStdString();

                        const auto titlu1 = txtTitluNou->text().toStdString();
                        const auto autor1 = txtAutorNou->text().toStdString();
                        const auto gen1 = txtGenNou->text().toStdString();
                        const auto an1 = txtAnNou->text().toStdString();

                        try
                        {
                            auto a = stoi(an);
                            auto a1 = stoi(an1);

                            try
                            {
                                serv.actualizeaza(titlu, autor, gen, a, titlu1, autor1, gen1, a1);
                                loadData(serv.get_carti());

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

                        updateW->close();
                    });

                loadData(serv.get_carti());
            });

        /*QObject::connect(list->selectionModel(), &QItemSelectionModel::selectionChanged, [&]()
            {
                int dap{ 1 };
        	
                if (list->selectionModel()->selectedIndexes().isEmpty() == true)
                {
                    txtTitlu->setText("");
                    txtAutor->setText("");
                    txtGen->setText("");
                    txtAn->setText("");
                }
                else
                {
                    auto sel = list->selectionModel()->selectedIndexes().at(0);
                	
                    /*auto i = sel.at(0);

                    for (int p = 0; p < serv.get_carti().size(); p++)
                    {
                        auto z = list->item(p);

                        z->setBackground(QBrush{ Qt::transparent });
                    }*/

                   /* x->close();

                    //sel.setBackground(QBrush{ Qt::darkGreen, Qt::SolidPattern });

                    auto tit = sel.data(Qt::DisplayRole).toString();

                    auto info = sel.data(Qt::UserRole).toString().toStdString();

                    vector<string> s;

                    int start = 0;
                    int end = info.find("_");

                    for (int i = 0; i <= 2; i++)
                    {
                        s.push_back(info.substr(start, end - start));
                        start = end + 1;
                        end = info.find("_", start);
                    }

                    auto aut = s[0];
                    auto gen = s[1];
                    auto as = s[2];
                    auto an = stoi(s[2]);

                    txtTitlu->setText(tit);
                    txtAutor->setText(QString::fromStdString(aut));
                    txtGen->setText(QString::fromStdString(gen));
                    txtAn->setText(QString::number(an));

                    QVBoxLayout* sor = new QVBoxLayout;



                    QWidget* n = new QWidget;

                    string Titlu = "Titlu: ";
                    Titlu += tit.toStdString();

                    string Autor = "Autor: ";
                    Autor += aut;

                    string Gen = "Gen: ";
                    Gen += gen;

                    string An = "An: ";
                    An += as;

                    sor->addWidget(new QLabel("Atributele cartii selectate sunt: "));
                    sor->addWidget(new QLabel(QString::fromStdString(Titlu)));
                    sor->addWidget(new QLabel(QString::fromStdString(Autor)));
                    sor->addWidget(new QLabel(QString::fromStdString(Gen)));
                    sor->addWidget(new QLabel(QString::fromStdString(An)));
                    QPushButton* close = new QPushButton{ "Inchide" };
                    sor->addWidget(close);

                    n->setLayout(sor);

                    x->close();

                    x = n;

                    n->show();

                    QObject::connect(close, &QPushButton::clicked, [n]()
                        {
                            n->close();
                        });

                    //j = i;

                }
                //auto i = sel.at(0);

                //i->setBackground(QBrush{ Qt::red, Qt::Dense6Pattern });
            ;});*/

	QObject::connect(list->selectionModel(), &QItemSelectionModel::selectionChanged, [&]()
    {


        auto sel = list->selectionModel()->selectedIndexes();

        if (sel.empty())
        {
            txtTitlu->setText("");
            txtAutor->setText("");
            txtGen->setText("");
            txtAn->setText("");
        }
        else
        {
            auto i = sel.at(0);

            /*for (auto p : list)
            {
                p->setBackground(QBrush{ Qt::transparent });
            }*/

            /*for (int p = 0; p < serv.get_carti().size(); p++)
            {
                auto z = list

                z->setBackground(QBrush{ Qt::transparent });
            }*/

            x->close();

            auto ala = i.data(Qt::BackgroundRole);

            //i.setBackground(QBrush{ Qt::darkGreen, Qt::SolidPattern });

            auto tit = i.data(Qt::DisplayRole).toString();

            auto info = i.data(Qt::UserRole).toString().toStdString();

            vector<string> s;

            int start = 0;
            int end = info.find("_");

            for (int i = 0; i <= 2; i++)
            {
                s.push_back(info.substr(start, end - start));
                start = end + 1;
                end = info.find("_", start);
            }

            auto aut = s[0];
            auto gen = s[1];
            auto as = s[2];
            auto an = stoi(s[2]);

            txtTitlu->setText(tit);
            txtAutor->setText(QString::fromStdString(aut));
            txtGen->setText(QString::fromStdString(gen));
            txtAn->setText(QString::number(an));

            QVBoxLayout* sor = new QVBoxLayout;



            QWidget* n = new QWidget;

            string Titlu = "Titlu: ";
            Titlu += tit.toStdString();

            string Autor = "Autor: ";
            Autor += aut;

            string Gen = "Gen: ";
            Gen += gen;

            string An = "An: ";
            An += as;

            sor->addWidget(new QLabel("Atributele cartii selectate sunt: "));
            sor->addWidget(new QLabel(QString::fromStdString(Titlu)));
            sor->addWidget(new QLabel(QString::fromStdString(Autor)));
            sor->addWidget(new QLabel(QString::fromStdString(Gen)));
            sor->addWidget(new QLabel(QString::fromStdString(An)));
            QPushButton* close = new QPushButton{ "Inchide" };
            sor->addWidget(close);

            n->setLayout(sor);

            x->close();

            x = n;

            n->show();

            QObject::connect(close, &QPushButton::clicked, [n]()
                {
                    n->close();
                });

            // j = i;

        }
        //auto i = sel.at(0);

        //i->setBackground(QBrush{ Qt::red, Qt::Dense6Pattern });
    });

        QObject::connect(btnExit, &QPushButton::clicked, [&]()
            {
                //qDebug() << "Ati parasit aplicatia!";

                QMessageBox* msgBox = new QMessageBox;

                msgBox->setStandardButtons(QMessageBox::Ok | QMessageBox::Cancel);

                msgBox->setText("Sunteti sigur ca doriti sa parasiti aplicatia?");

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

public:

    MyGUIModel(Service& s) : serv{ s }
    {
        model = new MyListModel{ serv.get_carti() };
        list->setModel(model);
        tbl->setModel(model);
    	
        initGUI();
        list->setSelectionMode(QAbstractItemView::SingleSelection);
        adauga_automat();
        loadData(serv.get_carti());
        initConnect();
    }


};

/*
 * int main(int argc, char *argv[])
    {
        test_all();

        QApplication a(argc, argv);

        //UnNouRepository repo{0.5};
        FileRepository repo("numeFis.txt");
        //repo.load_from_file();
        //Repository repo;
        Validator val;
        Service serv{ repo, val };

        MyGUI wnd{serv};

        wnd.show();
    }
 *
 *
 */
