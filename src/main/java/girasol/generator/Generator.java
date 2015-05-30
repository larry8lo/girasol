package girasol.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;

import girasol.dom.Page;
import girasol.runtime.Output;
import girasol.runtime.Workspace;
import girasol.util.ArgumentsParser;

public class Generator {
	
	protected final static String[] argsSpec = {
		"-D",
		"-v",
		"-a",
		"-l"
	};

	public static void main(String args[])
	{
		Workspace workspace = new Workspace();
		ArgumentsParser ap = new ArgumentsParser();
		ap.parseArguments(args, argsSpec);
		workspace.setWorkingDirectory(new File(ap.getOptionValue("-D", ".")));
		
		String files[];
		if (ap.hasOption("-a")) {
			files = getAllPages(workspace.getWorkingDirectory());
		} else {
			files = ap.getArguments();
		}
		
		boolean generate = true;
		if (ap.hasOption("-l")) {
			generate = false;
		}
		
		for(String arg : files) {
			try {
				Page page = workspace.getPageByName(arg);
				String outFileName = page.getOutputName();
				File outFile = new File(workspace.getWorkingDirectory(), outFileName);
				System.out.println(arg);
				if (generate) {
					FileWriter writer = new FileWriter(outFile);
					Output output = new Output(writer);
					page.generate(output);
					writer.close();
				}
			} catch(Exception e) {
				if (ap.hasOption("-v")) {
					e.printStackTrace();					
				} else {
					System.err.println(e.getMessage());
				}
			}
		}
	}
	
	protected static String[] getAllPages(File root)
	{
		String files[] = root.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".gsp");
			}
		});
		for(int i = 0; i < files.length; i++) {
			int p = files[i].indexOf(".gsp");
			if (p > 0) {
				files[i] = files[i].substring(0, p);
			}
		}
		return files;
	}
	
}
