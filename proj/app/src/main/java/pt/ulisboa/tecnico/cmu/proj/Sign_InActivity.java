package pt.ulisboa.tecnico.cmu.proj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Sign_InActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText text_username;
    private EditText text_code;
    private Button button_signIn;
    private Button button_cancel;

    private webserver_simulator wb;
    private int res;

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

        Intent i = getIntent();
        wb = (webserver_simulator)i.getSerializableExtra("webserverObject");

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

        /* ****************************************************** */
        /* *******Send message to authenticate in webserver****** */
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


        }
    }

    public void process_Autentication(String user, String code){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        res = wb.authenticate(user, code); //simulate server
        if( res==0 ){
            dlgAlert.setTitle("User Already Exists");
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
    }
}
