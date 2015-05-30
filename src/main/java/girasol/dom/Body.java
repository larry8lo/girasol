package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.types.Value;

public class Body extends Node {

	public Value evaluate(Context ctx) throws EvaluationException
	{
		Context ctx2 = new Context(ctx);
		return super.evaluate(ctx2);
	}

}
