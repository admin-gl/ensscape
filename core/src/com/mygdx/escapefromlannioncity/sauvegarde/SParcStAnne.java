package com.mygdx.escapefromlannioncity.sauvegarde;

import com.badlogic.gdx.Screen;
import com.mygdx.escapefromlannioncity.EscapeFromLannionCity;
import com.mygdx.escapefromlannioncity.screens.AmphiEnssat;
import com.mygdx.escapefromlannioncity.screens.ParcStAnne;

import java.io.*;

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


    public static void Enregistrer(Screen Amp){
        int[] number = new int[4];
        ParcStAnne parc = (ParcStAnne) Amp;
        parc.Convertir(number);
        SParcStAnne s = new SParcStAnne(parc.getTimeTotal(),parc.getTimeFromBegin(),
                parc.getBonus(), parc.getUsedHint(),parc.isOpened(),parc.isBatonPicked(),
                parc.isStringPicked(),parc.isMetalPicked(),parc.isPaperPicked(), parc.hasCanne(),
                parc.hasEchelle(),number);
        try{
            FileOutputStream fout=new FileOutputStream("./f.txt");
            ObjectOutputStream out=new ObjectOutputStream(fout);
            out.writeObject(s);
            out.flush();
            //closing the stream
            out.close();
            System.out.println("success");
        }catch(Exception e){System.out.println(e);}
    }


    public static ParcStAnne Ouvrir(EscapeFromLannionCity game){
        try{
            //Creating stream to read the object
            ObjectInputStream in=new ObjectInputStream(new FileInputStream("f.txt"));
            SParcStAnne s=(SParcStAnne) in.readObject();
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
