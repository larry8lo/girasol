package girasol.dom;

import girasol.runtime.Context;
import girasol.types.NumberValue;
import girasol.types.Value;

public class NumberLiteral extends Literal {
	
	private int value;
	
	public NumberLiteral(int value)
	{
		this.value = value;
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException 
	{
		return new NumberValue(value);
	}

}
