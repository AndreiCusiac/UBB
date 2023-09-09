/********************************************************************************
** Form generated from reading UI file 'ModelSuport.ui'
**
** Created by: Qt User Interface Compiler version 6.1.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_MODELSUPORT_H
#define UI_MODELSUPORT_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_ModelSuportClass
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *ModelSuportClass)
    {
        if (ModelSuportClass->objectName().isEmpty())
            ModelSuportClass->setObjectName(QString::fromUtf8("ModelSuportClass"));
        ModelSuportClass->resize(600, 400);
        menuBar = new QMenuBar(ModelSuportClass);
        menuBar->setObjectName(QString::fromUtf8("menuBar"));
        ModelSuportClass->setMenuBar(menuBar);
        mainToolBar = new QToolBar(ModelSuportClass);
        mainToolBar->setObjectName(QString::fromUtf8("mainToolBar"));
        ModelSuportClass->addToolBar(mainToolBar);
        centralWidget = new QWidget(ModelSuportClass);
        centralWidget->setObjectName(QString::fromUtf8("centralWidget"));
        ModelSuportClass->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(ModelSuportClass);
        statusBar->setObjectName(QString::fromUtf8("statusBar"));
        ModelSuportClass->setStatusBar(statusBar);

        retranslateUi(ModelSuportClass);

        QMetaObject::connectSlotsByName(ModelSuportClass);
    } // setupUi

    void retranslateUi(QMainWindow *ModelSuportClass)
    {
        ModelSuportClass->setWindowTitle(QCoreApplication::translate("ModelSuportClass", "ModelSuport", nullptr));
    } // retranslateUi

};

namespace Ui {
    class ModelSuportClass: public Ui_ModelSuportClass {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_MODELSUPORT_H
