#include "service.h"

void Service::adauga(const string& i, const string& n, const string& p)
{
	Product x{ i, n, p };

	val.valideaza(x);

	rep.store(x);
}

void Service::sterge(const string& i, const string& n, const string& p)
{
	rep.sterge(i, n, p);

}

bool Service::cauta(const string& i, const string& n, const string& p)
{
	return rep.exista(i, n, p);
}



