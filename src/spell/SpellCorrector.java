package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
    Trie myTrie = new Trie();
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File inputFile = new File(".//src//spell//" + dictionaryFileName);
        if (!inputFile.exists()) {
            throw new IOException("file " + dictionaryFileName + " does not exist.");
        }
        Scanner fileScanner = new Scanner(inputFile);
        while (fileScanner.hasNextLine()) {
            String currentLine = fileScanner.nextLine();
            String[] newWords = currentLine.split("\\s+");
            for (String newWord : newWords) {
                String formattedWord = newWord.toLowerCase().trim().replaceAll("[^a-zA-z]", "");
                myTrie.add(formattedWord);
            }
        }
        fileScanner.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        String deletionSuggestion = checkDeletions(inputWord);
        if (deletionSuggestion != null) {
            System.out.println("You might have meant " + deletionSuggestion);
        }
        return null;
    }
// If there is more than one word in the dictionary that is an edit distance of 1 from the input string
// then return the one that appears the greatest number of times in the original text file.
// If two or more words are an edit distance of 1 from the input string
// and they both appear the same number of times in the input file,
// return the word that is alphabetically first.
    private String checkDeletions(String inputWord) {
        String suggestion = null;
        for (int i = 0; i < inputWord.length(); i++) {
            String firstPart;
            if (i > 0) {
                firstPart = inputWord.substring(0, i);
            } else {
                firstPart = "";
            }
            String lastPart = inputWord.substring(i + 1);
            String modifiedWord = firstPart + lastPart;
            if (myTrie.find(modifiedWord) != null) {
                suggestion = modifiedWord;
            }
        }
        return suggestion;
    }

    private String checkTranspositions(String inputWord) {
        return null;
    }
    private String checkAlterations(String inputWord) {
        return null;
    }
    private String checkInsertions(String inputWord) {
        return null;
    }


}
