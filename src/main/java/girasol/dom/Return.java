package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.types.NullValue;
import girasol.types.Value;

public class Return extends Node {
	
	protected Expression returnValue;
	
	public Return(Expression returnValue)
	{
		this.returnValue = returnValue;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value rv = returnValue.evaluate(ctx);
		ctx.raiseReturnJump(rv);
		return NullValue.instance;
	}

}
