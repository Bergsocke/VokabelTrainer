package com.example.bergsocke.vokabelapp.View;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bergsocke.vokabelapp.Model.MySQLiteHelper;
import com.example.bergsocke.vokabelapp.Model.Vocable;
import com.example.bergsocke.vokabelapp.R;

import java.util.List;

/**
 * Insert new vocable into database
 *
 * Created by Bergsocke on 04.01.15.
 */

public class AddVocables extends ListActivity {

    private EditText theWord;
    private EditText translation;
    private EditText boxNr;

    private String newWord;
    private String newTranslation;
    private String newBoxNr;

    private Button btn_add;
    private Button btn_mainMenu;

    private final Context context = this;

    private MySQLiteHelper db = new MySQLiteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_vocable);

        // get all Vocables
        List<Vocable> list = db.getAllVocables();

        // Use the SimpleCursorAdapter to show the elements in a ListView
        final ArrayAdapter<Vocable> adapter = new ArrayAdapter<Vocable>(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

        ListView lv = (ListView)findViewById(android.R.id.list);
        lv.setAdapter(adapter);

        btn_add = (Button)findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create and show AlertDialog

                LayoutInflater inflater = LayoutInflater.from(context);
                final View textEntry = inflater.inflate(R.layout.dialog_edit, null);

                final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setView(textEntry)
                        .setPositiveButton(R.string.btn_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                theWord = (EditText) textEntry.findViewById(R.id.txt_word);
                                translation = (EditText) textEntry.findViewById(R.id.txt_translation);
                                boxNr = (EditText) textEntry.findViewById(R.id.txt_box);


                                newWord = theWord.getText().toString();
                                newTranslation = translation.getText().toString();
                                newBoxNr = boxNr.getText().toString();

                                Vocable vocableNew = new Vocable(newWord, newTranslation, newBoxNr);

                                // insert vocable
                                db.addVocable(vocableNew);

                                // update ListView
                                adapter.add(vocableNew);
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder.create();
                builder.show();

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
