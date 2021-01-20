package com.mygdx.escapefromlannioncity.siteweb;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Iterator;


public class GetJoueur {
    /**
     * Prend les identifiants d'un joueur et retourne son pseudo si ces identifiants son bon, echoue sinon
     * ou s'il y a une erreur, et pas d'internet s'il n'y a pas d'internet
     * @param email à tester
     * @param mdp à tester
     * @return le pseudo du joueur, echoue si echec, pas d'internet sinon
     */
    public static String LogIn(String email, String mdp){
        try {
            JSONParser jsonParser = new JSONParser();
            String joueur=getJoueur();
            if(joueur.equals("pas d'internet")) {return joueur; }

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
                        (email.equals(jsonObject.get("email").toString())) &&
                                (mdp.equals( jsonObject.get("mdp").toString()))
                ){
                    return jsonObject.get("pseudo").toString();
                }
                j++;
                i.next();
            }
            System.out.println("no user  found");
            return "echoue";
        }catch(Exception e){
            System.out.println("erreur"+e);
            return "echoue";
        }
    }

    /**
     * Se connecte à la base de donnée en ligne et récupère les infos de tous les joueurs
     * @return un json avec les infos des joueurs
     */
    public static String getJoueur(){
        try {
            System.out.println("begin the connection now");
            StringBuilder res = new StringBuilder();

            URL url = new URL("https://escapefromlannion.netlify.app/.netlify/functions/get-joueur");
            URLConnection urlCon = url.openConnection();
            InputStream input = urlCon.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = reader.readLine(); // reads a line
            res.append(line);
            System.out.println(line);

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                res.append(line);
            }
            input.close();
            return res.toString();
        } catch (UnknownHostException e) {
            System.out.println("pas d'internet");
            e.printStackTrace();
            return "pas d'internet";
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
