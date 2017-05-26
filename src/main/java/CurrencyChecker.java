import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class provides current currency using fixer.io as a resource. Implements Singleton pattern.
 */



class CurrencyChecker {
    private static Map<String, Double> currencies;
    private static CurrencyChecker instance;


    static synchronized CurrencyChecker getInstance() {
        if (instance == null) {
            instance = new CurrencyChecker();
        }
        return instance;
    }

//Get currency list with base of EUR. In order to work needs internet connection.
    Map<String, Double> getBaseCurrencies() {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(
                    "http://api.fixer.io/latest");
            getRequest.addHeader("accept", "application/json");
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
            String output, data = "";
            while ((output = br.readLine()) != null) data += output;

            //Parsing JSON

            JSONObject dataObj = new JSONObject(data).getJSONObject("rates");
            currencies = new HashMap<>();
            currencies.put("EUR", 1.0);
            for (int i = 0; i < dataObj.names().length(); ++i)
                currencies.put(dataObj.names().get(i).toString(),
                        (Double) dataObj.get(dataObj.names().get(i).toString()));

            httpClient.getConnectionManager().shutdown();
        } catch (IOException | JSONException e) {
            System.out.println("There is problem with getting the current currencies.\n" +
                    "Please check your internet connection");
            return null;
        }
        return currencies;
    }
    static Set<String> currencyTypes(){
        if (currencies == null) getInstance().getBaseCurrencies();
        return CurrencyChecker.currencies.keySet();
    }
}

