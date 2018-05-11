package pt.ulisboa.tecnico.cmu.proj;

/**
 * Created by ist426300 on 09-05-2018.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.ListLocationsCommand;
import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;


public class ListMonumentsActivity extends AppCompatActivity {
    ArrayList arrayList;
    ArrayAdapter<String> itemsAdapter;
    private Command c;

    ListView listView; // ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listmonuments);

        arrayList = new ArrayList();

        listView = findViewById(R.id.listView); //connect ListView to layout

        //Create a new ArrayAdapter in this activity
        //With the layout: simple_list_item_1 (layout for each item)
        //Bound to arrayList
        itemsAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(itemsAdapter); //connect ListView to ArrayAdapter
        load();
    }

    public void load(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        String user = pref.getString("User", null);
        String sid = pref.getString("sessionId", null);

        String json = JsonHandler.ListLocationsToServer(user, sid);
        Log.d("-----Lit Monuments----- Message", json);

        c = new ListLocationsCommand( json );
        new DummyTask(ListMonumentsActivity.this, c).execute();

    }

    public void updateInterface(String reply) {

        String[] t= JsonHandler.ListLocationsFromServer(reply);
        //Log.d("-----Mainactivity----- Message", t[1]);

        for(int i=0; i<t.length; ++i){
            itemsAdapter.insert(t[i],i);
        }
    }

}