import attributes.Content;
import attributes.ContentExtractor;
import attributes.ContentExtractorNasa;
import clientHttp.ClientHTTP;
import resources.ApiProperties;
import stickers.StickerGenerator;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class StickersNasa {
    public static void main(String[] args) throws Exception {

        String keyNasa = ApiProperties.getApiProperties("NASA.API.KEY");
        String url = "https://api.nasa.gov/planetary/apod?api_key=" + keyNasa;
        ContentExtractor extractor = new ContentExtractorNasa();

        ClientHTTP http = new ClientHTTP();
        String json = http.searchData(url);

        List<Content> contentList = extractor.extractContent(json);

        var generator = new StickerGenerator();

        File directory = new File("stickersGenerated/");
        if(!directory.exists()){
            directory.mkdir();
        }

        String phrase = "Space is Awesome";

        for (int i = 0; i < contentList.toArray().length; i++){
            var content = contentList.get(i);

            String nameArchive = "stickersGenerated/" + content.title() + ".png";
            InputStream inputStream = new URL(content.urlImage())
                    .openStream();
            generator.createSticker(inputStream, nameArchive, phrase);

            System.out.println("\u001b[97m \u001b[104m Título: " + content.title() + " \u001b[m");

            System.out.println("\n");
        }

    }
}
