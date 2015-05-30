package girasol.dom;

import girasol.runtime.Context;
import girasol.types.BaseObject;
import girasol.types.NullValue;
import girasol.types.PageObject;
import girasol.types.Value;

public class MemberReference extends Reference {
	
	protected Expression container;
	protected Variable member;
	
	public MemberReference(Expression container, Variable member)
	{
		this.container = container;
		this.member = member;
	}
	
	public MemberReference(Expression container, String member)
	{
		this(container, new Variable(member));
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		Value obj = container.evaluate(ctx);
		
//		if (obj instanceof PageObject) {
//			Page page = ((PageObject) obj).getPage();
//			String ref = member.getReference();
//			return page.getContext().getValue(ref);
//		}

		if (obj instanceof BaseObject) {
			BaseObject basObj = (BaseObject) obj;
			if (basObj.hasMember(member.getReference())) {
				return basObj.getValue(member.getReference());
			}
		}

		return NullValue.instance;
	}

	public void set(Context ctx, Value value) throws EvaluationException
	{
		Value obj = container.evaluate(ctx);
		if (obj instanceof BaseObject) {
			BaseObject absObj = (BaseObject) obj;
			absObj.setValue(member.getReference(), value);
		}
	}
	
}
