#include <QtWidgets/QApplication>
#include "gui.h"
#include "domain.h"
#include "validator.h"
#include "repository.h"

void test_all()
{
    test_domain();
    test_validare();
    test_repo();
}

int main(int argc, char *argv[])
{
    test_all();
	
    QApplication a(argc, argv);

    MyGUI* w = new MyGUI;
	
    w->show();
	
    return a.exec();
}
