package girasol.types;

import java.io.StringWriter;
import java.io.Writer;

import girasol.dom.EvaluationException;
import girasol.dom.Function;
import girasol.dom.Page;
import girasol.runtime.Context;
import girasol.runtime.Output;
import girasol.util.HTMLFilterWriter;
import girasol.util.JSONWriter;
import girasol.util.SnippetWriter;

/**
 * Represents a page as an object
 * 
 * @author larry
 */
public class PageObject extends BaseObject {
	
	protected Page page;
	
	private static Prototype prototype = new Prototype("Page")
		.addFunction("getLink", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				Page page = ((PageObject) object).getPage();
				return new StringObject(page.getOutputName());
			}
		})
		.addFunction("getSnippet", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				Page page = ((PageObject) object).getPage();
				StringWriter sw = new StringWriter();
				Writer writer = new HTMLFilterWriter(new SnippetWriter(new JSONWriter(sw), 1000));
				final Output output = new Output(writer);
				page.getDocument().getBody().evaluate(new Context(context, output));
				return new StringObject(sw.toString());
			}
		})
	;
	
	public PageObject(Page page)
	{
		super(page.getWorkspace(), new Prototype(prototype, page.getName()));
		this.page = page;
	}

	public Page getPage()
	{
		return page;
	}
	
	public Value getValue(String key) throws EvaluationException
	{
		page.getContext();
		return super.getValue(key);
	}
	
	public void setValue(String key, Value value) throws EvaluationException
	{
		page.getContext();
		super.setValue(key, value);
	}

	@Override
	public String asString() {
		return page != null ? ("@" + page.getDocument().getName()) : "";
	}

	@Override
	public boolean isPrimitive() {
		return false;
	}

}
