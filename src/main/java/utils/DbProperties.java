package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс служит для получения настроек коннекта к базе.
 */
public class DbProperties {

    private static final Properties props = new Properties();

    public static Properties getProps() {
        try (FileInputStream in = new FileInputStream("src\\main\\resources\\db.properties")) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
