#pragma once
#include <QWidget>
#include <QAbstractListModel>
#include <QAbstractItemView>
#include <QAbstractItemModel>
#include "service.h"

class MyListModel : public QAbstractListModel
{
private:

	vector<Carte> all;

public:

	MyListModel(const vector<Carte>& v) : all{v}
	{
		
	}

	int rowCount(const QModelIndex& parent) const override
	{
		return all.size();
	}

	int columnCount(const QModelIndex& parent) const override
	{
		return 4;
	}

	QVariant data(const QModelIndex& index, int role) const override
	{
		if (role == Qt::DisplayRole)
		{
			if (index.column() == 0)
			{
				return QString("%1").arg(all[index.row()].get_titlu().c_str());
			}
			/*else if (index.column() == 1)
			{
				return QString("%1").arg(all[index.row()].get_autor().c_str());
			}
			else if (index.column() == 2)
			{
				return QString("%1").arg(all[index.row()].get_gen().c_str());
			}
			else if (index.column() == 3)
			{
				return QString("%1").arg(std::to_string(all[index.row()].get_an()).c_str());
			}*/
		}

		if (role == Qt::UserRole)
		{
			string a;

			a += all[index.row()].get_autor();
			a += "_";
			a += all[index.row()].get_gen();
			a += "_";
			a += std::to_string(all[index.row()].get_an());

			return QString::fromStdString(a);
		}

		if (role == Qt::ImCurrentSelection)
		{
			//i->setBackground(QBrush{ Qt::darkGreen, Qt::SolidPattern });

			return QBrush{ Qt::darkGreen, Qt::SolidPattern };
		}

		return QVariant{};
	}

	void setCarte(const vector<Carte>& v)
	{
		all = v;

		auto topLeft = createIndex(0, 0);
		auto bottomRight = createIndex(all.size(), 4);

		emit dataChanged(topLeft, bottomRight);
	}
	
};
