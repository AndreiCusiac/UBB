#pragma once
#include "domain.h"
#include <assert.h>
#include <vector>

class RepoException
{
private:

	string erori;

public:

	RepoException(const string& e): erori{e}
	{
		
	}

	string get_erori() const
	{
		return erori;
	}
	
};

class Repo
{
private:

	vector<Product> all;

	string nume = "";

public:

	Repo() = default;
	
	Repo(const string& n) : nume{n}
	{
		
	}

	Repo(const Repo& ot) = delete;

	void store(const Product& p);

	void sterge(const string& id, const string& n, const string& p);

	bool exista(const string& id, const string& n, const string& p);

	vector<Product> get_all() const;

	int get_size() const;
	
};

void test_repo();