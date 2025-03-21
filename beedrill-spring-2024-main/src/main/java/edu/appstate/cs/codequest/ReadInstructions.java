package edu.appstate.cs.codequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class to get and read the text file containing the level instructions.
 */
public class ReadInstructions {

	// String that contains the repeated parts of the path
	public static final String filePathStr = Resource.resourcePath + "instructions/";

	/**
	 * Method that takes the level as an int and
	 * loads the corresponding text file then
	 * returns it as a string
	 * 
	 * @param level level the player is on as an int
	 * @return returns string read from file
	 */
	public static String readFile(String level) {
		try {
			String output = "";
			List<String> lines = Files.readAllLines(Paths.get(filePathStr + "level" + level + "instr.txt"));
			for (String str : lines)
				output += str + "\n";
			return output;
		} catch (IOException e) {
			return "File Not Found";
		}
	}

}
