package com.example.bergsocke.vokabelapp.View;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bergsocke.vokabelapp.Model.MySQLiteHelper;
import com.example.bergsocke.vokabelapp.Model.Vocable;
import com.example.bergsocke.vokabelapp.R;

import java.util.List;

/**
 * Update selected vovable
 *
 * Created by Bergsocke on 04.01.15.
 */

public class EditVocable extends ListActivity {

    private Button btn_mainMenu;

    private Vocable vocableToUpdate;

    private EditText updatedWord;
    private EditText updatedTranslation;
    private EditText updatedBox;

    private EditText oldWorld;
    private EditText oldTranslation;
    private EditText oldBox;


    private MySQLiteHelper db = new MySQLiteHelper(this);

    private final Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vocable);

        // get all Vocables
        List<Vocable> list = db.getAllVocables();

        // Use the SimpleCursorAdapter to show the elements in a ListView
        final ArrayAdapter<Vocable> adapter = new ArrayAdapter<Vocable>(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

        ListView lv = (ListView)findViewById(android.R.id.list);
        lv.setAdapter(adapter);

        // Get selected element
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                vocableToUpdate = adapter.getItem(position);

                // Create and show AlertDialog

                LayoutInflater inflater = LayoutInflater.from(context);
                final View textEntry = inflater.inflate(R.layout.dialog_edit, null);

                // AlertDialog Text mit alten Vokabeldaten vorbelegen
                oldWorld = (EditText) textEntry.findViewById(R.id.txt_word);
                oldTranslation = (EditText) textEntry.findViewById(R.id.txt_translation);
                oldBox = (EditText) textEntry.findViewById(R.id.txt_box);

                oldWorld.setText(vocableToUpdate.getTheWord());
                oldTranslation.setText(vocableToUpdate.getTranslation());
                oldBox.setText(vocableToUpdate.getBoxNr());

                final AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setView(textEntry)
                        .setPositiveButton(R.string.btn_save, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // get update data
                                updatedWord = (EditText) textEntry.findViewById(R.id.txt_word);
                                updatedTranslation = (EditText) textEntry.findViewById(R.id.txt_translation);
                                updatedBox = (EditText) textEntry.findViewById(R.id.txt_box);

                                // set new values
                                vocableToUpdate.setTheWord(updatedWord.getText().toString());
                                vocableToUpdate.setTranslation(updatedTranslation.getText().toString());

                                // check new box nr.
                                String tempBox = updatedBox.getText().toString();
                                int nr = Integer.parseInt(tempBox);

                                // if new box nr. > 3 don't change the box nr.
                                if(nr < 3){
                                    // set new value
                                    vocableToUpdate.setBoxNr(updatedBox.getText().toString());
                                }
                                else {
                                    // don't change the box nr
                                }

                                // save the updated vocable to the database
                                db.updateVocable(vocableToUpdate);

                                // update ListView
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
