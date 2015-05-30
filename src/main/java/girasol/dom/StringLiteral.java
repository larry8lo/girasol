package girasol.dom;

import java.util.StringTokenizer;

import girasol.runtime.Context;
import girasol.types.StringObject;
import girasol.types.Value;

public class StringLiteral extends Literal {
	
	private String value;
	
	public StringLiteral(String value)
	{
		setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		StringBuffer rv = new StringBuffer();
		StringTokenizer st = new StringTokenizer(value, "\\", true);
		while (st.hasMoreTokens()) {
			String s = st.nextToken();
			if (s.equals("\\")) {
				if (st.hasMoreTokens()) {
					s = st.nextToken();
					char x = s.charAt(0);
					if (x == 'n') {
						rv.append("\n");
					} else if (x == 'r') {
						rv.append("\r");
					} else if (x == 't') {
						rv.append("\t");
					} else {
						rv.append("\\").append(x);
					}
					s = s.substring(1);
				}
			}
			rv.append(s);
		}
		this.value = rv.toString();
	}
	
	public Value evaluate(Context ctx) throws EvaluationException
	{
		return new StringObject(getValue());
	}
	
}
