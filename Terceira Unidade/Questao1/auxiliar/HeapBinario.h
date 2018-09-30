#pragma once

#include "VariaveisGlobais.h"
#include <vector>

using namespace std;

class BinaryHeap{
public:
	BinaryHeap(): satellite(1), size(0) {};

	void Insert(double k, int s);
	int DeleteMin();
	void ChangeKey(double k, int s);
	void Remove(int s);
	int Size();
	void Clear();

private:
	vector<double> key;
	vector<int> pos;
	vector<int> satellite;

	int size;
};



