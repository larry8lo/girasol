package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.types.NullValue;
import girasol.types.PageObject;
import girasol.types.UndefinedValue;
import girasol.types.Value;

public class Content extends Node {

	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value v = ctx.getValue("__target__");
		if (v != null && v instanceof PageObject) {
			PageObject p = (PageObject) v;
			return p.getPage().getDocument().getBody().evaluate(ctx);
		} else {
			return NullValue.instance;
		}
	}

}
