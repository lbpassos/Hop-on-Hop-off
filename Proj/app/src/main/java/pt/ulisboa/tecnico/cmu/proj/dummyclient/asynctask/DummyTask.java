package pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import pt.ulisboa.tecnico.cmu.proj.ListMonumentsActivity;
import pt.ulisboa.tecnico.cmu.proj.QuizActivity;
import pt.ulisboa.tecnico.cmu.proj.Sign_InActivity;
import pt.ulisboa.tecnico.cmu.proj.command.AnotherHello;
import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.DownloadQuizCommand;
import pt.ulisboa.tecnico.cmu.proj.command.HelloCommand;
import pt.ulisboa.tecnico.cmu.proj.MainActivity;
import pt.ulisboa.tecnico.cmu.proj.command.ListLocationsCommand;
import pt.ulisboa.tecnico.cmu.proj.command.LogOutCommand;
import pt.ulisboa.tecnico.cmu.proj.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.proj.command.SignInCommand;
import pt.ulisboa.tecnico.cmu.proj.programActivity;
import pt.ulisboa.tecnico.cmu.proj.response.DownloadQuizResponse;
import pt.ulisboa.tecnico.cmu.proj.response.HelloResponse;
import pt.ulisboa.tecnico.cmu.proj.response.ListLocationsResponse;
import pt.ulisboa.tecnico.cmu.proj.response.LogOutResponse;
import pt.ulisboa.tecnico.cmu.proj.response.LoginResponse;
import pt.ulisboa.tecnico.cmu.proj.response.Response;
import pt.ulisboa.tecnico.cmu.proj.response.SignInResponse;


public class DummyTask extends AsyncTask<Void, Void, String> {

    //private QuizActivity mainActivity;
    private Context mainActivity;

    private Command comm; //Command handler for functions

    public DummyTask(MainActivity mainActivity, Command comm) {
        this.mainActivity = mainActivity;
        this.comm = comm;
    }
    public DummyTask(Sign_InActivity mainActivity, Command comm) {
        this.mainActivity = mainActivity;
        this.comm = comm;
    }

    public DummyTask(programActivity mainActivity, Command comm) {
        this.mainActivity = mainActivity;
        this.comm = comm;
    }

    public DummyTask(ListMonumentsActivity mainActivity, Command comm) {
        this.mainActivity = mainActivity;
        this.comm = comm;
    }

    public DummyTask(QuizActivity mainActivity, Command comm) {
        this.mainActivity = mainActivity;
        this.comm = comm;
    }

    @Override
    protected String doInBackground(Void[] params) {
        Socket server = null;
        String reply = null;
        //HelloCommand hc = new HelloCommand(params[0]);
        //AnotherHello hc = new AnotherHello(params[0]);


        try {
            Log.d("DummyClient", "Before Server");
            server = new Socket("10.0.2.2", 9090);
            Log.d("DummyClient", "After Server");

            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            oos.writeObject(comm);

            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());

            //HelloResponse hr = (HelloResponse) ois.readObject();
            //LoginResponse hr = (LoginResponse) ois.readObject();
            //Response hr;
            if( comm instanceof LoginCommand ){
                LoginResponse hr = (LoginResponse) ois.readObject();
                reply = hr.getMessage();
                Log.d("DummyClient instanceof", "TRUE");
            }

            if( comm instanceof SignInCommand){
                SignInResponse hr = (SignInResponse) ois.readObject();
                reply = hr.getMessage();
            }

            if( comm instanceof LogOutCommand){
                LogOutResponse hr = (LogOutResponse) ois.readObject();
                reply = hr.getMessage();
            }
            if( comm instanceof ListLocationsCommand){
                ListLocationsResponse hr = (ListLocationsResponse) ois.readObject();
                reply = hr.getMessage();
            }
            if( comm instanceof DownloadQuizCommand){
                DownloadQuizResponse hr = (DownloadQuizResponse) ois.readObject();
                reply = hr.getMessage();
            }


            //Response hr = (Response) ois.readObject();

            //reply = hr.getMessage();
            Log.d("DummyClient ------ Reply", reply);


            oos.close();
            ois.close();
            Log.d("DummyClient", "Hi there!!");
        }
        catch (Exception e) {
            Log.d("DummyClient", "DummyTask failed..." + e.getMessage());
            e.printStackTrace();
        } finally {
            if (server != null) {
                try { server.close(); }
                catch (Exception e) { }
            }
        }
        return reply;
    }

    @Override
    protected void onPostExecute(String o) {
        if (o != null) {
            //mainActivity.updateInterface(o);
            if( comm instanceof LoginCommand ){
                MainActivity a = (MainActivity)mainActivity;
                a.updateInterface(o);
            }
            else
                if( comm instanceof SignInCommand){
                    Sign_InActivity a = (Sign_InActivity)mainActivity;
                    a.updateInterface(o);
                }
                else
                if( comm instanceof LogOutCommand){
                    programActivity a = (programActivity)mainActivity;
                    a.updateInterface(o);
                }
                else
                if( comm instanceof ListLocationsCommand){
                    ListMonumentsActivity a = (ListMonumentsActivity)mainActivity;
                    a.updateInterface(o);
                }
                else
                if( comm instanceof DownloadQuizCommand){
                    QuizActivity a = (QuizActivity)mainActivity;
                    a.updateInterface(o);
                }

        }
    }
}
