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
        // insertAllWords();
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

    private static void insertAllWords() {
        ArrayList<String> allWords = new ArrayList<String>();
        for (int i = 1; i <= 2500; i++) {
            System.out.println("Processing: " + i);
            ArrayList<String> wordsOfCase = getWordsOfCase(i);
            allWords.addAll(wordsOfCase);
        }

        Set<String> set = new HashSet<String>(allWords);

        System.out.println("Size: " + allWords.size());
        System.out.println("unique words: " + set.size());

        int i = 0;
        for (String s : set) {
            if (i % 100 == 0) {
                System.out.println(i);
            }
            int frequency = Collections.frequency(allWords, s);
            Word w = new Word(s, frequency);
            boolean inserted = WordsController.insertWord(w);
            i++;
        }
    }

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

    private static ArrayList<String> getWordsOfCase(int n) {
        String directory = File.separator + "StopWordsRemovedCases";

        InputStream in = App.class.getResourceAsStream(directory + File.separator + n + ".txt");

        BufferedReader br = null;

        ArrayList<String> words = new ArrayList<String>();

        try {
            br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 2) {
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
