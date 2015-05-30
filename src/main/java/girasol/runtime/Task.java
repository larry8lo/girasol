package girasol.runtime;

import girasol.dom.Node;
import girasol.types.NullValue;
import girasol.types.Value;

public class Task {
	
	private Context context;
	private Node root;
	
	public Task(Context context, Node root)
	{
		this.context = context;
		this.root = root;
	}
	
	public Value run() throws RuntimeException
	{
		
		
		return NullValue.instance;
	}

}
