package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.types.NullValue;
import girasol.types.Value;

public class VariableDeclaration extends Expression {
	
	private String name;
	private Expression value;
	
	public VariableDeclaration(String name)
	{
		this(name, null);
	}
	
	public VariableDeclaration(String name, Expression value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		Value data = value != null ? value.evaluate(ctx) : NullValue.instance;
		ctx.createValue(name, data);
		return data;
	}
	
}
