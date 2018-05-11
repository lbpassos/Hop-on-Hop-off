package pt.ulisboa.tecnico.cmu.proj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import pt.ulisboa.tecnico.cmu.proj.quiz.ChildItemsInfo;
import pt.ulisboa.tecnico.cmu.proj.quiz.GroupItemsInfo;


public class QuizActivity extends AppCompatActivity {

    private ArrayList<GroupItemsInfo> groups = new ArrayList<GroupItemsInfo>();

    private MyExpandableListAdapter myExpandableListAdapter;
    private ExpandableListView simpleExpandableListView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // add data for displaying in expandable list view
        loadData();

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


    }

    // load some initial data into out list
    private void loadData() {


        String mQuestions[] = {

                "What is capital of Portugal?",
                "What is capital of Bhutan?",
                "When did Mobile Phone arrive in Bhutan?",
                "When did Gopal come to Portugal?",
                "The first person to take potato to Bhutan was from which country?",
                "Ola?"
        };

        String mChoices[][] = {

                {"Porto", "Faro", "Lisbon", "Madeira"},
                {"Thimphu", "Paro", "Dhaka", "Lisboa"},
                {"1960", "2003", "1999", "1972"},
                {"2015", "2017", "2012", "2016"},
                {"Portugal", "Spain", "United Kingdom United Kingdom", "Canada"},
                { "D"}
        };

        for(int i=0; i<mQuestions.length; ++i){
            GroupItemsInfo gi1 = new GroupItemsInfo(mQuestions[i]);
            for(int j=0; j<mChoices[i].length; ++j){
                    ChildItemsInfo c1 = new ChildItemsInfo(mChoices[i][j], Character.toString ((char)('A'+j)) );
                gi1.addChild(c1);
            }
            groups.add(i, gi1);
        }
        /*ChildItemsInfo c1 = new ChildItemsInfo("Ola", "A");
        ChildItemsInfo c2 = new ChildItemsInfo("Hi", "B");

        GroupItemsInfo gi1 = new GroupItemsInfo("Cumprimen");
        gi1.addChild(c1);
        gi1.addChild(c2);

        groups.add(0, gi1);

        c1 = new ChildItemsInfo("Tes", "A");
        gi1 = new GroupItemsInfo("Sun");
        gi1.addChild(c1);
        groups.add(1, gi1);
*/

    }
/*

    // here we maintain songsList and songs names
    private int addProduct(String songsListName, String songName) {

        int groupPosition = 0;

        //check the hashmap if the group already exists
        GroupItemsInfo headerInfo = songsList.get(songsListName);
        //add the group if doesn't exists
        if (headerInfo == null) {
            headerInfo = new GroupItemsInfo();
            headerInfo.setName(songsListName);
            songsList.put(songsListName, headerInfo);
            deptList.add(headerInfo);
        }

        // get the children for the group
        ArrayList<ChildItemsInfo> productList = headerInfo.getSongName();
        // size of the children list
        int listSize = productList.size();
        // add to the counter
        listSize++;

        // create a new child and add that to the group
        ChildItemsInfo detailInfo = new ChildItemsInfo();
        detailInfo.setName(songName);
        productList.add(detailInfo);
        headerInfo.setPlayerName(productList);

        // find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;
    }*/

}