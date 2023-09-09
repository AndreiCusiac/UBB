#include "gui.h"
#include <qboxlayout.h>
#include <qformlayout.h>
#include <qstring.h>
#include <qheaderview.h>
#include <qmessagebox.h>

MainGUI::MainGUI(Controller& c, Repository& r) : ctrl{ c }, repo{ r }
{
	init_gui();
	init_connect();
	load_data(ctrl.sort_by_price());
	create_info_widgets();
}

void MainGUI::init_gui()
{
	auto* main_layout = new QHBoxLayout;
	this->setLayout(main_layout);

	main_layout->addWidget(table);
	table->setSelectionBehavior(QAbstractItemView::SelectRows);
	table->setSelectionMode(QAbstractItemView::SingleSelection);
	table->horizontalHeader()->setStretchLastSection(true);

	auto* vertical_layout = new QVBoxLayout;
	main_layout->addLayout(vertical_layout);

	auto* edits_layout = new QFormLayout;
	vertical_layout->addLayout(edits_layout);

	edits_layout->addRow("ID:", id_le);
	edits_layout->addRow("nume:", nume_le);
	edits_layout->addRow("tip:", tip_le);
	edits_layout->addRow("pret:", pret_le);

	vertical_layout->addWidget(add_btn);

	vertical_layout->addWidget(slider);
	slider->setMinimum(1);
	slider->setMaximum(100);
}

void MainGUI::init_connect()
{
	table->setModel(model);
	QObject::connect(add_btn, &QPushButton::clicked, [this]() {
		auto id = id_le->text().toInt();
		auto nume = nume_le->text().toStdString();
		auto tip = tip_le->text().toStdString();
		auto pret = pret_le->text().toDouble();
		try
		{
			ctrl.add(id, nume, tip, pret);
			load_data(ctrl.sort_by_price());
		}
		catch (ValidationError& er)
		{
			QMessageBox::critical(nullptr, "", QString::fromStdString(er.get_message()));
		}
		catch (RepoError& er)
		{
			QMessageBox::critical(nullptr, "", QString::fromStdString(er.get_message()));
		}
		});
	QObject::connect(slider, &QSlider::sliderReleased, [this]() {
		load_data(ctrl.sort_by_price());
		});
}

void MainGUI::load_data(const vector<Produs>& produse)
{
	model->refresh_data(produse);
}

void MainGUI::create_info_widgets()
{
	auto& tip_map = ctrl.get_tip_map();
	for (const auto& [k, v] : tip_map)
	{
		auto* c = new InfoWidget(ctrl, repo, k, v);
		c->show();
	}
}

int CustomTableModel::rowCount(const QModelIndex&) const
{
	return static_cast<int>(produse.size());
}

int CustomTableModel::columnCount(const QModelIndex&) const
{
	return 5;
}

QVariant CustomTableModel::data(const QModelIndex& index, int role) const
{
	if (role == Qt::DisplayRole)
	{
		auto row = index.row();
		auto column = index.column();
		switch (column)
		{
		case 0:
			return QString::number(produse.at(row).get_id());
			break;
		case 1:
			return QString::fromStdString(produse.at(row).get_nume());
			break;
		case 2:
			return QString::fromStdString(produse.at(row).get_tip());
			break;
		case 3:
			return QString::number(produse.at(row).get_pret());
			break;
		case 4:
			return QString::number(produse.at(row).get_nr_vocale());
			break;
		default:
			break;
		}
	}
	if (role == Qt::BackgroundRole)
	{
		auto& price = produse.at(index.row()).get_pret();
		if (price < slider.value())
		{
			return QBrush{ Qt::red };
		}
		return QBrush{ Qt::transparent };
	}
	return QVariant();
}

QVariant CustomTableModel::headerData(int section, Qt::Orientation orientation, int role) const
{
	if (role == Qt::DisplayRole)
	{
		if (orientation == Qt::Horizontal)
		{
			switch (section)
			{
			case 0:
				return QString{ "id" };
				break;
			case 1:
				return QString{ "nume" };
				break;
			case 2:
				return QString{ "tip" };
				break;
			case 3:
				return QString{ "pret" };
				break;
			case 4:
				return QString{ "numar vocale" };
				break;
			default:
				break;
			}
		}
	}

	return QVariant();
}

void CustomTableModel::refresh_data(const vector<Produs>& newproduse)
{
	emit layoutAboutToBeChanged();
	this->produse = newproduse;
	emit layoutChanged();
}

InfoWidget::InfoWidget(Controller& c, Repository& r, const string& title, const int& val) : ctrl{ c }, repo{ r }
{
	this->setWindowTitle(QString::fromStdString(title));
	auto* ly = new QHBoxLayout;
	setLayout(ly);
	info->setText(QString::number(val));
	ly->addWidget(info);
	repo.add_observer(this);
}

void InfoWidget::update()
{
	auto& tip_map = ctrl.get_tip_map();
	for (const auto& [k, v] : tip_map)
	{
		if (this->windowTitle().toStdString() == k)
		{
			info->setText(QString::number(v));
			break;
		}
	}
}

InfoWidget::~InfoWidget()
{
	repo.remove_observer(this);
}