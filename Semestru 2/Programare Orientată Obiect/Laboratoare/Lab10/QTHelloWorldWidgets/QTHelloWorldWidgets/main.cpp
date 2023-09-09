#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qlineedit.h>
#include <QtWidgets/qlistwidget.h>
#include <QtWidgets/qtablewidget.h>
#include <QtWidgets/qpushbutton.h>

#include <QtWidgets/qcombobox.h>
#include <QtWidgets/qboxlayout.h>
#include <QtWidgets\qformlayout.h>
#include <QtWidgets\qgridlayout.h>

void horizontalVerticalLayout() {
	QWidget* wnd = new QWidget;
	QHBoxLayout* hLay = new QHBoxLayout();
	//QVBoxLayout *hLay = new QVBoxLayout();
	QPushButton* btn1 = new QPushButton("Btkjashdakjdakjskjsasd &1");
	QPushButton* btn2 = new QPushButton("Bt &2");
	QPushButton* btn3 = new QPushButton("Bt &3");
	hLay->addWidget(btn1);
	//hLay->addStretch();
	hLay->addWidget(btn2);
	//hLay->addStretch();
	hLay->addWidget(btn3);
	wnd->setLayout(hLay);
	wnd->show();
}

void formLayout() {
	QWidget* wnd3 = new QWidget;
	QVBoxLayout* vL = new QVBoxLayout;
	wnd3->setLayout(vL);
	//create a detail widget
	QWidget* details = new QWidget;
	QFormLayout* fL = new QFormLayout;
	details->setLayout(fL);
	QLabel* lblName = new QLabel("Nameasdasd");
	QLineEdit* txtName = new QLineEdit;
	fL->addRow(lblName, txtName);
	QLabel* lblAge = new QLabel("Age");
	QLineEdit* txtAge = new QLineEdit;
	fL->addRow(lblAge, txtAge);
	//add detail to window
	vL->addWidget(details);
	QPushButton* store = new QPushButton("&Store");
	vL->addWidget(store);
	//vL->addStretch();
	//show window
	wnd3->show();
}

void gridLayout() {
	QWidget* wnd = new QWidget;
	QGridLayout* ly = new QGridLayout;
	wnd->setLayout(ly);
	QLineEdit* lineEdt = new QLineEdit;
	ly->addWidget(lineEdt, 0, 0, 1, 5);

	QPushButton* clearMemoryButton = new QPushButton{ "MC" };
	ly->addWidget(clearMemoryButton, 2, 0);
	QPushButton* readMemoryButton = new QPushButton{ "MR" };
	ly->addWidget(readMemoryButton, 3, 0);
	QPushButton* setMemoryButton = new QPushButton{ "MS" };
	ly->addWidget(setMemoryButton, 4, 0);
	QPushButton* addToMemoryButton = new QPushButton{ "M+" };
	ly->addWidget(addToMemoryButton, 5, 0);

	for (int i = 1; i < 10; ++i) {
		int row = ((9 - i) / 3) + 2;
		int column = ((i - 1) % 3) + 1;
		QPushButton* btn = new QPushButton{ QString::number(i) };
		ly->addWidget(btn, row, column);
	}
	ly->addWidget(new QPushButton{ "0" }, 5, 1);
	ly->addWidget(new QPushButton{ "." }, 5, 2);
	ly->addWidget(new QPushButton{ "=" }, 5, 3);

	wnd->setWindowTitle("Qt Calculator");
	wnd->show();
}

void guiComponents() {
	QLabel* label = new QLabel("hello world");
	label->show();

	QLineEdit* txt = new QLineEdit;
	txt->show();

	QPushButton* btn = new QPushButton{ "Buton &Fain" };
	btn->show();

	QListWidget* lst = new QListWidget;
	lst->addItem("Bla 1");
	lst->addItem("Bla 2");
	lst->addItem("Bla 3");
	lst->addItem("Bla 4");
	new QListWidgetItem{ "Bla5",lst };
	lst->show();

	QComboBox* cmb = new QComboBox;
	cmb->addItem("Item1");
	cmb->addItem("Item2");
	cmb->addItem("Item3");
	cmb->addItem("Item4");
	cmb->show();

	QTableWidget* tbl = new QTableWidget{ 10,4 };
	QTableWidgetItem* cellItem1 = new QTableWidgetItem("Linie1");
	cellItem1->setBackground(Qt::red);

	tbl->setItem(0, 0, cellItem1);
	tbl->setItem(0, 1, new QTableWidgetItem("Linie1 coloana2"));
	tbl->show();
}


int main(int argc, char* argv[])
{
	QApplication a(argc, argv);
	
	/*
	QWidget* wnd = new QWidget;
	QVBoxLayout* hLy = new QVBoxLayout;
	wnd->setLayout(hLy);
	hLy->addWidget(new QPushButton{ "Butonul meu1" });
	hLy->addWidget(new QPushButton{ "Butonul meu2" });
	QWidget* wdg2 = new QWidget;
	QHBoxLayout* hLy2 = new QHBoxLayout;
	wdg2->setLayout(hLy2);
	hLy2->addWidget(new QPushButton{ "Butonul meu11" });
	hLy2->addWidget(new QPushButton{ "Butonul meu21 sdfasda asdsada" });
	hLy2->addStretch();
	hLy2->addWidget(new QPushButton{ "Butonul meu23" });

	hLy->addWidget(wdg2);
	hLy->addWidget(new QPushButton{ "Butonul meu3" });
	hLy->addWidget(new QPushButton{ "Butonul meu4" });
	hLy->addStretch();
	wnd->show();
	*/
	//guiComponents();

	//horizontalVerticalLayout();

	//formLayout();

	//gridLayout();
	QWidget* btns = new QWidget;
	QVBoxLayout* btnsL = new QVBoxLayout;
	btns->setLayout(btnsL);
	QPushButton* store = new QPushButton("&Store");
	btnsL->addWidget(store);
	btnsL->addStretch();
	QPushButton* close = new QPushButton("&Close");
	btnsL->addWidget(close);
	btns->show();
	return a.exec();
}