#pragma once

#include "Grafo.h"
#include "HeapBinario.h"
#include "VariaveisGlobais.h"
#include <limits>
using namespace std;

pair< vector<int>, vector<double> > Dijkstra(const Grafo & G, int origin, const vector<double> & cost){
	BinaryHeap B;

	int n = G.GetNumVertices();

	vector<int> father(n, -1);
	vector<bool> permanent(n, false);
	vector<double> pathCost(n, numeric_limits<double>::infinity());
	
	B.Insert(0, origin);
	pathCost[origin] = 0;

	for(int i = 0; i < n; i++){
		int u = B.DeleteMin();

		permanent[u] = true;

		for(list<int>::const_iterator it = G.AdjList(u).begin(); it != G.AdjList(u).end(); it++){
			int v = *it;
			
			if(permanent[v])
				continue;

			double c = pathCost[u] + cost[G.GetEdgeIndex(u,v)];

			if(father[v] == -1){
				father[v] = u;	
				pathCost[v] = c;
				B.Insert(c, v);
			}
			else if( LESS(c, pathCost[v])){
				father[v] = u;
				pathCost[v] = c;
				B.ChangeKey(c, v);
			}
		}
	}

	if(B.Size() > 0)
		throw "Erro: o grafo nao esta conectado";

	return make_pair(father, pathCost);
}

