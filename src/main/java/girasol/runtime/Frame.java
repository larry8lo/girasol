package girasol.runtime;

import java.util.HashMap;
import java.util.Map;

import girasol.types.UndefinedValue;
import girasol.types.Value;

public class Frame {

	private Map<String,Value> values = new HashMap<String,Value>();
	
	public boolean hasKey(String key)
	{
		return values.containsKey(key);
	}

	public void setValue(String key, Value value)
	{
		values.put(key, value);
	}
	
	public Value getValue(String key)
	{
		Value data = values.get(key);
		if (data == null) {
			return UndefinedValue.instance;
		} else {
			return data;
		}
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(String key : values.keySet()) {
			if (!first) { sb.append(", "); }
			sb.append(key).append("=\"").append(values.get(key)).append("\"");
			first = false;
		}
		return sb.toString();
	}

}
