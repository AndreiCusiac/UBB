#pragma once

class ActiuneUndo
{
public:

	virtual void doUndo() = 0;

	virtual ~ActiuneUndo() = default;

};

class UndoAdauga : public ActiuneUndo
{
	Carte carteAdaugata;
	AbstractRepo& rep;

public:

	UndoAdauga(AbstractRepo& r, const Carte& c) : rep{ r }, carteAdaugata{ c } {}

	void doUndo() override
	{
		rep.sterge(carteAdaugata.get_titlu(), carteAdaugata.get_autor(), carteAdaugata.get_gen(), carteAdaugata.get_an());
	}

};

class UndoSterge : public ActiuneUndo
{
	AbstractRepo& rep;
	Carte carteStearsa;

public:

	UndoSterge(AbstractRepo& r, const Carte& c) : rep{ r }, carteStearsa{ c } {}

	void doUndo() override
	{
		rep.store(carteStearsa);
	}

};

class UndoModifica : public ActiuneUndo
{
	AbstractRepo& rep;
	Carte carteInitiala;
	Carte carteModificata;

public:

	UndoModifica(AbstractRepo& r, const Carte& c_i, const Carte& c_f) : rep{ r }, carteInitiala{ c_i }, carteModificata{ c_f } {}

	void doUndo() override
	{
		rep.sterge(carteModificata.get_titlu(), carteModificata.get_autor(), carteModificata.get_gen(), carteModificata.get_an());
		rep.store(carteInitiala);
	}
	
};