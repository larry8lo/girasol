package girasol.types;

public class DataUtil {
	
	public static Value asData(String value)
	{
		return value != null ? new StringObject(value) : NullValue.instance;
	}
	
	public static int parseInt(Value value)
	{
		if (value instanceof NumberValue) {
			return ((NumberValue) value).getInt();
		} else {
			return Integer.parseInt(value.asString());
		}
	}
	
}
