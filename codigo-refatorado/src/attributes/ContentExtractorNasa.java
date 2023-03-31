package attributes;

import resources.JsonParserNasa;

import java.util.List;
import java.util.Map;

public class ContentExtractorNasa implements ContentExtractor{

    public List<Content> extractContent(String json) {
        var parser = new JsonParserNasa();
        List<Map<String,String>> listOfContent = parser.parse(json);

        return listOfContent.stream()
                .map(attributes -> new Content(attributes.get("title"), attributes.get("url")))
                .toList();

    }
}
