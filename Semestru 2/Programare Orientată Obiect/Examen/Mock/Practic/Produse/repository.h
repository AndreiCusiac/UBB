#pragma once
#include <vector>
#include <string>
#include <unordered_map>
#include "domain.h"
#include "observer.h"

using std::vector;
using std::string;
using std::unordered_map;

class RepoError
{
private:
	string msg;
public:
	RepoError(const string& m) : msg{ m } {}
	const string& get_message() { return msg; }
};

class Repository : public Observable
{
private:
	vector<Produs> all;
	unordered_map<string, int> tip_map;
	string file_name;
	void load_from_file();
	void store_into_file();

public:
	Repository(const string& file) : file_name{ file } { load_from_file(); }
	const vector<Produs>& get_all() const { return all; };
	void store(const Produs& p);
	const unordered_map<string, int>& get_tip_map() { return tip_map; }
};

void test_repo();