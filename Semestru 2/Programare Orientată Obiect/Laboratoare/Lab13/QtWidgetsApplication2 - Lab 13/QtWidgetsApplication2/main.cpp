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
#include "gui.h"
#include "guiModel.h"
#include <string>

using std::string;

/**
* Create GUI using absolute positioning
*/
void createAbsolute() {
    QWidget* main = new QWidget();
    QLabel* lbl = new QLabel("Name:", main);
    lbl->setGeometry(10, 10, 40, 20);
    QLineEdit* txt = new QLineEdit(main);
    txt->setGeometry(60, 10, 100, 20);
    main->show();
    main->setWindowTitle("Absolute");
}
/**
* Create the same GUI using form layout
*/
void createWithLayout() {
    QWidget* main = new QWidget();
    QFormLayout* fL = new QFormLayout(main);
    QLabel* lbl = new QLabel("Name:", main);
    QLineEdit* txt = new QLineEdit(main);
    fL->addRow(lbl, txt);
    main->show();
    main->setWindowTitle("Layout");
    //fix the height to the "ideal" height
    main->setFixedHeight(main->sizeHint().height());
}


void test_all()
{
    test_domain();
    test_validare();
    test_repo();
    test_wishlist();
    test_da_mi_numar_random();
    test_service();
}

int main(int argc, char *argv[])
{
    test_all();
	
    QApplication a(argc, argv);    

    //UnNouRepository repo{0.5};
    FileRepository repo("numeFis.txt");
	//repo.load_from_file();
    //Repository repo;
    Validator val;
    Service serv{ repo, val };

    //MyGUI wnd{serv};
    MyGUIModel wnd{ serv };

    wnd.show();

    
	

    /*QLabel *lb = new QLabel{ "           Salut " };
    //lb.show();

    QPushButton *btn = new QPushButton("&Apasa-ma!");
    //btn.show();
	
    QWidget w{};

    QVBoxLayout* q = new QVBoxLayout{};
    QHBoxLayout* x = new QHBoxLayout{};

    w.setLayout(q);

    x->addWidget(lb);
    x->addWidget(new QLineEdit);
    x->addWidget(new QPushButton{"Hei"});
    q->addLayout(x);
    q->addWidget(btn);
    q->addWidget(new QLineEdit);
    q->addWidget(new QPushButton{"Apasa mai sus!"});

    auto* form = new QFormLayout;

    form->addRow("Nume:", new QLineEdit);
    form->addRow("Prenume:", new QLineEdit);
    form->addWidget(new QPushButton("Asta ce mai face?"));

    q->addLayout(form);
	
    w.show();

    QWidget* wnd = new QWidget;
    QHBoxLayout* hLay = new QHBoxLayout();
    QPushButton* btn1 = new QPushButton("Bt &1");
    QPushButton* btn2 = new QPushButton("Bt &2");
    QPushButton* btn3 = new QPushButton("Bt &3");
    hLay->addWidget(btn1);
    hLay->addWidget(btn2);
    hLay->addWidget(btn3);
    wnd->setLayout(hLay);
    wnd->show();

    QWidget* wnd2 = new QWidget;
    QVBoxLayout* vLay = new QVBoxLayout();
    QPushButton* btnn1 = new QPushButton("B&1");
    QPushButton* btnn2 = new QPushButton("B&2");
    QPushButton* btnn3 = new QPushButton("B&3");
    vLay->addWidget(btnn1);
    vLay->addWidget(btnn2);
    vLay->addStretch();
    vLay->addWidget(btnn3);
    wnd2->setLayout(vLay);
    wnd2->show();

    QWidget* wnd3 = new QWidget;
    QVBoxLayout* vL = new QVBoxLayout;
    wnd3->setLayout(vL);
    //create a detail widget
    QWidget* details = new QWidget;
    QFormLayout* fL = new QFormLayout;
    details->setLayout(fL);
    QLabel* lblName = new QLabel("Name");
    QLineEdit* txtName = new QLineEdit;
    fL->addRow(lblName, txtName);
    QLabel* lblAge = new QLabel("Age");
    QLineEdit* txtAge = new QLineEdit;
    fL->addRow(lblAge, txtAge);
    //add detail to window
    vL->addWidget(details);
    QPushButton* store = new QPushButton("&Store");
    vL->addWidget(store);
    //show window
    wnd3->show();

    createAbsolute();

    */
	
    /*
	
    QLabel lb{ "           Salut " };
    lb.show();
	
    QPushButton btn("&Apasa-ma!");
    btn.show();

    QListWidget* list = new QListWidget;
    new QListWidgetItem("Item 1", list);
    new QListWidgetItem("Item 2", list);
    QListWidgetItem* item3 = new QListWidgetItem("Item 3");
    list->insertItem(0, item3);
    list->show();

    */
	
    return a.exec();
}
