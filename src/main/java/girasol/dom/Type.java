package girasol.dom;

import java.util.HashMap;
import java.util.Map;

public class Type extends Node {
	
	protected String name;
	protected Map<String,Expression> variables;
	protected Map<String,Function> functions;
	
	public Type(String name)
	{
		this.name = name;
		this.variables = new HashMap<String,Expression>();
		this.functions = new HashMap<String,Function>();
	}
	
	public void setVariable(String name, Expression value)
	{
		this.variables.put(name, value);
	}
	
	public Expression getVariable(String name)
	{
		return this.variables.get(name);
	}
	
	public void setFunction(String name, Function function)
	{
		functions.put(name, function);
	}
	
	public Function getFunction(String name)
	{
		return functions.get(name);
	}

}
