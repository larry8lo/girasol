package girasol.dom;

import girasol.runtime.Context;
import girasol.types.ArrayObject;
import girasol.types.BooleanValue;
import girasol.types.NullValue;
import girasol.types.Value;

public class While extends Node {

	protected Expression condition;
	
	public While(Expression condition)
	{
		this.condition = condition;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		while (true) {
			Value value = condition.evaluate(ctx);
			if (value instanceof BooleanValue) {
				if (!((BooleanValue) value).get()) {
					break;
				}
				ctx.pushFrame();
				super.evaluateChildren(ctx);
				ctx.popFrame();
			} else {
				break;
			}
		}
		return NullValue.instance;
	}

}
