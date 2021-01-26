package com.mygdx.escapefromlannioncity.siteweb;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Lis toutes les données de la base de donnée.
 */
public class GetScore {
    /**
     * Stocke dans ./Score/Tableau.json le score issus de la base de donnée sur internet.
     * @return 0 si aucun pb, 1 si pas d'internet, 2 si une exception
     */
    public static int StoreScore(){
        String score = getScore();
        if(score != null) {
            if (score.equals("pas d'internet")) {
                return 1;
            } else {
                FileWriter file;
                try {
                    Path path = Paths.get("./Score/");
                    Files.createDirectories(path);
                    System.out.println("Directory is created!");
                } catch (IOException e) {
                    System.err.println("Failed to create directory!" + e.getMessage());
                    return 2;
                }
                try {
                    file = new FileWriter("./Score/Tableau.json");
                    file.write(score);
                    file.flush();
                    file.close();
                    FileWriter filescec = new FileWriter("./Score/Tableau.js");
                    filescec.write("Tableau="+score+";");
                    filescec.flush();
                    filescec.close();
                    return 0;
                } catch (Exception e) {
                    System.err.println("serialization des scores en ligne a echoue" + e);
                    return 2;
                }
            }
        }else {
            return 2;
        }
    }


    /**
     * Récupère tous les scores en ligne
     * @return json de tous les scores
     */
    public static String getScore(){
        try{
            StringBuilder res=new StringBuilder();

            URL url = new URL("https://escapefromlannion.netlify.app/.netlify/functions/get-score");
            URLConnection urlCon = url.openConnection();
            InputStream input = urlCon.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine(); // reads a line
            res.append(line);
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            input.close();
            return res.toString();
        } catch (UnknownHostException e) {
            System.err.println("pas d'internet");
            e.printStackTrace();
            return "pas d'internet";
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}