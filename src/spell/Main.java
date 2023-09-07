package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the pass off program.
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		
//		String dictionaryFileName = args[0];
//		String inputWord = args[1];
		String dictionaryFileName = "blahblah.txt";
		String inputWord = "keenobi";

		
		//
        //Create an instance of your corrector here
        //
		SpellCorrector corrector = new SpellCorrector();
		DLMatrix test = new DLMatrix("suggestion", "smmestion");
		test.printMatrix();
		System.out.println("Difference: "  + test.getDistance());
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		
		System.out.println("Suggestion is: " + suggestion);
	}

}
