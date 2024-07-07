package modules.flowcollector.writer;

import codes.prop.DBProperties;
import modules.flowcollector.dao.Netflow;
import modules.flowcollector.db.jdbc.ConnectionFactory;
import modules.flowcollector.db.jdbc.FlowJdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class JdbcWriter implements Runnable {
    List<Netflow> list;
    FlowJdbc flowJdbc;

    public JdbcWriter() {

    }

    public JdbcWriter(List<Netflow> list) {
        this.list = list;
    }

    @Override
    public void run() {
        try {
            FlowJdbc flowJdbc = new FlowJdbc(false);
            flowJdbc.insertNetflow(list);
            flowJdbc.clearConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
