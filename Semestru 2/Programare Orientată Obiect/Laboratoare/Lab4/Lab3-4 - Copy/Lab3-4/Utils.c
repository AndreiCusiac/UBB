#include "Domain.h"
#include "Utils.h"
#include "Repo.h"
//#include "Service.h"
#include "UI.h"
#include "UI.h"

#include <assert.h>
#include <stdio.h>

unsigned int numar_de_cifre(int n)
{
	unsigned int i = 0;
	while (n != 0)
	{
		n /= 10;
		i = i + 1;
	}
	return i;
}

void test_numar_de_cifre()
{
	assert(numar_de_cifre(10) == 2);
	assert(numar_de_cifre(-10) == 2);
	assert(numar_de_cifre(0) == 0);
	assert(numar_de_cifre(901) == 3);

}

void test_utils()
{
	void test_numar_de_cifre();
}