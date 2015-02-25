package com.example.bergsocke.vokabelapp.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bergsocke.vokabelapp.Model.MySQLiteHelper;
import com.example.bergsocke.vokabelapp.Model.Vocable;
import com.example.bergsocke.vokabelapp.R;

import java.util.List;
import java.util.Random;

/**
 * Activity to train vocables
 *
 * Created by Bergsocke on 25.01.15.
 */

public class TrainVocables extends Activity {

    private Button btn_mainMenu;
    private Button btn_showTranslation;
    private Button btn_nextWord;

    private TextView theWord;
    private TextView translation;

    private final Context context = this;

    private MySQLiteHelper db = new MySQLiteHelper(this);

    private List<Vocable> list;
    private Vocable trainVocable;       // aktuelle Vokabel

    private String boxNr;
    private Random randomGenerator;
    private int index;
    private int listSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_vocables);

        // get BoxNr from view SelectBox
        Intent i = getIntent();
        boxNr = i.getStringExtra("BoxNr");

        // get all Vocables from selected Box
        list = db.getAllVocablesBox(boxNr);

        // show a vocable on view
        showRandomVocable();


        // Button go to MainActivity
        btn_mainMenu = (Button) findViewById(R.id.btn_mainMenu);
        btn_mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    // show random vocable
    public void showRandomVocable(){

        // get list size
        listSize = list.size();

        // Prüfung, ob die Liste mehr als eine Vokabel beinhaltet
        if (listSize > 1) {
            // create a random vocable from list
            createRandomVocable();
        }
        // Wenn die Liste nur eine Vokabel beinhaltet, wird der Next-Button (zum Anzeigen
        // weiterer Vokabeln) deaktiviert
        else if(listSize == 1){

            // get this vocable from location 0
            trainVocable = list.get(0);

            // show vocable on the view
            theWord = (TextView) findViewById(R.id.txt_word);
            theWord.setText(trainVocable.getTheWord());

            // Button btn_showTranslation
            btn_showTranslation = (Button) findViewById(R.id.btn_showTranslation);
            btn_showTranslation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // create AlertDialog and show translation
                    list.remove(trainVocable);
                    createDialogWindow();
                }
            });

            // deactivate next-Button
            btn_nextWord = (Button) findViewById(R.id.btn_nextWord);
            btn_nextWord.setEnabled(false);
            btn_nextWord.setVisibility(View.INVISIBLE);

        }
        // Ist die Liste leer, wird ausgegeben, dass die Box leer ist bzw. alle Vokabeln der
        // Box bereits abgearbeitet sind
        else if(listSize == 0) {

            // show message
            theWord = (TextView) findViewById(R.id.txt_word);
            theWord.setTextColor(Color.parseColor("#FF0000"));
            theWord.setText(R.string.txt_empty);

            // deactivate Buttons translation and next
            btn_showTranslation = (Button) findViewById(R.id.btn_showTranslation);
            btn_showTranslation.setEnabled(false);
            btn_showTranslation.setVisibility(View.INVISIBLE);
            btn_nextWord = (Button) findViewById(R.id.btn_nextWord);
            btn_nextWord.setEnabled(false);
            btn_nextWord.setVisibility(View.INVISIBLE);

        }
    }

    // create a random vocable from list
    public void createRandomVocable(){

        randomGenerator = new Random();
        // create random number
        index = randomGenerator.nextInt(list.size());
        // get random vocable
        trainVocable = list.get(index);

        // show random vocable on the view
        theWord = (TextView) findViewById(R.id.txt_word);
        theWord.setText(trainVocable.getTheWord());

        // Button btn_showTranslation
        btn_showTranslation = (Button) findViewById(R.id.btn_showTranslation);
        btn_showTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // beim Klick auf den Button showTranslation, wird die aktuelle Vokabel
                // aus der Liste entfernt (wird als abgearbeitet bewertet)
                list.remove(trainVocable);

                // create AlertDialog and show translation
                createDialogWindow();

            }
        });

        // Button next word - show an other word
        btn_nextWord = (Button) findViewById(R.id.btn_nextWord);
        btn_nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // beim Klick auf den Button showTranslation, wird die aktuelle Vokabel
                // aus der list entfernt (wird als abgearbeitet bewertet)
                list.remove(trainVocable);

                // go to an other vocable
                showRandomVocable();

            }
        });
    }

    // create and show AlertDialog
    public void createDialogWindow(){

        LayoutInflater inflater = LayoutInflater.from(context);
        final View textEntry = inflater.inflate(R.layout.dialog_train, null);

        // get TextView fields
        theWord = (TextView) textEntry.findViewById(R.id.txt_word);
        translation = (TextView) textEntry.findViewById(R.id.txt_translation);

        // set values into TextView fields
        theWord.setText(trainVocable.getTheWord());
        translation.setText(trainVocable.getTranslation());

        // AlertDialog to show translation
        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(textEntry)
                 // if translation is correct -> move vocable to next box
                .setPositiveButton(R.string.btn_correct, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // get actual box number
                        int boxNumber = Integer.parseInt(boxNr);
                        // if box number not greater than 3, change box number
                        // if boxnumer is 3, don't change the box number, because there is no box 4
                        if (boxNumber < 3) {
                            boxNumber++;
                            trainVocable.setBoxNr(String.valueOf(boxNumber));
                            // save updated vocable in DB
                            db.updateVocable(trainVocable);
                            // vocable remove from list
                            list.remove(trainVocable);
                        }
                        // show the next vocable
                        showRandomVocable();
                    }
                })
                // if translation ist not correct
                .setNegativeButton(R.string.btn_not_correct, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // let vocable in the list and box -> do nothing with the vocable
                        dialog.cancel();
                        // vocable delete from list
                        list.remove(trainVocable);
                        // show the next vocable
                        showRandomVocable();
                    }
                });

        // create DialogAlert
        builder.create();
        // show DialogAlert
        builder.show();

    }
}
