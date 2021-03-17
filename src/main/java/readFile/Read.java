package readFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Read {
	
	private int[] data = new int[] {0,0,0,0,0,0,0,0,0};
	
	public Read() {
		
	}
	
	public int[] read(String file) {
	int count=0;
	try {
		File myObj = new File(file);
		Scanner myReader = new Scanner(myObj);

		while (myReader.hasNextLine()) {
			String input = myReader.nextLine();
			//System.out.println(input);
			try {
				if( input.contains(",") ) {
				int position = input.indexOf(","); //position of ','
				
					data[count]=Integer.parseInt(input.substring(0, position));
				count++;
				data[count]=Integer.parseInt(input.substring(position+1));	
				}
				else
				data[count]=Integer.parseInt(input);
			count++;	
			
			}
				catch(Exception e) {
					//System.out.println("An error occurred. You have introduced incorrect data.");
					data[7]=-1;
					//e.printStackTrace();
		}
		}
		myReader.close();
	} catch (FileNotFoundException e) {
		//System.out.println("An error occurred. Cannot find file.");
		data[8]=-1;
		//e.printStackTrace();
	}  
	if(data[7]==-1)
		System.out.println("An error occurred. You have introduced incorrect data.");
	if(data[8]==-1)
		System.out.println("An error occurred. Cannot find file.");
	return data;
	}
}
