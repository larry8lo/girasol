package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.runtime.RuntimeException;
import girasol.types.NullValue;
import girasol.types.Value;

public class LineBreak extends Text {

	public LineBreak() 
	{
		super("\n");
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		try {
			Value format = ctx.getValue("%%FORMAT%%");
			if (format != null) {
				if ("none".equals(format.asString())) {
					return super.evaluate(ctx);
				}
			}
			ctx.getOutput().write("<br />\n");
		} catch(RuntimeException e) {
			throw new EvaluationException(e.getMessage(), e);
		}
		return NullValue.instance;
	}

}
