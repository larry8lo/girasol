package girasol.types;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import girasol.dom.EvaluationException;
import girasol.dom.Function;
import girasol.runtime.Context;

/**
 * String (literal text) object
 * 
 * @author larry
 */
public class StringObject extends BaseObject {
	
	private String value;
	
	public static Prototype prototype = new Prototype("String")
		// returns length of string
		.addFunction("length", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				StringObject strObj = (StringObject) object;
				return new NumberValue(strObj.asString().length());
			}
		})
		// finds first instance of substring
		.addFunction("indexOf", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				StringObject strObj = (StringObject) object;
				return new NumberValue(strObj.asString().indexOf(args[0].asString()));
			}
		})
		// returns string in upper case
		.addFunction("upper", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				StringObject strObj = (StringObject) object;
				return new StringObject(strObj.asString().toUpperCase());
			}
		})
		// returns string in lower case
		.addFunction("lower", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				StringObject strObj = (StringObject) object;
				return new StringObject(strObj.asString().toLowerCase());
			}
		})
		// performs string equality check
		.addFunction("equals", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				StringObject strObj = (StringObject) object;
				return new BooleanValue(strObj.asString().equals(args[0].asString()));
			}
		})
		// performs string equality check ignoring case
		.addFunction("equalsIgnoreCase", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				StringObject strObj = (StringObject) object;
				return new BooleanValue(strObj.asString().equalsIgnoreCase(args[0].asString()));
			}
		})
		// concatenate to another string
		.addFunction("concat", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				StringObject strObj = (StringObject) object;
				StringBuffer buffer = new StringBuffer(strObj.asString());
				for(Value arg : args) {
					buffer.append(arg.asString());
				}
				return new StringObject(buffer.toString());
			}
		})
		// split string by token
		.addFunction("split", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				String str = ((StringObject) object).asString();
				String strArray[] = str.split(args[0].toString());
				Value array[] = new Value[strArray.length];
				for(int i = 0; i < strArray.length; i++) {
					array[i] = new StringObject(strArray[i]);
				}
				return new ArrayObject(array);
			}
		})
		.addFunction("replace", new Function(new String[] { "pattern", "replacement" }) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				String str = ((StringObject) object).asString();
				return new StringObject(str.replace(args[0].asString(), args[1].asString()));
			}
		})
		.addFunction("compareTo", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				String str = ((StringObject) object).asString();
				return new NumberValue(str.compareTo(args[0].asString()));
			}
		})
		.addFunction("match", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				String str = ((StringObject) object).asString();
				if (args.length == 1) {
					return new BooleanValue(str.matches(args[0].asString()));
				} else if (args.length == 2) {
					Pattern p = Pattern.compile(args[0].asString());
					Matcher m = p.matcher(str);
					boolean f;
					if (m.matches()) {
						ArrayObject matches = (ArrayObject) args[1];
						for(int i = 0; i <= m.groupCount(); i++) {
							matches.set(i, new StringObject(m.group(i)));
						}
						f = true;
					} else {
						f = false;
					}
					return new BooleanValue(f);
				} else {
					throw new EvaluationException("Invalid number of arguments", null);
				}
			}			
		});

	public StringObject(String value)
	{
		super(null, prototype);
		this.value = value;
	}

	@Override
	public String asString() 
	{
		return value;
	}

	@Override
	public boolean isPrimitive() 
	{
		return true;
	}
	
	public int compareTo(Value other)
	{
		return value.compareTo(other.asString());
	}

}
