package pt.ulisboa.tecnico.cmu.proj.ranking;

/**
 * Created by ist426300 on 08-05-2018.
 */

import java.util.ArrayList;

public class GroupItemsInfo {

    private String monument;
    private ArrayList<ChildItemsInfo> list;

    public GroupItemsInfo(String q){
        this.monument = q;
        list = new ArrayList<ChildItemsInfo>();
    }

    public String getMonument() {
        return monument;
    }

    public void addChild(ChildItemsInfo ci){
        list.add(ci);
    }

    public ArrayList<ChildItemsInfo> getChilds() {
        return list;
    }

}
