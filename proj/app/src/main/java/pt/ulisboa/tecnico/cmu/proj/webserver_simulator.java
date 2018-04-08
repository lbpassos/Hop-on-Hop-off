package pt.ulisboa.tecnico.cmu.proj;

import java.io.Serializable;

/**
 * Created by ist426300 on 27-03-2018.
 */

public class webserver_simulator implements Serializable{
    private String Username;
    private String Code;


    webserver_simulator(String u, String c){
        this.Username = u;
        this.Code = c;
    }

    int authenticate(String u, String c){
        if( Username.equals(u) && Code.equals(c) ){
            return 1;
        }
        else{
            if( !Username.equals(u) ){
                return 0;
            }
            else{
                return -1;
            }
        }
    }



}
