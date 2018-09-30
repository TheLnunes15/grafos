#include <iostream> 
  
using namespace std;

// Numero de vertices no grafo
#define V 9
  
// Uma funcao utilitaria para encontrar o vertice com o valor minimo de Distancia, de
// o conjunto de vertices ainda nao incluidos na arvore de caminho mais curto
int minDistancia(int dist[], bool sptSet[]){
   // inicializa o valor minimo
   int v, min = INT_MAX, min_index;
  
   for (v = 0; v < V; v++)
     if (sptSet[v] == false && dist[v] <= min)
         min = dist[v], min_index = v;
  
   return min_index;
}
  
// Funcao que implementa o algoritmo de caminho mais curto de origem unica de Dijkstra
// para um grafo representado usando representacao da matriz de adjacencia
void dijkstra(int graph[V][V], int origem){
     // O array de saida. dist [i] mantera o menor 
	 // Distancia da origem ate i
	 int dist[V], i, count, u, v; 
	 /* sptSet[i] sera verdadeiro se o vertice i for incluido no menor
      caminho arvore ou menor Distancia da origem para i eh finalizado */
     bool sptSet[V];
  
     // Inicialize todas as Distancias como INFINITE e stpSet[] como false
     for (i = 0; i < V; i++)
        dist[i] = INT_MAX, sptSet[i] = false;
  
     // Distancia do vertice de origem from de si mesmo eh sempre 0
     dist[origem] = 0;
  
     // Encontre o caminho mais curto para todos os vértices
     for (count = 0; count < V-1; count++){
       /* Escolha o vertice de distancia minima do conjunto de vertices nao
        ainda processado. u eh sempre igual a origem na primeira iteracao. */
       u = minDistancia(dist, sptSet);
  
       // Marcar o vertice escolhido como processado
       sptSet[u] = true;
  
       // Atualiza o valor de dist dos vertices adjacentes do vertice escolhido
       for (v = 0; v < V; v++)  
         /* Update dist[v] somente se nao estiver em sptSet, existe uma aresta de
          u para v, e o peso total do caminho de origem para v ate u é
          menor que o valor atual de dist [v] */
         if (!sptSet[v] && graph[u][v] && dist[u] != INT_MAX && dist[u]+graph[u][v] < dist[v])
            dist[v] = dist[u] + graph[u][v];
     }
  
    // imprime a matriz construida de distancia
    cout << "Vertice   Distancia" << endl;
    for (int i = 0; i < V; i++)
       cout << "   " << i << "          " << dist[i] << endl;
}
  

int main(void){
   int graph[V][V] = {{0, 4, 0, 0, 0, 0, 0, 8, 0},
                      {4, 0, 8, 0, 0, 0, 0, 11, 0},
                      {0, 8, 0, 7, 0, 4, 0, 0, 2},
                      {0, 0, 7, 0, 9, 14, 0, 0, 0},
                      {0, 0, 0, 9, 0, 10, 0, 0, 0},
                      {0, 0, 4, 14, 10, 0, 2, 0, 0},
                      {0, 0, 0, 0, 0, 2, 0, 1, 6},
                      {8, 11, 0, 0, 0, 0, 1, 0, 7},
                      {0, 0, 2, 0, 0, 0, 6, 7, 0}
                     };
  
    dijkstra(graph, 0);
}
