package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.types.NullValue;
import girasol.types.Value;

public class Echo extends Node {
	
	protected Expression expression;
	
	public Echo(Expression expression)
	{
		this.expression = expression;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value value = expression.evaluate(ctx);
		try {
			ctx.getOutput().write(value);
			ctx.getOutput().flush();
		} catch(Exception e) {
			throw new EvaluationException(e.toString(), e);
		}
		return NullValue.instance;
	}

}
