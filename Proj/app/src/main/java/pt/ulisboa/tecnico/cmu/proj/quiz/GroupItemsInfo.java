package pt.ulisboa.tecnico.cmu.proj.quiz;

/**
 * Created by ist426300 on 08-05-2018.
 */

import java.util.ArrayList;

public class GroupItemsInfo {

    private String question;
    private ArrayList<ChildItemsInfo> list;

    public GroupItemsInfo(String q){
        this.question = q;
        list = new ArrayList<ChildItemsInfo>();
    }

    public String getQuestion() {
        return question;
    }

    public void addChild(ChildItemsInfo ci){
        list.add(ci);
    }

    public ArrayList<ChildItemsInfo> getChilds() {
        return list;
    }

    public void changeFlag(ChildItemsInfo ci, boolean flag){
        for(int i=0; i<list.size(); ++i){
            if( list.get(i).equals(ci) ){
                list.get(i).setFlag(flag);
            }
        }
    }

}
