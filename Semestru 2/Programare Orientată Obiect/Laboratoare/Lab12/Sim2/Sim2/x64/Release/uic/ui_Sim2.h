/********************************************************************************
** Form generated from reading UI file 'Sim2.ui'
**
** Created by: Qt User Interface Compiler version 6.1.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_SIM2_H
#define UI_SIM2_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_Sim2Class
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *Sim2Class)
    {
        if (Sim2Class->objectName().isEmpty())
            Sim2Class->setObjectName(QString::fromUtf8("Sim2Class"));
        Sim2Class->resize(600, 400);
        menuBar = new QMenuBar(Sim2Class);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        Sim2Class->setMenuBar(menuBar);
        mainToolBar = new QToolBar(Sim2Class);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        Sim2Class->addToolBar(mainToolBar);
        centralWidget = new QWidget(Sim2Class);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        Sim2Class->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(Sim2Class);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        Sim2Class->setStatusBar(statusBar);

        retranslateUi(Sim2Class);

        QMetaObject::connectSlotsByName(Sim2Class);
    } // setupUi

    void retranslateUi(QMainWindow *Sim2Class)
    {
        Sim2Class->setWindowTitle(QCoreApplication::translate("Sim2Class", "Sim2", nullptr));
    } // retranslateUi

};

namespace Ui {
    class Sim2Class: public Ui_Sim2Class {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_SIM2_H
