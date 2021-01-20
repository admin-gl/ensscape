package com.mygdx.escapefromlannioncity.siteweb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class AddJoueur {
    /**
     * Inscrit un joueur dans la base de donnée avec les infos si dessous
     *
     * @param pseudo
     * @param email
     * @param mdp
     * @return 0 si réussie et IO exeption, 1 si pas d'internet, 2 si une autre execption
     */
    public static int addJoueur(String pseudo,String email, String mdp){
        try{
            URL urlObj = new URL("https://escapefromlannion.netlify.app/.netlify/functions/add-joueur");
            HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();

            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");
            String parameters = "token=7E60M4Gsc-h4Q8llqQ26PgOmhVUK76IwR6kOAHiAFl7cGzI"+
                    "&pseudo="+pseudo+"&email="+email+"&mdp="+mdp;

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
            e.printStackTrace();
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
