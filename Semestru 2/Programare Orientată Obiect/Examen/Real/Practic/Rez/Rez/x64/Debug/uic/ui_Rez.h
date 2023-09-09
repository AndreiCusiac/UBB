/********************************************************************************
** Form generated from reading UI file 'Rez.ui'
**
** Created by: Qt User Interface Compiler version 6.1.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_REZ_H
#define UI_REZ_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_RezClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *RezClass)
    {
        if (RezClass->objectName().isEmpty())
            RezClass->setObjectName(QString::fromUtf8("RezClass"));
        RezClass->resize(600, 400);
        menuBar = new QMenuBar(RezClass);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        RezClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(RezClass);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        RezClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(RezClass);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        RezClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(RezClass);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        RezClass->setStatusBar(statusBar);

        retranslateUi(RezClass);

        QMetaObject::connectSlotsByName(RezClass);
    } // setupUi

    void retranslateUi(QMainWindow *RezClass)
    {
        RezClass->setWindowTitle(QCoreApplication::translate("RezClass", "Rez", nullptr));
    } // retranslateUi

};

namespace Ui {
    class RezClass: public Ui_RezClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_REZ_H
