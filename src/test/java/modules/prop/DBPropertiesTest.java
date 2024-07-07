package modules.prop;

import codes.prop.DBProperties;
import org.junit.jupiter.api.Test;

class DBPropertiesTest {
    
    @Test
    void Path_테스트(){
        String property = System.getProperty("user.dir");
        System.out.println("property = " + property);
    }

    @Test
    void DBProp_테스트(){
        System.out.println(DBProperties.DB_URL);
    }



}