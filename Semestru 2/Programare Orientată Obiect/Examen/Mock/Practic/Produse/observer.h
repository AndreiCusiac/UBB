#pragma once
#include <list>
#include <memory>

using std::list;

class Observer
{
public:
	virtual void update() = 0;
	virtual ~Observer() = default;
};

class Observable
{
private:
	list<Observer*> observers;
protected:
	void notify();
public:
	void add_observer(Observer* obs);
	void remove_observer(Observer* obs);
};