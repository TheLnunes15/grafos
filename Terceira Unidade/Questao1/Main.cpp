#include "./auxiliar/Grafo.h"
#include "ChinesePostman.h"
#include <fstream>
#include <iostream>
#include <string>
#include <sstream>
#include <vector>

using namespace std;

pair<Grafo, vector<double> > ReadWeightedGraph(string filename){
	//Ler dados da matriz de adjacencia e transforma-la em grafo
	ifstream fileTest;
	fileTest.open(filename.c_str());
	int countVertices = 0, countArestas = 0;
	string s;

	// contar quantidade de vertices
	while (getline(fileTest, s))
		countVertices++;

	int valores[countVertices][countVertices];
	
	// inicializando matriz de adjacencia
	for (int i = 0; i < countVertices; i++){
		for (int j = 0; j < countVertices; j++){
			valores[i][j] = 0;
		}
	}

	fileTest.close();

	// Tratamento da matriz de adjacencia
	ifstream file;
	file.open(filename.c_str());
	
	// Aqui escreve valores em matriz e conta-se a quantidade de arestas	
	for (int y = 0; y < countVertices; y++){
		for (int x = 0; x < countVertices; x++){
			file >> valores[y][x];
			if (valores[y][x] != 0) countArestas++;
		}
	}

	cout << "Matriz de adjacencia:" << endl;

	for (int y = 0; y < countVertices; y++){
		for (int x = 0; x < countVertices; x++){
			cout << valores[y][x];		
		}
		cout << endl;
	}

	countArestas = countArestas/2;

	cout << "Quantidade de vertices: " << countVertices << endl;
	cout <<"Quantidade de arestas: " << countArestas << endl;
	
	Grafo G(countVertices);
	vector<double> cost(countArestas);
	double c;

	for (int i = 0; i < countVertices; i++){
		for (int j = 0; j < countVertices; j++){
			if (valores[i][j] != 0) {
				G.AddEdge(i, j);
				c = valores[i][j];
				cost[G.GetEdgeIndex(i, j)] = c;
			}
		}
	}

	file.close();
	return make_pair(G, cost);
}

int main(int argc, char * argv[]){
	string filename = "";

	int i = 1;
	while(i < argc){
		string a(argv[i]);
		if(a == "-f")
			filename = argv[++i];
		i++;
	}

	if(filename == ""){
		cout << endl << "Use o nome do arquivo correto, como usa a extensao: grafo.txt !";
		return 1;
	}

	try{
	    Grafo G;
	    vector<double> cost;
	
	    //Lendo o grafo
	    pair<Grafo, vector<double> > p = ReadWeightedGraph(filename);
	    G = p.first;
	    cost = p.second;

	    //Solucao
     	pair< list<int> , double > sol = ChinesePostman(G, cost);

		cout << "Custo da solucao: " << sol.second << endl;

		list<int> s = sol.first;

        //Imprime as arestas da solucao
		cout << "Arestas da solucao:" << endl;
		for(list<int>::iterator it = s.begin(); it != s.end(); it++)
			cout << *it << " ";
		cout << endl;
	}
	catch(const char * msg){
		cout << msg << endl;
		return 1;
	}

	return 0;
}

