package modules.flowcollector.writer;

import modules.flowcollector.dao.Netflow;
import modules.flowcollector.db.jdbc.ConnectionFactory;
import modules.flowcollector.db.jdbc.FlowJdbc;

import java.io.File;
import java.util.List;

public class JdbcWriter implements Runnable {
    List<Netflow> list;
    FlowJdbc flowJdbc;
    public JdbcWriter(List<Netflow> list){
        this.list = list;
    }
    @Override
    public void run() {
        System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
        flowJdbc = new FlowJdbc(ConnectionFactory.getConnection());
        System.out.println("1 = " + 1);
        flowJdbc.insertNetflow(list);
        System.out.println("done");

    }
}
