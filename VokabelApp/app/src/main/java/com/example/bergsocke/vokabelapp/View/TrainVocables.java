package com.example.bergsocke.vokabelapp.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bergsocke.vokabelapp.Model.MySQLiteHelper;
import com.example.bergsocke.vokabelapp.Model.Vocable;
import com.example.bergsocke.vokabelapp.R;

import java.util.List;

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

    private final Context context = this;

    private MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_vocables);

        // get BoxNr from view SelectBox
        Intent i = getIntent();
        String boxNr = i.getStringExtra("BoxNr");


        // get all Vocables from selected Box
        List<Vocable> list = db.getAllVocablesBox(boxNr);


        // textView vocable and translation
        theWord =(TextView)findViewById(R.id.txt_word);


        // show translation
        btn_showTranslation = (Button)findViewById(R.id.btn_showTranslation);
        btn_showTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        // select an other word
        btn_nextWord = (Button)findViewById(R.id.btn_nextWord);
        btn_nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        // go to MainActivity
        btn_mainMenu = (Button)findViewById(R.id.btn_mainMenu);
        btn_mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}
