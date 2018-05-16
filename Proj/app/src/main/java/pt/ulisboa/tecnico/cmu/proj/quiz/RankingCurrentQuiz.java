package pt.ulisboa.tecnico.cmu.proj.quiz;

/**
 * Created by ist426300 on 15-05-2018.
 */

public class RankingCurrentQuiz { //Current ranking
    private String numQuestions;
    private String numAnswers;
    private String monumenDesc;

    public RankingCurrentQuiz(String numQuestions, String numAnswers, String monumenDesc){
        this.numQuestions = numQuestions;
        this.numAnswers = numAnswers;
        this.monumenDesc = monumenDesc;
    }

    public String getNumQuestions(){
        return numQuestions;
    }
    public String getNumAnswers(){
        return numAnswers;
    }
    public String getmonumenDesc(){
        return monumenDesc;
    }
}
