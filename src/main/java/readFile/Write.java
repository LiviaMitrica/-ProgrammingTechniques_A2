package readFile;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Write {

	public Write() {

	}

	public void writeFile(String string, String file) {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(file, true);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.println(string);  //New line
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} //Set true for append mode
	}
}
