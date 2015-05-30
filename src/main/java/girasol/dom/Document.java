package girasol.dom;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Document {
	
	private File file;
	private String name;
	private String extension;
	private String template;
	private Map<String,Expression> properties;
	private Map<String,Type> types;
	private Body body;
	
	public Document()
	{
		this.properties = new HashMap<String,Expression>();
		this.body = new Body();
	}

	public Document(String name, File file)
	{
		this();
		this.name = name;
		this.file = file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public File getFile() {
		return file;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	public Expression getProperty(String key) {
		return properties.get(key);
	}
	
	public void setProperty(String key, Expression value) {
		properties.put(key, value);
	}
	
	public Set<String> getPropertyKeys() {
		return properties.keySet();
	}
	
	public void setType(String key, Type type) {
		types.put(key, type);
	}
	
	public Type getType(String key) {
		return types.get(key);
	}

	public Body getBody() {
		return body;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}
	
}
