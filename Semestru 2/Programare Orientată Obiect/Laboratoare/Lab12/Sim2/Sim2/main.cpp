#include <QtWidgets/QApplication>
#include <QLabel>

#include "domain.h"
#include "gui.h"
#include "repository.h"
#include "service.h"
//#include "gui.h"

void test_all()
{
    test_domain();
    test_repo();
    test_service();
}

int main(int argc, char *argv[])
{
    test_all();

    Repository r("te1.txt");

    Service s(r);

	
	
    QApplication a(argc, argv);
    QLabel w("Hei");
    w.show();

    MyGUI x;

   // x.show();
	
    return a.exec();
}
