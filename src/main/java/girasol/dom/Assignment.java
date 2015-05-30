package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.types.Value;

public class Assignment extends Expression {
	
	protected Reference variable;
	protected Expression value;
	
	public Assignment(Reference variable)
	{
		this.variable = variable;
	}
	
	public Assignment(Reference variable, Expression value)
	{
		this.variable = variable;
		this.value = value;
	}

	public Reference getVariable() {
		return variable;
	}

	public void setName(Variable variable) {
		this.variable = variable;
	}

	public Expression getValue() {
		return value;
	}

	public void setValue(Expression value) {
		this.value = value;
	}
	
	public void addChild(Node child)
	{
		if (child instanceof Expression) {
			setValue((Expression) child);
		}
		super.addChild(child);
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value data = value.evaluate(ctx);
		variable.set(ctx, data);
		return data;
	}
	
}
