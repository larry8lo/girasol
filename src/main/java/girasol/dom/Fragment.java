package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.types.FragmentObject;

public class Fragment extends Node {
	
	private String name;
	
	public Fragment(String name)
	{
		this.name = name;
	}

	public void generate(Context ctx) throws GeneratorException
	{
		ctx.createValue(name, new FragmentObject(this));
	}
}
