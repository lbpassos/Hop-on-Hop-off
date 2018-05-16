package pt.ulisboa.tecnico.cmu.proj.ranking;

/**
 * Created by ist426300 on 08-05-2018.
 */

//Each choice
public class ChildItemsInfo {

    //private final String numQuestions;
    //private final String numCorrectQuestions;
    private String numQuestions_val;
    private String numCorrectQuestions_val;
    private String total_ranking;

    public ChildItemsInfo(String numQuestions_val, String numCorrectQuestions_val, String total){
        //this.numQuestions = "Number Of Questions";
        //this.numCorrectQuestions = "Correct Answers";
        this.numQuestions_val = numQuestions_val;
        this.numCorrectQuestions_val = numCorrectQuestions_val;
        this.total_ranking = total;
    }
    /*public String getNumQuestions() {
        return numQuestions;
    }
    public String getNumCorrectQuestions() {
        return numCorrectQuestions;
    }
    */
    public String getNumQuestions_val(){return numQuestions_val;}
    public String getNumCorrectQuestions_val(){return numCorrectQuestions_val;}
    public String geTotal_ranking(){return total_ranking;}

}