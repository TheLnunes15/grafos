package ff;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Fluxo implements Grafo
{

	private Map<Integer, LinkedList<Aresta>> adjacencias = new HashMap<Integer, LinkedList<Aresta>>();

	private Map<Integer, LinkedList<Aresta>> incidencias = new HashMap<Integer, LinkedList<Aresta>>();

	private Map<Integer, Vertice> nodes = new HashMap<Integer, Vertice>();

	private Vertice origem;

	private Vertice destino;
	
	@Override
	public void adicionaNo(Vertice n)
	{
		if(containsNode(n)) throw new IllegalArgumentException("No " + n + " nao existente");
		nodes.put(n.id, n);
		adjacencias.put(n.id, new LinkedList<Aresta>());
		incidencias.put(n.id, new LinkedList<Aresta>());
	}
	
	@Override
	public void adicionaAresta(Aresta e)
	{
		if(!containsNode(e.origem) || !containsNode(e.destino))
			throw new IllegalArgumentException("Arco nao possivel " + e);
		List<Aresta> adjacent = adjacencias.get(e.origem.id);
		List<Aresta> incident = incidencias.get(e.destino.id);
		adjacent.add(e);
		incident.add(e);
	}	
	
	@Override
	public void deOrigem(Vertice node)
	{
		origem = node;
	}

	@Override
	public Vertice getOrigem()
	{
		return origem;
	}

	@Override
	public void paraDestino(Vertice node)
	{
		destino = node;		
	}

	@Override
	public Vertice getDestino()
	{
		return destino;
	}

	@Override
	public int numeroNos()
	{
		return nodes.size();
	}

	@Override
	public int numeroArestas()
	{
		int numeroArestas = 0;
		
		for(List<Aresta> adjList : adjacencias.values())
		{
			numeroArestas += adjList.size();
		}
		
		return numeroArestas;
	}	
	

	@Override
	public List<Aresta> adjacent(Vertice n)
	{
		return adjacencias.get(n.id);
	}
	
	@Override
	public boolean containsNode(Vertice n)
	{
		return nodes.containsKey(n.id);
	}
	
	@Override
	public boolean contemAresta(Aresta e)
	{
		List<Aresta> adjList = adjacencias.get(e.origem.id);
		return adjList.contains(e);
	}
	
	@Override
	public Vertice retornaNo(int id)
	{
		return nodes.get(id);
	}

	@Override
	public Collection<Vertice> retornaNos()
	{
		return nodes.values();
	}
	
	@Override
	public Set<Integer> getNodesIDs()
	{
		return new HashSet<Integer>(nodes.keySet());
	}


	@Override
	public List<Aresta> retornaArestas()
	{
		List<Aresta> edges = new LinkedList<Aresta>();
		for(List<Aresta> adjList: adjacencias.values())
		{
			edges.addAll(adjList);
		}
		
		return edges;
	}
	
	@Override
	public void removeNo(Vertice n)
	{
		nodes.remove(n.id);
		adjacencias.remove(n.id);
		incidencias.remove(n.id);
		
		for(List<Aresta> adjList : adjacencias.values())
		{
			Iterator<Aresta> it = adjList.iterator();
			while(it.hasNext())
			{
				Aresta e = it.next();
				if(e.destino.equals(n))
				{
					it.remove();
					break; // non � mica un multigrafo...
				}
			}
		}
		
		for(List<Aresta> incList : incidencias.values())
		{
			Iterator<Aresta> it = incList.iterator();
			while(it.hasNext())
			{
				Aresta e = it.next();
				if(e.origem.equals(n))
				{
					it.remove();
					break; // non � mica un multigrafo...
				}
			}
		}
	}
	
	@Override
	public void removeEdge(Aresta e)
	{
		List<Aresta> adjList = adjacencias.get(e.origem.id);
		List<Aresta> incList = incidencias.get(e.destino.id);
		adjList.remove(e);
		incList.remove(e);
	}
	
	@Override
	public void clear()
	{
		nodes.clear();
		adjacencias.clear();
		incidencias.clear();
	}
	

	@Override
	public List<Aresta> incident(Vertice n)
	{
		return incidencias.get(n.id);
	}
	
	@Override
	public Object clone()
	{
		Fluxo graph = new Fluxo();
		for(Vertice n : retornaNos())
		{ 
			Vertice clonedNode = new Vertice(n);
			graph.adjacencias.put(n.id, new LinkedList<Aresta>());
			graph.incidencias.put(n.id, new LinkedList<Aresta>());
			graph.nodes.put(n.id, clonedNode);
			
			if(n.equals(origem))
			{
				graph.deOrigem(clonedNode);
			}
			else if(n.equals(destino))
			{
				graph.paraDestino(clonedNode);
			}
		}
		
		
		for(Vertice n : retornaNos())
		{
			LinkedList<Aresta> clonedAdjList = graph.adjacencias.get(n.id);
			

			for(Aresta e : adjacent(n))
			{
				Vertice clonedSrc = graph.nodes.get(e.origem.id);
				Vertice clonedDest = graph.nodes.get(e.destino.id);
				Aresta clonedEdge = new Aresta(clonedSrc, clonedDest, e.capacidade, e.fluxo);
				clonedAdjList.add(clonedEdge);
				LinkedList<Aresta> clonedIncList = graph.incidencias.get(e.destino.id);
				clonedIncList.add(clonedEdge);
			}
		}
		
		
		return graph;
	}
	

	@Override
	public Grafo retornaSubgrafo(Set<Integer> s)
	{
		Fluxo subGraph = new Fluxo();
		
		for(int n : s)
		{
			Vertice node = nodes.get(n);
			Vertice clonedNode = new Vertice(node);
			subGraph.adicionaNo(clonedNode);
		}
		
		if(origem != null)
		{
			Vertice clonedSource = new Vertice(origem);
			subGraph.adicionaNo(clonedSource);
			subGraph.deOrigem(clonedSource);
		}
		if(destino != null)
		{
			Vertice clonedSink = new Vertice(destino);
			subGraph.adicionaNo(clonedSink);
			subGraph.paraDestino(clonedSink);
		}
		
		for(int n : subGraph.getNodesIDs())
		{
			LinkedList<Aresta> clonedAdjList = subGraph.adjacencias.get(n);
			Vertice node = nodes.get(n);
			// riempi le liste di adiacenza e di incidenza
			for(Aresta e : adjacent(node))
			{
				if(subGraph.containsNode(e.destino))
				{
					Vertice clonedSrc = subGraph.nodes.get(e.origem.id);
					Vertice clonedDest = subGraph.nodes.get(e.destino.id);
					Aresta clonedEdge = new Aresta(clonedSrc, clonedDest, e.capacidade, e.fluxo);
					clonedAdjList.add(clonedEdge);
					LinkedList<Aresta> clonedIncList = subGraph.incidencias.get(e.destino.id);
					clonedIncList.add(clonedEdge);
				}
			}
		}

		return subGraph;
	}

	@Override
	public String toString()
	{
		return "Adjacencias: " + adjacencias.toString() + "\nIncidencias: " + incidencias.toString();
	}
	
}
