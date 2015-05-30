package girasol.runtime;

import java.io.IOException;
import java.io.Writer;

import girasol.generator.GeneratorException;
import girasol.types.Value;

public class Output {
	
	private Writer output;
	
	public Output(Writer output)
	{
		this.output = output;
	}
	
	public void write(String str) throws RuntimeException
	{
		try {
			output.write(str);
		} catch(IOException e) {
			throw new RuntimeException("Error while writing to output", e);
		}
	}
	
	public void write(Value data) throws RuntimeException
	{
		write(data.asString());
	}
	
	public void flush() throws RuntimeException
	{
		try {
			output.flush();
		} catch(IOException e) {
			throw new RuntimeException("Error while flushing output", e);
		}
	}

}
