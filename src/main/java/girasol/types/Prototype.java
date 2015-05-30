package girasol.types;

import girasol.dom.Function;
import girasol.runtime.Frame;

public class Prototype {
	
	public final String name;
	protected Prototype parent = null;
	protected Frame frame = new Frame();
	
	public Prototype(String name)
	{
		this.parent = null;
		this.name = name;
	}
	
	public Prototype(Prototype parent, String name)
	{
		this.parent = parent;
		this.name = name;
	}
	
	public Prototype addFunction(String name, Function function)
	{
		setValue(name, new FunctionValue(null, function));
		return this;
	}
	
	public Prototype getParent()
	{
		return parent;
	}
	
	public Value getValue(String key)
	{
		return frame.getValue(key);
	}
	
	public void setValue(String key, Value value)
	{
		frame.setValue(key, value);
	}
	
}
