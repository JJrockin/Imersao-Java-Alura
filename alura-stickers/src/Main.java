import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) throws Exception{

        String key = "";
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("resources.properties")){
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
        var geradora = new GeradoraDeFigurinhas();

        var diretorio = new File("stickers/");
        diretorio.mkdir();

        String frase = "";

        for (int i = 0; i < 5; i++){
            Map<String, String> serie = listaDeSeries.get(i);

            double classificacao = Double.parseDouble(serie.get("imDbRating"));
            if (classificacao <= 7.5){
                frase = "Fuleiro";
            } else if (classificacao <= 8.5){
                frase = "Bacana";
            } else {
                frase = "Top Demais!";
            }

            String urlImagem = serie.get("image");
            Pattern regexPat = Pattern.compile("(.*)(?:\\._)");
            Matcher mat = regexPat.matcher(urlImagem);
            String urlNew = "";
            while (mat.find()) {
                urlNew = mat.group() + ".jpg";
            }

            String titulo = serie.get("title").replace(":","-");

            String nomeArquivo = "stickers/" + titulo + ".png";
            InputStream inputStream = new URL(urlNew)
                                .openStream();
            geradora.cria(inputStream, nomeArquivo, frase);

            System.out.println("\u001b[97m \u001b[104m Título: " + titulo + " \u001b[m");

            System.out.println("\n");
        }
    }

}