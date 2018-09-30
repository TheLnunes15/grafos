#pragma once

#include <list>
#include <vector>

using namespace std;

class Grafo{
public:
	Grafo(int n, const list< pair<int, int> > & edges = list< pair<int, int> >());
	Grafo(): n(0), m(0) {};

	int GetNumVertices() const { return n; };
	int GetNumEdges() const { return m; };

	pair<int, int> GetEdge(int e) const;
	int GetEdgeIndex(int u, int v) const;

	void AddVertex();
	void AddEdge(int u, int v);

	const list<int> & AdjList(int v) const;
	const vector< vector<bool> > & AdjMat() const;
private:
	int n, m;

	vector< vector<bool> > adjMat;
	vector< list<int> > adjList;
	vector< pair<int, int> > edges;
	vector< vector<int> > edgeIndex;
};
