package spell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector {
    Trie myTrie = new Trie();
    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File inputFile = new File(".//src//" + dictionaryFileName);
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
        String lowercaseInputWord = inputWord.toLowerCase();
        if (lowercaseInputWord.isEmpty()) {
            return null;
        }
        if (myTrie.find(lowercaseInputWord) != null) {
            return lowercaseInputWord;
        }
        String[] fIAlterations = alterWord(lowercaseInputWord);
        ArrayList<String> validFIAlterations = new ArrayList<>();
        ArrayList<String> validSIAlterations = new ArrayList<>();
        for (String alteration : fIAlterations) {
            if (myTrie.find(alteration) != null) {
                validFIAlterations.add(alteration);
            } else {
                String[] newAlterations = alterWord(alteration);
                for (String newAlteration : newAlterations) {
                    if (myTrie.find(newAlteration) != null && !validSIAlterations.contains((newAlteration))) {
                        validSIAlterations.add(newAlteration);
                    }
                }
            }
        }
        ArrayList<String> finalCandidates = !validFIAlterations.isEmpty() ? validFIAlterations : validSIAlterations;
        if (finalCandidates.isEmpty()) {
            return null;
        }
        int highScore = 0;
        ArrayList<String> bestWords = new ArrayList<>();
        for (String alteration : finalCandidates) {
            int alterationScore = myTrie.find(alteration).getValue();
            if (alterationScore > highScore) {
                highScore = alterationScore;
                bestWords.clear();
                bestWords.add(alteration);
            } else if (alterationScore == highScore) {
                bestWords.add(alteration);
            }
        }
        Collections.sort(bestWords);
        return bestWords.get(0);

    }


    private String[] alterWord(String inputWord) {
        String[] deletionAlterations = getDeletions(inputWord);
        String[] transpositionAlterations = getTranspositions(inputWord);
        String[] substitutionAlterations = getSubstitutions(inputWord);
        String[] additionAlterations = getAdditions(inputWord);
        ArrayList<String> allAlterations = new ArrayList<>(deletionAlterations.length + transpositionAlterations.length + substitutionAlterations.length + additionAlterations.length);
        Collections.addAll(allAlterations, deletionAlterations);
        Collections.addAll(allAlterations, transpositionAlterations);
        Collections.addAll(allAlterations, substitutionAlterations);
        Collections.addAll(allAlterations, additionAlterations);
        return allAlterations.toArray(new String[allAlterations.size()]);
    }
// If there is more than one word in the dictionary that is an edit distance of 1 from the input string
// then return the one that appears the greatest number of times in the original text file.
// If two or more words are an edit distance of 1 from the input string
// and they both appear the same number of times in the input file,
// return the word that is alphabetically first.
    private String[] getDeletions(String inputWord) {
        if (inputWord.length() < 2) {
            return new String[0];
        }
        String[] alterations = new String[inputWord.length()];
        for (int i = 0; i < inputWord.length(); i++) {
            String firstPart;
            if (i > 0) {
                firstPart = inputWord.substring(0, i);
            } else {
                firstPart = "";
            }
            String lastPart = inputWord.substring(i + 1);
            String modifiedWord = firstPart + lastPart;
            alterations[i] = modifiedWord;
        }
        return alterations;
    }

    private String[] getTranspositions(String inputWord) {
        String[] alterations = new String[inputWord.length() - 1];
        for (int i = 1; i < inputWord.length(); i++) {
            char firstChar = inputWord.charAt(i - 1);
            char secondChar = inputWord.charAt(i);
            String modifiedWord = inputWord.substring(0, i - 1) + secondChar + firstChar + inputWord.substring(i + 1);
            alterations[i - 1] = modifiedWord;
        }
        return alterations;
    }
    private String[] getSubstitutions(String inputWord) {
        String[] alterations = new String[inputWord.length() * 25];
        int alterationIndex = 0;
        for (int charIndex = 0; charIndex < inputWord.length(); charIndex++) {
            for (char newChar = 'a'; newChar <= 'z'; newChar++) {
                if (newChar == inputWord.charAt(charIndex)) {
                    continue;
                }
                String modifiedWord = inputWord.substring(0, charIndex) + newChar + inputWord.substring(charIndex + 1);
                alterations[alterationIndex] = modifiedWord;
                alterationIndex++;
            }
        }
        return alterations;
    }
    private String[] getAdditions(String inputWord) {
        String[] alterations = new String[(inputWord.length() + 1) * 26];
        int alterationIndex = 0;
        for (int i = 0; i <= inputWord.length(); i++) {
            for (char newChar = 'a'; newChar <= 'z'; newChar++) {
                String modifiedWord = inputWord.substring(0, i) + newChar + inputWord.substring(i);
                alterations[alterationIndex] = modifiedWord;
                alterationIndex++;
            }
        }
        return alterations;
    }

}
