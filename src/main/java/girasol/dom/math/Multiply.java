package girasol.dom.math;

import girasol.dom.EvaluationException;
import girasol.dom.Expression;
import girasol.types.NumberValue;
import girasol.types.Value;

public class Multiply extends BinaryOperator {

	public Multiply(Expression arg0, Expression arg1)
	{
		super(arg0, arg1);
	}

	@Override
	public Value operate(Value val0, Value val1) throws EvaluationException 
	{
		try {
			NumberValue n0 = (NumberValue) val0;
			NumberValue n1 = (NumberValue) val1;
			return new NumberValue(n0.getInt() * n1.getInt());
		} catch(Exception e) {
			throw new EvaluationException("Incorrect number", null);
		}
	}

}
