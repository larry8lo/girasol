package girasol.types;

public class NullValue extends Value {
	
	public final static NullValue instance = new NullValue();

	@Override
	public String asString() 
	{
		return "null";
	}

	@Override
	public boolean isPrimitive() 
	{
		return true;
	}

}
