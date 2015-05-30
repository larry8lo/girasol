package girasol.dom;

import java.util.ArrayList;
import java.util.List;

import girasol.runtime.Context;
import girasol.types.BooleanValue;
import girasol.types.NullValue;
import girasol.types.Value;

public class Switch extends Node {
	
	public static class Case {
		Expression condition;
		Node block;
		public Case(Expression condition, Node block) {
			this.condition = condition;
			this.block = block;
		}
	}
	
	private List<Case> cases = new ArrayList<Case>();
	private Node elseBlock;
	
	public Switch()
	{ }
	
	public void addCase(Expression condition, Node block)
	{
		Case caseBlock = new Case(condition, block);
		cases.add(caseBlock);
	}
	
	public List<Case> getCases()
	{
		return cases;
	}
	
	public Node getElseBlock() {
		return elseBlock;
	}

	public void setElseBlock(Node elseBlock) {
		this.elseBlock = elseBlock;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		for(Case c : cases) {
			Value value = c.condition.evaluate(ctx);
			if (value instanceof BooleanValue) {
				if (((BooleanValue) value).get()) {
					return c.block.evaluate(ctx);
				}
			}
		}
		if (elseBlock != null) {
			return elseBlock.evaluate(ctx);
		} else {
			return NullValue.instance;
		}
	}

}
