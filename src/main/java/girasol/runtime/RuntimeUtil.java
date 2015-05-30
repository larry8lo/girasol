package girasol.runtime;

import girasol.dom.Function;
import girasol.types.FunctionValue;

public class RuntimeUtil {

	public static void addFunction(Context ctx, String name, Function function)
	{
		ctx.createValue(name, new FunctionValue(ctx, function));
	}
	
}
