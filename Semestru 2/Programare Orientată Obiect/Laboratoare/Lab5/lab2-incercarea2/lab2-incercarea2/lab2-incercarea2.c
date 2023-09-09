
#define _CRTDBG_MAP_ALLOC
#include <crtdbg.h>
#include "teste.h"
#include "domain.h"
#include "utils.h"
#include "controller.h"
#include "ui.h"
#include <stdlib.h> 

int main()
{
    toate_testele();
    
    //meniu();
    _CrtDumpMemoryLeaks();

	/*
	 * Vezi
	 * Participant* get_participant_adresa_cu_pozitie(Lista* l, int poz) {
	return *l->lista_participanti[poz];
}
	 *  distruge_lista_participanti(&ls);
	 */

	return 0;
}
