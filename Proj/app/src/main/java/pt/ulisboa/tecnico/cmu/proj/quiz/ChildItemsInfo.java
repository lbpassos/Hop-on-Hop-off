package pt.ulisboa.tecnico.cmu.proj.quiz;

/**
 * Created by ist426300 on 08-05-2018.
 */

//Each choice
public class ChildItemsInfo {

    private String option;
    private String position;
    private boolean isChecked;

    public ChildItemsInfo(String option, String pos){
        this.option = option;
        this.position = pos;
        this.isChecked = false;
    }
    public String getName() {
        return option;
    }
    public String getPosition() {
        return position;
    }

    public void setFlag(boolean v){
        isChecked = v;
    }
    public boolean getFlag(){
        return isChecked;
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof ChildItemsInfo))return false;
        ChildItemsInfo otherMyClass = (ChildItemsInfo)other;
        if(option.equals(otherMyClass.getName()) && position.equals(otherMyClass.getPosition()) ){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return option.hashCode() + position.hashCode();
    }

}