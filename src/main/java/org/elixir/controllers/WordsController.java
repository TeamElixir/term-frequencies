package org.elixir.controllers;

import org.elixir.db.DBCon;
import org.elixir.models.Word;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WordsController {
    private static Connection conn = DBCon.getConnection();

    public static boolean insertWord(Word word) {
        String query = "INSERT INTO " + Word.TABLE_NAME + "(word, frequency) VALUES (?, ?)";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, word.getWord());
            ps.setInt(2, word.getFrequency());
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Word> getAllWords() {
        ArrayList<Word> words = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;

        String query = "SELECT * FROM " + Word.TABLE_NAME;

        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String word = rs.getString("word");
                int frequency = rs.getInt("frequency");

                Word w = new Word(word, frequency);
                w.setId(id);
                words.add(w);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return words;
    }

    public static boolean updateMaxTermDocumentOfWord(Word word) {
        String query = "UPDATE " + Word.TABLE_NAME +
                " SET maxDocumentFrequency = ? WHERE id = ?";

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, word.getMaxDocumentFrequency());
            ps.setInt(2, word.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}