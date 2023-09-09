#pragma once
#include <iostream>
#include <string>
#include <vector>
#include <assert.h>

using namespace std;

class Doctor
{
private:

	string cnp;
	string nume;
	string prenume;
	string sectie;
	string concediu;

public:

	Doctor() = default;

	Doctor(const string& c, const string& n, const string& p, const string& s, const string& con) : cnp{ c }, nume{ n }, prenume{ p }, sectie{ s }, concediu{ con }
	{

	}

	Doctor(const Doctor& d) : cnp{ d.cnp }, nume{ d.nume }, prenume{ d.prenume }, sectie{ d.sectie }, concediu{ d.concediu }
	{

	}

	string get_cnp() const;

	string get_nume() const;

	string get_prenume() const;

	string get_sectie() const;

	string get_concediu() const;

	void set_cnp(const string& c);

	void set_nume(const string& n);

	void set_prenume(const string& p);

	void set_sectie(const string& s);

	void set_concediu(const string& con);
};

void test_domain();