#pragma once
#include <qwidget.h>
#include <qtableview.h>
#include <qabstractitemmodel.h>
#include <vector>
#include <qlineedit.h>
#include <qpushbutton.h>
#include <qslider.h>
#include <qlabel.h>
#include "service.h"
#include "domain.h"

using std::vector;

class CustomTableModel : public QAbstractTableModel
{
private:
	vector<Produs> produse;
	QSlider& slider;
public:
	CustomTableModel(QSlider& s) : slider{ s } {}
	int rowCount(const QModelIndex& parent = QModelIndex()) const override;
	int columnCount(const QModelIndex& parent = QModelIndex()) const override;
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override;
	QVariant headerData(int section, Qt::Orientation orientation, int role = Qt::DisplayRole) const override;
	void refresh_data(const vector<Produs>& newproduse);
};

class InfoWidget : public QWidget, public Observer
{
private:
	QLabel* info = new QLabel;
	Controller& ctrl;
	Repository& repo;
public:
	InfoWidget(Controller& c, Repository& r, const string& title, const int& val);
	void update() override;
	~InfoWidget();
};

class MainGUI : public QWidget
{
private:
	Controller& ctrl;
	Repository& repo;
	
	QTableView* table = new QTableView;
	QSlider* slider = new QSlider{ Qt::Orientation::Horizontal };
	CustomTableModel* model = new CustomTableModel{ *slider };
	QLineEdit* id_le = new QLineEdit;
	QLineEdit* nume_le = new QLineEdit;
	QLineEdit* tip_le = new QLineEdit;
	QLineEdit* pret_le = new QLineEdit;
	QPushButton* add_btn = new QPushButton{ "Adauga" };
	void init_gui();
	void init_connect();
	void load_data(const vector<Produs>& produse);
	void create_info_widgets();

public:
	MainGUI(Controller& c, Repository& r);
};