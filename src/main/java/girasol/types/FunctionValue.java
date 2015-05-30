package girasol.types;

import girasol.dom.Function;
import girasol.runtime.Context;

public class FunctionValue extends Value {
	
	protected Context context;
	protected Function function;
	
	public FunctionValue(Context context, Function function)
	{
		this.context = context;
		this.function = function;
	}
	
	public Context getContext()
	{
		return this.context;
	}
	
	public Function getFunction()
	{
		return this.function;
	}
	
	@Override
	public String asString() 
	{
		return "f(...)";
	}

	@Override
	public boolean isPrimitive() 
	{
		return false;
	}

}
