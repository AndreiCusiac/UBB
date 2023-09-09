#pragma once
#include <iostream>
#include <string>
#include <cassert>
#include <assert.h>

using namespace std;

class Product
{
private:

	string ID;
	string nume;
	string pret;

public:

	Product() = default;

	Product(const string& i, const string& n, const string& p) : ID{ i }, nume{n}, pret{p}
	{
		
	}

	Product(const Product& ot): ID{ot.ID}, nume{ot.nume}, pret{ot.pret}
	{
		
	}

	void set_ID(const string& i);

	void set_nume(const string& n);

	void set_pret(const string& p);

	string get_ID() const;

	string get_nume() const;

	string get_pret() const;
	
};

void test_domain();