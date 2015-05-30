package girasol.dom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import girasol.generator.GeneratorException;
//import girasol.parser.Locator;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.types.NullValue;
import girasol.types.Value;

public class Node {

	private Node parent;
	private List<Node> children = null;
	//private Locator locator = null;
	
	//protected Node(Node parent)
	//{
	//	this.parent = parent;
	//}
	
	protected void ensureChildren()
	{
		if (children == null) {
			children = new ArrayList<Node>();
		}
	}
	
	public boolean canAddChild(Node child)
	{
		return true;
	}
	
	public void addChild(Node child)
	{
		ensureChildren();
		children.add(child);
	}
	
	public void addAllChildren(Collection<Node> children)
	{
		ensureChildren();
		this.children.addAll(children);
	}
	
	public List<Node> getChildren()
	{
		ensureChildren();
		return children;
	}
	
	public int getChildCount()
	{
		if (children == null) {
			return 0;
		}
		return children.size();
	}
	
	public Node getChildAt(int i)
	{
		return children.get(i);
	}
	
	public Node getFirstChild()
	{
		return children.get(0);
	}
	
	public Node getLastChild()
	{
		return children.get(children.size() - 1);
	}
	
	public Node setChildAt(int i, Node child)
	{
		return children.set(i, child);
	}

	public Node removeChildAt(int i)
	{
		return children.remove(i);
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		return evaluateChildren(ctx);
	}

	protected Value evaluateChildren(Context ctx) throws EvaluationException
	{
		if (children != null) {
			for(Node child : children) {
				child.evaluate(ctx);
				if (ctx.getJumpType() == 1) {
					break;
				}
			}
		}
		return NullValue.instance;
	}
	
	public String toString()
	{
		return getClass().getName();
	}
	
	/*
	public Locator getLocator()
	{
		return locator;
	}
	
	public void setLocator(Locator locator)
	{
		this.locator = locator;
	}
	*/
	
	public void printInfo()
	{
		printInfo(0);
	}
	
	public void printInfo(int depth)
	{
		for(int i = 0; i < depth; i++) {
			System.out.print("  ");
		}
		System.out.println(toString());
		if (children != null) {
			for(Node child : children) {
				child.printInfo(depth + 1);
			}
		}
	}

}
