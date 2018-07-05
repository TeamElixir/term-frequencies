package org.elixir;

import org.elixir.controllers.WordsController;
import org.elixir.models.Word;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class App {
    public static void main(String[] args) {
//        insertAllWordsToDB();
        removeCJVanStopWords();
    }

    // TODO: complete the functionality
    private static void updateMaxDocumentFrequencies() {
        ArrayList<Word> words = getMaximumTermFrequencies();
        for (Word w : words) {
            int maxDocumentFrequency = w.getMaxDocumentFrequency();
            boolean b = WordsController.updateMaxTermDocumentOfWord(w);
            if (!b) {
                System.out.println("Failed! WordID: " + w.getId());
            }
        }
    }

    // remove CJVan stop-words from list of words and write new list to file
    private static void removeCJVanStopWords() {
        ArrayList<Word> allWords = WordsController.getAllWords();
        ArrayList<Word> newWords = new ArrayList<>();
        ArrayList<String> cjVanStopWordsList = getCJVanStopWordsList();
        System.out.println("Words initial size: " + allWords.size());
        System.out.println("Stopwords list size: " + cjVanStopWordsList.size());
        for (Word w : allWords) {
            if (cjVanStopWordsList.contains(w.getWord())) {
                continue;
            }
            newWords.add(w);
        }

        System.out.println("Words final size: " + newWords.size());
        writeNewWordsToFile(newWords);
    }

    // TODO: use with updateMaxDocumentFrequencies()
    private static ArrayList<Word> getMaximumTermFrequencies() {
        ArrayList<Word> allWords = WordsController.getAllWords();
        for (Word w : allWords) {
            int maximum = 0;
            for (int i = 1; i <= 2500; i++) {
                ArrayList<String> wordsOfCase = getWordsOfCase(i);
                int frequency = Collections.frequency(wordsOfCase, w);
                maximum = Math.max(frequency, maximum);
            }
            w.setMaxDocumentFrequency(maximum);
        }
        return allWords;
    }

    // manually write words list to CSV file
    private static void writeNewWordsToFile(ArrayList<Word> words) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File("new-words.csv"));
            StringBuilder sb = new StringBuilder();
            for (Word w : words) {
                sb.append(w.getWord());
                sb.append(',');
                sb.append(w.getFrequency());
                sb.append('\n');
            }

            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // insert all words to database
    private static void insertAllWordsToDB() {
        ArrayList<String> allWords = new ArrayList<String>();
        for (int i = 1; i <= 231; i++) {
            System.out.println("Processing: " + i);
            ArrayList<String> wordsOfCase = getWordsOfCase(i);
            allWords.addAll(wordsOfCase);
        }

        Set<String> set = new HashSet<>(allWords);

        System.out.println("Size: " + allWords.size());
        System.out.println("Unique words: " + set.size());

        int i = 0;
        for (String s : set) {
            if (i % 10 == 0) {
                // running indicator
                System.out.println(i);
            }
            int frequency = Collections.frequency(allWords, s);
            Word w = new Word(s, frequency);
            boolean inserted = WordsController.insertWord(w);
            i++;
        }
    }

    // get the list of CJVan top-words from file
    private static ArrayList<String> getCJVanStopWordsList() {
        String fileName = File.separator + "CJVanStopWords.txt";

        InputStream in = App.class.getResourceAsStream(fileName);

        BufferedReader br = null;

        ArrayList<String> stopWords = new ArrayList<>();

        try {
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String[] strings = line.split("\\s+");
                for (String s : strings) {
                    stopWords.add(s.trim().toLowerCase());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stopWords;
    }

    // get the words of the case specified by the case number
    private static ArrayList<String> getWordsOfCase(int n) {
        String directory = File.separator + "StopWordsRemovedCriminalCases";

        InputStream in = App.class.getResourceAsStream(directory + File.separator + n + ".txt");

        BufferedReader br = null;

        ArrayList<String> words = new ArrayList<String>();

        try {
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 2) {
                    // words with length < 2 are not useful in sentiment analysis
                    words.add(line.trim().toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return words;
    }
}
