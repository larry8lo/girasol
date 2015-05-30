package girasol.runtime;

import java.util.LinkedList;

import girasol.dom.Page;
import girasol.types.UndefinedValue;
import girasol.types.Value;

public class Context {
	
	protected Workspace workspace;
	protected Context parent = null;
	protected LinkedList<Frame> frames = new LinkedList<Frame>();
	protected int jumpType = -1;
	protected Output output = null;
	
	public Context()
	{
		this(null, null, null, new Frame());
	}
	
	public Context(Workspace workspace)
	{
		this(workspace, null, null, new Frame());
	}
	
	public Context(Context parent)
	{
		this(parent.workspace, parent, parent.output, new Frame());
	}
	
	public Context(Workspace workspace, Context parent)
	{
		this(workspace, parent, parent.output, new Frame());
	}
	
	public Context(Context parent, Output output)
	{
		this(parent.workspace, parent, output, new Frame());
	}
	
	protected Context(Workspace workspace, Context parent, Output output, Frame frame)
	{
		this.workspace = workspace;
		this.parent = parent;
		this.output = output;
		this.frames.add(frame);
	}
	
	public Workspace getWorkspace()
	{
		return workspace;
	}
	
	public Page getPage(String name)
	{
		return getWorkspace().getPageByName(name);
	}
	
	protected void pushFrame(Frame frame)
	{
		frames.push(frame);
	}
	
	public void pushFrame()
	{
		frames.push(new Frame());
	}
	
	public void popFrame()
	{
		frames.pop();
	}
	
	public void createValue(String key, Value value)
	{
		frames.peek().setValue(key, value);
	}
	
	public void setValue(String key, Value value)
	{
		if (!setValueIfExists(key, value)) {
			createValue(key, value);
		}
	}
	
	protected boolean setValueIfExists(String key, Value value)
	{
		for(Frame frame : frames) {
			if (frame.hasKey(key)) {
				frame.setValue(key, value);
				return true;
			}
		}
		if (parent != null) {
			return parent.setValueIfExists(key, value);
		}
		return false;
	}
	
	public Value getValue(String key)
	{
		for(Frame frame : frames) {
			if (frame.hasKey(key)) {
				return frame.getValue(key);
			}
		}
		if (parent != null) {
			return parent.getValue(key);
		}
		return UndefinedValue.instance;
	}
	
	public Output getOutput() 
	{
		if (output != null) {
			return output;
		} else {
			return Application.getApplication().getOutput();
		}
	}
	
	public void raiseReturnJump(Value value)
	{
		jumpType = 1;
		createValue("%%RETURN%%", value);
	}
	
	public int getJumpType()
	{
		return jumpType;
	}

}
