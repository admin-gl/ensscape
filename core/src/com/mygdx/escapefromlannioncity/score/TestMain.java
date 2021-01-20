package com.mygdx.escapefromlannioncity.score;

import com.mygdx.escapefromlannioncity.siteweb.GetJoueur;
import com.mygdx.escapefromlannioncity.siteweb.GetScore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TestMain {
    public static void main(String[] args) throws Exception{
        Score j1 = new Score("polpo",9,2,3,2, LocalDate.now());
        Score j2 = new Score("potre",3,2,3,2, LocalDate.now());
        List<Score> table = new ArrayList<>();
        table.add(j1);
        table.add(j2);
        Score.Serialize(table,"Local");
        //List<Score> aff = Score.Deserialize("Local");
        //System.out.println(aff.get(0).ToString());
        //System.out.println(aff.get(1).ToString());
        System.out.println(GetJoueur.LogIn("1:00","3-3"));
 Score.AddScore(new Score("new",90,1,5,1,LocalDate.now()),"Local");
System.out.println(GetScore.ScoreLocal());

    }
}
