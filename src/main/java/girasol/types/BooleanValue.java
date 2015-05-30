package girasol.types;

/**
 * Represents a boolean value
 * 
 * @author larry
 */
public class BooleanValue extends Value {
	
	protected boolean value;
	
	public BooleanValue(boolean value)
	{
		this.value = value;
	}
	
	public boolean get()
	{
		return this.value;
	}

	@Override
	public String asString() 
	{
		return Boolean.toString(value);
	}

	@Override
	/**
	 * 
	 */
	public boolean isPrimitive() 
	{
		return true;
	}

}
