#pragma once
#include <string>
#include <vector>
#include <memory>
#include <iostream>
#include <cassert>

using std::string;
using std::to_string;
using std::vector;
using std::shared_ptr;
using std::cout;
using std::endl;
using std::make_shared;

class DuplicateIDException
{
private:
	string msg;
public:
	DuplicateIDException(const string& s) : msg{ s } {}
	const string& getMessage() { return msg; }
};

class Payable
{
private:
	
	string id;

public:
	
	Payable(const string& id_new) : id{ id_new } {}
	
	const string& getID() { return id; }
	
	virtual const double& monthlyIncome() = 0;
	
	const string toString()
	{
		string str{ "ID: " };
		str += id;
		str += "; suma de platit: ";
		str += to_string(monthlyIncome());
		return str;
	}
	
	virtual ~Payable() = default;
};

class Student : public Payable
{
private:
	
	double scholarship;

public:
	
	Student(const string& id_new, const double& sch) : Payable{ id_new }, scholarship{ sch } {}
	
	const double& monthlyIncome() override { return scholarship; }
	~Student() override = default;
};

class Teacher : public Payable
{
private:
	
	double salary;

public:
	
	Teacher(const string& id_new, const double& sal) : Payable{ id_new }, salary{ sal } {}
	
	const double& monthlyIncome() override { return salary; }
	
	~Teacher() override = default;
};

class University
{
private:
	
	vector<shared_ptr<Payable>> payables;
	string name;

public:
	
	University(const string& n) : name{ n } {}
	
	const Payable& findPayable(const string& id)
	{
		for (const auto& p : payables)
		{
			if (p->getID() == id)
			{
				return *p;
			}
		}
		throw DuplicateIDException{ "Nu exista payable cu id " + id };
	}
	
	void addPayable(const shared_ptr<Payable>& p)
	{
		auto id = p->getID();
		for (const auto& pay : payables)
		{
			if (pay->getID() == id)
			{
				throw DuplicateIDException{ "Exista deja payable cu id " + id };
			}
		}
		payables.push_back(p);
	}
	
	const vector<shared_ptr<Payable>>& getAllPayables() { return payables; }
	
	const double& totalAmountToPay()
	{
		double total{ 0 };
		for (const auto& p : payables)
		{
			total += p->monthlyIncome();
		}
		return total;
	}
	
	~University() = default;
};

void f1()
{
	University u{ "UBB" };
	u.addPayable(make_shared<Student>("02", 456));
	u.addPayable(make_shared<Teacher>("01", 1400));
	u.addPayable(make_shared<Student>("03", 560));
	u.addPayable(make_shared<Teacher>("04", 700));
	try
	{
		u.addPayable(make_shared<Teacher>("01", 1400));
		assert(false);
	}
	catch (DuplicateIDException& ex)
	{
		assert(true);
	}
	for (const auto& p : u.getAllPayables())
	{
		cout << p->toString() << endl;
	}
	cout << u.totalAmountToPay() << endl;
}