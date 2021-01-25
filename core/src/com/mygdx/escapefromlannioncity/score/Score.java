package com.mygdx.escapefromlannioncity.score;

//import com.github.googlecode.json-simple.
//import com.github.cliftonlabs.json_simple.JsonObject;
//import com.github.cliftonlabs.json_simple.Jsonable;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Score implements Serializable {
    /**
     * Les champs d'une ligne du tableau de score.
     */
    String score;
    String pseudo;
    String temps;
    String date;

    public Score(){}


    /**
     * Constructeur de l'objet utilisé.
     *
     * @param pseudo pseudo du joueur qui a réalisé le score
     * @param temps temps de résolution du défi
     * @param bonus bonus gagné pendant le défi
     * @param indice nombre d'indice utilisé
     * @param date date de réalisation du score
     */
    public Score(String pseudo, String temps, int bonus, int indice,LocalDate date){
        int min = Integer.parseInt(temps.substring(0,2));
        int sec = Integer.parseInt(temps.substring(3,5));
        int sc = (15*60 - (min*60 +sec))* + bonus*20 - indice*300;
        this.score= String.valueOf(sc);
        this.pseudo = pseudo;
        this.temps= temps;
        this.date = date.toString();
    }
    public Score(String score, String pseudo,String temps, String date){
        this.score=score;
        this.pseudo=pseudo;
        this.temps=temps;
        this.date=date;
    }

    public String ToString(){
        System.out.println("Score de "+getPseudo()+" : "+ getScore()+" le "+getDate()+
                " en "+getTemps());
        return "Score de "+getPseudo()+" : "+ getScore()+" le "+getDate()+
                " en "+getTemps();
    }
/**
 * On crée tous les getters et setters pour que la conversion
 * en json se passe bien
 */
    public String getScore() {
        return score;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public String getTemps() {
        return temps;
    }
    public void setTemps(String temps) {
        this.temps = temps;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("score", this.score);
        json.put("pseudo", this.pseudo);
        json.put("temps", this.temps);
        json.put("date", this.date);
        return json.toJSONString();
    }



    public static String ListtoJson(List<Score> table) {
        try {
            JSONArray list = new JSONArray();
            String jsonText;
            for (Score value : table) {
                list.add(value.toJson());
            }
            jsonText = list.toJSONString();
            System.out.println(jsonText);

            return jsonText;

        } catch (Exception ignored) {
            return "";
        }
    }
    /**
     * Serialize sous json une liste de score d'un meme ou de plusieurs joueurs.
     * @param table liste des scores d'un ou plusieurs joueurs
     * @param name nom : du joueur si les scores personnels du joueurs ou Score pour le score général de tous
     */
    public static void Serialize(List<Score> table, String name){
        FileWriter file;
        try {
            Path path = Paths.get("./Score/");
            Files.createDirectories(path);
            System.out.println("Directory is created!");
        } catch (IOException e) {
            System.err.println("Failed to create directory!" + e.getMessage());

        }
        try {
            file = new FileWriter("./Score/"+name+".json");
            file.write(ListtoJson(table));
            file.flush();
            file.close();
            FileWriter filescec = new FileWriter("./Score/"+name+".js");
            filescec.write(name+"="+ListtoJson(table)+";");
            filescec.flush();
            filescec.close();
        }catch(Exception e){
            System.out.println("serialization a echoue"+e);
        }
    }

    /**
     * Récupère la liste de score associée à un joueur ou de Score, le tableau de score général.
     *
     * @param name nom du joueur ou Score our le tableau général
     * @return  la liste de score associé au nom rentré
     * @throws FileNotFoundException  lance une exception de fichier non trouvé si la liste de score n'existe pas dans le stockage.
     */
    public static List<Score> Deserialize(String name) throws FileNotFoundException { try {
            List<Score> table = new ArrayList<>();
            FileReader reader = new FileReader("./Score/"+name+".json");
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
            Iterator i = jsonArray.iterator();
            Object obj ;
            JSONObject jsonObject;
            int j = 0;
            while (i.hasNext()) {
                 //object = (JSONObject) jsonArray.get(j);
                 System.out.println(jsonArray.get(j).toString());
                obj = jsonParser.parse( jsonArray.get(j).toString());
                jsonObject = (JSONObject)obj;
                table.add(new Score(jsonObject.get("score").toString(),jsonObject.get("pseudo").toString(),
                         jsonObject.get("temps").toString(), jsonObject.get("date").toString()));
                 j++;
		         i.next();
                }
        System.out.println("deserialization reeussie");
        return table;
    }catch(IOException | ParseException e){
        System.out.println("deserialisation ratee"+e);
        return new ArrayList<>();
    }
    }


    /**
     * Ajoute un score à une liste de score déjà stockée, ou si aucune n'est déjà stockée pour le nom du joueur
     * ou le score général, crée la liste de score.
     * @param score le score à stocker
     */
   public static void AddScoreLoc(Score score) {
       try {
           List<Score> table = Score.Deserialize("Local");
           int i = 0;
            while ((i < table.size()) &&
                    (Integer.parseInt(table.get(i).getScore()) > Integer.parseInt(score.getScore()))) {
                i++;
            }
            table.add(i,score);

            /* On limite le stockage local à 100 */
           System.out.print(table.size());
           if(table.size() >= 100){
               for(i=100;i<table.size();i++){
                   table.remove(i);
               }
           }
           Score.Serialize(table, "Local");


           /* Si aucun fichier au nom du joueur n'existe encore */
        }catch(FileNotFoundException e){
            List<Score> table = new ArrayList<>();
            table.add(score);
            Score.Serialize(table,"Local");
         }
    }
}
