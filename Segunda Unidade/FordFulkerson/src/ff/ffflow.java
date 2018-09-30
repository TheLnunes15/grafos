package ff;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ff.Aresta;
import ff.Fluxo;
import ff.Vertice;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import ff.Grafo;


public class ffflow
{	
    	public static void main(String[] args)
	{

		Grafo g = FordFulkerson();		
		double f = ffflow.retornaFluxoMaximo(g);
		System.out.println("Capacidade Maxima em cada Aresta = " + g.retornaArestas());
	}
    private static Grafo FordFulkerson()
	{
                Grafo grafo = new Fluxo();
                Vertice na = null;
                Vertice nb = null;
                //Node node = null;
                int quantidadeNos = 0;
                
                int A,B,C = 0;
                
                int inicio,fim = 0;
                //Arquivo
                try{

                    System.out.printf("Ao Grafo lido:\n");
                    //Arquivo se encontra na RAIZ do projeto
                    FileInputStream stream = new FileInputStream("entradaFordFulkerson.txt");
                    InputStreamReader reader = new InputStreamReader(stream);  
                    BufferedReader lerArq = new BufferedReader(reader);
           
                    String linha = lerArq.readLine(); //lê a primeira linha que define quantas arestas tem o grafo   
                    System.out.printf("%s\n", linha);
                    //ADICIONA VERTICES
                    na = new Vertice(Integer.parseInt(linha.substring(0,1)));
                    nb = new Vertice(Integer.parseInt(linha.substring(2,3)));
                    

                    
                    grafo.adicionaNo(na); //nó INICIAL
                    grafo.deOrigem(na); //SETA INICIO

                  
                    //IDEIA
                    inicio = Integer.parseInt(linha.substring(0,1));
                    fim = Integer.parseInt(linha.substring(2,3));
                    int qtdNos = (fim - inicio) - 1;
                    int index = 2;
                    ArrayList<Vertice> nos = new ArrayList(qtdNos);
                    while(qtdNos > 0){
                        nos.add(new Vertice(index));
                        index+=1;
                        qtdNos-=1;
                    }
                    for (int i = 0; i < nos.size(); i++) {
                        grafo.adicionaNo(nos.get(i));
                    }
                    
                    grafo.adicionaNo(nb); //nó FINAL
		    grafo.paraDestino(nb); //SETA FINAL
                                    
                    linha = lerArq.readLine();                   
                    while(linha != null){
                        System.out.printf("%s\n", linha);
                        A = Integer.parseInt(linha.substring(0,1));
                        B = Integer.parseInt(linha.substring(2,3));
                        C = Integer.parseInt(linha.substring(4,5));
                        //CONTROLE
                        grafo.adicionaAresta(new Aresta(grafo.retornaNo(A),grafo.retornaNo(B),C));


                        linha = lerArq.readLine(); //lê segunda e adiante.
                    }
                }catch (IOException e){
                    System.err.printf("Erro na abertura do arquivo. %s.\n", e.getMessage());
                }
                System.out.println();
                System.out.println("======================================================");
               

		return grafo;
	}

	private static class Rotulos
	{
		private Map<Vertice, Integer> nomes;
		

		private int[] nos;
		

		public Rotulos(int n)
		{
			nomes = new HashMap<Vertice, Integer>();
			nos = new int[n + 1];
		}
		

		public int retornaRotulo(Vertice n)
		{
			return nomes.get(n);
		}
		

		public boolean setaRotulo(Vertice n, int label)
		{
			boolean existsUnassignedLabel = false;
			Integer oldLabel = nomes.get(n);
			if(oldLabel != null)
			{
				nos[oldLabel]--;	
				if(nos[oldLabel] == 0) existsUnassignedLabel = true;
			}
			
			nomes.put(n, label);
			nos[label]++;
			return existsUnassignedLabel;
		}		
	}
	

	public static double retornaFluxoMaximo(Grafo g)
	{
		if(g.numeroNos() == 0)
		{
			return 0;
		}
		
		Rotulos nomes = calcDistanceLabels(g);
		double f = 0; // max flow
		int n = g.numeroNos();
		List<Aresta> backEdges = adicionaArestaRetorno(g); 
		LinkedList<Aresta> path = new LinkedList<Aresta>(); 
		int sourceDist; 
		Vertice i = g.getOrigem(); 
		


		while(i != null && (sourceDist = nomes.retornaRotulo(g.getOrigem())) < n)
		{
			Aresta e = retornaArestaPossiel(g, i, nomes);
			if(e != null)
			{
				i = advance(e, path);
				if(i.equals(g.getDestino()))
				{
					double delta = augment(g, path);
					f += delta;
					i = g.getOrigem();
					path.clear();
				}
			}
			else i = retreat(g, nomes, i, path);
		}
		
		removeBackEdges(g, backEdges);
		
		return f;
	}
	

	private static Rotulos calcDistanceLabels(Grafo g)
	{
		int n = g.numeroNos();
		Rotulos labels = new Rotulos(n);
		
		Set<Vertice> visited = new HashSet<Vertice>();

		for (Vertice i : g.retornaNos())
		{
			labels.setaRotulo(i, n);
		}

		labels.setaRotulo(g.getDestino(), 0);
		
		LinkedList<Vertice> queue = new LinkedList<Vertice>();
		queue.addLast(g.getDestino());

		while (!queue.isEmpty())
		{
			Vertice j = queue.removeFirst();


			for (Aresta e : g.incident(j))
			{
				Vertice i = e.getOrigem();
				if (!visited.contains(i))
				{
					labels.setaRotulo(i, labels.retornaRotulo(j) + 1);

					visited.add(i);
					queue.addLast(i);
				}
			}
			visited.add(j);

		}

		return labels;
	}
	

	private static List<Aresta> adicionaArestaRetorno(Grafo g)
	{
		List<Aresta> backEdges = new LinkedList<Aresta>();
		for(Aresta e : g.retornaArestas())
		{
			Aresta backEdge = new Aresta(e.getDestino(), e.getOrigem(), 0, 0);
			g.adicionaAresta(backEdge);
			backEdges.add(backEdge);
		}
		return backEdges;
	}

	private static Aresta retornaArestaPossiel(Grafo g, Vertice i, Rotulos d)
	{
		for(Aresta e: g.adjacent(i))
		{
			if(e.retornaCapacidadeResidual() > 0 && d.retornaRotulo(i) == 1 + d.retornaRotulo(e.getDestino()))
			{
				return e;
			}
		}
		return null;
	}
	

	private static Vertice advance(Aresta e, LinkedList<Aresta> path)
	{
		path.addLast(e);
		return e.getDestino();
	}
	

	private static double augment(Grafo g, LinkedList<Aresta> path)
	{

		double delta = Double.MAX_VALUE; 
		

		for(Aresta e : path)
		{ 
			double residualCap = e.retornaCapacidadeResidual();
			if(residualCap < delta) delta = residualCap;
		}

		for(Aresta e : path)
		{

			double flow = e.getFluxo();
			flow += delta;
			e.setFluxo(flow);

			Aresta revEdge = null;
			for(Aresta incEdge : g.incident(e.getOrigem()))
			{
				if(incEdge.getOrigem().equals(e.getDestino())) 
				{
					revEdge = incEdge;
					break;
				}
			}
			
			double cap = revEdge.getCapacidade();
			cap += delta;
			revEdge.setCapacidade(cap);
			flow = revEdge.getFluxo();
			if(flow > 0)
			{
				flow -= delta;
				revEdge.setFluxo(flow);
			}
	
		}
		return delta;
	}
	

	private static Vertice retreat(Grafo g, Rotulos labels, Vertice i, LinkedList<Aresta> path)
	{

		int dMin = g.numeroNos() - 1;
		
		for(Aresta e : g.adjacent(i))
		{
			if(e.retornaCapacidadeResidual() > 0)
			{
				Vertice j = e.getDestino();
				int dj = labels.retornaRotulo(j);
				if(dj < dMin) dMin = dj;
			}
		}
		
		boolean flag = labels.setaRotulo(i, 1 + dMin); 
		
		Vertice predecessor;
		if(!flag)
		{

			if(!i.equals(g.getOrigem()))
			{
				Aresta e = path.removeLast();    
				predecessor = e.getOrigem();
			}
			else predecessor = g.getOrigem();
		}
		else predecessor = null;

		
		return predecessor;
	}
	
	private static void removeBackEdges(Grafo g, List<Aresta> backEdges)
	{
		for(Aresta e : backEdges)
		{
			g.removeEdge(e);
		}
	}	
}