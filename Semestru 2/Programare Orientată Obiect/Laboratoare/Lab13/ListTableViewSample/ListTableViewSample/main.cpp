
#include <QtWidgets/QApplication>
#include <QtWidgets/qtablewidget.h>
#include <QtWidgets/qtableview.h>
#include <QtWidgets/qlistview.h>
#include <QtWidgets/qtreeview.h>
#include <QtWidgets/qboxlayout.h>
#include <qfilesystemmodel.h>
#include <qdebug.h>

int ROWS = 100000;
int COLS = 100;

void createLargeTable() {
	QWidget* gui = new QWidget;
	gui->setLayout(new QHBoxLayout);

	QTableWidget* tV = new QTableWidget(ROWS, COLS);
	qDebug() << "Start adding elements";
	//populate table
	for (int i = 0; i < ROWS; i++) {
		for (int j = 0; j < COLS; j++) {
			QTableWidgetItem* newItem = new QTableWidgetItem(
				QString("Row%1, Column%2").arg(i).arg(j));
			tV->setItem(i, j, newItem);
		}
	}
	qDebug() << "Finished adding elements";
	gui->layout()->addWidget(tV);
	gui->show();
}


class TestTableModel :public QAbstractTableModel {
public:
	int rowCount(const QModelIndex& parent = QModelIndex()) const override {
		return ROWS;
	}
	int columnCount(const QModelIndex& parent = QModelIndex()) const override {
		return COLS;
	}
	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
		if (role == Qt::DisplayRole) {
			qDebug() << "row:" << index.row() << " col:" << index.column();
			return QString("Row%1, Column%2").arg(index.row()).arg(index.column());
		}

		/*	if (role == Qt::BackgroundRole) {
				if (index.row() % 2 == 0) {
					return QBrush(Qt::red);
				}
				else {
					return QBrush(Qt::yellow);
				}
			}*/

		return QVariant{};
	}
};
void createTableModelV() {
	QWidget* gui = new QWidget;	
	gui->setLayout(new QHBoxLayout);
	QTableView* tV = new QTableView();	
	TestTableModel* model = new TestTableModel();
	tV->setModel(model);
	gui->layout()->addWidget(tV);
	gui->show();
}

void create2Views() {
	TestTableModel* model = new TestTableModel();
	
	QWidget* gui1 = new QWidget;	
	gui1->setLayout(new QHBoxLayout);
	QListView* tVT = new QListView();
	tVT->setUniformItemSizes(true);
	tVT->setModel(model);
	gui1->layout()->addWidget(tVT);
	gui1->show();

	QWidget* gui2 = new QWidget;
	gui2->setLayout(new QHBoxLayout);
	QTableView* tV = new QTableView();
	tV->setModel(model);
	gui2->layout()->addWidget(tV);
	gui2->show();

}

void createTree() {
	QWidget* gui = new QWidget;
	gui->setLayout(new QHBoxLayout);
	QTreeView* tV = new QTreeView();
	QFileSystemModel *model = new QFileSystemModel;
	model->setRootPath(QDir::currentPath());
	tV->setModel(model);
	gui->layout()->addWidget(tV);
	gui->show();
}

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
	//createLargeTable();
	createTableModelV();
	create2Views();
	createTree();
    return a.exec();
}
