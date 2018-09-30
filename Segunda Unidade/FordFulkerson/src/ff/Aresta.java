package ff;
public class Aresta
{
	final Vertice origem;
	final Vertice destino; 
	double capacidade = 0.0d;
	double fluxo = 0.0d; 
	
	public Aresta(Vertice origem, Vertice destino, double capacidade)
	{
		this.origem = origem;
		this.destino = destino;
		this.capacidade = capacidade;
	}	

	public Aresta(Vertice origem, Vertice destino, double capacidade, double fluxo)
	{
		this.origem = origem;
		this.destino = destino;
		this.capacidade = capacidade;
		this.fluxo = fluxo;
	}
	
	public Aresta(Aresta e)
	{
		origem = e.origem;
		destino = e.destino;
		capacidade = e.capacidade;
		fluxo = e.fluxo;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o == null) return false;
		if(!(o instanceof Aresta)) return false;
		Aresta e = (Aresta)o;
		return e.origem.equals(origem) && e.destino.equals(destino) && e.fluxo == fluxo && e.capacidade == capacidade;
	}

	@Override
	public String toString(){
                           int fluxoNovo = (int) this.fluxo;
                           return ""+fluxoNovo+"";
	}		

	public double getFluxo()
	{
		return fluxo;
	}

	public void setFluxo(double fluxo)
	{
		if(fluxo > capacidade) throw new IllegalArgumentException("Impossivel calcular fluxo "
				+ "maxima capacidade");
		this.fluxo = fluxo;
	}

	public Vertice getOrigem()
	{
		return origem;
	}

	public Vertice getDestino()
	{
		return destino;
	}

	public double getCapacidade()
	{
		return capacidade;
	}
	
	public void setCapacidade(double capacidade)
	{
		this.capacidade = capacidade;
	}
	
	public double retornaCapacidadeResidual()
	{
		return capacidade - fluxo;
	}

}