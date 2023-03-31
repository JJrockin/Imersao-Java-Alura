import attributes.ContentExtractorImdb;
import attributes.ContentImdb;
import clientHttp.ClientHTTP;
import resources.API;
import resources.ApiProperties;
import stickers.StickerGenerator;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class StickersImdb {
    public static void main(String[] args) throws Exception{

        API api = API.IMDB_TOP_TV;
        String keyImdb = ApiProperties.getApiProperties("IMDB.API.KEY");
        String url = api.getUrl() + keyImdb;
        ContentExtractorImdb extractor = new ContentExtractorImdb();

        ClientHTTP http = new ClientHTTP();
        String json = http.searchData(url);

        List<ContentImdb> contentList = extractor.extractContent(json);

        var generator = new StickerGenerator();

        File directory = new File("stickersGenerated/");
        if(!directory.exists()){
            directory.mkdir();
        }

        String phrase = "";

        for (int i = 0; i < 5; i++){
            var content = contentList.get(i);

            double rating = Double.parseDouble(content.rating());
            if (rating <= 7.5){
                phrase = "Fuleiro";
            } else if (rating <= 8.5){
                phrase = "Bacana";
            } else {
                phrase = "Top Demais!";
            }

            String nameArchive = "stickersGenerated/" + content.title() + ".png";
            InputStream inputStream = new URL(content.urlImage())
                    .openStream();
            generator.createSticker(inputStream, nameArchive, phrase);

            System.out.println("\u001b[97m \u001b[104m Título: " + content.title() + " \u001b[m");

            System.out.println("\n");
        }


    }
}