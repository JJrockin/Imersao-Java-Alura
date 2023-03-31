package attributes;

import resources.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContentExtractorImdb {

    public List<ContentImdb> extractContent(String json) {
        var parser = new JsonParser();
        List<Map<String,String>> listOfContent = parser.parse(json);

        List<ContentImdb> contents = new ArrayList<>();

        for (Map<String, String> attributes: listOfContent){
            String title = attributes.get("title").replace(":","-");
            String rating = attributes.get("imDbRating");
            String urlImage = attributes.get("image");
            Pattern regexPat = Pattern.compile("(.*)(?:\\._)");
            Matcher mat = regexPat.matcher(urlImage);
            String urlNew = "";
            while (mat.find()) {
                urlNew = mat.group() + ".jpg";
            }
            var content = new ContentImdb(title, urlNew, rating);
            contents.add(content);
        }

        return contents;
    }
}
