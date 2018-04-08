package pt.ulisboa.tecnico.cmu.proj;

import android.app.ProgressDialog;
import android.content.Intent;
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

import pt.ulisboa.tecnico.cmu.proj.dummyclient.asynctask.DummyTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String sign_in_text;
    private EditText text_username;
    private EditText text_code;
    private Button button_login;

    private webserver_simulator wb;
    private int res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sign_in_text = "Sign In";
        text_username = findViewById(R.id.input_username);
        text_code = findViewById(R.id.input_code);
        button_login = findViewById(R.id.btn_login);
        button_login.setOnClickListener(this);




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
        Log.d("MY DEBUG", "I'm on Click");

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

        /* ****************************************************** */
        /* *******Send message to authenticate in webserver****** */

        //Start the communication task in background
        new DummyTask(MainActivity.this).execute();

        final ProgressDialog progressDialog;
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
        );


        /* Check authentication and open new app */
        // ******************* If authentication ok






    }


    public void process_Autentication(String user, String code){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        res = wb.authenticate(user, code); //simulate server
        if( res==0 ){
            dlgAlert.setTitle("User Inexistent");
            dlgAlert.setMessage("Please Sign In");
        }
        else{
            if(res==-1) {
                dlgAlert.setTitle("Invalid Code");
                dlgAlert.setMessage("Please Check the Code");
            }
        }
        if(res!=1){
            dlgAlert.create().show();
            return;
        }
        else{
            Intent intent = new Intent(MainActivity.this, programActivity.class);
            intent.putExtra("webserverObject", (Serializable) wb);
            intent.putExtra("User", user);
            startActivity(intent);
            finish();
        }
    }
}