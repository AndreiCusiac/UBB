#include "repository.h"

void Repository::store(const Doctor& d)
{
	all.push_back(d);
}


void Repository::load_from_file()
{
	ifstream f(name);
	string str, nume, prenume, cnp, sectie, con;

	int i{ -1 };

	while (getline(f, str))
	{
		i++;

		if (i%5==0)
		{
			cnp = str;
		}
		else if (i % 5 ==1)
		{
			nume = str;
		}
		else if (i % 5 ==2)
		{
			prenume = str;
		}
		else if (i % 5 ==3)
		{
			sectie = str;
		}
		else
		{
			con = str;

			Doctor d{ cnp, nume, prenume, sectie, con };

			store(d);
		}
		
	}
}


vector<Doctor> Repository::get_all()
{
	return all;
}

int Repository::get_size()
{
	return all.size();
}


void test_repo()
{
	Repository r("te.txt");

	r.load_from_file();

	assert(r.get_size() == 2);

	Doctor d = r.get_all()[0];

	assert(d.get_cnp() == "123");

	Doctor d1 = r.get_all()[1];

	assert(d1.get_cnp() == "124");
}