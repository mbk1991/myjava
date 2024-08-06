package modules.flowcollector.reader;

import modules.flowcollector.dao.Netflow;
import modules.flowcollector.processor.FlowProcessor;
import modules.flowcollector.writer.JdbcWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 **/
public class FlowCsvReader {

    private JdbcWriter writer;
    private FlowProcessor processor;
    private boolean hasProcessor;

    public FlowCsvReader(JdbcWriter writer) {
        this.writer = writer;
    }

    public void setProcessor(FlowProcessor flowProcessor) {
        this.processor = processor;
        hasProcessor = true;
    }

    public void clearProcessor() {
        this.processor = null;
        hasProcessor = false;
    }


    public File[] readFilesInDirectory(String dirpath) {
        File file = new File(dirpath);
        File[] files = new File[0];
        if (file.isDirectory()) {
            files = file.listFiles();
        }
        return files;
    }


    public void readCsvFiles(File[] files, int chunkSize, int threadPoolSize) {
        BufferedReader br;
        boolean isHeader;

        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolSize);
        List<Netflow> buff = new ArrayList<>();
        int recordCnt = 0;
        for (File f : files) {
            if (!checkFile(f, "csv")) {
                continue;
            }

            isHeader = true;
            try {
                br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null) {
                    //skip csv header
                    if (isHeader) {
                        isHeader = false;
                        continue;
                    }

                    //skip etc row
                    String[] values = line.split(",");
                    if (values.length < 48) {
                        break;
                    }

                    //flow processor
                    Netflow n;
                    if (hasProcessor) {
                        n = processor.flowProcess(setNetflow(values));
                    } else {
                        n = setNetflow(values);
                    }

                    //add list
                    if (n != null) {
                        buff.add(n);
                    }

                    //chunk size process
                    if (++recordCnt == chunkSize) {
                        //writer process
                        executorService.execute(new JdbcWriter(buff));
                        //init
                        recordCnt = 0;
                        buff = new ArrayList<>();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //file process, file move

            Path sPath = f.toPath();
            String path = f.getPath();
            String name = path.substring(path.lastIndexOf(File.separator));
            Path dPath = Paths.get(path.replace(name, "/completed" + name));
            try {
                Files.deleteIfExists(dPath);
                Files.move(sPath, dPath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

        }


        if (!buff.isEmpty()) {
            executorService.execute(new JdbcWriter(buff));
        }
        executorService.shutdown();
    }


    private Netflow setNetflow(String[] v) {
        Netflow result = new Netflow();

        result.setCollect_time(Instant.now().getEpochSecond());
//        result.setTcpFlag();
//        result.setTos();
//        result.setFwd();
//        result.setIcmpType();
//        result.setIcmpCode();
        result.setIpVer("test");
        result.setTs(v[0]);
        result.setTe(v[1]);
        result.setTd(v[2]);
        result.setSa(v[3]);
        result.setDa(v[4]);
        result.setSp(Integer.parseInt(v[5]));
        result.setDp(Integer.parseInt(v[6]));
        result.setPr(v[7]);
        result.setFlg(v[8]);   // tcp flag
        result.setFwd(Integer.parseInt(v[9]));
        result.setStos(Integer.parseInt(v[10]));
        result.setIpkt(Long.parseLong(v[11]));
        result.setIbyt(Long.parseLong(v[12]));
        result.setOpkt(Long.parseLong(v[13]));
        result.setObyt(Long.parseLong(v[14]));
        result.setInif(Integer.parseInt(v[15]));
        result.setOutif(Integer.parseInt(v[16]));
        result.setSas(Integer.parseInt(v[17]));
        result.setDas(Integer.parseInt(v[18]));
        result.setSmk(Integer.parseInt(v[19]));
        result.setDmk(Integer.parseInt(v[20]));
        result.setDtos(Integer.parseInt(v[21]));
        result.setDir(Integer.parseInt(v[22]));
        result.setNh(v[23]);
        result.setNhb(v[24]);
        result.setSvln(Integer.parseInt(v[25]));
        result.setDvln(Integer.parseInt(v[26]));
        result.setIsmc(v[27]);
        result.setOdmc(v[28]);
        result.setIdmc(v[29]);
        result.setOsmc(v[30]);
        result.setMpls1(v[31]);
        result.setMpls2(v[32]);
        result.setMpls3(v[33]);
        result.setMpls4(v[34]);
        result.setMpls5(v[35]);
        result.setMpls6(v[36]);
        result.setMpls7(v[37]);
        result.setMpls8(v[38]);
        result.setMpls9(v[39]);
        result.setMpls10(v[40]);
        result.setCl(v[41]);
        result.setSl(v[42]);
        result.setAl(v[43]);
        result.setRa(v[44]);
        result.setEng(v[45]);
        result.setExid(v[46]);
        result.setTr(v[47]);


        return result;
    }

    private boolean checkFile(File file, String nameChk) {
        if (!file.isFile() || !file.getName().contains(nameChk)) {
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        FlowProcessor flowProcessor;
        JdbcWriter jdbcWriter = new JdbcWriter();
        FlowCsvReader flowCsvReader = new FlowCsvReader(jdbcWriter);

        String property = System.getProperty("user.dir");
        String dirpath = property + "/file";

        File[] files = flowCsvReader.readFilesInDirectory(dirpath);
        flowCsvReader.readCsvFiles(files, 1_000, 10);

    }


}
