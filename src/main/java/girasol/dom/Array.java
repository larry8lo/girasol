package girasol.dom;

import java.util.List;

import girasol.runtime.Context;
import girasol.types.ArrayObject;
import girasol.types.Value;

public class Array extends Expression {

	@Override
	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value array[] = new Value[getChildCount()];
		int i = 0;
		for(Node child : getChildren()) {
			if (child instanceof Expression) {
				Expression elem = (Expression) child;
				array[i++] = elem.evaluate(ctx);
			}
		}
		return new ArrayObject(array);
	}
	
	public void addAll(List<Expression> list)
	{
		for(Expression expr : list) {
			addChild(expr);
		}
	}

}
