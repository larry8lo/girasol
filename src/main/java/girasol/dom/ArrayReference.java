package girasol.dom;

import girasol.runtime.Context;
import girasol.types.ArrayObject;
import girasol.types.NullValue;
import girasol.types.NumberValue;
import girasol.types.UserObject;
import girasol.types.Value;

public class ArrayReference extends Reference {
	
	private Expression array;
	private Expression index;
	
	public ArrayReference(Expression array, Expression index)
	{
		this.array = array;
		this.index = index;
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException 
	{
		Value ary = array.evaluate(ctx);
		Value idx = index.evaluate(ctx);
		if (ary instanceof ArrayObject && idx instanceof NumberValue) {
			ArrayObject ao = (ArrayObject) ary;
			int i = ((NumberValue) idx).getInt();
			return ao.get(i);
		} else if (ary instanceof UserObject) {
			return ((UserObject) ary).getValue(idx.asString());
		}
		return NullValue.instance;
	}
	
	public void set(Context ctx, Value value) throws EvaluationException
	{
		Value ary = array.evaluate(ctx);
		Value idx = index.evaluate(ctx);
		
		if (ary instanceof ArrayObject && idx instanceof NumberValue) {
			ArrayObject ao = (ArrayObject) ary;
			int i = ((NumberValue) idx).getInt();
			ao.set(i, value);
		} else if (ary instanceof UserObject) {
			((UserObject) ary).setValue(idx.asString(), value);
		}
	}

}
