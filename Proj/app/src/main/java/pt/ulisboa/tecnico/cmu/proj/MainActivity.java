package pt.ulisboa.tecnico.cmu.proj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;

import pt.ulisboa.tecnico.cmu.proj.command.Command;
import pt.ulisboa.tecnico.cmu.proj.command.LoginCommand;
import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String sign_in_text;
    private EditText text_username;
    private EditText text_code;
    private Button button_login;

    //private webserver_simulator wb;
    //private int res;
    private Command c;
    private String user;
    private String code;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sign_in_text = "Sign In";
        text_username = findViewById(R.id.input_username);
        text_code = findViewById(R.id.input_code);
        button_login = findViewById(R.id.btn_login);
        button_login.setOnClickListener(this);

        //Save globally
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        //Sign In
        //Setting just the "Sign In" text to be clickable
        TextView link_signup = findViewById(R.id.link_signup);
        link_signup.setLinkTextColor(Color.RED);
        String myString = link_signup.getText().toString();
        int i1 = myString.indexOf(sign_in_text);
        //Log.d("MY DEBUG", String.valueOf(i1));
        link_signup.setMovementMethod(LinkMovementMethod.getInstance());
        link_signup.setText(myString, TextView.BufferType.SPANNABLE);
        Spannable mySpannable = (Spannable)link_signup.getText();
        ClickableSpan myClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                /* do something */
                Log.d("MY DEBUG", "I'm In Sign In");
                Intent intent = new Intent(MainActivity.this, Sign_InActivity.class);
                startActivity(intent);
            }
        };
        mySpannable.setSpan(myClickableSpan, i1, i1+sign_in_text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    @Override
    public void onClick(View view) {
        //Log.d("MY DEBUG", "I'm on Click");

        //final String user, code;
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

        /* ****************************************************** */
        /* *******Send message to authenticate in webserver****** */

        //Start the communication task in background
        //Log.d("-----Mainactivity----- User", user);
        //Log.d("-----Mainactivity----- Code", code);
        String json = JsonHandler.LoginToServer(user, code);
        //Log.d("-----Mainactivity----- Json", json);
        c = new LoginCommand( json );
        new DummyTask(MainActivity.this, c).execute();
    }

    public void updateInterface(String reply) {
        String[] t= JsonHandler.LoginFromServer(reply);
        //Log.d("-----Mainactivity----- SessionID", t[0]);
        //Log.d("-----Mainactivity----- Message", t[1]);

        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        if(t[0].equals("0")){ //No session id
            dlgAlert.setTitle("Login Error");
            dlgAlert.setMessage(t[1]);
            dlgAlert.create().show();
        }
        else{
            editor.putString("sessionId", t[0]);
            editor.putString("User", user);
            editor.commit(); // commit changes

            Intent intent = new Intent(MainActivity.this, programActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); //caller activity eliminated
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //this activity will become the start of a new task on this history stack.
            startActivity(intent);
        }
    }


}