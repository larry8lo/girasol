package girasol.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import girasol.dom.EvaluationException;
import girasol.dom.Function;
import girasol.generator.GeneratorException;
import girasol.runtime.Context;
import girasol.runtime.ObjectContext;

public class ArrayObject extends BaseObject {
	
	private ArrayList<Value> array;
	
	private final static Prototype prototype = new Prototype("Array")
		// returns length of array
		.addFunction("length", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				ArrayObject arrayObj = (ArrayObject) object;
				return new NumberValue(arrayObj.length());
			}
		})
		// adds an element to array
		.addFunction("add", new Function(new String[] {}) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				ArrayObject arrayObj = (ArrayObject) object;
				for(Value arg : args) {
					arrayObj.add(arg);
				}
				return arrayObj;
			}
		})
		// sorts array
		.addFunction("sort", new Function(new String[] { "cmp" }) {
			public Value invoke(Context context, BaseObject object, Value args[]) throws EvaluationException {
				ArrayObject arrayObj = (ArrayObject) object;
				Function comparator = null;
				if (args.length == 1 && args[0] instanceof FunctionValue) {
					comparator = ((FunctionValue) args[0]).getFunction();
					context = ((FunctionValue) args[0]).getContext();
				}
				arrayObj.sort(comparator, context);
				return NullValue.instance;
			}
		})
	;

	public ArrayObject()
	{
		super(null, prototype);
		this.array = new ArrayList<Value>();
	}
	
	public ArrayObject(Value[] array)
	{
		super(null, prototype);
		this.array = new ArrayList<Value>(array.length);
		Collections.addAll(this.array, array);
	}
	
	public int length()
	{
		return array.size();
	}
	
	public Value get(int i)
	{
		return array.get(i);
	}
	
	public void set(int i, Value elem)
	{
		array.ensureCapacity(i + 1);
		array.set(i, elem);
	}
	
	public void add(Value elem)
	{
		array.add(elem);
	}
	
	private static Comparator<Value> defaultComparator = new Comparator<Value>() {
		@Override
		public int compare(Value arg0, Value arg1) {
			return arg0.compareTo(arg1);
		}
	};
	
	public void sort(final Function f, final Context ctx)
	{
		Comparator<Value> cmp;
		if (f == null) {
			cmp = defaultComparator;
		} else {
			cmp = new Comparator<Value>() {
				@Override
				public int compare(Value arg0, Value arg1) {
					try {
						Value r = f.invoke(ctx, null, new Value[] { arg0, arg1 });
						if (r instanceof NumberValue) {
							return ((NumberValue) r).getInt();
						}
					} catch(EvaluationException e) {
					}
					return 0;
				}
			};
		}
		Collections.sort(array, cmp);
	}

	@Override
	public String asString() 
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < array.size(); i++) {
			sb.append(array.get(i).asString());
		}
		return sb.toString();
	}

	@Override
	public boolean isPrimitive() 
	{
		return false;
	}
	
}
