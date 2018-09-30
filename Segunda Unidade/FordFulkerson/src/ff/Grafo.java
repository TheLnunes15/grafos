package ff;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Grafo extends Cloneable
{

	void adicionaNo(Vertice n);


	void adicionaAresta(Aresta e);


	int numeroNos();
	

	int numeroArestas();
	

	List<Aresta> adjacent(Vertice n);


	List<Aresta> incident(Vertice n);
	

	boolean containsNode(Vertice n);


	boolean contemAresta(Aresta e);


	Vertice retornaNo(int id);
	

	Collection<Vertice> retornaNos();
	

	Set<Integer> getNodesIDs();
	

	void deOrigem(Vertice node);
	

	Vertice getOrigem();
	

	void paraDestino(Vertice node);
	

	Vertice getDestino();


	List<Aresta> retornaArestas();


	void removeNo(Vertice n);


	void removeEdge(Aresta e);


	void clear();


	Object clone();


	Grafo retornaSubgrafo(Set<Integer> s);
	
	

}