package girasol.dom;

import girasol.runtime.Context;
import girasol.types.Value;

public abstract class Expression extends Node {
	
	public abstract Value evaluate(Context ctx) throws EvaluationException;
	
}
