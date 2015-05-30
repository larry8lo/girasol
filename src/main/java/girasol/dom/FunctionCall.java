package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.types.ArrayObject;
import girasol.types.BaseObject;
import girasol.types.FunctionValue;
import girasol.types.MethodValue;
import girasol.types.NullValue;
import girasol.types.NumberValue;
import girasol.types.Value;

public class FunctionCall extends Expression {
	
	protected Expression function;
	protected Expression arguments[];
	
	protected FunctionCall()
	{
		// does nothing
	}
	
	public FunctionCall(Expression function, Expression arguments[])
	{
		this.function = function;
		this.arguments = new Expression[arguments.length];
		System.arraycopy(arguments, 0, this.arguments, 0, arguments.length);
	}
	
	public Expression getFunction() {
		return function;
	}
	public Expression[] getArguments() {
		return arguments;
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value f = function.evaluate(ctx);
		if (f instanceof FunctionValue) {
			// genuine function call
			FunctionValue fv = ((FunctionValue) f);
			Function fn = fv.getFunction();
			//if (fn.getArgumentCount() != arguments.length) {
			//	throw new GeneratorException("Invalid number of arguments");
			//}
			Value args[] = new Value[arguments.length];
			for(int i = 0; i < arguments.length; i++) {
				args[i] = arguments[i].evaluate(ctx);
			}
			BaseObject object = null;
			if (fv instanceof MethodValue) {
				object = ((MethodValue) fv).getObject();
			}
			Value r = fn.invoke(ctx, object, args);
			return r;
		}
		
		return NullValue.instance;
	}
	
}
