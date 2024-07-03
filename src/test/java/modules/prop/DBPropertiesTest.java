package modules.prop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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