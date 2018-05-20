package pt.ulisboa.tecnico.cmu.proj.peerscanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.ArrayList;

import pt.inesc.termite.wifidirect.SimWifiP2pBroadcast;
import pt.inesc.termite.wifidirect.SimWifiP2pDevice;
import pt.inesc.termite.wifidirect.SimWifiP2pInfo;
import pt.inesc.termite.wifidirect.SimWifiP2pManager;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.Channel;
import pt.inesc.termite.wifidirect.service.SimWifiP2pService;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocket;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketManager;
import pt.inesc.termite.wifidirect.sockets.SimWifiP2pSocketServer;
import pt.inesc.termite.wifidirect.SimWifiP2pDeviceList;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.PeerListListener;
import pt.inesc.termite.wifidirect.SimWifiP2pManager.GroupInfoListener;
import pt.ulisboa.tecnico.cmu.proj.R;
import pt.ulisboa.tecnico.cmu.proj.quiz.RankingCurrentQuiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class MsgSenderActivity extends Activity implements
		PeerListListener, GroupInfoListener {

    public static final String TAG = "msgsender";

    private SimWifiP2pManager mManager = null;
    private Channel mChannel = null;
    private Messenger mService = null;
	private boolean mBound = false;
	private SimWifiP2pSocketServer mSrvSocket = null;
	private SimWifiP2pSocket mCliSocket = null;
	private TextView mTextInput;
	private TextView mTextOutput;
    private SimWifiP2pBroadcastReceiver mReceiver;

    private ArrayList<String> peersStr;
	private final int MY_REQUEST_CODE = 1;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// initialize the UI
		setContentView(R.layout.main);
		//guiSetButtonListeners();
		//guiUpdateInitState();
		mTextOutput = (TextView) findViewById(R.id.editText2);
		mTextOutput.setEnabled(true);
		mTextOutput.setText("");
		findViewById(R.id.idConnectButton).setOnClickListener(listenerConnectButton);

		peersStr = new ArrayList<String>();
		//Log.d("ON CREATE", "_________________________");
		// initialize the WDSim API
		SimWifiP2pSocketManager.Init(getApplicationContext());
		
		// register broadcast receiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
		filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
		filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
		filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
		mReceiver = new SimWifiP2pBroadcastReceiver(this);
		registerReceiver(mReceiver, filter);


		//WIFI ON
		Intent intent = new Intent(MsgSenderActivity.this, SimWifiP2pService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		mBound = true;

		new IncommingCommTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		//guiUpdateDisconnectedState();

	}

	public void teste(){
		mManager.requestGroupInfo(mChannel, MsgSenderActivity.this);
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

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_STATE_CHANGED_ACTION);
		filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_PEERS_CHANGED_ACTION);
		filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_NETWORK_MEMBERSHIP_CHANGED_ACTION);
		filter.addAction(SimWifiP2pBroadcast.WIFI_P2P_GROUP_OWNERSHIP_CHANGED_ACTION);
		mReceiver = new SimWifiP2pBroadcastReceiver(this);
		registerReceiver(mReceiver, filter);
	}




	private OnClickListener listenerConnectButton = new OnClickListener() {
		@Override
		public void onClick(View v) {
			findViewById(R.id.idConnectButton).setEnabled(true);

			Bundle b = new Bundle(); //passing values to other activity
			b.putStringArrayList("arrayIP", peersStr);
			Intent i=new Intent(MsgSenderActivity.this, NeighbourAdd.class);
			i.putExtras(b);
			startActivityForResult(i, MY_REQUEST_CODE);

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK) {
			//Log.d("HI","==============================");
			String res = intent.getStringExtra("arrayIP");
			//Log.d("HI=================",res);
			int pos = Integer.valueOf(res);
			String myID = peersStr.get(pos);
			//Log.d("HI================",myID);
			new OutgoingCommTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, myID);

		}
	}



	private ServiceConnection mConnection = new ServiceConnection() {
		// callbacks for service binding, passed to bindService()

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = new Messenger(service);
			mManager = new SimWifiP2pManager(mService);
			mChannel = mManager.initialize(getApplication(), getMainLooper(), null);
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mService = null;
			mManager = null;
			mChannel = null;
			mBound = false;
		}
	};


	/*
	 * Asynctasks implementing message exchange
	 */
	
	public class IncommingCommTask extends AsyncTask<Void, String, Void> {

		@Override
		protected Void doInBackground(Void... params) {
			
			Log.d(TAG, "IncommingCommTask started (" + this.hashCode() + ").");

			try {
				mSrvSocket = new SimWifiP2pSocketServer(
						Integer.parseInt(getString(R.string.port)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (!Thread.currentThread().isInterrupted()) {
				try {
					SimWifiP2pSocket sock = mSrvSocket.accept();
                    try {
                        BufferedReader sockIn = new BufferedReader(
                                new InputStreamReader(sock.getInputStream()));
                        String st = sockIn.readLine();
                        publishProgress(st);
                        sock.getOutputStream().write(("\n").getBytes());
                    } catch (IOException e) {
                        Log.d("Error reading socket:", e.getMessage());
                    } finally {
                        sock.close();
                    }
				} catch (IOException e) {
					Log.d("Error socket:", e.getMessage());
					break;
					//e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			String[] splited = values[0].split(":");

			Log.d("ON PROGRESS -----", values[0]);
			//mTextOutput.append(values[0] + "\n");
			new AlertDialog.Builder(MsgSenderActivity.this)
					.setTitle("Msg Received")
					.setMessage("Progress from user: " + splited[0] +"\n" + "Questions answered: " + splited[1] + "\n" + "Corrected Answers: " + splited[2] + "\n" + "Response Time [s]: " + Long.parseLong(splited[3],10)/1000 + "\n" + "Monument: " + splited[4])
					.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					})
					.show();
		}
	}

	public class OutgoingCommTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			mTextOutput.setText("Connecting...");
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				mCliSocket = new SimWifiP2pSocket(params[0],
						Integer.parseInt(getString(R.string.port)));
			} catch (UnknownHostException e) {
				return "Unknown Host:" + e.getMessage();
			} catch (IOException e) {
				return "IO error:" + e.getMessage();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
                //guiUpdateDisconnectedState();
				//new SendCommTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"ola");
				mTextOutput.setText(result);
				/*new AlertDialog.Builder(MsgSenderActivity.this)
						.setTitle("Msg Received")
						.setMessage(result)
						.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						})
						.show();*/

			} else {
                /*findViewById(R.id.idDisconnectButton).setEnabled(true);
                findViewById(R.id.idConnectButton).setEnabled(false);
                findViewById(R.id.idSendButton).setEnabled(true);
                mTextInput.setHint("");
                mTextInput.setText("");*/

				SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
				Gson gson = new Gson();
				String json = pref.getString("lastRankingMonument", "");

				RankingCurrentQuiz rq = gson.fromJson(json, RankingCurrentQuiz.class);
				String user = pref.getString("User",""); //user

				//User NumQuestions NumAnswers MonumentID
				String test = user + ":" + rq.getNumQuestions() + ":" + rq.getNumAnswers() + ":" + rq.getTimeStamp() + ":" + rq.getmonumenDesc();
				Log.d("SENDING===============", test);
				new SendCommTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,test);
                mTextOutput.setText("");

            }
		}
	}

	public class SendCommTask extends AsyncTask<String, String, Void> {

		@Override
		protected Void doInBackground(String... msg) {
            try {
                mCliSocket.getOutputStream().write((msg[0] + "\n").getBytes());
                BufferedReader sockIn = new BufferedReader(
                        new InputStreamReader(mCliSocket.getInputStream()));
                sockIn.readLine();
                mCliSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCliSocket = null;
            return null;
        }

		@Override
		protected void onPostExecute(Void result) {
            //mTextInput.setText("");
            //guiUpdateDisconnectedState();
        }
	}

	/*
	 * Listeners associated to Termite
	 */
	
	@Override
	public void onPeersAvailable(SimWifiP2pDeviceList peers) {

	}

	@Override
	public void onGroupInfoAvailable(SimWifiP2pDeviceList devices, 
			SimWifiP2pInfo groupInfo) {
		
		// compile list of network members
		//StringBuilder peersStr = new StringBuilder();
		peersStr.clear();
		for (String deviceName : groupInfo.getDevicesInNetwork()) {
			SimWifiP2pDevice device = devices.getByName(deviceName);
			//String devstr = "" + deviceName + " (" +	((device == null)?"??":device.getVirtIp()) + ")\n";
			String devstr = device.getVirtIp();
			peersStr.add(devstr);
		}


	}


}
