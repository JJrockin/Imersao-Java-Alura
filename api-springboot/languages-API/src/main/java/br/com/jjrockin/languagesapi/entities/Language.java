package br.com.jjrockin.languagesapi.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "languages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language {

    @Id
    private String id;
    private String title;
    private String image;
    private int ranking;

    public Language(String title, String image, int ranking) {
        this.title = title;
        this.image = image;
        this.ranking = ranking;
    }
}
