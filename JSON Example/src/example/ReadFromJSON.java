package example;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class ReadFromJSON {

	public static void main(String[] args) {
		
		// You need to define this filepath yourself
		// This is where the file will be written to
		// In this example it is written to my desktop
		// If Example.json doesn't exist it will be created
		String myFilePath = "C:\\Users\\MelloKitty\\Desktop\\Example.json";
		
		// JSONParser is used to parse the data
		JSONParser parser = new JSONParser();
		
		// notice JSON passes integers as Longs
		long myInteger = 0;
		boolean myBoolean = true;
		double myDouble = 0.0;
		long myLong = 0l;
		String myString = "stuff";
		
		// the double list is passed as its own object
		Object myList = null;
		
		try {
			Object obj = parser.parse(new FileReader(myFilePath));
			JSONObject jsonObject = (JSONObject) obj;
			myInteger = (long) jsonObject.get("myInteger");
			myBoolean = (boolean) jsonObject.get("myBoolean");
			myDouble = (double) jsonObject.get("myDouble");
			myLong = (long) jsonObject.get("myLong");
			myString = (String) jsonObject.get("myString");
			myList = jsonObject.get("myList");
			
		} catch (FileNotFoundException e) {
			System.out.println("No file found.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("I/O exception found.");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Parse exception found.");
			e.printStackTrace();
		}
		
		System.out.println("myInteger = " + myInteger);
		System.out.println("myBoolean = " + myBoolean);
		System.out.println("myDouble = " + myDouble);
		System.out.println("myLong = " + myLong);
		System.out.println("myString = " + myString);
		System.out.println("myList = " + myList);
	}
}