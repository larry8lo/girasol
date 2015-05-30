package girasol.runtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import girasol.dom.EvaluationException;
import girasol.dom.Function;
import girasol.dom.Page;
import girasol.parser.Parser;
import girasol.types.ArrayObject;
import girasol.types.BaseObject;
import girasol.types.Value;

/**
 * Represents a set of pages
 * 
 * @author larry
 */
public class Workspace {

	private File workingDirectory;
	private Map<String,Page> pages = new HashMap<String,Page>();
	private WorkspaceContext context;
	
	public Workspace()
	{
		context = new WorkspaceContext(this);
		initFunctions();
	}
	
	protected void initFunctions()
	{
		RuntimeUtil.addFunction(context, "pages", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				Page pages[] = context.getWorkspace().getAllPages();
				ArrayObject array = new ArrayObject();
				for(Page page : pages) {
					array.add(page.getObject());
				}
				return array;
			}
		});
	}
	
	public Context getContext()
	{
		return context;
	}

	public Page getPageByName(String name)
	{
		Page pg = pages.get(name);
		if (pg == null) {
			pg = loadPage(name);
			if (pg != null) {
				//context.createValue(name, pg.getObject());
				pages.put(name, pg);
			}
		}
		return pg;
	}
	
	public Page[] getAllPages()
	{
		File dir = getWorkingDirectory();
		List<Page> allPages = new LinkedList<Page>();
		findPagesInDirectory(dir, allPages);
		Page pages[] = new Page[allPages.size()];
		allPages.toArray(pages);
		return pages;
	}
	
	protected void findPagesInDirectory(File dir, List<Page> allPages)
	{
		String fileNames[] = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".gsp");
			}
		});
		
		for(String fileName : fileNames) {
			String name = fileName.substring(0, fileName.length() - 4);
			Page page = getPageByName(name);
			if (page != null) {
				allPages.add(page);
			}
		}
	}
	
	public Page loadPage(String name)
	{
		File file = new File(getWorkingDirectory(), name + ".gsp");
		if (file.exists() && file.canRead()) {
			Page page = new Page(this, name, file);
			return page;
		}
		return null;
	}
	
	public void loadPage(Page page) throws RuntimeException
	{
		try {
			//Lexer lexer = new Lexer(page.getFile());
			Parser parser = new Parser(new FileInputStream(page.getFile()));
			parser.start(page.getDocument());
			//parser.parse(lexer, page.getDocument());
			//lexer.close();
		} catch(Exception e) {
			//System.err.println("Error while loading @" + page.getName());
			throw new RuntimeException("Error while loading @" + page.getName() + ": " + e.getMessage(), e);
		}
	}
	
	public void setWorkingDirectory(File workingDirectory)
	{
		this.workingDirectory = workingDirectory;
	}
	
	public File getWorkingDirectory()
	{
		return workingDirectory;
	}

}
