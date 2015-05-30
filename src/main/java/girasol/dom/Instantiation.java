package girasol.dom;

import java.util.ArrayList;
import java.util.List;

import girasol.runtime.Context;
import girasol.types.Prototype;
import girasol.types.UserObject;
import girasol.types.Value;

public class Instantiation extends Expression {
	
	protected String type;
	protected List<Expression> args;
	
	public Instantiation()
	{
		this.type = "Object";
		this.args = new ArrayList<Expression>();
	}
	
	public Instantiation(String type, List<Expression> args)
	{
		this.type = type;
		this.args = new ArrayList<Expression>(args.size());
		this.args.addAll(args);
	}

	@Override
	public Value evaluate(Context ctx) throws EvaluationException 
	{
		UserObject userObject = new UserObject(ctx.getWorkspace(), new Prototype(type));
		
		
		
		return userObject;
		//return null;
	}

}
