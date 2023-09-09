#pragma once

#include <QtWidgets/QApplication>
#include <QtWidgets/qlabel.h>
#include <QtWidgets/qpushbutton.h>
#include <QtWidgets/qformlayout.h>
#include <QListWidget>
#include <QHBoxLayout>
#include <QVBoxLayout>
#include <QLineEdit>
#include <QtWidgets/qlistview.h>
#include <QTableWidget>
#include <qmessagebox.h>
#include "gui.h"
#include <string>
#include "service.h"
#include "repository.h"
#include <algorithm>
#include <QIcon>
#include <qpainter.h>

class ReadOnlyGUI : public QWidget, public Observer
{
private:

    Service& serv;

	void update() override
	{
        repaint();
	}

	void paintEvent(QPaintEvent* event) override
	{
        QPainter p{ this };

        int i = 0;

        int y = 0;
		
        for (const auto& carte : serv.get_wish())
        {
            QImage img;

            img.load("Book2.png");

            auto x = this->height()/3;

            auto dis = img.scaledToHeight(x, Qt::SmoothTransformation);
        	
            p.drawImage(i, y, dis);

            i += dis.width();

            i += 15;

        	if (i > this->width()-dis.width())
        	{
                i = 0;

                y += dis.height();

                y += 10;
        	}

        	/*if (y > this->height()-dis.height())
        	{
                y = 0;

        		
        	}*/
        }

        
	}

    ~ReadOnlyGUI()
    {
        serv.removeObserver(this);
    }

public:

    ReadOnlyGUI(Service& s) : serv{ s }
    {
        serv.addObserver(this);
    }
};