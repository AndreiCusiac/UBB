#pragma once
#include "domain.h"
#include "repository.h"
#include "validator.h"

class Service
{
private:

	Repo& rep;
	Validator& val;

public:

	Service() = default;
	
	Service(Repo& r, Validator& v) : rep{r}, val{v}
	{
		
	}

	Service(const Service& ot) = delete;

	void adauga(const string& i, const string& n, const string& p);

	void sterge(const string& i, const string& n, const string& p);

	bool cauta(const string& i, const string& n, const string& p);
};