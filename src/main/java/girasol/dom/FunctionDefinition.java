package girasol.dom;

import girasol.runtime.Context;
import girasol.types.BaseObject;
import girasol.types.FunctionValue;
import girasol.types.MethodValue;
import girasol.types.PageObject;
import girasol.types.Value;

public class FunctionDefinition extends Expression {
	
	protected String name;
	protected String arguments[];
	
	public FunctionDefinition(String name, String arguments[])
	{
		this.name = name;
		this.arguments = arguments;
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException 
	{
		Value obj = ctx.getValue("");
		PageObject pgObj = (obj != null && obj instanceof PageObject) ? (PageObject) obj : null;
		
		Function function = new Function(arguments, getChildren());
		if (pgObj != null && name != null) {
			pgObj.getPrototype().addFunction(name, function);
			MethodValue mv = new MethodValue(pgObj, name, function);
			return mv;
		} else {
			FunctionValue fd = new FunctionValue(ctx, function);
			if (name != null) {
				ctx.createValue(name, fd);
			}
			return fd;
		}
	}
	
}
