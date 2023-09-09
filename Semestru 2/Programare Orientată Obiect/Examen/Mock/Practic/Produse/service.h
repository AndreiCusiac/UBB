#pragma once
#include "repository.h"
#include "domain.h"
#include <vector>
#include <algorithm>
#include <string>
#include <unordered_map>

using std::vector;
using std::sort;
using std::string;
using std::unordered_map;

class ValidationError
{
private:
	string msg;
public:
	ValidationError(const string& m) : msg{ m } {}
	const string& get_message() { return msg; }
};

class Controller
{
private:
	Repository& repo;
public:
	Controller(Repository& r) : repo{ r } {}
	const vector<Produs> sort_by_price();
	void add(const int& id, const string& nume, const string& tip, const double& pret);
	const unordered_map<string, int>& get_tip_map() { return repo.get_tip_map(); }
};

void test_service();