package com.example.bergsocke.vokabelapp.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * SQLiteHelper
 * Die Klasse "MySQLiteHelper" bietet die Methoden zum Anzeigen, Speichern und
 * Löschen von Datensätzen der Tabelle "vocabulary"
 *
 * Created by Bergsocke on 22.01.15.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Datenbank-Version
    private static final int DATABASE_VERSION = 1;

    // Datenbank-Name
    private static final String DATABASE_NAME = "VocabelDB";

    // Tabellen-Name
    private static final String TABLE_NAME = "vocabulary";

    // Spaltennamen der Tabelle
    private static final String KEY_ID = "id";
    private static final String KEY_THEWORD = "theWord";
    private static final String KEY_TRANSLATION = "translation";
    private static final String KEY_BOXNR = "boxNr";

    // SQLiteDatabase
    private SQLiteDatabase db;



    // Konstruktor
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Datenbank neu erstellen
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create vocable table
        String CREATE_VOCABLE_TABLE = "CREATE TABLE " + TABLE_NAME + "( " + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_THEWORD +  " TEXT, "+ KEY_TRANSLATION +
                " TEXT, " + KEY_BOXNR + " TEXT);";

        // create vocabulary table
        db.execSQL(CREATE_VOCABLE_TABLE);
    }

    // Datenbank Upgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older vocabulary table if existed
        db.execSQL("DROP TABLE IF EXISTS vocabulary");

        // create fresh vocabulary table
        this.onCreate(db);
    }

    // open database
    public void openDB() {
        db = this.getWritableDatabase();
    }

    // close database
    public void closeDB() {
        db.close();
    }


    // speichert eine neue Vokabel in die Datenbank
    public void addVocable(Vocable vocable){

        // get reference to writable DB
        openDB();

        // create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_THEWORD, vocable.getTheWord());
        values.put(KEY_TRANSLATION, vocable.getTranslation());
        values.put(KEY_BOXNR, vocable.getBoxNr());

        // insert vocable
        db.insert(TABLE_NAME, null, values);

        // close DB
        closeDB();
    }


    // Get all vocables
    public List<Vocable> getAllVocables() {
        List<Vocable> vocables = new LinkedList<Vocable>();

        // build the query
        String query = "SELECT  * FROM " + TABLE_NAME;

        // get reference to writable DB
        openDB();

        Cursor cursor = db.rawQuery(query, null);

        Vocable vocable = null;

        // go over each row, build vocable and add it to list
        if (cursor.moveToFirst()) {
            do {
                vocable = new Vocable();
                vocable.setId(Integer.parseInt(cursor.getString(0)));
                vocable.setTheWord(cursor.getString(1));
                vocable.setTranslation(cursor.getString(2));
                vocable.setBoxNr(cursor.getString(3));

                // Add vocable to vocables
                vocables.add(vocable);
            } while (cursor.moveToNext());
        }

        // close the cursor
        cursor.close();

        // close DB
        closeDB();

        // return vocables
        return vocables;
    }

    // Get all vocables from selected box
    public List<Vocable> getAllVocablesBox(String boxNr) {

        String boxNumber = boxNr;
        List<Vocable> vocables = new LinkedList<Vocable>();

        // build the query
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE " + KEY_BOXNR + " = " + boxNumber;

        // get reference to writable DB
        openDB();

        Cursor cursor = db.rawQuery(query,null);

        Vocable vocable = null;

        // go over each row, build vocable and add it to list
        if (cursor.moveToFirst()) {
            do {
                vocable = new Vocable();
                vocable.setId(Integer.parseInt(cursor.getString(0)));
                vocable.setTheWord(cursor.getString(1));
                vocable.setTranslation(cursor.getString(2));
                vocable.setBoxNr(cursor.getString(3));

                // Add vocable to vocables
                vocables.add(vocable);
            } while (cursor.moveToNext());
        }

        // close the cursor
        cursor.close();

        // close DB
        closeDB();

        // return vocables
        return vocables;
    }



    // Updating single vocable
    public void updateVocable(Vocable vocable) {

        // get reference to writable DB
        openDB();

        //create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_THEWORD, vocable.getTheWord());
        values.put(KEY_TRANSLATION, vocable.getTranslation());
        values.put(KEY_BOXNR, vocable.getBoxNr());

        // updating row
        db.update(TABLE_NAME,       //table
                values,             // column/value
                KEY_ID + " = ?",    // selections
                new String[] { String.valueOf(vocable.getId()) });  //selection args

        // close DB
        closeDB();

    }

    // Deleting single vocable
    public void deleteVocable(int id) {

        // get reference to writable DB
        openDB();

        // delete vocable from database
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(id)});

        // close DB
       closeDB();

    }
}
