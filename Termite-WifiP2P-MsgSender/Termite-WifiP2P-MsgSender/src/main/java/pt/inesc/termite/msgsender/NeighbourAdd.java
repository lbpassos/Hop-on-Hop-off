package pt.inesc.termite.msgsender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ist426300 on 18-05-2018.
 */

public class NeighbourAdd extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_neighbouradd);

        // Get reference of widgets from XML layout
        ListView lv = (ListView) findViewById(R.id.lv);
        final TextView tv = (TextView) findViewById(R.id.tv);

        Bundle b = this.getIntent().getExtras();
        ArrayList<String> ipNeighbour=b.getStringArrayList("arrayIP");

        // Initializing a new String Array
        /*String[] fruits = new String[] {
                "Japanese Persimmon",
                "Kakadu lime",
                "Illawarra Plum",
                "Malay Apple ",
                "Mamoncillo",
                "Persian lime"
        };*/

        // Create a List from String Array elements
        //List<String> fruits_list = new ArrayList<String>(Arrays.asList(fruits));

        // Create a ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, ipNeighbour);

        // Populate ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);

        // Set an item click listener for ListView
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                //String selectedItem = (String) parent.getItemAtPosition(position);

                // Display the selected item text on TextView
                //tv.setText("Your favorite : " + selectedItem);
                Bundle b = new Bundle(); //passing values to other activity
                b.putString("IP_position", String.valueOf(position));
                //b.putStringArrayList("arrayIP", peersStr);
                Intent i=new Intent(NeighbourAdd.this, MsgSenderActivity.class);
                i.putExtras(b);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //caller activity eliminated
                startActivity(i);


            }
        });
    }
}

