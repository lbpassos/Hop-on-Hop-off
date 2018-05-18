package pt.ulisboa.tecnico.cmu.proj;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.LogOutCommand;
import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;
import pt.ulisboa.tecnico.cmu.proj.peerscanner.PeerScannerActivity;

public class programActivity extends AppCompatActivity {

    private TextView title;

    private String[] tags;
    private ListView lv;
    private ArrayAdapter<String> itemsadapter;
    private int selectedItem;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Command c;
    private String user;
    private String sid;

    @Override
    protected void onDestroy(){
        editor.clear();
        editor.commit(); // commit changes
        super.onDestroy();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);



        title = findViewById(R.id.title_text);
        Intent i = getIntent();

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        user = pref.getString("User", null);
        sid = pref.getString("sessionId", null);
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
                Intent intent;
                // Starting a new async task
                switch(selectedItem){
                    case 0: // List Tour Locations
                        Log.d("ListView", "------ List Tour Locations");
                        //intent = new Intent(programActivity.this, PeerScannerActivity.class);
                        intent = new Intent(programActivity.this, ListMonumentsActivity.class);
                        startActivity(intent);
                        break;
                    case 1: //Download Quiz
                        //Log.d("ListView", "----- Download Quiz");
                        intent = new Intent(programActivity.this, QuizActivity.class);
                        startActivity(intent);
                        break;
                    case 2: //Answer Quiz
                        //load do quiz
                        //Log.d("ListView", "----- Answer Quiz");
                        intent = new Intent(programActivity.this, AnswerQuizActivity.class);
                        startActivity(intent);
                        break;
                    case 3: //Check Ranking
                        //Log.d("ListView", "----- Check Ranking");
                        intent = new Intent(programActivity.this, RankingActivity.class);
                        startActivity(intent);
                        break;
                    case 4: //Share Progress
                        Log.d("ListView", "----- Share Progress");
                        break;
                    case 5: //Log Out
                        //Terminate connection with the server
                        logOut();

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

    public void updateInterface(String reply) {

        String t= JsonHandler.LogOutFromServer(reply);
        //Log.d("-----Mainactivity----- Message", t[1]);


        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Log out");
        dlgAlert.setMessage(t);

        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) { //destroy and back

                //Intent intent = new Intent(this, Menu.class);
                //Log.d("----- Sign_InActivity ------", "AQUI");
                Intent intent = new Intent(programActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //caller activity eliminated
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //this activity will become the start of a new task on this history stack.
                startActivity(intent);
            }
        });

        AlertDialog dialog = dlgAlert.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    public void logOut(){
        editor.clear();
        editor.commit(); // commit changes

        String json = JsonHandler.LogouToServer(user, sid);
        c = new LogOutCommand( json );
        new DummyTask(programActivity.this, c).execute();

    }
}
