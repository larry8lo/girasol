package girasol.util;

import java.io.IOException;
import java.io.Writer;

public class JSONWriter extends Writer {
	
	private static char[] tab = { '\\', 't' };
	private static char[] cr = { '\\', 'r' };
	private static char[] lf = { '\\', 'n' };
	private static char[] dq = { '\\', '"' };

	private Writer output;
	
	public JSONWriter(Writer output) {
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
		while (len > 0 && off + len <= cbuf.length) {
			int n = nextSpecialChar(cbuf, off, len);
			if (n == -1) {
				try {
					output.write(cbuf, off, len);
				} catch(IndexOutOfBoundsException e) {
					e.printStackTrace();
				}
				break;
			} else {
				if (n > off) output.write(cbuf, off, n - off);
				if (cbuf[n] == '\t') output.write(tab, 0, tab.length);
				else if (cbuf[n] == '\r') output.write(cr, 0, cr.length);
				else if (cbuf[n] == '\n') output.write(lf, 0, lf.length);
				else if (cbuf[n] == '"') output.write(dq, 0, dq.length);
				int l = n - off + 1;
				len -= l;
				off += l;
			}
		}
	}

	private int nextSpecialChar(char[] cbuf, int off, int len) {
		for(int i = 0; i < len; i++) {
			char ch = cbuf[i + off];
			if (ch == '\t' || ch == '\r' || ch == '\n' || ch == '"') {
				return i + off;
			}
		}
		return -1;
	}
}
