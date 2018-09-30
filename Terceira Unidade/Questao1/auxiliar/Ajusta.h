#pragma once

#include "Grafo.h"
#include "HeapBinario.h"
#include <list>
#include <vector>

using namespace std;

#define EVEN 2
#define ODD 1
#define UNLABELED 0

class Matching{
public:
	Matching(const Grafo & G);
	pair< list<int>, double > SolveMinimumCostPerfectMatching(const vector<double> & cost);
	list<int> SolveMaximumMatching();

private:
	void Grow();
	void Expand(int u, bool expandBlocked);
	void Augment(int u, int v);
	void Reset();
	int Blossom(int u, int v);
	void UpdateDualCosts();
	void Clear();
	void DestroyBlossom(int t);
	void Heuristic();
	void PositiveCosts();
	list<int> RetrieveMatching();

	int GetFreeBlossomIndex();
	void AddFreeBlossomIndex(int i);
	void ClearBlossomIndices();

	bool IsEdgeBlocked(int u, int v);
	bool IsEdgeBlocked(int e);
	bool IsAdjacent(int u, int v);

	const Grafo & G;

	list<int> free;

	vector<int> outer;
	vector< list<int> > deep;
	vector< list<int> > shallow;
	vector<int> tip;
	vector<bool> active;

	vector<int> type;
	vector<int> forest;
	vector<int> root;

	vector<bool> blocked;
	vector<double> dual;
	vector<double> slack;
	vector<int> mate;
	int m, n;

	bool perfect;

	list<int> forestList;
	vector<int> visited;
};

