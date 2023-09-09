#pragma once
#include <vector>
#include "domain.h"
#include <fstream>
#include <string.h>

using namespace std;

class Repository
{
private:

	vector<Doctor> all;
	string name;

public:

	Repository() = default;
	
	Repository(const string& n): name{n}
	{
		
	}

	void store(const Doctor& d);
	
	void load_from_file();

	vector<Doctor> get_all();

	int get_size();
	
};

void test_repo();