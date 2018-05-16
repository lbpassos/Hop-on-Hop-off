package pt.ulisboa.tecnico.cmu.proj.ranking;

import java.util.ArrayList;

/**
 * Created by ist426300 on 16-05-2018.
 */
class MonumentRanking{
    private String monument;
    private String numQuestions;
    private String numCorrectQuestions;
    private String total;

    public MonumentRanking(String m, String nq, String ncq, String total){
        this.monument = m;
        this.numQuestions = nq;
        this.numCorrectQuestions = ncq;
        this.total = total;
    }

    public String getMonument(){
        return monument;
    }
    public String getNumQuestions(){
        return numQuestions;
    }
    public String getNumCorrectQuestions(){
        return numCorrectQuestions;
    }
    public String geTotal(){
        return total;
    }
}

public class RankingToShow {
    private String global;
    private ArrayList<MonumentRanking> cont;

    public RankingToShow(String global){
        this.global = global;
        cont = new ArrayList<MonumentRanking>();
    }

    public void insert(String m, String nq, String ncq, String total){
        cont.add( new MonumentRanking(m, nq, ncq, total));
    }

    public String getGlobalRanking(){
        return global;
    }
    public int size(){
        return cont.size();
    }

    public String getMonument(int pos){
        return cont.get(pos).getMonument();
    }

    public String getNumQuestions(int pos){
        return cont.get(pos).getNumQuestions();
    }

    public String getNumCorrectQuestions(int pos){
        return cont.get(pos).getNumCorrectQuestions();
    }

    public String geTotal(int pos){
        return cont.get(pos).geTotal();
    }
}
