package com.mygdx.escapefromlannioncity.siteweb;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;

public class AddJoueur {
    /**
     * Vérifie si les informations à ajouter ne sont pas redondantes avec un joueur déjà existant
     * @param email à vérifier s'il n'existe pas déjà
     * @param pseudo à vérifier s'il n'existe pas déjà
     * @return "ok" si c'est bon, "echoue" si erreur, "pas d'internet" dans le cas correspondant, "déjà pseudo"
     * si le pseudo existe déjà, "déjà email" si l'email existe déjà
     */
    public static String Verif(String email, String pseudo){
        try {
            JSONParser jsonParser = new JSONParser();
            String joueur=GetJoueur.getJoueur();
            assert joueur != null;
            if(joueur.equals("pas d'internet")) {return "pas d'internet"; }

            JSONArray jsonArray = (JSONArray) jsonParser.parse(joueur);
            //  JSONArray jsonArray = (JSONArray) jsonParser.parse(
            //        "[{\"pseudo\":\"hello\",\"email\":\"1:00\",\"mdp\":\"3-3\"},{\"pseudo\":\"hello\",\"email\":\"1:00\",\"mdp\":\"3-3\"},{\"pseudo\":\"hello\",\"email\":\"1:00\",\"mdp\":\"3-3\"},{\"pseudo\":\"hellooooo\",\"email\":\"1:00\",\"mdp\":\"3-3\"},{\"pseudo\":\"testbon\",\"email\":\"1:00\",\"mdp\":\"3-3\"},{\"pseudo\":\"tests\",\"email\":\"1:00\",\"mdp\":\"3-3\"}]"
            //);

            Iterator i = jsonArray.iterator();
            Object obj ;
            JSONObject jsonObject ;
            int j = 0;
            while (i.hasNext()) {
                //object = (JSONObject) jsonArray.get(j);
                //System.out.println(jsonArray.get(j));
                obj = jsonParser.parse(jsonArray.get(j).toString());
                jsonObject = (JSONObject)obj;
                if(
                        (email.equals(jsonObject.get("email").toString()))) {
                    return "déjà email";
                }else if(pseudo.equals( jsonObject.get("pseudo").toString())){
                    return "déjà pseudo";
                }
                j++;
                i.next();
            }
            System.out.println("no user  found");
            return "ok";
        }catch(Exception e){
            System.out.println("erreur"+e);
            return "echoue";
        }
    }

    /**
     * Inscrit un joueur dans la base de donnée avec les infos si dessous
     *
     * @param pseudo
     * @param email
     * @param mdp
     * @return 0 si réussie et IO exeption, 1 si pas d'internet, 2 si une autre execption,
     * 3 si le pseudo existe déjà, 4 si l'email existe déjà
     */
    public static int addJoueur(String pseudo,String email, String mdp){
        try {
            String verif = Verif(email, pseudo);
            if (verif.equals("ok")) {


                URL urlObj = new URL("https://escapefromlannion.netlify.app/.netlify/functions/add-joueur");
                HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();

                httpCon.setDoOutput(true);
                httpCon.setRequestMethod("POST");
                String parameters = "token=7E60M4Gsc-h4Q8llqQ26PgOmhVUK76IwR6kOAHiAFl7cGzI" +
                        "&pseudo=" + pseudo + "&email=" + email + "&mdp=" + mdp;

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

            }else{
                System.out.println(verif);
                switch (verif) {
                    case "pas d'internet":
                        return 1;
                    case "déjà pseudo":
                        return 3; //déjà pseudo

                    case "déjà email":
                        return 4;  //déjà email

                    default:
                        return 2;
                }
            }
        } catch(UnknownHostException e){
            System.out.println("pas d'internet");
            e.printStackTrace();
            return 1;
        }
        catch(IOException e){
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
