#include "mainwindow.h"

#include <QApplication>
#include <QtWidgets/QApplication>
#include <QtWidgets/QLabel>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    QLabel l("Hello!");
    l.show();
    return a.exec();
}
