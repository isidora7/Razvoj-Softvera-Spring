package org.raflab.studsluzba.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/kurs")
public class KursAPIController {


    String apiURL = "https://kurs.resenje.org/api/v1/currencies/eur";

    @GetMapping(path = "/today")
    public String today() throws IOException {
        return callAPI("/rates/today");
    }

    // "https://kurs.resenje.org/api/v1/currencies/usd/rates/2016-01-25"
    // uneti datum formata YEAR-MO-DA
    @GetMapping(path="/date/{datum}")
    public String date(@PathVariable String datum) throws IOException {
        System.out.println(datum);
        return callAPI("/rates/" + datum);
    }

    private String callAPI(String ext) {
        String ret;
        String fullURL = apiURL + ext;

        try {

            URL url = new URL(fullURL);
            System.out.println("URL: " + fullURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                ret = response.toString();
                connection.disconnect();


                System.out.println("API Response: " + ret);
                return ret;
            } else {
                System.out.println("API call failed with response code: " + responseCode);
                connection.disconnect();
                return null;
            }


        } catch (MalformedURLException malformedURLException) {
            malformedURLException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("OUT OUT OUT OUT OUT OUT OUT OUT OUT OUT OUT OUT OUT");
        return null;
    }



}
