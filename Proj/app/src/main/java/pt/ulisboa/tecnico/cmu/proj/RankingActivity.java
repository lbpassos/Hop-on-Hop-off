package pt.ulisboa.tecnico.cmu.proj;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmu.proj.command.Command;

import pt.ulisboa.tecnico.cmu.proj.command.GetRankingCommand;
import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;

import pt.ulisboa.tecnico.cmu.proj.ranking.ChildItemsInfo;
import pt.ulisboa.tecnico.cmu.proj.ranking.GroupItemsInfo;
import pt.ulisboa.tecnico.cmu.proj.ranking.RankingToShow;


public class RankingActivity extends AppCompatActivity {

    private ArrayList<GroupItemsInfo> groups = new ArrayList<GroupItemsInfo>();

    private MyExpandableListAdapter_ranking myExpandableListAdapter;
    private ExpandableListView simpleExpandableListView;
    private Command c;
    private RankingToShow rs;
    private TextView globalRank;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_ranking);

        globalRank = (TextView) findViewById(R.id.rankTop);

        loadData();
    }

    // load some initial data into out list
    private void loadData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        String user = pref.getString("User", null);
        String sid = pref.getString("sessionId", null);

        // DO NOT FORGET THE ID of the MONUMENT must be subtracted by one
        String json = JsonHandler.GetRankingToServer(user, sid);
        //Log.d("-----Lit Monuments----- Message", json);
        c = new GetRankingCommand( json );
        new DummyTask(RankingActivity.this, c).execute();



    }
    public void updateInterface(String reply) {

        //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        //SharedPreferences.Editor editor = pref.edit();;
        //editor.putString("monument ID", t[0]);
        //editor.commit(); // commit changes

        rs = JsonHandler.GetRankingFromServer(reply);

        for(int i=0; i<rs.size(); ++i){

            GroupItemsInfo gi1 = new GroupItemsInfo( rs.getMonument(i) );
            ChildItemsInfo c1 = new ChildItemsInfo( rs.getNumQuestions(i), rs.getNumCorrectQuestions(i), rs.geTotal(i) );
            gi1.addChild(c1);

            groups.add(i, gi1);
        }
        globalRank.setText("Global Rank: " + rs.getGlobalRanking());

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.lvExpRanking);
        // create the adapter by passing the ArrayList data
        myExpandableListAdapter = new MyExpandableListAdapter_ranking(RankingActivity.this, groups);
        // attach the adapter to the expandable list view
        simpleExpandableListView.setAdapter(myExpandableListAdapter);

    }

}