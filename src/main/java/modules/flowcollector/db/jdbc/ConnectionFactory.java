package modules.flowcollector.db.jdbc;

import codes.prop.DBProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
//    private static final HikariConfig hikariConfig;
//    private static final HikariDataSource hikariDataSource;
//
//    static {
//        System.out.println("2");
//        hikariConfig = new HikariConfig();
//        hikariConfig.setJdbcUrl(DBProperties.DB_URL);
//        hikariConfig.setUsername(DBProperties.DB_USER);
//        hikariConfig.setPassword(DBProperties.DB_PW);
//    hikariConfig.setMinimumPoolSize(10);
//        hikariConfig.setMaximumPoolSize(10);
//        hikariDataSource = new HikariDataSource(hikariConfig);
//    }

    public static Connection getConnection(){
        try {
            Class.forName(DBProperties.DB_DRIVER);
            return DriverManager.getConnection(DBProperties.DB_URL, DBProperties.DB_USER, DBProperties.DB_PW);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("exception");
            throw new RuntimeException(e);
        }
    }

//    public static Connection getConnectionFromPool() {
//        try {
//            System.out.println("hikari");
//            return hikariDataSource.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
}

