package girasol.dom;

import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.runtime.RuntimeException;
import girasol.types.NullValue;
import girasol.types.Value;

public class Text extends Node {
	
	protected String text;
	
	public Text(String text)
	{
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		try {
			ctx.getOutput().write(text);
		} catch(RuntimeException e) {
			throw new EvaluationException(e.getMessage(), e);
		}			
		return NullValue.instance;
	}

}
