package com.example.bergsocke.vokabelapp.View;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.bergsocke.vokabelapp.Model.MySQLiteHelper;
import com.example.bergsocke.vokabelapp.Model.Vocable;
import com.example.bergsocke.vokabelapp.R;

import java.util.List;

/**
 * Activity to show all vocables
 *
 * Created by Bergsocke on 04.01.15.
 */

public class ShowAllVocables extends ListActivity {


    private Button btn_mainMenu;

    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_vocables);

        MySQLiteHelper db = new MySQLiteHelper(this);


        // get all Vocables
        List<Vocable> list = db.getAllVocables();

        // Use the SimpleCursorAdapter to show the elements in a ListView
        ArrayAdapter<Vocable> adapter = new ArrayAdapter<Vocable>(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

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
