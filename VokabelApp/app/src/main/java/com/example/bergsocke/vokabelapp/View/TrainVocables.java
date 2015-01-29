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
 * train vocables
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

    private Random randomGenerator;

    private List<Vocable> list;
    private Vocable trainVocable;

    private String boxNr;

    private int index;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_vocables);

        // get BoxNr from view SelectBox
        Intent i = getIntent();
        boxNr = i.getStringExtra("BoxNr");


        // get all Vocables from selected Box
        list = db.getAllVocablesBox(boxNr);

        this.createRandomVocable();

        // go to MainActivity
        btn_mainMenu = (Button) findViewById(R.id.btn_mainMenu);
        btn_mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    // create random vocable
    public void createRandomVocable(){

        // get list size
        int sizeList = list.size();

        // if vocable box is not empty and there are more vocable than one
        if (sizeList > 1) {

            // get random vocable from list
            randomGenerator = new Random();
            index = randomGenerator.nextInt(list.size());
            trainVocable = list.get(index);


            // show random vocable on the view
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

            // Button next word - select an other word
            btn_nextWord = (Button) findViewById(R.id.btn_nextWord);
            btn_nextWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // got to an other vocable
                    list.remove(trainVocable);
                    createRandomVocable();

                }
            });
        }
        // if only one vocable is in the box
        else if(sizeList == 1){

            // get vocable from location 0
            trainVocable = list.get(0);

            // show random vocable on the view
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
        // if vocable box is empty
        else if(sizeList == 0) {

            // show message
            theWord = (TextView) findViewById(R.id.txt_word);
            theWord.setTextColor(Color.parseColor("#FF0000"));
            theWord.setText("Die Box ist leer");

            // deactivate Buttons translation and next
            btn_showTranslation = (Button) findViewById(R.id.btn_showTranslation);
            btn_showTranslation.setEnabled(false);
            btn_showTranslation.setVisibility(View.INVISIBLE);
            btn_nextWord = (Button) findViewById(R.id.btn_nextWord);
            btn_nextWord.setEnabled(false);
            btn_nextWord.setVisibility(View.INVISIBLE);

        }
    }

    // create and show AlertDialog
    public void createDialogWindow(){

        LayoutInflater inflater = LayoutInflater.from(context);
        final View textEntry = inflater.inflate(R.layout.dialog_train, null);

        // get TextView fields
        theWord = (TextView) textEntry.findViewById(R.id.txt_word);
        translation = (TextView) textEntry.findViewById(R.id.txt_translation);

        // set values
        theWord.setText(trainVocable.getTheWord());
        translation.setText(trainVocable.getTranslation());

        // AlertDialog to show translation

        final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(textEntry)
                        // if translation is correct -> move vocable to next box
                .setPositiveButton(R.string.btn_correct, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        int boxNr = Integer.parseInt(trainVocable.getBoxNr());
                        if (boxNr < 3) {
                            boxNr++;
                            trainVocable.setBoxNr(String.valueOf(boxNr));
                            // save updated vocable in DB
                            db.updateVocable(trainVocable);
                            // vocable remove from list
                            list.remove(trainVocable);
                        }
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
                    }
                });

        builder.create();
        builder.show();

    }
}
