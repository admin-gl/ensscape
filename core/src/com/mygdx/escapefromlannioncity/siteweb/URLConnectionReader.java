package com.mygdx.escapefromlannioncity.siteweb;

import java.net.*;
import java.io.*;

/**
 * Lis toutes les données de la base de donnée.
 */
public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
        System.out.println("begin the connection now");

        //sur internet : "https://kind-mclean-21a458.netlify.app/.netlify/functions/get-signatures"
        //en locale :"http://localhost:3000/.netlify/functions/get-signatures"
        URL url = new URL("https://kind-mclean-21a458.netlify.app/.netlify/functions/get-signatures");
        URLConnection urlCon = url.openConnection();
        InputStream input = urlCon.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = reader.readLine(); // reads a line
        System.out.println(line);
        System.out.println("should print sthg");
        while ((line = reader.readLine()) != null)
            System.out.println(line);
        input.close();
    }
}