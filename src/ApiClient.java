import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final String API_KEY = "ae028e8c2dbaf9eb23dccf16";

    public static MoedaResponse getRates(String baseCurrency) {
        try {
            String endpoint = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + baseCurrency;
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            MoedaResponse response = new Gson().fromJson(reader, MoedaResponse.class);
            reader.close();

            return response;
        } catch (Exception e) {
            System.out.println("Erro ao obter taxas de câmbio: " + e.getMessage());
            return null;
        }
    }
}
