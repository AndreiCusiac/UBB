#include <QtWidgets/QApplication>
#include "domain.h"
#include "repository.h"
#include "service.h"
//#include "gui.h"

void test_all()
{
    test_domain();
}

int main(int argc, char *argv[])
{
    test_all();
	
    QApplication a(argc, argv);

    //MyGUI w;

    //w.show();

    return a.exec();
}
