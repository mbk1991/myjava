package modules.csv_reader.csv;

import modules.db.jdbc.JdbcUtil;
import modules.csv_reader.data.Netflow;
import modules.prop.DBProperties;
import jdk.internal.vm.annotation.ForceInline;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvReader {

    private static CsvParser csvParser = new CsvParser();
    private static JdbcUtil db = new JdbcUtil(DBProperties.DB_DRIVER, DBProperties.DB_URL, DBProperties.DB_USER, DBProperties.DB_PW);

    public static void readCsv(String pathname) {

        File file = new File(pathname);

        if (file.isDirectory()) {
            File[] files = file.listFiles();


            List<Netflow> l = new ArrayList<>();
            for (File f : files) {

                System.out.println(f.getName());
                try {
                    BufferedReader br = new BufferedReader(new FileReader(f));

                    String line;
                    boolean isHeader = true;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                        String[] values = line.split(",");
                        System.out.println("values.length = " + values.length);

                        if (isHeader) {
                            isHeader = false;
                            continue;
                        } else {
                            Netflow n = csvParser.parseNetflow(values);
                            if (n != null) {
                                l.add(n);
                            }
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //f.delete();
            }

            if (!l.isEmpty()) {
                db.insertNetflow(l);
            }

        }
    }

}
