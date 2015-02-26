package com.example.bergsocke.vokabelapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bergsocke.vokabelapp.R;

/**
 * Activity to select vocable box
 *
 * Created by Bergsocke on 04.01.15.
 *
 */

public class SelectBox extends Activity implements View.OnClickListener{

    private Button btn_box1;
    private Button btn_box2;
    private Button btn_box3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_box);

        btn_box1 = (Button)findViewById(R.id.btn_box1);
        btn_box1.setOnClickListener(this);

        btn_box2 = (Button)findViewById(R.id.btn_box2);
        btn_box2.setOnClickListener(this);

        btn_box3 = (Button)findViewById(R.id.btn_box3);
        btn_box3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v == btn_box1){
            String boxNr = "1";
            Intent intent = new Intent(this, TrainVocables.class);
            // send boxNr to view TrainVocables
            intent.putExtra("BoxNr", boxNr);
            startActivity(intent);
        }

        else if (v == btn_box2) {
            String boxNr = "2";
            Intent intent = new Intent(this, TrainVocables.class);
            // send boxNr to view TrainVocables
            intent.putExtra("BoxNr", boxNr);
            startActivity(intent);
        }

        else if (v == btn_box3) {
            String boxNr = "3";
            Intent intent = new Intent(this, TrainVocables.class);
            // send boxNr to view TrainVocables
            intent.putExtra("BoxNr", boxNr);
            startActivity(intent);
        }
    }
}
