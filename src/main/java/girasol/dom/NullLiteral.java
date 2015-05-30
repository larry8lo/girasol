package girasol.dom;

import girasol.runtime.Context;
import girasol.types.NullValue;
import girasol.types.Value;

public class NullLiteral extends Literal {
	
	public static NullLiteral instance = new NullLiteral();

	@Override
	public Value evaluate(Context ctx) throws EvaluationException 
	{
		return NullValue.instance;
	}

}
