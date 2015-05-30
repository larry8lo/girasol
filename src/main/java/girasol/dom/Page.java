package girasol.dom;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

import girasol.runtime.Application;
import girasol.runtime.Context;
import girasol.runtime.ObjectContext;
import girasol.runtime.Output;
import girasol.runtime.RuntimeException;
import girasol.runtime.RuntimeUtil;
import girasol.runtime.Workspace;
import girasol.types.BaseObject;
import girasol.types.DataUtil;
import girasol.types.PageObject;
import girasol.types.Prototype;
import girasol.types.StringObject;
import girasol.types.Value;
import girasol.util.HTMLFilterWriter;
import girasol.util.JSONWriter;
import girasol.util.SnippetWriter;

public class Page {

	private Workspace workspace;
	private File file;
	private String name;
	private Document document = null;
	private PageObject object = null;
	private Context context = null;
	
	private boolean initialized = false;
	
	public Page(Workspace workspace, String name, File file)
	{
		this.workspace = workspace;
		this.name = name;
		this.file = file;
		this.object = new PageObject(this);
		this.document = new Document(name, file);
	}
	
	public Workspace getWorkspace() {
		return workspace;
	}
	
	public File getFile() {
		return file;
	}
	
	public String getName() {
		return name;
	}

	public Document getDocument() {
		return document;
	}
	
	public PageObject getObject() 
	{
		return object;
	}
	
	public Context getContext() throws EvaluationException
	{
		ensureInitialized();
		return context;
	}
	
	public String getOutputName() throws EvaluationException
	{
		ensureInitialized();
		String extension = document.getExtension();
		if (extension == null)	
			extension = "html";
		return name + "." + extension;
	}
	
	private void load() throws EvaluationException
	{
		try {
			workspace.loadPage(this);
		} catch(RuntimeException e) {
			throw new EvaluationException(e.getMessage(), e);
		}
	}
	
	private void initialize() throws EvaluationException
	{
		context = object.getObjectContext();

		if (document.getTemplate() != null) {
			Page templatePage = workspace.getPageByName(document.getTemplate());
			context = new ObjectContext(workspace, templatePage.getContext(), object, object.getFrame());
			context.createValue("_template", templatePage.getObject());
		}
		
		context.createValue("_name", DataUtil.asData(document.getName()));
		context.createValue("", object);
		
		for(String key : document.getPropertyKeys()) {
			Expression expr = document.getProperty(key);
			Value value = expr.evaluate(context);
			context.createValue(key, value);
		}
	}
	
	private void ensureInitialized() throws EvaluationException
	{
		if (!initialized) {
			load();
			initialize();
			initialized = true;
		}
	}
	
	public void generate(Output output) throws RuntimeException, EvaluationException
	{
		ensureInitialized();
		
		Application.getApplication().setOutput(output);
		
		try {
			Context rtCtx = new Context(context);
			String template = document.getTemplate();
			if (template != null) {
				rtCtx.createValue("__target__", object);
				Page templatePage = workspace.getPageByName(template);
				if (templatePage != null) {
					templatePage.getDocument().getBody().evaluate(rtCtx);
				}
			} else {
				document.getBody().evaluate(rtCtx);
			}
			output.flush();
		} finally {
			// clean up
			Application.getApplication().clearOutput();
		}
	}
	
	public Prototype getPrototype()
	{
		return object.getPrototype();
	}
	
}
