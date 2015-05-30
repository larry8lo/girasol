package girasol.types;

public class NumberValue extends Value {
	
	private int value;
	
	public NumberValue(int value)
	{
		this.value = value;
	}

	@Override
	public String asString() 
	{
		return String.valueOf(value);
	}
	
	public int getInt()
	{
		return value;
	}

	@Override
	public boolean isPrimitive() 
	{
		return true;
	}
	
	public boolean equals(Object other)
	{
		if (other instanceof NumberValue) {
			return value == ((NumberValue) other).value;
		} else {
			return super.equals(other);
		}
	}
	
	public int compareTo(Value other)
	{
		if (other instanceof NumberValue) {
			return ((NumberValue) other).value - value;
		} else {
			return super.compareTo(other);
		}
	}

}
