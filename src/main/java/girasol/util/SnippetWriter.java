package girasol.util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class SnippetWriter extends Writer {
	
	private Writer output;
	private int maxLength;
	private int length = 0;
	private boolean html = false;
	private boolean complete = false;
	
	public SnippetWriter(Writer output, int maxLength) {
		this.output = output;
		this.maxLength = maxLength;
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
		if (complete) return;
		int visLen = 0;
		for(int i = 0; i < len; i++) {
			if (html) {
				if (cbuf[i + off] == '>') {
					html = false;
				}
			} else {
				if (cbuf[i + off] == '<') {
					html = true;
				} else {
					if (visLen + length >= maxLength) {
						if (".,;:?!".indexOf(cbuf[i + off]) > -1) {
							output.write(cbuf, off, i + 1);
							length += i + 1;
							complete = true;
							return;
						}
					}
					visLen++;
				}
			}
		}		
		
		output.write(cbuf, off, len);
		length += visLen;
	}
	
	public static void main(String args[]) {
		SnippetWriter output = new SnippetWriter(new OutputStreamWriter(System.out), 100);
		try {
			output.write("Hello there can you tell what is going on? I think there is a big space ship out there.");
			output.flush();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
