# grafos

**Provas práticas da disciplina Grafos e Algoritmos Computacionais (C/C++ e Java)**

[**Primeira Unidade**] - *C/C++*

* Algoritmo de Bellman-Ford 
* Algoritmo de Dijkstra
* Algoritmo de Kruskal
* Algoritmo de Prim

*Os algoritmos de Dijkstra e Bellman-Ford são para encontrar caminhos mínimos, e os de Kruskal e Prim para encontrar árvores geradoras mínimas.*

[**Segunda Unidade**] - *Java*

1. Algoritmo de Ford–Fulkerson (nome usado *ffflow*)
2. Algoritmo de Push–Relabel (nome usado *prflow*)

[**Terceira Unidade**] - *C++ e Java*

**1. Grafos Eulerianos (em C++):**
Implemente um algoritmo para encontrar o ciclo de menor custo em um grafo que passa pelo menos uma vez por cada aresta. O algoritmo deve ler um arquivo textual onde um grafo simples ponderado é representado através de matrizes de adjacências. Os valores diferentes de 0 indicam o peso. O grafo do exemplo abaixo seria representado da seguinte forma:

0 1 0 0 0 3 1 0 0 0

1 0 1 0 3 0 2 0 0 0

0 1 0 5 0 0 0 2 0 0

0 0 5 0 2 0 0 0 1 0

0 3 0 2 0 1 0 0 4 0

3 0 0 0 1 0 0 0 0 2

1 2 0 0 0 0 0 2 0 3

0 0 2 0 0 0 2 0 1 1

0 0 0 1 4 0 0 1 0 2

0 0 0 0 0 2 3 1 2 0

Se o grafo não é Euleriano uma solução possível é a seguinte:
Seja V impar = {V1, V2,..., Vk} o conjunto dos vértices de grau ímpar do grafo G.
1. Para cada par (Vi, Vj) de Vímpar encontre o caminho Pi,j de Vi a Vj com menor custo.
2. Determine um conjunto de k/2 caminhos Pi,je que contempla todos os vértices de V impar e tem a menor soma de custos.
3. Crie G’ duplicando em G as arestas de cada um dos caminhos Pi,j do conjunto determinado no item anterior.
4. Ache um ciclo Euleriano em G’.

**2. Grafos Planares (Java):**
Implemente um algoritmo para decidir se um grafo simples é planar ou não. O algoritmo deve ler um arquivo textual onde um grafo simples é representado através da matriz de adjacência.

**3. Coloração de Vértices (Java):**
Implemente um algoritmo guloso para coloração de vértices. O algoritmo deve ler um arquivo textual onde um grafo simples é representado através da matriz de adjacência. A saída é uma lista com n elementos (cada elemento é um vértice do grafo) com as cores atribuídas a cada vértice.



