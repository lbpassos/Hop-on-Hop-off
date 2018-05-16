package pt.ulisboa.tecnico.cmu.proj;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.DownloadQuizCommand;
import pt.ulisboa.tecnico.cmu.proj.command.UploadQuizCommand;
import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;
import pt.ulisboa.tecnico.cmu.proj.questions.Question;
import pt.ulisboa.tecnico.cmu.proj.questions.QuestionsByMonument;
import pt.ulisboa.tecnico.cmu.proj.quiz.ChildItemsInfo;
import pt.ulisboa.tecnico.cmu.proj.quiz.GroupItemsInfo;
import pt.ulisboa.tecnico.cmu.proj.quiz.RankingCurrentQuiz;


public class AnswerQuizActivity extends AppCompatActivity {

    private ArrayList<GroupItemsInfo> groups = new ArrayList<GroupItemsInfo>();

    private MyExpandableListAdapter myExpandableListAdapter;
    private ExpandableListView simpleExpandableListView;
    private Button b;
    private Button buttonSend;
    private Command c;
    private QuestionsByMonument qm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answerquiz);

        loadData();

        b = (Button) findViewById(R.id.button2);
        buttonSend = (Button) findViewById(R.id.buttonSend);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Save if necessary
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                for(int i=0; i<groups.size(); ++i){
                    ArrayList<ChildItemsInfo> childs = groups.get(i).getChilds();
                    for(int j=0; j<childs.size(); ++j){
                        boolean status = childs.get(j).getFlag();
                        qm.getQuestion(i).getChoiceObj(j).setStatus( status );
                    }
                }

                //Save object
                Gson gson = new Gson(); //added in gradle
                String json = gson.toJson(qm);
                editor.putString("questions", json);
                editor.commit();

                finish();

            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //Send if necessary
                //Send file only if current monumentID != monumentID saved
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

                String user = pref.getString("User", null);
                String sid = pref.getString("sessionId", null);

                // DO NOT FORGET THE ID of the MONUMENT must be subtracted by one
                String json = JsonHandler.UploadAnswerQuizToServer(user, sid, "2", qm);
                //Log.d("-----Lit Monuments----- Message", json);
                c = new UploadQuizCommand( json );
                new DummyTask(AnswerQuizActivity.this, c).execute();


            }
        });



    }

    // load some initial data into out list
    private void loadData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode


        //Load object
        Gson gson = new Gson();
        String json = pref.getString("questions", "");
        qm = gson.fromJson(json, QuestionsByMonument.class);
        if(qm==null){
            finish();
            return;
        }
        for(int i=0; i<qm.getSize(); ++i){
            Question q = qm.getQuestion(i);
            GroupItemsInfo gi1 = new GroupItemsInfo(q.getQuestion());

            for(int j=0; j<q.getNumOfChoices(); ++j){
                ChildItemsInfo c1 = new ChildItemsInfo(q.getChoice(j), Character.toString ((char)('A'+j)) );
                c1.setFlag( q.getChoiceObj(j).getStatus() );
                gi1.addChild(c1);
            }
            groups.add(i, gi1);
        }

        //get reference of the ExpandableListView
        simpleExpandableListView = (ExpandableListView) findViewById(R.id.lvExp);
        // create the adapter by passing the ArrayList data
        myExpandableListAdapter = new MyExpandableListAdapter(AnswerQuizActivity.this, groups);
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
    }

    public void updateInterface(String reply) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String[] tmp = JsonHandler.UploadAnswerQuizFromServer(reply);
        RankingCurrentQuiz rq = new RankingCurrentQuiz(tmp[0], tmp[1], tmp[2]);

        Gson gson = new Gson(); //added in gradle
        String json = gson.toJson(rq);
        editor.putString("lastRankingMonument", json);
        editor.commit();

        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle(rq.getmonumenDesc());

        String alert1 = "Questions Answered: " + rq.getNumQuestions();
        String alert2 = "Correct Questions: " + rq.getNumAnswers();

        dlgAlert.setMessage(alert1 + "\n" + alert2);
        dlgAlert.create().show();
    }
}