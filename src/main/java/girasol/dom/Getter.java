package girasol.dom;

import girasol.runtime.Context;
import girasol.types.Value;

public class Getter extends Function {
	
	protected String name;
	
	public Getter(String name)
	{
		super(new String[0]);
		this.name = name;
	}

	public Value invoke(Context context, Value args[]) throws EvaluationException 
	{
		return context.getValue(name);
	}
	
}
