package example;

import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteToJSON {

	public static void main(String[] args) {
		
		// This is the object that will be stored in JSON
		MyClassHere myObject = new MyClassHere(5);
		
		// You need to define this filepath yourself
		// This is where the file will be written to
		// In this example it is written to my desktop
		// If Example.json doesn't exist it will be created
		String myFilePath = "C:\\Users\\MelloKitty\\Desktop\\Example.json";
		
		// Gson is used to create a json object that is spaced nicely
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		// Instantiate the writer since we're writing to a JSON file.
		FileWriter writer = null;
		try {
			writer = new FileWriter(myFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Object is converted to a JSON String
		String jsonString = gson.toJson(myObject);
		
		// Write the file
		try {
			writer.write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Close the Writer
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
