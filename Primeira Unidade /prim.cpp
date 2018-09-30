#include <iostream> 
 
using namespace std;

// Numero de vertices no grafo
#define V 9
 
/* Uma funcao utilitaria para encontrar o vertice com valor de chave minimo, de
 um conjunto de vertices ainda nao incluidos no Prim */
int minchave(int chave[], bool mstSet[]){
   // Inicialize o valor minimo
   int v, min = INT_MAX, min_index;
 
   for (v = 0; v < V; v++)
     if (mstSet[v] == false && chave[v] < min)
         min = chave[v], min_index = v;
 
   return min_index;
}
 
// Funcao para construir e imprimir a arvore para um grafo representado usando adjacencia
// representação da matriz
void prim(int grafo[V][V]){
     // matriz para armazenar a arvore construida
     // chaves usadas para escolher a borda de peso minimo no corte
	 int pai[V], chave[V], i, count, u, v; 
     bool mstSet[V]; // para representar conjunto de vertices ainda nao incluidos na arvore
 
     // Inicialize todas as chaves como INFINITE
     for (i = 0; i < V; i++)
        chave[i] = INT_MAX, mstSet[i] = false;
 
     // sempre inclua o primeiro primeiro vertice na arvore
     chave[0] = 0; // faca chave 0 para que este vertice seja escolhido como primeiro vertice
     pai[0] = -1; // primeiro "no" eh sempre raiz da arvore
 
     // a arvore tem V vertices
     for (count = 0; count < V-1; count++){
        /* Escolha o minimo do vertice chave do conjunto de vertices
          ainda nao incluido na arvore */
        u = minchave(chave, mstSet);
 
        // Adiciona o vertice escolhido ao conjunto
        mstSet[u] = true;
 
        /* Atualizar o valor da chave e o indice pai dos vertices adjacentes
         o vertice escolhido. Considere apenas aqueles vertices que ainda nao sao
         incluido na arvore */
        for (v = 0; v < V; v++) 
          /* grafo [u] [v] eh diferente de zero apenas para vertices adjacentes de m
          mstSet [v] eh falso para vertices ainda não incluidos na arvore e
          atualiza a chave somente se o grafo [u][v] for menor que a chave [v] */
          if (grafo[u][v] && mstSet[v] == false && grafo[u][v] < chave[v])
             pai[v] = u, chave[v] = grafo[u][v];
     }
 
     // imprime a construcao da arvore
     cout << "Aresta   Peso" << endl;
     for (i = 1; i < V; i++)
	    cout << pai[i] << " - " << i << "     " << grafo[i][pai[i]] << endl; 
}
 
 
int main(void){
   int grafo[V][V] = {{0, 4, 0, 0, 0, 0, 0, 8, 0},
                      {4, 0, 8, 0, 0, 0, 0, 11, 0},
                      {0, 8, 0, 7, 0, 4, 0, 0, 2},
                      {0, 0, 7, 0, 9, 14, 0, 0, 0},
                      {0, 0, 0, 9, 0, 10, 0, 0, 0},
                      {0, 0, 4, 14, 10, 0, 2, 0, 0},
                      {0, 0, 0, 0, 0, 2, 0, 1, 6},
                      {8, 11, 0, 0, 0, 0, 1, 0, 7},
                      {0, 0, 2, 0, 0, 0, 6, 7, 0}
                     };
 
    // imprime a solucao
    prim(grafo);
}
