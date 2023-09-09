#include <qapplication.h>
#include "../../domain.h"
#include "../../gui.h"
#include "../../repository.h"
#include "../../service.h"
#include "../../observer.h"

void test_all()
{
	test_domain();
	test_repo();
	test_service();
}

int main(int argc, char** argv)
{
	//test_all();
	QApplication a(argc, argv);
	Repository r{ "produse.txt" };
	Controller c{ r };
	MainGUI g{ c, r };
	g.show();
	a.exec();
}