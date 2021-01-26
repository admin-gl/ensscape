package com.mygdx.escapefromlannioncity.sauvegarde;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.screens.AmphiEnssat;
import com.mygdx.escapefromlannioncity.screens.ParcStAnne;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SParcStAnne implements Serializable {
    int[] number = new int[4];
    boolean isOpened;
    boolean isBatonPicked;
    boolean isStringPicked;
    boolean isMetalPicked;
    boolean isPaperPicked;
    boolean canne;
    boolean echelle;

    String timeTotal;
    String timeFromBegin;
    int bonus;
    int usedHint;

    /**
     * Constructeur de la classe qu'on serialize pour stocker les informations nécéssaires à la reconstruction
     * de l'écran de la partie en cours.
     * @param timeTotal   -- commun à tous les écrans, temps depuis le commencement du jeu
     * @param timeFromBegin temps depuis que l'écran actif est affiché
     * @param bonus  --commun à tous les écrans bonus que l'utilisateur a gagné
     * @param usedHint  -- commun à tous les écrans nombre d'indice utilisés par le joueur
     * @param isOpened
     * @param isBatonPicked
     * @param isStringPicked
     * @param isMetalPicked
     * @param isPaperPicked
     * @param canne
     * @param echelle
     * @param number
     */
    public SParcStAnne(String timeTotal, String timeFromBegin, int bonus, int usedHint,
                       boolean isOpened, boolean isBatonPicked, boolean isStringPicked,
                       boolean isMetalPicked, boolean isPaperPicked, boolean canne,
                       boolean echelle, int[] number) {
       for(int i=0;i< number.length;i++){
            this.number[i] = number[i];
        }
        this.isOpened = isOpened;
        this.isBatonPicked = isBatonPicked;
        this.isStringPicked = isStringPicked;
        this.isMetalPicked = isMetalPicked;
        this.isPaperPicked = isPaperPicked;
        this.canne = canne;
        this.echelle = echelle;

        this.timeFromBegin = timeFromBegin;
        this.timeTotal = timeTotal;
        this.bonus = bonus;
        this.usedHint = usedHint;
    }

    /**
     * Stocke sous forme d'un fichier .txt les informations importantes à la reconstruction
     * d'une partie sauvegarder dans la pièce où la sauvegarde à lieu, ici le parc st anne.
     * @param Amp la classe de la scene à enregistrer, qui contient qussi des informations sur l'utilisateur pour qui on enregistre la partie
     */
    public static void Enregistrer(Screen Amp){
        int[] number = new int[4];
        ParcStAnne parc = (ParcStAnne) Amp;
        parc.Convertir(number);
        SParcStAnne s = new SParcStAnne(parc.getTimeTotal(),parc.getTimeFromBegin(),
                parc.getBonus(), parc.getUsedHint(),parc.isOpened(),parc.isBatonPicked(),
                parc.isStringPicked(),parc.isMetalPicked(),parc.isPaperPicked(), parc.hasCanne(),
                parc.hasEchelle(),number);
        try{
            try {
                Path path = Paths.get(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/");
                Files.createDirectories(path);
                System.out.println("Directory is created!");
            } catch (IOException e) {
                System.err.println("Failed to create directory!" + e.getMessage());

            }
            FileOutputStream fout;
            if(parc.game.isLoggedin==1){
                try {
                    Path path = Paths.get(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/1/");
                    Files.createDirectories(path);
                    System.out.println("Directory is created!");
                } catch (IOException e) {
                    System.err.println("Failed to create directory!" + e.getMessage());

                }
                SWarp.Supprimer(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/1/", parc.game.pseudo);
                fout=new FileOutputStream(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/1/"+parc.game.pseudo+"P.txt");
            }else{
                try {
                    Path path = Paths.get(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/2/");
                    Files.createDirectories(path);
                    System.out.println("Directory is created!");
                } catch (IOException e) {
                    System.err.println("Failed to create directory!" + e.getMessage());

                }
                SWarp.Supprimer(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/2/", parc.game.pseudo);
                fout=new FileOutputStream(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/2/"+parc.game.pseudo+"P.txt");
            }            ObjectOutputStream out=new ObjectOutputStream(fout);
            out.writeObject(s);
            out.flush();
            //closing the stream
            out.close();
            System.out.println("success");
        }catch(Exception e){System.out.println(e);}
    }

    /**
     * Ouvre un fichier de sauvegarde d'une partie depuis un fichier .txt stocké dans Parties, et retourne la classe ParcStAnne.java correspondante avec
     * les paramètres et écrans correspondants au stockage.
     * @param game le jeu dans lequel on se trouve
     * @return ParcStAnne Screen de la partie enregistrée
     */
    public static ParcStAnne Ouvrir(EscapeFromLannionCity game){
        try{
            //Creating stream to read the object
            ObjectInputStream in;
            //Creating stream to read the object
            if(game.isLoggedin==1) {
                in = new ObjectInputStream(new FileInputStream(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/1/" + game.pseudo + "P.txt"));
            }else{
                in = new ObjectInputStream(new FileInputStream(Gdx.files.getExternalStoragePath() + "EscapeFromLannionCity/Parties/2/" + game.pseudo + "P.txt"));
            }            SParcStAnne s=(SParcStAnne) in.readObject();
            //closing the stream
            in.close();
            System.out.println(s.usedHint);
            ParcStAnne amph = new ParcStAnne(game, s.timeTotal, s.bonus, s.usedHint);
            amph.setTimeFromBegin(s.timeFromBegin);
            amph.setTimeTotal(s.timeTotal);
            amph.setBonus(s.bonus);
            amph.setUsedHint(s.usedHint);
            amph.setEchelle(s.echelle);
            amph.setPaperPicked(s.isPaperPicked);
            amph.setCanne(s.canne);
            amph.setBatonPicked(s.isBatonPicked);
            amph.setMetalPicked(s.isMetalPicked);
            amph.setStringPicked(s.isStringPicked);
            amph.setOpened(s.isOpened);

            amph.Synchronize(s.number);
            amph.setBackground();

            return  amph;
        }catch(Exception e){
            System.out.println(e);
            System.out.println("lecture de la sauvegarde echouee");
            return  new ParcStAnne(game, "00:00",0,0);
        }
    }

}
