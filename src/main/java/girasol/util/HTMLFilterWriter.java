package girasol.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

public class HTMLFilterWriter extends Writer {
	
	enum State { 
		TEXT, LT, OPEN, CLOSE, COMMENT
	};
	
	private State state = State.TEXT;
	private StringBuffer buffer = new StringBuffer();
	private Writer output;
	private boolean skipText = false;
	private boolean skipTag = false;
	
	public HTMLFilterWriter(Writer output) {
		this.output = output;
	}

	@Override
	public void close() throws IOException {
		output.close();
	}

	@Override
	public void flush() throws IOException {
		output.flush();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		int start = 0;
		String token = null;
		for(int i = 0; i < len; i++) {
			char ch = cbuf[off + i];
			if (state == State.TEXT) {
				if (ch == '<') {
					if (!skipText) {
						output.write(cbuf, start + off, i - start);
					}
					state = State.LT;
					buffer.setLength(0);
				}
			} else if (state == State.LT) {
				token = null;
				if (ch == '/') {
					state = State.CLOSE;
				} else if (ch == '!') {
					state = State.COMMENT;
				} else {
					buffer.append(ch);
					state = State.OPEN;
				}
			} else if (state == State.OPEN) {
				if (ch == ' ') {
					if (token == null) token = buffer.toString().toUpperCase();
					buffer.append(ch);
				} else if (ch == '>') {
					if (token == null) token = buffer.toString().toUpperCase();
					buffer.append(ch);
					if (token.equals("SCRIPT") || token.equals("STYLE") || token.equals("RPHIDE")) {
						skipText = true;
						skipTag = true;
					} else if (token.equals("IMG")) {
						skipTag = true;
					} 
					if (!skipTag && !skipText) {
						output.write("<");
						output.write(buffer.toString());
					}
					skipTag = false;
					buffer.setLength(0);
					state = State.TEXT;
					start = i + 1;
				} else {
					buffer.append(ch);
				}
			} else if (state == State.CLOSE) {
				if (ch == '>') {
					token = buffer.toString().toUpperCase();
					boolean oldSkipTag = skipTag;
					boolean oldSkipText = skipText;
					if (token.equals("SCRIPT") || token.equals("STYLE") || token.equals("RPHIDE")) {
						skipText = false;
						skipTag = false;
					}
					buffer.append(ch);
					if (!oldSkipTag && !oldSkipText) {
						output.write("</");
						output.write(buffer.toString());
					}
					buffer.setLength(0);
					state = State.TEXT;
					start = i + 1;
				} else {
					buffer.append(ch);
				}
			} else if (state == State.COMMENT) {
				if (ch == '>') {
					state = State.TEXT;
				}
			}
		}
		if (state == State.TEXT && !skipText && start < len) {
			output.write(cbuf, start + off, len - start);
		}
	}

	public static void main(String args[]) {
		HTMLFilterWriter out = new HTMLFilterWriter(new OutputStreamWriter(System.out));
		try {
			out.write("\n<p>hello there</p>");
			out.write("<p");
			out.write(" style=\"abc: xyz\"");
			out.write(">too true ");
			out.write("yeah yeah yeah");
			out.write("<style>");
			out.write(".abc { text-decoration: none; }");
			out.write("</style>");
			out.write("<script type=\"text/javascript\">");
			//out.write("<!--");
			out.write("function abc() { return 1 + 2; }");
			//out.write("// -->");
			out.write("</script>");
			out.write("<rphide><table><tr><td>abc</td></tr></table></rphide>");
			out.write("<img src=\"abc.gif\">");
			out.write("</p>");
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
