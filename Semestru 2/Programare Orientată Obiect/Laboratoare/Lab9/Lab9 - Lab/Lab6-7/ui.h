#pragma once
#include "service.h"

class UI
{
public:

	UI(Service& s): serv{s}
	{
		
	}

	void adaugaUI();

	void actualizeazaUI();

	void stergeUI();

	void cautaUI();

	void afiseazaUI();

	void filtreazaUI();

	void sorteazaUI();

	void cos_inchirieriUI();

	void adauga_automat();

	void undoUI();

	void exportUI();

	void start_ui();
	
private:

	Service& serv;
	
};