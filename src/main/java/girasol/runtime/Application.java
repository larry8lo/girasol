package girasol.runtime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import girasol.dom.EvaluationException;
import girasol.dom.Function;
import girasol.types.ArrayObject;
import girasol.types.BaseObject;
import girasol.types.BooleanValue;
import girasol.types.FunctionValue;
import girasol.types.NullValue;
import girasol.types.NumberValue;
import girasol.types.PageObject;
import girasol.types.StringObject;
import girasol.types.UndefinedValue;
import girasol.types.Value;

/**
 * Girasol System
 * 
 * @author larry
 */
public class Application {
	
	private static final Application application = new Application();

	private ApplicationContext context;
	private Map<Thread,Output> outputs = new HashMap<Thread,Output>();
	
	public static Application getApplication() 
	{
		return application;
	}
	
	private Application()
	{
		context = new ApplicationContext();
		initFunctions();
	}
	
	public Context getContext() {
		return context;
	}
	
	public void setOutput(Output output) 
	{
		outputs.put(Thread.currentThread(), output);
	}
	
	public void clearOutput()
	{
		outputs.remove(Thread.currentThread());
	}
	
	public Output getOutput()
	{
		return outputs.get(Thread.currentThread());
	}

	private void initFunctions() 
	{
		RuntimeUtil.addFunction(context, "a", new Function(new String[] { "page" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				
				String ref;
				String descr = args.length > 1 ? args[1].asString() : null;
				if (args[0] instanceof PageObject) {
					ref = ((PageObject) args[0]).getPage().getOutputName();
					if (descr == null) {
						Value title = ((PageObject) args[0]).getPage().getContext().getValue("title");
						descr = title.asString();
					}
				} else if (args[0] != null) {
					ref = args[0].asString();
					descr = descr != null ? descr : ref;
				} else {
					return NullValue.instance;
				}
				
				if (descr == null) {
					descr = ref;
				}
	
				try {
					context.getOutput().write("<a href=\"" + ref + "\">");
					context.getOutput().write(descr);
					context.getOutput().write("</a>");
				} catch(RuntimeException e) {
					throw new EvaluationException(e.getMessage(), e);
				}
				return NullValue.instance;
			}
		});
	
		RuntimeUtil.addFunction(context, "echo", new Function(new String[] { "value" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				try {
					for(Value arg : args) {
						context.getOutput().write(arg);
					}
					context.getOutput().flush();
				} catch(RuntimeException e) {
					throw new EvaluationException(e.getMessage(), e);
				}
				return NullValue.instance;
			}
		});
		
		RuntimeUtil.addFunction(context, "year", new Function(new String[] { } ) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				return new StringObject(format.format(new Date()));
			}
		});
		
		RuntimeUtil.addFunction(context, "parseInt", new Function(new String[] { "value", "lenient" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				String s = args[0].asString();
				if (args.length > 1) {
					if (args[1] instanceof BooleanValue && ((BooleanValue) args[1]).get()) {
						StringBuffer t = new StringBuffer();
						if (s.length() > 0) {
							int n = 0;
							if (s.charAt(0) == '-') {
								t.append(s.charAt(0));
								n++;
							}
							for(int i = n; i < s.length(); i++) {
								if (Character.isDigit(s.charAt(i))) {
									t.append(s.charAt(i));
								}
							}
							s = t.toString();
						}
					}
				}
				try {
					return new NumberValue(Integer.parseInt(s));
				} catch(Exception e) {
					return new NumberValue(0);
				}
			}
		});
		
		RuntimeUtil.addFunction(context, "defined", new Function(new String[] { "object" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				return new BooleanValue(!args[0].equals(UndefinedValue.instance));
			}			
		});
		
		RuntimeUtil.addFunction(context, "eq", new Function(new String[] { "a", "b" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				return new BooleanValue(args[0].equals(args[1]));
			}			
		});
		
		RuntimeUtil.addFunction(context, "ne", new Function(new String[] { "a", "b" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				return new BooleanValue(!args[0].equals(args[1]));
			}			
		});
		
		RuntimeUtil.addFunction(context, "gt", new Function(new String[] { "a", "b" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				NumberValue n0 = (NumberValue) args[0];
				NumberValue n1 = (NumberValue) args[1];
				return new BooleanValue(n0.getInt() > n1.getInt());
			}			
		});
		
		RuntimeUtil.addFunction(context, "lt", new Function(new String[] { "a", "b" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				NumberValue n0 = (NumberValue) args[0];
				NumberValue n1 = (NumberValue) args[1];
				return new BooleanValue(n0.getInt() < n1.getInt());
			}			
		});
		
		RuntimeUtil.addFunction(context, "gteq", new Function(new String[] { "a", "b" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				NumberValue n0 = (NumberValue) args[0];
				NumberValue n1 = (NumberValue) args[1];
				return new BooleanValue((n0.getInt() > n1.getInt() || (n0.getInt() == n1.getInt())));
			}			
		});
		
		RuntimeUtil.addFunction(context, "lteq", new Function(new String[] { "a", "b" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				NumberValue n0 = (NumberValue) args[0];
				NumberValue n1 = (NumberValue) args[1];
				return new BooleanValue((n0.getInt() < n1.getInt() || (n0.getInt() == n1.getInt())));
			}			
		});
		
		RuntimeUtil.addFunction(context, "regex", new Function(new String[] { "a", "b" } ) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				return new BooleanValue(args[0].toString().matches(args[1].toString()));
			}			
		});		
		
		RuntimeUtil.addFunction(context, "and", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				boolean v = true;
				for(Value arg : args) {
					if (arg instanceof BooleanValue) {
						v &= ((BooleanValue) arg).get();
					} else if (arg instanceof NumberValue) {
						v &= ((NumberValue) arg).getInt() != 0;
					} else if (NullValue.instance == arg) {
						v &= false;
					} else {
						v &= true;
					}
				}
				return new BooleanValue(v);
			}
		});
		
		RuntimeUtil.addFunction(context, "or", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				boolean v = true;
				for(Value arg : args) {
					if (arg instanceof BooleanValue) {
						v |= ((BooleanValue) arg).get();
					} else if (arg instanceof NumberValue) {
						v |= ((NumberValue) arg).getInt() != 0;
					} else if (NullValue.instance == arg) {
						v |= false;
					} else {
						v |= true;
					}
				}
				return new BooleanValue(v);
			}
		});

		/*
		RuntimeUtil.addFunction(context, "add", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				int sum = 0;
				for(Value arg : args) {
					try {
						sum += DataUtil.parseInt(arg);
					} catch(Exception e) {
						throw new EvaluationException("Invalid number", null);
					}
				}
				return new NumberValue(sum);
			}
		});
		
		RuntimeUtil.addFunction(context, "sub", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				int value = 0;
				try {
					value = DataUtil.parseInt(args[0]) - DataUtil.parseInt(args[1]);
				} catch(Exception e) {
					throw new EvaluationException("Invalid number", null);
				}
				return new NumberValue(value);
			}
		});
		
		RuntimeUtil.addFunction(context, "mul", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				int prod = 0;
				for(Value arg : args) {
					try {
						prod *= DataUtil.parseInt(arg);
					} catch(Exception e) {
						throw new EvaluationException("Invalid number", null);
					}
				}
				return new NumberValue(prod);
			}
		});

		RuntimeUtil.addFunction(context, "div", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				int value = 0;
				int numer = 0;
				int denom = 0;
				try {
					numer = DataUtil.parseInt(args[0]);
					denom = DataUtil.parseInt(args[1]);
				} catch(Exception e) {
					throw new EvaluationException("Invalid number", null);
				}
				if (denom == 0) {
					throw new EvaluationException("Division by zero", null);
				}
				value = numer / denom;
				return new NumberValue(value);
			}
		});
		*/
		
	}

}
