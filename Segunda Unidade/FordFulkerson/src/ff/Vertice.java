package ff;
public class Vertice
{
	final int id;

	public Vertice(int id)
	{
		this.id = id;
	}
	
	public Vertice(Vertice n)
	{
		id = n.getId();

	}	

	public int getId()
	{
		return id;
	}	


	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) return false;
		if(!(obj instanceof Vertice)) return false;
		Vertice n = (Vertice)obj;
		return n.id == id;
	}

	@Override
	public int hashCode()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return ""+id;
	}	
	
	
}
