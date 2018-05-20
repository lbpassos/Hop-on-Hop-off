package pt.ulisboa.tecnico.cmu.proj.peerscanner;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.ulisboa.tecnico.cmu.proj.AnswerQuizActivity;
import pt.ulisboa.tecnico.cmu.proj.QuizActivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SimWifiP2pBroadcastReceiver extends BroadcastReceiver {

    //private PeerScannerActivity mActivity;
    private Context mActivity;


    public SimWifiP2pBroadcastReceiver(PeerScannerActivity activity) {
        super();
        this.mActivity = activity;
    }
    public SimWifiP2pBroadcastReceiver(QuizActivity activity) {
        super();
        this.mActivity = activity;
    }
    public SimWifiP2pBroadcastReceiver(AnswerQuizActivity activity) {
        super();
        this.mActivity = activity;
    }
    public SimWifiP2pBroadcastReceiver(MsgSenderActivity activity) {
        super();
        this.mActivity = activity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();



        if (SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

        	// This action is triggered when the Termite service changes state:
        	// - creating the service generates the WIFI_P2P_STATE_ENABLED event
        	// - destroying the service generates the WIFI_P2P_STATE_DISABLED event

            int state = intent.getIntExtra(SimWifiP2pBroadcast.EXTRA_WIFI_STATE, -1);
            if (state == SimWifiP2pBroadcast.WIFI_P2P_STATE_ENABLED) {
        		Toast.makeText(mActivity, "WiFi Direct enabled",
        				Toast.LENGTH_SHORT).show();
            } else {
        		Toast.makeText(mActivity, "WiFi Direct disabled",
        				Toast.LENGTH_SHORT).show();
            }

        } else if (SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // Request available peers from the wifi p2p manager. This is an
            // asynchronous call and the calling activity is notified with a
            // callback on PeerListListener.onPeersAvailable()
            if( mActivity instanceof PeerScannerActivity){
                PeerScannerActivity a = (PeerScannerActivity)mActivity;
                a.teste();
            }
            if( mActivity instanceof QuizActivity){
                QuizActivity a = (QuizActivity)mActivity;
                a.teste();
            }
            if( mActivity instanceof AnswerQuizActivity){
                AnswerQuizActivity a = (AnswerQuizActivity)mActivity;
                a.teste();
            }




            Toast.makeText(mActivity, "Peer list changed",
    				Toast.LENGTH_SHORT).show();

        } else if (SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION.equals(action)) {

            if( mActivity instanceof MsgSenderActivity){
                MsgSenderActivity a = (MsgSenderActivity)mActivity;
                a.teste();
            }

        	SimWifiP2pInfo ginfo = (SimWifiP2pInfo) intent.getSerializableExtra(
        			SimWifiP2pBroadcast.EXTRA_GROUP_INFO);
        	ginfo.print();
    		Toast.makeText(mActivity, "Network membership changed",
    				Toast.LENGTH_SHORT).show();

        } else if (SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION.equals(action)) {

        	SimWifiP2pInfo ginfo = (SimWifiP2pInfo) intent.getSerializableExtra(
        			SimWifiP2pBroadcast.EXTRA_GROUP_INFO);
        	ginfo.print();
    		Toast.makeText(mActivity, "Group ownership changed",
    				Toast.LENGTH_SHORT).show();
        }
    }
}
