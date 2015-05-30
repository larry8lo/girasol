package girasol.dom;

import girasol.runtime.Context;
import girasol.types.Value;

public class Format extends Node {
	
	private Expression format;
	
	public Format(Expression format)
	{
		this.format = format;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		ctx.pushFrame();
		ctx.createValue("%%FORMAT%%", format.evaluate(ctx));
		Value value = super.evaluate(ctx);
		ctx.popFrame();
		return value;
	}

	public Expression getFormat() {
		return format;
	}

	public void setFormat(Expression format) {
		this.format = format;
	}

}
