package pl.rstepniewski.sockets.propertiesUtil;

import pl.rstepniewski.sockets.game.UserInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            loadProperties();
        } catch (FileNotFoundException e) {
            UserInterface.printText("FileNotFoundException while PropertiesUtil initialisation");
        }
    }

    private PropertiesUtil() {
    }

    private static void loadProperties() throws FileNotFoundException {
        try (var stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")
        ) {
            PROPERTIES.load(stream);
        } catch (IOException ex) {
            throw new FileNotFoundException();
        }
    }

    public static String getString(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static int getNumber(String key) {
        String value = PROPERTIES.getProperty(key);
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format for key: " + key, e);
        }
    }

}