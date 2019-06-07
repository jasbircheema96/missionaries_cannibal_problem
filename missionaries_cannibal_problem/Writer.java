package missionaries_cannibal_problem;

import java.io.FileWriter;
import java.io.IOException;

public class Writer {
	FileWriter fileWriter;
	Writer() {
		try {
			fileWriter=new FileWriter(Constants.OUTPUT_FILE_NAME);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String s)  {
		try {
			fileWriter.write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
