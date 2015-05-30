package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.runtime.RuntimeException;
import girasol.types.Value;

public class Italic extends Node {

	public Value evaluate(Context ctx) throws EvaluationException
	{
		try {
			ctx.getOutput().write("<i>");
			Value v = super.evaluate(ctx);
			ctx.getOutput().write("</i>");
			return v;
		} catch(RuntimeException e) {
			throw new EvaluationException(e.getMessage(), e);
		}
	}

}
