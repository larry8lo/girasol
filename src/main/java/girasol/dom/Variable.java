package girasol.dom;

import girasol.runtime.Context;
import girasol.types.Value;

public class Variable extends Reference {
	
	private String reference;
	
	public Variable(String reference)
	{
		this.reference = reference;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		return ctx.getValue(reference);
	}
	
	public void set(Context ctx, Value value) throws EvaluationException
	{
		ctx.setValue(reference, value);
	}
	
}
