package girasol.types;

import girasol.dom.EvaluationException;
import girasol.runtime.Frame;
import girasol.runtime.ObjectContext;
import girasol.runtime.Workspace;

/**
 * Base object that all GS objects derives from
 * 
 * @author larry
 */
public class BaseObject extends Value {
	
	protected Frame frame;
	protected ObjectContext context;
	protected Prototype prototype = basePrototype;
	protected int objectId;
	
	private static int objectIdSequence = 0;
	
	private static final Prototype basePrototype = new Prototype("Base");
	
	public static synchronized int getNextObjectId()
	{
		return objectIdSequence++;
	}
	
	/**
	 * Constructor
	 * @param prototype
	 */
	public BaseObject(Workspace workspace, Prototype prototype)
	{
		this.frame = new Frame();
		this.context = new ObjectContext(workspace, workspace != null ? workspace.getContext() : null, this, this.frame);
		this.prototype = prototype;
		this.objectId = getNextObjectId();
	}
	
	public Prototype getPrototype()
	{
		return prototype;
	}
	
	public ObjectContext getObjectContext() 
	{
		return context;
	}

	@Override
	/**
	 * Returns string representation
	 */
	public String asString() 
	{
		return prototype.name + "@" + objectId;
	}

	@Override
	/**
	 * Returns false because an object is a compound value
	 */
	public boolean isPrimitive() 
	{
		return false;
	}

	/**
	 * Returns the frame associated with this object
	 * @return
	 */
	public Frame getFrame() 
	{
		if (frame == null) {
			frame = new Frame();
		}
		return frame;
	}
	
	public boolean hasMember(String name)
	{
		if (frame.hasKey(name)) {
			return true;
		} else {
			return prototype.getValue(name) != null;
		}
	}

	/**
	 * Get value associated with frame
	 * @param key
	 * @return
	 */
	public Value getValue(String key) throws EvaluationException
	{
		if (frame.hasKey(key)) {
			// frame contains variable
			return frame.getValue(key);
		} else {
			// look for value in prototype
			Value value = prototype.getValue(key);
			if (value instanceof UndefinedValue) {
				if (prototype.getParent() != null) {
					value = prototype.getParent().getValue(key);
				}
			}
			if (value instanceof FunctionValue) {
				return new MethodValue(this, key, ((FunctionValue) value).getFunction());
			} 
			return value;
		}
	}
	
	public void setValue(String key, Value value) throws EvaluationException
	{
		frame.setValue(key, value);
	}
	
}
