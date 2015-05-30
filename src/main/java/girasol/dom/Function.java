package girasol.dom;

import java.util.List;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.Frame;
import girasol.runtime.MethodContext;
import girasol.types.BaseObject;
import girasol.types.Value;

public class Function extends Node {
	
	protected String arguments[];
	
	public Function(String arguments[])
	{
		this.arguments = arguments;
	}
	
	public Function(String arguments[], Node body)
	{
		this(arguments);
		addChild(body);
	}
	
	public Function(String arguments[], List<Node> body)
	{
		this(arguments);
		addAllChildren(body);
	}
	
	public int getArgumentCount()
	{
		return this.arguments.length;
	}
	
	public Value invoke(Context context, BaseObject obj, Value args[]) throws EvaluationException
	{
		Context ctx = obj != null ? new MethodContext(context, obj) : new Context(context);
		
		// set input parameters
		for(int i = 0; i < arguments.length; i++) {
			if (i < args.length) {
				ctx.createValue(arguments[i], args[i]);
			}
		}
		
		// evaluate body
		evaluateChildren(ctx);
		Value value = ctx.getValue("%%RETURN%%");
		
		ctx.popFrame();
		
		return value;
	}

}
