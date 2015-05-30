package girasol.types;

public class UndefinedValue extends Value {

	public final static UndefinedValue instance = new UndefinedValue();

	@Override
	public String asString() {
		return "undefined";
	}

	@Override
	public boolean isPrimitive() {
		return true;
	}
	
	@Override
	public boolean equals(Object other)
	{
		return other instanceof UndefinedValue;
	}

}
