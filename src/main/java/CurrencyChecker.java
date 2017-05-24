import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class provides tools to work with currency.
 */
public class CurrencyChecker {
    private String currency;

    public CurrencyChecker() {
        //this.currency = curr;
    }


    public static void main(String[] args) {
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(
                    "http://localhost:8080/RESTfulExample/json/product/get");
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            httpClient.getConnectionManager().shutdown();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }

}

