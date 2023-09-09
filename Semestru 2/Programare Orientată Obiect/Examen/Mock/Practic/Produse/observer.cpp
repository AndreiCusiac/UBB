#include "observer.h"

void Observable::notify()
{
	for (auto& o : observers)
	{
		o->update();
	}
}

void Observable::add_observer(Observer* obs)
{
	observers.push_back(obs);
}

void Observable::remove_observer(Observer* obs)
{
	observers.remove(obs);
}