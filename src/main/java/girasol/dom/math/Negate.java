package girasol.dom.math;

import girasol.dom.EvaluationException;
import girasol.dom.Expression;
import girasol.runtime.Context;
import girasol.types.NumberValue;
import girasol.types.Value;

public class Negate extends Expression {
	
	protected Expression expression;
	
	public Negate(Expression expression)
	{
		this.expression = expression;
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException 
	{
		Value value = expression.evaluate(ctx);
		if (value instanceof NumberValue) {
			return new NumberValue(-1 * ((NumberValue) value).getInt());
		}
		throw new EvaluationException("Invalid number", null);
	}

}
