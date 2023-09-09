#include "domain.h"

void test_domain()
{
	Produs p{ 1, "n1", "t1", 12.5 };
	assert(p.get_id() == 1);
	assert(p.get_nume() == "n1");
	assert(p.get_tip() == "t1");
	assert(p.get_pret() == 12.5);
	Produs p2{ 2, "telefon", "t1", 1231 };
	assert(p2.get_nr_vocale() == 3);
}