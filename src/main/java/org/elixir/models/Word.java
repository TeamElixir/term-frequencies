package org.elixir.models;

public class Word {
    public static final String TABLE_NAME = "words";
    private String word;
    private int sentiment;
    private int frequency;

    public Word(String word, int sentiment, int frequency) {
        this.word = word;
        this.sentiment = sentiment;
        this.frequency = frequency;
    }

    public Word(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public int getSentiment() {
        return sentiment;
    }

    public int getFrequency() {
        return frequency;
    }
}
