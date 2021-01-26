package com.mygdx.escapefromlannioncity.sauvegarde;

import com.badlogic.gdx.Screen;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.screens.Warp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SWarp implements Serializable {
    boolean isDecapsuleurPicked;
    boolean isPiecePicked;
    boolean isBorneTurnedOn;
    boolean isBorneAlredyClicked;
    boolean areChevBross;
    boolean isBrossePicked;
    boolean isCoffreDiscovered;
    boolean isOpened;
    boolean isChev1Bross;
    boolean isChev2Bross;
    boolean isBiereAlreadyClicked;
    boolean isTableauAlreadyClicked;
    boolean clef;

    String timeTotal;
    String timeFromBegin;
    int bonus;
    int usedHint;

    /**
     * Création de la classe SWarp, classe qui est sérializée pour stocker une partie, car elle contient les informations nécessaires pour reprendre une partie depuis
     * là où l'on l'a laissée dans le Warp.
     * @param timeTotal   -- commun à tous les écrans, temps depuis le commencement du jeu
     * @param timeFromBegin temps depuis que l'écran actif est affiché
     * @param bonus  --commun à tous les écrans bonus que l'utilisateur a gagné
     * @param usedHint  -- commun à tous les écrans nombre d'indice utilisés par le joueur
     * @param isOpened
     * @param isDecapsuleurPicked
     * @param isBorneTurnedOn
     * @param isPiecePicked
     * @param isBorneAlreadyClicked
     * @param areChevBross
     * @param isBrossePicked
     * @param isCoffreDiscoverd
     * @param isChev1Bross
     * @param isChev2Bross
     * @param isBiereAlreadyClicked
     * @param isTableauAlreadyClicked
     * @param clef
     */
    public SWarp(String timeTotal, String timeFromBegin, int bonus, int usedHint,
                       boolean isOpened, boolean isDecapsuleurPicked, boolean isBorneTurnedOn,
                       boolean isPiecePicked, boolean isBorneAlreadyClicked, boolean areChevBross,
                       boolean isBrossePicked, boolean isCoffreDiscoverd,boolean isChev1Bross, boolean isChev2Bross,
                       boolean isBiereAlreadyClicked, boolean isTableauAlreadyClicked, boolean clef) {
        this.isOpened = isOpened;
        this.isDecapsuleurPicked = isDecapsuleurPicked;
        this.isPiecePicked = isPiecePicked;
        this.isBorneTurnedOn = isBorneTurnedOn;
        this.isBorneAlredyClicked = isBorneAlreadyClicked;
        this.areChevBross = areChevBross;
        this.isBrossePicked = isBrossePicked;
        this.isCoffreDiscovered = isCoffreDiscoverd;
        this.isChev1Bross =isChev1Bross;
        this.isChev2Bross = isChev2Bross;
        this.isBiereAlreadyClicked = isBiereAlreadyClicked;
        this.isTableauAlreadyClicked = isTableauAlreadyClicked;
        this.clef = clef;

        this.timeFromBegin = timeFromBegin;
        this.timeTotal = timeTotal;
        this.bonus = bonus;
        this.usedHint = usedHint;
    }

    /**
     * Stocke sous forme d'un fichier .txt les informations importantes à la reconstruction
     * d'une partie sauvegarder dans la pièce où la sauvegarde à lieu, ici le Warp.
     * @param Amp la classe de la scene à enregistrer, qui contient qussi des informations sur l'utilisateur pour qui on enregistre la partie
     */
    public static void Enregistrer(Screen Amp){

        Warp warp = (Warp) Amp;
        SWarp s = new SWarp(warp.getTimeTotal(),warp.getTimeFromBegin(),
                warp.getBonus(), warp.getUsedHint(),warp.isOpened(),
                warp.isDecapsuleurPicked(), warp.isBorneTurnedOn(), warp.isPiecePicked(), warp.isBorneAlredyClicked(),
                warp.isAreChevBross(), warp.isBrossePicked(),
                warp.isCoffreDiscovered(), warp.isChev1Bross(), warp.isChev2Bross(), warp.isBiereAlreadyClicked(),
                warp.isTableauAlreadyClicked(), warp.hasClef());
        try{
            try {
                Path path = Paths.get(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/");
                Files.createDirectories(path);
                System.out.println("Directory is created!");
            } catch (IOException e) {
                System.err.println("Failed to create directory!" + e.getMessage());

            }
            FileOutputStream fout;
            if(warp.game.isLoggedin==1){
                try {
                    Path path = Paths.get(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/1/");
                    Files.createDirectories(path);
                    System.out.println("Directory is created!");
                } catch (IOException e) {
                    System.err.println("Failed to create directory!" + e.getMessage());

                }
                Supprimer(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/1/", warp.game.pseudo);
                fout=new FileOutputStream(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/1/"+warp.game.pseudo+"W.txt");
            }else{
                try {
                    Path path = Paths.get(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/2/");
                    Files.createDirectories(path);
                    System.out.println("Directory is created!");
                } catch (IOException e) {
                    System.err.println("Failed to create directory!" + e.getMessage());

                }
                Supprimer(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/2/", warp.game.pseudo);
                fout=new FileOutputStream("./Parties/2/"+warp.game.pseudo+"W.txt");
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
     * Ouvre un fichier de sauvegarde d'une partie depuis un fichier .txt stocké dans Parties, et retourne la classe Warp.java correspondante avec
     * les paramètres et écrans correspondants au stockage.
     * @param game le jeu dans lequel on se trouve
     * @return Warp Screen de la partie enregistrée
     */
    public static Warp Ouvrir(EscapeFromLannionCity game){
        try{
            ObjectInputStream in;
            //Creating stream to read the object
            if(game.isLoggedin==1) {
               in = new ObjectInputStream(new FileInputStream(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/1/" + game.pseudo + "W.txt"));
            }else{
               in = new ObjectInputStream(new FileInputStream(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/2/" + game.pseudo + "W.txt"));
            }

            SWarp s=(SWarp) in.readObject();
            //closing the stream
            in.close();
            System.out.println(s.usedHint);
            Warp amph = new Warp(game, s.timeTotal, s.bonus, s.usedHint);
            amph.setTimeFromBegin(s.timeFromBegin);
            amph.setTimeTotal(s.timeTotal);
            amph.setBonus(s.bonus);
            amph.setUsedHint(s.usedHint);
            amph.setClef(s.clef);
            amph.setBorneTurnedOn(s.isBorneTurnedOn);
            amph.setPiecePicked(s.isPiecePicked);
            amph.setBorneAlredyClicked(s.isBorneAlredyClicked);
            amph.setAreChevBross(s.areChevBross);
            amph.setBrossePicked(s.isBrossePicked);
            amph.setCoffreDiscovered(s.isCoffreDiscovered);
            amph.setChev1Bross(s.isChev1Bross);
            amph.setChev2Bross(s.isChev2Bross);
            amph.setBiereAlreadyClicked(s.isBiereAlreadyClicked);
            amph.setTableauAlreadyClicked(s.isTableauAlreadyClicked);
            amph.setDecapsuleurPicked(s.isDecapsuleurPicked);

            amph.setOpened(s.isOpened);
            amph.setBackground();

            return  amph;
        }catch(Exception e){
            System.out.println(e);
            System.out.println("lecture de la sauvegarde echouee");
            return  new Warp(game, "00:00",0,0);
        }
    }

    /**
     * Supprime une sauvegarde d'un utilisateur
     * @param dir répertoire où est stocké le fichier à supprimer
     * @param pseudo du joueur dont on veut supprimer une sauvegarde
     */
    public static void Supprimer(String dir, String pseudo){
        File file = new File(dir);
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if(files[i].getName().equals(pseudo+"A.txt")||
                        files[i].getName().equals(pseudo+"P.txt")
                        ||files[i].getName().equals(pseudo+"W.txt")) {
                    System.out.println("  Fichier: " + files[i].getName());
                    files[i].delete();
                }

            }
        }
    }

    /**
     * Verifie si une partie a été enregistré dans Parties pour le joueur, et retourne l'endroit de la partie enregistré si c'est le cas.
     *
     * @param isLoggedin si l'utilisateur joue hors ligne ou non
     * @param pseudo le pseudo de la personne dont on cherche une sauvegarde de partie
     * @return 0 si aucun fichier trouvé, 1 si on était dans l'amphi, 2 dans le parc, 3 dans le warp
     */
    public static int isPartie(int isLoggedin, String pseudo){
        File file;

        if(isLoggedin==1){
             file = new File(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/1/");
         }else{
            file = new File(System.getProperty("user.home")+"/Public/EFLC/Parties/Parties/2/");

        }
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if(files[i].getName().equals(pseudo+"A.txt")){
                    return 1;
                }else if(files[i].getName().equals(pseudo+"P.txt")){
                    return 2;
                }else if(files[i].getName().equals(pseudo+"W.txt")){
                    return 3;
                }
            }
            return 0;
        }
        return 0;
    }
}
