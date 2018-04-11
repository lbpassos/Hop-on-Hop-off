package pt.ulisboa.tecnico.cmu.proj;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.proj.command.SignInCommand;
import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;

public class Sign_InActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText text_username;
    private EditText text_code;
    private Button button_signIn;
    private Button button_cancel;

    private Command c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in);

        text_username = findViewById(R.id.input_username);
        text_code = findViewById(R.id.input_code);
        button_signIn = findViewById(R.id.btn_signIn);
        button_signIn.setOnClickListener(this);
        button_cancel = findViewById(R.id.btn_cancel);
        button_cancel.setOnClickListener(this);

        //Intent i = getIntent();
        //wb = (webserver_simulator)i.getSerializableExtra("webserverObject");

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_cancel: //terminate this activity
                finish();
                break;
            case R.id.btn_signIn:
                final String user, code;
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

                user = text_username.getText().toString();
                code = text_code.getText().toString();

                //Check if Username or Code are empty
                if(user.isEmpty()==true || code.isEmpty()==true){
                    dlgAlert.setTitle("Empty Field");
                    if(user.isEmpty()==true){
                        dlgAlert.setMessage("Please provide a UserName");
                    }
                    else{
                        dlgAlert.setMessage("Please provide a Code (See your ticket)");
                    }
                    dlgAlert.create().show();
                    return;
                }

                String json = JsonHandler.LoginToServer(user, code);
                Log.d("-----Mainactivity----- Json", json);
                c = new SignInCommand( json );
                new DummyTask(Sign_InActivity.this, c).execute();
        /* ****************************************************** */
        /* *******Send message to authenticate in webserver****** */
                /*final ProgressDialog progressDialog;
                progressDialog = new ProgressDialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                // Delay of 5 s to simulate contact with server
                new android.os.Handler().postDelayed(
                        new Runnable(){
                            @Override
                            public void run() {
                                process_Autentication( user, code);
                                progressDialog.dismiss();
                            }
                        },5000
                );*/

        /* Check authentication and open new app */


        }
    }

    public void updateInterface(String reply) {
        String[] t= JsonHandler.LoginFromServer(reply);
        Log.d("-----Mainactivity----- SessionID", t[0]);
        Log.d("-----Mainactivity----- Message", t[1]);

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        if(t[0].equals("0")){ //No session id
            dlgAlert.setTitle("Sign In Error");
            dlgAlert.setMessage(t[1]);
            dlgAlert.create().show();
        }
        else{
            //Intent intent = new Intent(Sign_InActivity.this, MainActivity.class);
            //intent.putExtra("session_id", t[0]);
            //intent.putExtra("User", user);
            dlgAlert.setTitle("Sign In Success");
            dlgAlert.setMessage(t[1]);
            //dlgAlert.create().show();

            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    //Intent intent = new Intent(this, Menu.class);
                    Log.d("----- Sign_InActivity ------", "AQUI");
                    Intent intent = new Intent(Sign_InActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            AlertDialog dialog = dlgAlert.create();
            dialog.setCanceledOnTouchOutside(false);
            //dlgAlert.create().show();
            dialog.show();



            //startActivity(intent);
            //finish();
        }
    }
}
