package com.mygdx.escapefromlannioncity.sauvegarde;

import com.badlogic.gdx.Screen;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.screens.AmphiEnssat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SAmphiEnssat implements Serializable {
    int[] interrupteur = new int[11];
    int[] number = new int[4];
    boolean lights;
    boolean carteValide;
    boolean porteVerr;
    boolean hint2;
    boolean carte;

    String timeTotal;
    String timeFromBegin;
    int bonus;
    int usedHint;

    /**
     * * Constructeur de la classe qu'on serialize pour stocker les informations nécéssaires à la reconstruction
     * de l'écran de la partie en cours.
     * @param timeTotal   -- commun à tous les écrans, temps depuis le commencement du jeu
     * @param timeFromBegin temps depuis que l'écran actif est affiché
     * @param bonus  --commun à tous les écrans bonus que l'utilisateur a gagné
     * @param usedHint  -- commun à tous les écrans nombre d'indice utilisés par le joueur
     * @param lights
     * @param carteValide
     * @param porteVerr
     * @param hint2
     * @param carte
     * @param interrupteur
     * @param number
     */
    public SAmphiEnssat(String timeTotal, String timeFromBegin, int bonus, int usedHint, boolean lights,
            boolean carteValide, boolean porteVerr, boolean hint2, boolean carte, int[] interrupteur,  int[] number) {
        for (int i = 0; i < interrupteur.length; i++){
            this.interrupteur[i] = interrupteur[i];
        }
        for(int i=0;i< number.length;i++){
            this.number[i] = number[i];
        }
        this.lights = lights;
        this.carteValide=carteValide;
        this.porteVerr = porteVerr;
        this.hint2 = hint2;
        this.carte = carte;

        this.timeFromBegin = timeFromBegin;
        this.timeTotal = timeTotal;
        this.bonus = bonus;
        this.usedHint = usedHint;
    }

    /**
     * Stocke sous forme d'un fichier .txt les informations importantes à la reconstruction
     * d'une partie sauvegarder dans la pièce où la sauvegarde à lieu, ici l'amphitheatre.
     * @param Amp la classe de la scene à enregistrer, qui contient qussi des informations sur l'utilisateur pour qui on enregistre la partie
     */
    public static void Enregistrer(Screen Amp){
        int[] interrupteur = new int[11];
        int[] number = new int[4];
        AmphiEnssat amp = (AmphiEnssat) Amp;
        amp.Convertir(interrupteur,number);
        SAmphiEnssat s = new SAmphiEnssat(amp.getTimeTotal(),amp.getTimeFromBegin(),
                amp.getBonus(), amp.getUsedHint(),amp.isLights(),amp.isCarteValide(),
                amp.isPorteVerr(),amp.isHint2(),amp.getCarte(),
                interrupteur, number);
        try{
            try {
                Path path = Paths.get("./Parties/");
                Files.createDirectories(path);
                System.out.println("Directory is created!");
            } catch (IOException e) {
                System.err.println("Failed to create directory!" + e.getMessage());

            }
            FileOutputStream fout;
            if(amp.game.isLoggedin==1){
                try {
                    Path path = Paths.get(System.getProperty("user.home")+"/Public/EFLC/Parties/1/");
                    Files.createDirectories(path);
                    System.out.println("Directory is created!");
                } catch (IOException e) {
                    System.err.println("Failed to create directory!" + e.getMessage());

                }
                SWarp.Supprimer(System.getProperty("user.home")+"/Public/EFLC/Parties/1/", amp.game.pseudo);
                fout=new FileOutputStream(System.getProperty("user.home")+"/Public/EFLC/Parties/1/"+amp.game.pseudo+"A.txt");
            }else{
                try {
                    Path path = Paths.get(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/2/");
                    Files.createDirectories(path);
                    System.out.println("Directory is created!");
                } catch (IOException e) {
                    System.err.println("Failed to create directory!" + e.getMessage());

                }
                SWarp.Supprimer(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/2/", amp.game.pseudo);
                fout=new FileOutputStream(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/2/"+amp.game.pseudo+"A.txt");
            }
            ObjectOutputStream out=new ObjectOutputStream(fout);
            out.writeObject(s);
            out.flush();
            //closing the stream
            out.close();
            System.out.println("success");
        }catch(Exception e){System.out.println(e);}
    }

    /**
     * Ouvre un fichier de sauvegarde d'une partie depuis un fichier .txt stocké dans Parties, et retourne la classe AmphEnssat.java correspondante avec
     * les paramètres et écrans correspondants au stockage.
     * @param game le jeu dans lequel on se trouve
     * @return AmphiEnssat Screen de la partie enregistrée
     */
    public static AmphiEnssat Ouvrir(EscapeFromLannionCity game){
        try{
            ObjectInputStream in;
            //Creating stream to read the object
            if(game.isLoggedin==1) {
                in = new ObjectInputStream(new FileInputStream(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/1/" + game.pseudo + "A.txt"));
            }else{
                in = new ObjectInputStream(new FileInputStream(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/2/" + game.pseudo + "A.txt"));
            }
            SAmphiEnssat s=(SAmphiEnssat) in.readObject();
            //closing the stream
            in.close();

            AmphiEnssat amph = new AmphiEnssat(game, s.timeTotal, s.bonus, s.usedHint);
            amph.setTimeFromBegin(s.timeFromBegin);
            amph.setTimeTotal(s.timeTotal);
            amph.setBonus(s.bonus);
            amph.setUsedHint(s.usedHint);
            amph.setCarteValide(s.carteValide);
            amph.setLights(s.lights);
            amph.setCarte(s.carte);
            amph.setHint2(s.hint2);
            amph.setPorteVerr(s.porteVerr);

            amph.Synchronize(s.interrupteur,s.number);
            amph.setBackground(s.lights);

            return  amph;
        }catch(Exception e){
            System.out.println(e);
            System.out.println("lecture de la sauvegarde echouee");
            return  new AmphiEnssat(game, "00:00",0,0);
        }
    }

}
