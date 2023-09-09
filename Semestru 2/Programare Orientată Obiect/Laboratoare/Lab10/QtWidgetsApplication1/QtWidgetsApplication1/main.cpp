#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qpushbutton.h>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    QLabel lbl("Hello world!");
    lbl.show();
	
    return a.exec();
}
