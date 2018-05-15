package pt.ulisboa.tecnico.cmu.proj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.DownloadQuizCommand;
import pt.ulisboa.tecnico.cmu.proj.command.ListLocationsCommand;
import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;
import pt.ulisboa.tecnico.cmu.proj.questions.Question;
import pt.ulisboa.tecnico.cmu.proj.questions.QuestionsByMonument;
import pt.ulisboa.tecnico.cmu.proj.quiz.ChildItemsInfo;
import pt.ulisboa.tecnico.cmu.proj.quiz.GroupItemsInfo;


public class QuizActivity extends AppCompatActivity {

    private ArrayList<GroupItemsInfo> groups = new ArrayList<GroupItemsInfo>();

    private MyExpandableListAdapter myExpandableListAdapter;
    private ExpandableListView simpleExpandableListView;
    private Button b;
    private Command c;
    private QuestionsByMonument qm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        loadData();

        b = (Button) findViewById(R.id.button2);




    }

    // load some initial data into out list
    private void loadData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        String user = pref.getString("User", null);
        String sid = pref.getString("sessionId", null);

        // DO NOT FORGET THE ID of the MONUMENT must be subtracted by one
        String json = JsonHandler.DownloadQuizToServer(user, sid,"0");
        Log.d("-----Lit Monuments----- Message", json);
        c = new DownloadQuizCommand( json );
        new DummyTask(QuizActivity.this, c).execute();



    }
    public void updateInterface(String reply) {

        //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        //SharedPreferences.Editor editor = pref.edit();;
        //editor.putString("monument ID", t[0]);
        //editor.commit(); // commit changes

        qm = JsonHandler.DownloadQuizFromServer(reply);

        for(int i=0; i<qm.getSize(); ++i){
            Question q = qm.getQuestion(i);
            GroupItemsInfo gi1 = new GroupItemsInfo(q.getQuestion());

            for(int j=0; j<q.getNumOfChoices(); ++j){
                ChildItemsInfo c1 = new ChildItemsInfo(q.getChoice(j), Character.toString ((char)('A'+j)) );
                gi1.addChild(c1);
            }
            groups.add(i, gi1);
        }

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.lvExp);
        // create the adapter by passing the ArrayList data
        myExpandableListAdapter = new MyExpandableListAdapter(QuizActivity.this, groups);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(myExpandableListAdapter);

        // setOnChildClickListener listener for child row click
        simpleExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //get the group header
                GroupItemsInfo headerInfo = groups.get(groupPosition);
                //get the child info
                ChildItemsInfo detailInfo = headerInfo.getChilds().get(childPosition);

                //display it or do something with it
                //Toast.makeText(getBaseContext(), "ola", Toast.LENGTH_LONG).show();
                CheckBox childItem = (CheckBox)v.findViewById(R.id.check);
                childItem.toggle();
                boolean f = childItem.isChecked();
                // update the flag
                groups.get(groupPosition).changeFlag(detailInfo, f);

                return false;
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                //Save object
                Gson gson = new Gson(); //added in gradle
                String json = gson.toJson(qm);
                editor.putString("questions", json);
                editor.commit();

                finish(); //just ficinish
            }
        });



    }

}