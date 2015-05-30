package girasol.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArgumentsParser {

	private Map<String,List<String>> options = new HashMap<String,List<String>>();
	private String arguments[];
	
	public void clear()
	{
		this.options.clear();
		this.arguments = null;
	}
	
	public void parseArguments(String args[], String options[])
	{
		clear();
		int index = 0;
		for(String arg : args) {
			if (arg.startsWith("-")) {
				if (arg.equals("--")) {
					break;
				} else {
					for(String option : options) {
						if (arg.length() >= option.length()) {
							if (arg.startsWith(option)) {
								addOptionValue(option, arg.substring(option.length()));
							}
						}
					}
				}
			} else {
				break;
			}
			index++;
		}
		
		this.arguments = new String[args.length - index];
		int j = 0;
		for(int i = index; i < args.length; i++) {
			this.arguments[j++] = args[i];
		}
	}
	
	protected void addOptionValue(String option, String value)
	{
		List<String> values = options.get(option);
		if (values == null) {
			values = new ArrayList<String>();
			options.put(option, values);
		}
		values.add(value);
	}
	
	public List<String> getOptionValues(String option)
	{
		return options.get(option);
	}
	
	public String getOptionValue(String option)
	{
		List<String> values = getOptionValues(option);
		if (values != null && values.size() > 0) {
			return values.get(0);
		}
		return null;
	}
	
	public String getOptionValue(String option, String defaultValue)
	{
		String value = getOptionValue(option);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}
	
	public boolean hasOption(String option)
	{
		return options.containsKey(option);
	}
	
	public String[] getArguments()
	{
		return this.arguments;
	}
	
}
