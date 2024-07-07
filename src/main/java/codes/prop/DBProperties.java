package codes.prop;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {

    public static final String DB_DRIVER;
    public static final String DB_URL;
    public static final String DB_USER;
    public static final String DB_PW;
    public static Properties prop;

    static {
        String path = System.getProperty("user.dir");
        String propName = path + "/conf/db.properties";

        prop = new Properties();
        try {
            prop.load(new FileReader(propName));
            DB_DRIVER = prop.getProperty("DRIVER");
            DB_URL = prop.getProperty("URL");
            DB_USER = prop.getProperty("USER");
            DB_PW = prop.getProperty("PW");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
