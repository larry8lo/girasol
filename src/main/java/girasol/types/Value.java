package girasol.types;

public abstract class Value {
	
	public abstract String asString();
	
	public abstract boolean isPrimitive();
	
	public String toString()
	{
		return asString();
	}
	
	public boolean equals(Object other)
	{
		return toString().equals(other.toString());
	}
	
	public int compareTo(Value other)
	{
		return toString().compareTo(other.toString());
	}
	
}
