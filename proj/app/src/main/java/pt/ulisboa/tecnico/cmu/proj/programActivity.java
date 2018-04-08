package pt.ulisboa.tecnico.cmu.proj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class programActivity extends AppCompatActivity {

    private TextView title;
    private webserver_simulator wb;

    private String[] tags;
    private ListView lv;
    private ArrayAdapter<String> itemsadapter;
    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);


        title = findViewById(R.id.title_text);
        Intent i = getIntent();
        wb = (webserver_simulator)i.getSerializableExtra("webserverObject");
        String user = i.getStringExtra("User");
        title.setText("Hello " + user);

        tags = new String[] {
                "List Tour Locations",
                "Download Quiz",
                "Answer Quiz",
                "Check Ranking",
                "Share Progress",
                "Log Out"
        };
        // Getting object reference to listview of main.xml
        ListView listView = findViewById(R.id.listTopics);

        itemsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice,tags);
        listView.setAdapter(itemsadapter);

        lv = findViewById(R.id.listTopics);
        Button btnSubmit = new Button(this); //Add Button at the bottom of List View
        btnSubmit.setText("Execute");

        // Adding Load More button to lisview at bottom
        lv.addFooterView(btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Starting a new async task
                switch(selectedItem){
                    case 0: // List Tour Locations
                        Log.d("ListView", "------ List Tour Locations");
                        break;
                    case 1: //Download Quiz
                        Log.d("ListView", "----- Download Quiz");
                        break;
                    case 2: //Answer Quiz
                        Log.d("ListView", "----- Answer Quiz");
                        break;
                    case 3: //Check Ranking
                        Log.d("ListView", "----- Check Ranking");
                        break;
                    case 4: //Share Progress
                        Log.d("ListView", "----- Share Progress");
                        break;
                    case 5: //Log Out
                        //Terminate connection with the server
                        Intent intent = new Intent(programActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    default:
                        Log.d("ListView", "-----ERROR-----");

                }

                Log.d("BBBBBBB", "Click");
            }
        });

        //List for clicks in the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("In ListView",String.valueOf(position));
                selectedItem = position;
            }
        });
    }
}
