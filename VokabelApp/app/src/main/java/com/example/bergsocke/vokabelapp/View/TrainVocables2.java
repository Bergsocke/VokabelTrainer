package com.example.bergsocke.vokabelapp.View;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bergsocke.vokabelapp.Model.MySQLiteHelper;
import com.example.bergsocke.vokabelapp.R;

/**
 * train vocables 2nd View
 *
 * Created by Bergsocke on 25.01.15.
 */

public class TrainVocables2 extends Activity {

    private Button btn_mainMenu;

    private Button btn_correct;
    private Button btn_not_correct;

    private TextView theWord;
    private TextView translation;

    private final Context context = this;

    private MySQLiteHelper db = new MySQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_vocables2);

//        // get BoxNr from view SelectBox
//        Intent i = getIntent();
//        String boxNr = i.getStringExtra("BoxNr");


        // textView vocable and translation
        theWord =(TextView)findViewById(R.id.txt_word);
        translation = (TextView)findViewById(R.id.txt_translation);


        // translation was correct
        btn_correct = (Button)findViewById(R.id.btn_correct);
        btn_correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        // translation was not correct
        btn_not_correct = (Button)findViewById(R.id.btn_not_correct);
        btn_not_correct.setOnClickListener(new View.OnClickListener() {
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
