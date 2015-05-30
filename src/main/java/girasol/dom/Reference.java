package girasol.dom;

import girasol.runtime.Context;
import girasol.types.Value;

public abstract class Reference extends Expression {

	public abstract void set(Context ctx, Value value) throws EvaluationException;
	
}
