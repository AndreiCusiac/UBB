#include "Testare.h"

#include <iostream>

using namespace std;

template <typename MyVector>
MyVector testCopyIterate(MyVector v) {
	int totalPrice = 0;
	for (auto el : v) {
		totalPrice += el.get_an();
	}
	Carte p{ "total","tt","tt",totalPrice };
	v.push_back(p);
	return v;
}

template <typename MyVector>
void addPets(MyVector& v, int cate) {
	for (int i = 0; i < cate; i++) {
		Carte p{ std::to_string(i) + "_type",std::to_string(i) + "_spec",std::to_string(i) + "_spec", i + 10 };
		v.push_back(p);
	}
}

/*
Testare constructori / assignment
E template pentru a refolosi la diferite variante de VectorDinamic din proiect
*/
template <typename MyVector>
void testCreateCopyAssign() {
	MyVector v;//default constructor
	addPets(v, 100);
	assert(v.size() == 100);

	assert(v.get(50).get_titlu() == "50_type");

	MyVector v2{ v };//constructor de copiere
	assert(v2.size() == 100);
	assert(v2.get(50).get_titlu() == "50_type");

	MyVector v3;//default constructor
	v3 = v;//operator=   assignment
	assert(v3.size() == 100);
	assert(v3.get(50).get_titlu() == "50_type");

	auto v4 = testCopyIterate(v3);
	assert(v4.size() == 101);
	assert(v4.get(50).get_titlu() == "50_type");
}

/*
  Test pentru move constructor si move assgnment
*/
template <typename MyVector>
void testMoveConstrAssgnment() {
	std::vector<MyVector> v;
	//adaugam un vector care este o variabila temporara
	//se v-a apela move constructor
	v.push_back(MyVector{});

	//inseram, la fel se apeleaza move costructor de la vectorul nostru
	v.insert(v.begin(), MyVector{});

	assert(v.size() == 2);

	MyVector v2;
	addPets(v2, 50);

	v2 = MyVector{};//move assignment

	assert(v2.size() == 0);

}

template <typename MyVector>
void testAll() {
	testCreateCopyAssign<MyVector>();
	testMoveConstrAssgnment<MyVector>();
}

void test_implementare()
{
	testAll<MyVectorT<Carte>>();

	std::cout << "\nTestul de implementare a fost rulat cu succes!\n";
}
