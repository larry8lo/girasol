package girasol.dom;

import girasol.runtime.Context;
import girasol.types.FragmentObject;
import girasol.types.PageObject;
import girasol.types.Value;

public class Include extends Node {
	
	private Expression page;
	
	public Include(Expression page)
	{
		this.page = page;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		// resolve linkage
		Value dst = page.evaluate(ctx);
		
		if (dst instanceof PageObject) {
			// TODO introduce dynamic scope
			Page page = ((PageObject) dst).getPage();
			try {
				page.getContext();
			} catch(RuntimeException e) {
				throw new EvaluationException(e.getMessage(), e);
			}
			return page.getDocument().getBody().evaluate(ctx);
		} else if (dst instanceof FragmentObject) {
			return ((FragmentObject) dst).getFragment().evaluateChildren(ctx);
		} else {
			throw new EvaluationException("Invalid include \"" + dst.asString() + "\"", null);
		}
	}

}
