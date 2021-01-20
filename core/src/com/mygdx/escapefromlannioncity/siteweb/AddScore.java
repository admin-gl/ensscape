package com.mygdx.escapefromlannioncity.siteweb;

import com.mygdx.escapefromlannioncity.score.Score;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Ajoute un élément à la base de donnée.
 */
public class AddScore {
    //public static void main(String args[]) {

    /**
     * Ajoute le score à la bdd.
     * @param s score à ajouter
     * @return 0 si execution normal (mais si la donnée ne se charge pas quanq meme voir l'exeption IO
     * ) 1 si pas d'internet, 2 si autre exeption
     */
  // public static void main(String args[]) {
    public static int addScore(Score s) {
        try {
            URL urlObj = new URL("https://escapefromlannion.netlify.app/.netlify/functions/add-score");
            HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();

            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");
            String parameters = "token=7E60M4Gsc-h4Q8llqQ26PgOmhVUK76IwR6kOAHiAFl7cGzI&"+
                    "score="+s.getScore()+"&pseudo="+s.getPseudo()+"&temps="+
                    s.getTemps()+"&date="+s.getDate();
            // String parameters = "token=7E60M4Gsc-h4Q8llqQ26PgOmhVUK76IwR6kOAHiAFl7cGzI&score=45j&pseudo=fzfzf&temps=frgergerge&date=frgzgz";
            OutputStreamWriter writer = new OutputStreamWriter(
                    httpCon.getOutputStream());
            writer.write(parameters);
            writer.flush();
            writer.close();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            httpCon.getInputStream()));

            in.close();
            return 0;
        } catch (UnknownHostException e) {
            System.out.println("pas d'internet");
            //e.printStackTrace();
            return 1;
        }
        catch (IOException e){
            System.out.println("cette exeption apparait toujours, il faut regarder si ça marche pas les parametres");
            //e.printStackTrace();
            return 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return 2;
        }
    }
}