#pragma once
#include <vector>

using namespace std;

template <typename El>
class IteratorT;

template <typename El>
class MyVectorT
{
	
private:
	int load;
	int nrElem;

	El* v;

public:

	MyVectorT() : load{ 10 }, nrElem{ 0 }, v{ new El[load] }
	{

	}

	//Rule of 3

	// Constructor de copiere

	MyVectorT(const MyVectorT& ot)
	{
		v = new El[ot.load];

		for (int i=0; i<ot.nrElem; i++)
		{
			v[i] = ot.v[i];
		}

		nrElem = ot.nrElem;
		load = ot.load;
	}

	// Operator =

	MyVectorT& operator=(const MyVectorT& ot)
	{
		if (this == &ot)
		{
			return *this;
		}

		delete[] v;

		v = new El[ot.load];

		for (int i = 0; i < ot.nrElem; i++)
		{
			v[i] = ot.v[i];
		}

		nrElem = ot.nrElem;
		load = ot.load;

		return *this;
	}

	// Rule of 5

	// Constructor de mutare

	MyVectorT(MyVectorT&& ot)
	{
		v = ot.v;
		nrElem = ot.nrElem;
		load = ot.load;

		ot.v = nullptr;
		ot.nrElem = ot.load = 0;
	}

	// Move =

	MyVectorT& operator=(MyVectorT&& ot)
	{
		if (this == &ot)
		{
			return *this;
		}

		delete[] v;

		v = ot.v;
		nrElem = ot.nrElem;
		load = ot.load;

		ot.v = nullptr;
		ot.nrElem = ot.load = 0;

		return *this;
	}

	

	void push_back(const El& e)
	{
		if (nrElem == load)
		{
			El* New = new El[10 * load];

			for (int i=0; i<nrElem; i++)
			{
					New[i] = v[i];
			}

			delete[] v;

			load *= 10;

			v = New;
		}

		v[nrElem] = e;
		nrElem++;
	}

	void erase(const int j)
	{
		for (int i=j; i<nrElem - 1; i++)
		{
			v[i] = v[i + 1];
		}
		nrElem--;
	}

	// Destructor

	int size() const
	{
		return nrElem;
	}

	~MyVectorT() { delete[] v; }

	El& get(const int poz) const
	{
		return v[poz];
	}

	/*poate fara constul final*/
	void set(const int poz, const El& e)
	{
		v[poz] = e;
	}

	friend class IteratorT<El>;

	IteratorT<El> begin();

	IteratorT<El> end();

};

template <typename El>
IteratorT<El> MyVectorT<El>::begin()
{
	return IteratorT<El>(*this);
}

template <typename El>
IteratorT<El> MyVectorT<El>::end()
{
	return IteratorT<El>(*this, nrElem);
}








template <typename El>
class IteratorT
{
private:
	const MyVectorT<El>& v;
	int poz = 0;

public:

	IteratorT(const MyVectorT<El>& v) noexcept;

	IteratorT(const MyVectorT<El>& v, const int poz)noexcept;

	bool valid() const
	{
		return (poz >= 0 && poz < v.nrElem);
	}

	El& element() const
	{
		return v.v[poz];
	}

	void next()
	{
		poz++;
	}

	El& operator*()
	{
		return element();
	}
	
	IteratorT& operator++()
	{
		next();
		return *this;
	}

	IteratorT& operator+(const int j)
	{
		for (auto i : j)
		{
			next();
		}
		return *this;
	}
	
	bool operator==(const IteratorT& ot)noexcept
	{
		return poz == ot.poz;
	}
	
	bool operator!=(const IteratorT& ot)noexcept
	{
		return !(*this == ot);
	}

};

template <typename El>
IteratorT<El>::IteratorT(const MyVectorT<El>& v) noexcept : v{ v } {}

template <typename El>
IteratorT<El>::IteratorT(const MyVectorT<El>& v, const int poz)noexcept : v{ v }, poz{ poz } {}


template <typename T>
class IteratorGeanta;

template <typename T>
class Geanta
{
	string nume;
	vector<T> obiecte;

public:

	Geanta(const string& num) : nume{ num }
	{

	}

	Geanta(const string& num, const vector<T>& v) : nume{ num }, obiecte{ v }
	{

	}

	void adauga(const T& elem)
	{
		obiecte.push_back(elem);
	}

	int size() const
	{
		return obiecte.size();
	}

	Geanta operator +(const T& elem);

	Geanta& operator =(const Geanta& elem)
	{
		if (this == &elem)
		{
			return *this;
		}

		this->nume = elem.nume;

		this->obiecte = elem.obiecte;

		return *this;
	}

	friend class IteratorGeanta<T>;

	IteratorGeanta<T> begin()
	{
		return IteratorGeanta<T>(*this);
	}

	IteratorGeanta<T> end()
	{
		return IteratorGeanta<T>(*this, obiecte.size());
	}

};

template <typename T>
Geanta<T> Geanta<T>::operator+(const T& elem)
{
	Geanta g = Geanta(this->nume, this->obiecte);

	g.adauga(elem);

	return g;
}


template <typename T>
class IteratorGeanta
{
private:
	
	Geanta<T>& g;
	int poz = 0;

public:

	IteratorGeanta(Geanta<T>& geanta) : g{geanta}
	{
		
	}

	IteratorGeanta(Geanta<T>& geanta, const int& p) : g{ geanta }, poz{p}
	{

	}

	bool valid() const
	{
		return (poz >= 0 && poz < g.obiecte.size());
	}

	T& element() const
	{
		return g.obiecte[poz];
	}

	void next()
	{
		poz++;
	}

	T& operator*()
	{
		return element();
	}

	IteratorGeanta& operator++()
	{
		next();
		return *this;
	}

	IteratorGeanta& operator+(const int j)
	{
		for (auto i : j)
		{
			next();
		}
		return *this;
	}

	bool operator==(const IteratorGeanta& ot)noexcept
	{
		return poz == ot.poz;
	}

	bool operator!=(const IteratorGeanta& ot)noexcept
	{
		return !(*this == ot);
	}
};



