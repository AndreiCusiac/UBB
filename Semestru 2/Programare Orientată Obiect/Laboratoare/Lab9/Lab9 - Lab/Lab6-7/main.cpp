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
#include "validator.h"

    void test_all()
{
    test_domain();
    test_validare();
    test_repo();
    test_wishlist();
    test_da_mi_numar_random();
    test_service();

    printf("Testele au rulat cu succes!\n");
}

void consola()
{
    //UnNouRepository repo{0.5};
    FileRepository repo("numeFis.txt");
    // Repository repo;
    Validator val;
    Service serv{ repo, val};

    UI ui{ serv };

    ui.start_ui();
}

int main()
{
    test_all();

    consola();

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
