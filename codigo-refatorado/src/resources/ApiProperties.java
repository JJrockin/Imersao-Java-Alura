package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApiProperties {

    public static String getApiProperties(String key) {
        String value;

        try (InputStream input = new FileInputStream("src/resources/apiKey.properties")){
            Properties prop = new Properties();

            prop.load(input);
            value = prop.getProperty(key);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return value;
    }
}
