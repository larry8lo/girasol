package girasol.dom;

import java.io.IOException;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.types.BooleanValue;
import girasol.types.NullValue;
import girasol.types.Value;

public class If extends Node {
	
	protected Expression condition;
	protected Node thenBlock;
	protected Node elseBlock;
	
	public If()
	{
		thenBlock = new Node();
		elseBlock = new Node();
	}

	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
	}

	public Node getThenBlock() {
		return thenBlock;
	}

	public Node getElseBlock() {
		return elseBlock;
	}

	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value value = condition.evaluate(ctx);
		if (value instanceof BooleanValue) {
			if (((BooleanValue) value).get()) {
				return thenBlock.evaluate(ctx);
			} else {
				return elseBlock.evaluate(ctx);
			}
		} else {
			return NullValue.instance;
		}
	}

}
