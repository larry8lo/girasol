package girasol.types;

import girasol.dom.Function;
import girasol.runtime.Context;

public class MethodValue extends FunctionValue {
	
	protected BaseObject object;
	protected String member;
	
	public MethodValue(BaseObject object, String member, Function function)
	{
		super(object.getObjectContext(), function);
		this.object = object;
		this.member = member;
	}
	
	@Override
	public String asString() {
		return object.asString() + "." + member + "(..)";
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

	public BaseObject getObject() {
		return object;
	}

	public String getMember() {
		return member;
	}

}
