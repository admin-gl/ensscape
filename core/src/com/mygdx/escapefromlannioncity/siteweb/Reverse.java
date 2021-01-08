package com.mygdx.escapefromlannioncity.siteweb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Ajoute un élément à la base de donnée.
 */
public class Reverse {
    public static void main(String[] args) throws Exception{

        //sur internet : "https://kind-mclean-21a458.netlify.app/.netlify/functions/add-signature"
        //en locale : "http://localhost:3000/.netlify/functions/add-signature"
        URL urlObj = new URL("https://kind-mclean-21a458.netlify.app/.netlify/functions/add-signature");
        HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();

        httpCon.setDoOutput(true);
        httpCon.setRequestMethod("POST");
        String parameters = "signature="+"àchangerpourvoir";

        OutputStreamWriter writer = new OutputStreamWriter(
                httpCon.getOutputStream());
        writer.write(parameters);
        writer.flush();
        writer.close();

           /* String stringToReverse = URLEncoder.encode("tretre"
                    , "UTF-8");

            URL url = new URL("http://localhost:3000/.netlify/functions/add-signature");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(
                    connection.getOutputStream());
            out.write("signature=" + stringToReverse);
            out.close();*/

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            httpCon.getInputStream()));

            in.close();

    }
}