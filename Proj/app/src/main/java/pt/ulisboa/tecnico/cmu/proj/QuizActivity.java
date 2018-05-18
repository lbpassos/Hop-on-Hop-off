package pt.ulisboa.tecnico.cmu.proj;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.Channel;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.PeerListListener;


import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.DownloadQuizCommand;
import pt.ulisboa.tecnico.cmu.proj.command.ListLocationsCommand;
import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;
import pt.ulisboa.tecnico.cmu.proj.peerscanner.PeerScannerActivity;
import pt.ulisboa.tecnico.cmu.proj.peerscanner.SimWifiP2pBroadcastReceiver;
import pt.ulisboa.tecnico.cmu.proj.questions.Question;
import pt.ulisboa.tecnico.cmu.proj.questions.QuestionsByMonument;
import pt.ulisboa.tecnico.cmu.proj.quiz.ChildItemsInfo;
import pt.ulisboa.tecnico.cmu.proj.quiz.GroupItemsInfo;


public class QuizActivity extends AppCompatActivity implements
        PeerListListener {

    private ArrayList<GroupItemsInfo> groups = new ArrayList<GroupItemsInfo>();

    private MyExpandableListAdapter myExpandableListAdapter;
    private ExpandableListView simpleExpandableListView;
    private Button b;
    private Command c;
    private QuestionsByMonument qm;

    //Teste
    private SimWifiP2pManager mManager = null;
    private SimWifiP2pManager.Channel mChannel = null;
    private boolean mBound = false;
    private SimWifiP2pBroadcastReceiver mReceiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //loadData();

        b = (Button) findViewById(R.id.button2);

        IntentFilter filter = new IntentFilter();
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
        filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
        mReceiver = new SimWifiP2pBroadcastReceiver(this);
        registerReceiver(mReceiver, filter);

        Intent intent = new Intent(QuizActivity.this, SimWifiP2pService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mBound = true;




    }

    // load some initial data into out list
    private void loadData(int mId) {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        String user = pref.getString("User", null);
        String sid = pref.getString("sessionId", null);
        editor.putString("Download ID", String.valueOf(mId)); //store monumentId download
        editor.commit(); // commit changes

        // DO NOT FORGET THE ID of the MONUMENT must be subtracted by one
        String json = JsonHandler.DownloadQuizToServer(user, sid, String.valueOf(mId-1));
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

                finish(); //just ficinish
            }
        });



    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    public void teste(){
        mManager.requestPeers(mChannel, QuizActivity.this);
    }

    @Override
    public void onPeersAvailable(SimWifiP2pDeviceList peers) {

       StringBuilder peersStr = new StringBuilder();

        // compile list of devices in range
        for (SimWifiP2pDevice device : peers.getDeviceList()) {
            try{
                double d = Double.parseDouble(device.deviceName); //check if beacon is a number
            }
            catch(NumberFormatException nfe){
                continue;
            }
            int id_monument = Integer.parseInt(device.deviceName);
            loadData(id_monument);
            return;
        }

        // display list of devices in range
		new AlertDialog.Builder(this)
	    .setTitle("Not in Range of a Monument")
	    .setMessage(peersStr.toString())
	    .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        })
	     .show();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        // callbacks for service binding, passed to bindService()

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mManager = new SimWifiP2pManager(new Messenger(service));
            mChannel = mManager.initialize(getApplication(), getMainLooper(), null);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mManager = null;
            mChannel = null;
            mBound = false;
        }
    };

}