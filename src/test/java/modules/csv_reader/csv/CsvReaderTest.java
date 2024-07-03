package modules.csv_reader.csv;

import org.junit.jupiter.api.Test;

class CsvReaderTest {

    @Test
    void CsvRead_테스트(){

        String property = System.getProperty("user.dir");

        System.out.println("property = " + property);

        String pathname = property + "/file";

        CsvReader.readCsv(pathname);
    }








}