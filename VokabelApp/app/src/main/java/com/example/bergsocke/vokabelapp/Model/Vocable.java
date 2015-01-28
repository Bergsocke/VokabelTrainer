package com.example.bergsocke.vokabelapp.Model;

/**
 * vocable class
 *
 * Created by Bergsocke on 04.01.15.
 */

public class Vocable {

    private int id;
    private String theWord;
    private String translation;
    private String boxNr;

    public Vocable(){

    }

    public Vocable(String theWord, String translation, String boxNr) {
        super();
        this.theWord = theWord;
        this.translation = translation;
        this.boxNr = boxNr;
    }

    // Getter and Setter

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTheWord() {
        return theWord;
    }
    public void setTheWord(String theWord) {
        this.theWord = theWord;
    }

    public String getTranslation() {
        return translation;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getBoxNr() {
        return boxNr;
    }
    public void setBoxNr(String boxNr) {
        this.boxNr = boxNr;
    }

    public String toString() {
        return id + ": " + theWord + ", " + translation + ", " + boxNr;
    }
}

