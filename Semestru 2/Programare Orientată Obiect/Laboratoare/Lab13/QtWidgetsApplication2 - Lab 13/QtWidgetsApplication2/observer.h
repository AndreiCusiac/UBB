#pragma once

#include <vector>

using std::vector;

class Observer
{
public :

	virtual void update() = 0;
};

class Observable
{

private:
	
	vector<Observer*> interested;

protected:

	void notify()
	{
		for (auto obs : interested)
		{
			obs->update();
		}
	}

public:

	void addObserver(Observer* o)
	{
		interested.push_back(o);
	}

	void removeObserver(Observer* o)
	{
		interested.erase(remove(interested.begin(), interested.end(), o), interested.end());
	}
	
};