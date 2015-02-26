package com.example.bergsocke.vokabelapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bergsocke.vokabelapp.R;

/**
 * Main (Start) Activity
 *
 * Created by Bergsocke on 04.01.15.
 */

// Activites sind Klassen, die jeweils App-Bildschirme verwalten
// organisiert die Layouts und den Zugriff auf die darin enthaltenen Views

public class MainActivity extends Activity implements View.OnClickListener {

    private Button btn_train;
    private Button btn_add;
    private Button btn_delete;
    private Button btn_showAll;
    private Button btn_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button - Vokabel trainieren
        btn_train = (Button)findViewById(R.id.btn_train);
        btn_train.setOnClickListener(this);

        // Button - Anzeige aller vorhandenen Vokabeln
        btn_showAll = (Button)findViewById(R.id.btn_showAll);
        btn_showAll.setOnClickListener(this);

        // Button - Vokabel hinzufügen
        btn_add = (Button)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);

        // Button - Vokabel ändern
        btn_edit = (Button)findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(this);

        // Button - Vokabel löschen
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        // Intents, um Aktivitäten innerhalb einer Anwendung aufzurufen

        if (v == btn_train){
            Intent intent = new Intent(this, SelectBox.class);
            startActivity(intent);
        }
        else if (v == btn_showAll) {
            Intent intent = new Intent(this,ShowAllVocables.class);
            startActivity(intent);
        }
        else if (v == btn_add) {
            Intent intent = new Intent(this, AddVocables.class);
            startActivity(intent);
        }
        else if (v == btn_edit) {
            Intent intent = new Intent(this, EditVocable.class);
            startActivity(intent);
        }
        else if (v == btn_delete) {
            Intent intent = new Intent(this, DeleteVocable.class);
            startActivity(intent);
        }
    }
}
