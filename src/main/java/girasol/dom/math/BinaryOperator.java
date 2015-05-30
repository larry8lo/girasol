package girasol.dom.math;

import girasol.dom.EvaluationException;
import girasol.dom.Expression;
import girasol.runtime.Context;
import girasol.types.Value;

public abstract class BinaryOperator extends Expression {
	
	protected Expression arg0;
	protected Expression arg1;
	
	protected BinaryOperator(Expression arg0, Expression arg1)
	{
		this.arg0 = arg0;
		this.arg1 = arg1;
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException 
	{
		Value val0 = arg0.evaluate(ctx);
		Value val1 = arg1.evaluate(ctx);
		return operate(val0, val1);
	}
	
	public abstract Value operate(Value val0, Value val1) throws EvaluationException;

}
