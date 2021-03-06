package com.example.bergsocke.vokabelapp.View;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bergsocke.vokabelapp.Model.MySQLiteHelper;
import com.example.bergsocke.vokabelapp.Model.Vocable;
import com.example.bergsocke.vokabelapp.R;

import java.util.List;

/**
 * Activity to delete selected vocable
 *
 * Created by Bergsocke on 04.01.15.
 */

// ListActivity - class for Activities that feature a ListView bound to a data source

public class DeleteVocable extends ListActivity {

    private Button btn_mainMenu;

    private final Context context = this;

    private MySQLiteHelper db = new MySQLiteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_vocables);

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
                // get vocable
                Vocable vocableToDelete = adapter.getItem(position);

                // delete vocable from database
                db.deleteVocable(vocableToDelete.getId());

                // Message, dass Vokabel gelöscht wurde
                Toast message = Toast.makeText(context, context.getString(R.string.txt_delete), Toast.LENGTH_SHORT);
                message.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                message.show();

                // update ListView
                adapter.remove(vocableToDelete);
                adapter.notifyDataSetChanged();
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
