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
        ArrayList<String> allWords = new ArrayList<String>();
        for (int i = 1; i <= 2500; i++) {
            System.out.println("Processing: " + i);
            ArrayList<String> wordsOfCase = getWordsOfCase(i);
            allWords.addAll(wordsOfCase);
        }

        Set<String> set = new HashSet<String>(allWords);

        System.out.println("Size: " + allWords.size());
        System.out.println("unique words: " + set.size());

        for (String s : set) {
            int frequency = Collections.frequency(allWords, s);
            Word w = new Word(s, frequency);
            boolean inserted = WordsController.insertWord(w);
//            if (inserted) {
//                System.out.println("Inserted: " + s);
//            }
        }
    }

    private static ArrayList<String> getWordsOfCase(int n) {
        String directory = "StopWordsRemovedCases";
        String filePath = new File("").getPath() + directory + File.separator + n + ".txt";
        BufferedReader br = null;

        ArrayList<String> words = new ArrayList<String>();

        try {
            br = new BufferedReader(new FileReader(new File(filePath)));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 2) {
                    words.add(line.trim());
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
