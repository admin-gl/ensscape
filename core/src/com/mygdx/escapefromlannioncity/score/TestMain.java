package com.mygdx.escapefromlannioncity.score;

import com.mygdx.escapefromlannioncity.siteweb.GetJoueur;
import com.mygdx.escapefromlannioncity.siteweb.GetScore;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de test séparé de la gestion et l'enregistrement des scores, ainsi que son ouverture.
 */
public class TestMain {
    public static void main(String[] args) throws Exception{
        Score j1 = new Score("polpo","00:09",2,2, LocalDate.now());
        Score j2 = new Score("potre","04:08",2,2, LocalDate.now());
        List<Score> table = new ArrayList<>();
        table.add(j1);
        table.add(j2);
        Score.Serialize(table,"Local");
        //List<Score> aff = Score.Deserialize("Local");
        //System.out.println(aff.get(0).ToString());
        //System.out.println(aff.get(1).ToString());
        System.out.println(GetJoueur.LogIn("1:00","3-3"));
        Score.AddScoreLoc(new Score("new","08:02",1,1,LocalDate.now()));
        System.out.println(GetScore.StoreScore());
        AffScore.AffScore();
    }
}
