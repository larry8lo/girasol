package girasol.dom;

import girasol.runtime.Context;
import girasol.types.NullValue;
import girasol.types.Value;

/**
 * Reference to a page, of the form @page or @pkg.page
 * @author larry
 */
public class PageReference extends Reference {

	private String reference;
	
	public PageReference(String reference)
	{
		this.reference = reference;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	@Override
	public Value evaluate(Context ctx) throws EvaluationException
	{
		// reference to the local page scope
		if (reference.equals("")) {
			return ctx.getValue(reference);
		}
		
		Page page = ctx.getPage(reference);
		return page != null ? page.getObject() : NullValue.instance;
	}
	
	public void set(Context ctx, Value value) throws EvaluationException
	{
		throw new EvaluationException("Cannot set any value in a page", null);
	}

}
