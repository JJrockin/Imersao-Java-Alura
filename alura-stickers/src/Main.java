import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{

        String key = "";
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("key.properties")){
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find key.properties");
                return;
            }
            prop.load(input);
            key = prop.getProperty("IMDB.API.KEY");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String url = "https://imdb-api.com/en/API/MostPopularTVs/" + key;
        HttpClient client = HttpClient.newHttpClient();
        URI endereco = URI.create(url);
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        var parser = new JsonParser();
        List<Map<String,String>> listaDeSeries = parser.parse(body);

        for (int i = 0; i < 5; i++){
            Map<String, String> serie = listaDeSeries.get(i);
            System.out.println("\u001b[97m \u001b[104m Título: " + serie.get("title") + " \u001b[m");
            System.out.println(serie.get("image"));
            System.out.println("\u001b[38;5;214m \u001b[48;5;153m Rating: " + serie.get("imDbRating") + " \u001b[m");
            Double rating = Double.parseDouble(serie.get("imDbRating"));

            for (int j = 1; j < rating; j++) {
                System.out.print("⭐");
            }
            System.out.println("\n");
        }
    }

}