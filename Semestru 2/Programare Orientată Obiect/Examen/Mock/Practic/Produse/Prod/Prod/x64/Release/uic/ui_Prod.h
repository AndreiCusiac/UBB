/********************************************************************************
** Form generated from reading UI file 'Prod.ui'
**
** Created by: Qt User Interface Compiler version 6.1.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_PROD_H
#define UI_PROD_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ProdClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *ProdClass)
    {
        if (ProdClass->objectName().isEmpty())
            ProdClass->setObjectName(QString::fromUtf8("ProdClass"));
        ProdClass->resize(600, 400);
        menuBar = new QMenuBar(ProdClass);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        ProdClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(ProdClass);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        ProdClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(ProdClass);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        ProdClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(ProdClass);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        ProdClass->setStatusBar(statusBar);

        retranslateUi(ProdClass);

        QMetaObject::connectSlotsByName(ProdClass);
    } // setupUi

    void retranslateUi(QMainWindow *ProdClass)
    {
        ProdClass->setWindowTitle(QCoreApplication::translate("ProdClass", "Prod", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ProdClass: public Ui_ProdClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_PROD_H
