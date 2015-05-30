package girasol.runtime;

import girasol.types.BaseObject;

public class ObjectContext extends Context {
	
	private BaseObject object;

	public ObjectContext(Workspace workspace, BaseObject object, Frame frame) 
	{
		super(workspace, null, null, frame);
		this.object = object;
	}
	
	public ObjectContext(Workspace workspace, Context parent, BaseObject object, Frame frame) 
	{
		super(workspace, parent, null, frame);
		this.object = object;
	}
	
	public BaseObject getObject()
	{
		return this.object;
	}

}
