package com.mygdx.escapefromlannioncity.score;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.ArrayList; // import the ArrayList class
import java.time.*;
import java.util.List;

public class Score implements Serializable {
    /**
     * Les champs d'une ligne du tableau de score.
     */
    int score;
    String pseudo;
    int temps;
    String date;

    public Score(){}


    /**
     * Constructeur de l'objet utilisé.
     *
     * @param pseudo pseudo du joueur qui a réalisé le score
     * @param temps temps de résolution du défi
     * @param bonus bonus gagné pendant le défi
     * @param nbenig nombre d'énigme résolue
     * @param indice nombre d'indice utilisé
     * @param date date de réalisation du score
     */
    public Score(String pseudo, int temps, int bonus, int nbenig, int indice,LocalDate date){
        this.score= temps + bonus - nbenig - indice;
        this.pseudo = pseudo;
        this.temps=temps;
        this.date = date.toString();
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
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public int getTemps() {
        return temps;
    }
    public void setTemps(int temps) {
        this.temps = temps;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Serialize sous forme xml une liste de score d'un meme ou de plusieurs joueurs.
     * @param table liste des scores d'un ou plusieurs joueurs
     * @param name nom : du joueur si les scores personnels du joueurs ou Score pour le score général de tous
     */
    public static void Serialize(List<Score> table, String name){
   XMLEncoder encoder =null;
   try {
       encoder = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("./" + name + ".xml")));

   }catch(Exception e){
       System.out.println("serialization a echoue"+e);
   }
   encoder.writeObject(table);
   encoder.close();
}

    /**
     * Récupère la liste de score associée à un joueur ou de Score, le tableau de score général.
     *
     * @param name nom du joueur ou Score our le tableau général
     * @return  la liste de score associé au nom rentré
     * @throws FileNotFoundException  lance une exception de fichier non trouvé si la liste de score n'existe pas dans le stockage.
     */
    public static List<Score> Deserialize(String name) throws FileNotFoundException {
    XMLDecoder decoder = null;
    try {
        decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream("./" + name + ".xml")));
        List<Score> table = (List<Score>) decoder.readObject();
        System.out.println("deserialization reeussie");
        return table;
    }catch(IOException e){
        System.out.println("deserialisation ratee"+e);
        return new ArrayList<Score>();
    }
    }


    /**
     * Ajoute un score à une liste de score déjà stockée, ou si aucune n'est déjà stockée pour le nom du joueur
     * ou le score général, crée la liste de score.
     * @param score le score à stocker
     * @param pseudo le pseudo du joueur dont on stoke le nouveau score (normalement il ne faudrait utiliser
     *               cette fonction que pour le stockage d'un joueur, le stockage général devra passer par la base de donnée en ligne)
     */
   public static void AddScore(Score score, String pseudo) {
       try {
           List<Score> table = Score.Deserialize(pseudo);
           int i = 0;
            while ((i < table.size()) && (table.get(i).getScore() > score.getScore())) {
                i++;
            }
            table.add(i,score);


            /* On limite le nombre d'entrée stockées dans le tableau. */
            if(pseudo.equals("Score")){
                if(table.size() >= 100){
                    for(i=100;i<table.size();i++){
                        table.remove(i);
                    }
                }
            }else{
                System.out.print(table.size());
                if(table.size() >= 10){
                    for(i=10;i<table.size();i++){
                        table.remove(i);
                    }
                }
            }

           Score.Serialize(table, pseudo);


           /* Si aucun fichier au nom du joueur n'existe encore */
        }catch(FileNotFoundException e){
            List<Score> table = new ArrayList<Score>();
            table.add(score);
            Score.Serialize(table,pseudo);
         }
    }
}
