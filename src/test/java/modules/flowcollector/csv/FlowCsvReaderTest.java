package modules.flowcollector.csv;

import modules.flowcollector.processor.FlowProcessor;
import modules.flowcollector.reader.FlowCsvReader;
import org.junit.jupiter.api.Test;

import java.io.File;

class FlowCsvReaderTest {

    @Test
     void CsvRead_테스트(){
        FlowProcessor flowProcessor;

        FlowCsvReader flowCsvReader = new FlowCsvReader();

        long s = System.currentTimeMillis();

        String property = System.getProperty("user.dir");
        System.out.println("property = " + property);
        String dirpath = property + "/file";

        File[] files = flowCsvReader.readFilesInDirectory(dirpath);
        flowCsvReader.readCsvFiles(files, 1_000, 10);

//        jdbcWriter.writeFlow(netflows);
//        jdbcWriter.writeFlow( new File(dirpath).listFiles());

        System.out.println("(System.currentTimeMillis() - s) = " + (System.currentTimeMillis() - s));
    }


}