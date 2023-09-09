#pragma once
#include "repository.h"

class Service
{
private:

	Repository& r;

public:

	Service(Repository& repo) : r{repo}
	{
		
	}

	vector<Doctor> filtreaza_nume(const string& n);

	vector<Doctor> filtreaza_sectie(const string& s);

	vector<Doctor> get_all();

	int get_size();
	
};

void test_service();