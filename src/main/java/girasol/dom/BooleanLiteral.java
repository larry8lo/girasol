package girasol.dom;

import girasol.runtime.Context;
import girasol.types.BooleanValue;
import girasol.types.Value;

public class BooleanLiteral extends Literal {
	
	protected boolean value;
	
	public BooleanLiteral(boolean value)
	{
		this.value = value;
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException 
	{
		return new BooleanValue(value);
	}

}
