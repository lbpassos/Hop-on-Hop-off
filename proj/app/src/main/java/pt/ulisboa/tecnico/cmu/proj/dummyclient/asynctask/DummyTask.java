package pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import pt.ulisboa.tecnico.cmu.proj.command.AnotherHello;
import pt.ulisboa.tecnico.cmu.proj.command.HelloCommand;
import pt.ulisboa.tecnico.cmu.proj.MainActivity;
import pt.ulisboa.tecnico.cmu.proj.response.HelloResponse;

public class DummyTask extends AsyncTask<String, Void, String> {

    private MainActivity mainActivity;

    public DummyTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected String doInBackground(String[] params) {
        Socket server = null;
        String reply = null;
        //HelloCommand hc = new HelloCommand(params[0]);
        AnotherHello hc = new AnotherHello(params[0]);

        try {
            server = new Socket("10.0.2.2", 9090);

            ObjectOutputStream oos = new ObjectOutputStream(server.getOutputStream());
            oos.writeObject(hc);

            ObjectInputStream ois = new ObjectInputStream(server.getInputStream());
            HelloResponse hr = (HelloResponse) ois.readObject();
            reply = hr.getMessage();

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
        }
    }
}
