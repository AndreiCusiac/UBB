#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_QTHelloWorldWidgets.h"

class QTHelloWorldWidgets : public QMainWindow
{
    Q_OBJECT

public:
    QTHelloWorldWidgets(QWidget *parent = Q_NULLPTR);

private:
    Ui::QTHelloWorldWidgetsClass ui;
};
