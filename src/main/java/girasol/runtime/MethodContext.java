package girasol.runtime;

import girasol.types.BaseObject;

public class MethodContext extends Context {
	
	public MethodContext(Context context, BaseObject object)
	{
		super(context);
		frames.clear();
		frames.push(object.getFrame());
		frames.push(new Frame());
	}

}
