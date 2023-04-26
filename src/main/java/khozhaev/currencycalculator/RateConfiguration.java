package khozhaev.currencycalculator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class RateConfiguration {

    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            String configPath = "src/main/resources/configuration.properties";
            properties.load(new FileInputStream(configPath));
        } catch (IOException e) {
            throw new RuntimeException("Property file \"configuration.properties\" is not found!");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
