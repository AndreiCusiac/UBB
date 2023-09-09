    // Lab6-7.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#define _CRTDBG_MAP_ALLOC
#include <cassert>
#include <stdlib.h>
#include <crtdbg.h>

#include <iostream>
#include "domain.h"
#include "repository.h"
#include "service.h"
#include "ui.h"
#include "Template.h"
#include "Testare.h"
#include "main.h"

void test_template()
{
    Carte c1{ "Ana", "Tudor1", "rom", 2019 };
    Carte c2{ "Ana", "Tudor2", "rom", 2019 };
    Carte c3{ "3Ana", "Tudor3", "ro3m", 2020 };

    MyVectorT<Carte> v;

    v.push_back(c1);
    v.push_back(c2);
    v.push_back(c3);

	assert(v.size() == 3);

    auto it = v.begin();

    for (auto& p : v) {
        std::cout << p.get_autor() << std::endl;
    }
}

void test_all()
{
    test_domain();
    test_validare();
    test_repo();
    test_service();

    test_template();

    test_implementare();

	

    printf("Testele au rulat cu succes!\n");
}

void consola()
{
    Repository repo;
    Validator val;
    Service serv{ repo , val};

    UI ui{ serv };

    ui.start_ui();
}

void calatorie()
{
    Geanta<string> ganta{ "Ion" };

    ganta = ganta + string{ "Haine" };

    ganta + string{ "pahar" };

	for (auto o : ganta)
	{
        cout << o << endl;
	}
}

int main()
{
    //test_all();

    //consola();

    //teste();

    calatorie();
	
    _CrtDumpMemoryLeaks();

    return 0;
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
