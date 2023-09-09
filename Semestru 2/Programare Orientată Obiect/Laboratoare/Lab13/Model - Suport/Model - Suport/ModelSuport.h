#pragma once

#include <QtWidgets/QMainWindow>
#include "ui_ModelSuport.h"

class ModelSuport : public QMainWindow
{
    Q_OBJECT

public:
    ModelSuport(QWidget *parent = Q_NULLPTR);

private:
    Ui::ModelSuportClass ui;
};
