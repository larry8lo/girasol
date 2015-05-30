package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.types.ArrayObject;
import girasol.types.NullValue;
import girasol.types.Value;

public class Foreach extends Node {
	
	protected Variable elemVar;
	protected Expression array;
	
	public Foreach(Variable elemVar, Expression array)
	{
		this.elemVar = elemVar;
		this.array = array;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value data = array.evaluate(ctx);
		if (data instanceof ArrayObject) {
			ArrayObject arrayData = (ArrayObject) data;
			for(int i = 0; i < arrayData.length(); i++) {
				ctx.pushFrame();
				Value value = arrayData.get(i);
				ctx.createValue(elemVar.getReference(), value);
				super.evaluateChildren(ctx);
				ctx.popFrame();
			}
		}
		return NullValue.instance;
	}

}
