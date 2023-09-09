#pragma once
#include <string>

using namespace std;

class Om {

private:

    string nume;

public:

    Om(const string& n) : nume{ n }
    {

    }

	string get_nume() const
    {
        return nume;
    }

};

class Stud : public Om {

private:

    int nr_mat;

public:

    Stud(const string& n, const int& nr) : Om(n), nr_mat{nr}
    {
	    
    }

    string get_nume() const
    {
        string s{ "Sunt student: " };

        s += Om::get_nume();
    	
        return s;
    }
	
    int get_nr_mat() const
    {
        return nr_mat;
    }
};

class Prof : public Om {

private:

    string funct;

public:

    Prof(const string& n, const string& f) : Om(n), funct{ f }
    {

    }

    string get_nume() const
    {
        string s{ "Profesorul " };

        s += Om::get_nume();

        return s;
    }

    string get_functie() const
    {
        return funct;
    }
};

void teste()
{
    Om p{ "Ion" };

    Stud x{ "Ala", 10 };

    Om* aux = &x;

    cout << aux->get_nume() << endl;
	
    cout << p.get_nume() << endl;

    cout << x.get_nume() << " - " << x.get_nr_mat() << endl;

    p = x;

    cout << p.get_nume() << endl;

    Prof t{ "Cutarescu", "boss" };

    cout << t.get_nume() << endl;

    
}