package com.example.bergsocke.vokabelapp.Model;

/**
 * Die Klasse "Vocable" ist für die Erfassung und Rückgabe des Inhalts der
 * einzelnen Vokabel-Datensätze zuständig
 *
 * Created by Bergsocke on 04.01.15.
 */

public class Vocable {

    private int id;
    private String theWord;         // Vokabel
    private String translation;     // Übersetzung
    private String boxNr;


    // Konstruktor
    public Vocable(){
    }

    // Konstruktor
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

    // Will be used by the ArrayAdapter in the ListView
    public String toString() {
        return id + ": " + theWord + ", " + translation + ", " + boxNr;
    }
}

