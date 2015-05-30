package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.runtime.RuntimeException;
import girasol.types.Value;

public class Bold extends Node {
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		try {
			ctx.getOutput().write("<b>");
			Value v = super.evaluateChildren(ctx);
			ctx.getOutput().write("</b>");
			return v;
		} catch(RuntimeException e) {
			throw new EvaluationException(e.getMessage(), e);
		}
	}

}
