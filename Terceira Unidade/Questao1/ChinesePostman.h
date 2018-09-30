#include "./auxiliar/Dijkstra.h"
#include "./auxiliar/Ajusta.h"
#include "./auxiliar/Grafo.h"

bool Connected(const Grafo & G)
{
    vector<bool> visited(G.GetNumVertices(), false);
    list<int> L;
    
    int n = 0;
    L.push_back(0);
    while(not L.empty())
    {
        int u = L.back();
        L.pop_back();
        if(visited[u]) continue;
        
        visited[u] = true;
        n++;
        
        for(list<int>::const_iterator it = G.AdjList(u).begin(); it != G.AdjList(u).end(); it++)
		{
			int v = *it;
		    L.push_back(v);
		}
    }
   
    return G.GetNumVertices() == n;
}

/*
Função do Chinese Postman Problem.
*/
pair< list<int>, double > ChinesePostman(const Grafo & G, const vector<double> & cost)
{
	//Grafo eh conectado?
	if(not Connected(G))
		throw "Error: Grafo não é conectado";

	//Construindo listas de adjacencias usando arestas do grafo.
	vector< list<int> > A(G.GetNumVertices(), list<int>());
	for(int u = 0; u < G.GetNumVertices(); u++)
	    A[u] = G.AdjList(u);

	//Encontrando vértices de grau impar
	vector<int> odd;
	for(int u = 0; u < G.GetNumVertices(); u++)
		if(A[u].size() % 2)
			odd.push_back(u);

    //Se há vertice de grau impar...
	if(not odd.empty())
	{
		//Crie um grafo com os vertices de graus impares
		Grafo O(odd.size());
		for(int u = 0; u < (int)odd.size(); u++)
			for(int v = u+1; v < (int)odd.size(); v++)
				O.AddEdge(u, v);

        vector<double> costO(O.GetNumEdges());
        
       // Encontre os caminhos mais curtos entre todos os vertices de graus impares
		vector< vector<int> > shortestPath(O.GetNumVertices());
		for(int u = 0; u < (int)odd.size(); u++)
		{
			pair< vector<int>, vector<double> > sp = Dijkstra(G, odd[u], cost);
			
			shortestPath[u] = sp.first ;
			
			// O custo de uma borda uv em O sera o custo do caminho mais curto correspondente em G
			for(int v = 0; v < (int)odd.size(); v++)
			    if(v != u)
    			    costO[ O.GetEdgeIndex(u, v) ] = sp.second[odd[v]];
		}

	    //Encontre a correspondencia perfeita de custo minimo do grafo dos vertices de graus impares
	    Matching M(O);
	    pair< list<int>, double > p = M.SolveMinimumCostPerfectMatching(costO);
	    list<int> matching = p.first;
	    
	    //Se uma aresta u-v estiver na correspondencia, as arestas no caminho mais curto de u para v devem ser duplicadas em G
	    for(list<int>::iterator it = matching.begin(); it != matching.end(); it++)
	    {
		    pair<int, int> p = O.GetEdge(*it);
		    int u = p.first, v = odd[p.second];
		    
		    //Percorra o caminho adicionando as arestas
		    int w = shortestPath[u][v];
		    while(w != -1)
		    {
		        A[w].push_back(v);
		        A[v].push_back(w);
		        v = w;
		        w = shortestPath[u][v];
		    }
	    }
	}

	//Encontre um ciclo Euleriano no grafo sugerido por A
	list<int> cycle;
	//Isso eh para acompanhar quantas vezes podemos percorrer uma aresta
	vector<int> traversed(G.GetNumEdges(), 0);
	for(int u = 0; u < G.GetNumVertices(); u++)
	{
		for(list<int>::iterator it = A[u].begin(); it != A[u].end(); it++)
		{
			int v = *it;
			
			//isso eh para que a aresta nao seja contada duas vezes
			if(v < u) continue;

			traversed[G.GetEdgeIndex(u, v)]++;
		}
	}

	cycle.push_back(0);
	list<int>::iterator itp = cycle.begin();
	double obj = 0;
	while(itp != cycle.end())
	{
		//Seja v o vertice atual no ciclo, iniciando no primeiro
		int u = *itp;
		list<int>::iterator jtp = itp;
		jtp++;

		//se houver aresta nao atravessadas incidentes em u, encontre um subciclo começando em u substitua u no ciclo pelo subciclo
		while(not A[u].empty())
		{
			while(not A[u].empty() and traversed[ G.GetEdgeIndex(u, A[u].back()) ] == 0)
				A[u].pop_back();

			if(not A[u].empty())
			{
				int v = A[u].back();
				A[u].pop_back();
				cycle.insert(jtp, v);
				traversed[G.GetEdgeIndex(u, v)]--;
		        obj += cost[ G.GetEdgeIndex(u, v) ];
				u = v;
			}
		}

		//Seguira para o proximo vertice no ciclo e determina o mesmo
		itp++;
	}

	return pair< list<int>, double >(cycle, obj);
}
