#include "domain.h"

void Task::set_id(const int& i)
{
	id = i;
}

void Task::set_descriere(const string& d)
{
	descriere = d;
}

void Task::set_programatori(const vector<string>& v)
{
	programatori = v;
}

void Task::set_stare(const string& d)
{
	stare = d;
}

int Task::get_id() const
{
	return id;
}

string Task::get_descriere() const
{
	return descriere;
}

vector<string> Task::get_programatori() const
{
	return programatori;
}

string Task::get_stare() const
{
	return stare;
}


void test_domain()
{
	Task t{ 1, "ala", vector<string>{"Ion", "Geo"}, "open" };

	assert(t.get_id() == 1);

	assert(t.get_descriere() == "ala");

	assert(t.get_stare() == "open");

	assert(t.get_programatori()[0] == "Ion");
}


