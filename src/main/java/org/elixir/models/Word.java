package org.elixir.models;

public class Word {
    public static final String TABLE_NAME = "words";
    private int id;
    private String word;
    private int sentiment;
    private int frequency;
    private int maxDocumentFrequency;

    public Word(String word, int sentiment, int frequency) {
        this.word = word;
        this.sentiment = sentiment;
        this.frequency = frequency;
    }

    public int getMaxDocumentFrequency() {
        return maxDocumentFrequency;
    }

    public void setMaxDocumentFrequency(int maxDocumentFrequency) {
        this.maxDocumentFrequency = maxDocumentFrequency;
    }

    public Word(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
