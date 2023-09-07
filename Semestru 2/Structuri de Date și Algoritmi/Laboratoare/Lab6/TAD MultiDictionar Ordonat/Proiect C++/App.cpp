#include <iostream>
#include <vector>

using namespace std;
#include "../Teste/TestExtins.h"
#include "../Teste/TestScurt.h"

void test_vector()
{
	vector<int>* v;

	v = new vector<int>[10];

	v[1].push_back(13);
	v[5].push_back(24);
	v[3].push_back(30);
	v[2].push_back(3);
	v[7].push_back(2);
	v[7].push_back(30);

	for (int i=0; i<10; i++)
	{
		for (auto a : v[i])
		{
			cout << a << " ";
		}
		if (v[i].empty())
		{
			cout << "Gol";
		}
		cout << endl;
	}

	v[8] = v[7];

	cout << endl;

	for (int i = 0; i < 10; i++)
	{
		for (auto a : v[i])
		{
			cout << a << " ";
		}
		if (v[i].empty())
		{
			cout << "Gol";
		}
		cout << endl;
	}
	
	delete[] v;
}

void test()
{
	testAll();
	testAllExtins();

	test_cheie_maxima();

	//test_vector();
	cout << endl << endl;
}

int main(){

	test();
	
    std::cout<<"Finished Tests!"<<std::endl;
}
