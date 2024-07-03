package modules.csv_reader.csv;

import modules.csv_reader.data.Netflow;

import java.time.Instant;

public class CsvParser {

    public Netflow parseNetflow(String[] v) {

        if(v.length < 48){
            return null;
        }




        Netflow n = new Netflow();

        n.setCollect_time(Instant.now().getEpochSecond());
//        n.setTcpFlag();
//        n.setTos();
//        n.setFwd();
//        n.setIcmpType();
//        n.setIcmpCode();
//        n.setIpVer();

        n.setTs(v[0]);
        n.setTe(v[1]);
        n.setTd(v[2]);
        n.setSa(v[3]);
        n.setDa(v[4]);
        n.setSp(Integer.parseInt(v[5]));
        n.setDp(Integer.parseInt(v[6]));
        n.setPr(v[7]);
        n.setFlg(v[8]);
        n.setFwd(Integer.parseInt(v[9]));
        n.setStos(Integer.parseInt(v[10]));
        n.setIpkt(Long.parseLong(v[11]));
        n.setIbyt(Long.parseLong(v[12]));
        n.setOpkt(Long.parseLong(v[13]));
        n.setObyt(Long.parseLong(v[14]));
        n.setIn(Integer.parseInt(v[15]));
        n.setOut(Integer.parseInt(v[16]));
        n.setSas(Integer.parseInt(v[17]));
        n.setDas(Integer.parseInt(v[18]));
        n.setSmk(Integer.parseInt(v[19]));
        n.setDmk(Integer.parseInt(v[20]));
        n.setDtos(Integer.parseInt(v[21]));
        n.setDir(Integer.parseInt(v[22]));
        n.setNh(v[23]);
        n.setNhb(v[24]);
        n.setSvln(Integer.parseInt(v[25]));
        n.setDvln(Integer.parseInt(v[26]));
        n.setIsmc(v[27]);
        n.setOdmc(v[28]);
        n.setIdmc(v[29]);
        n.setOsmc(v[30]);
        n.setMpls1(v[31]);
        n.setMpls2(v[32]);
        n.setMpls3(v[33]);
        n.setMpls4(v[34]);
        n.setMpls5(v[35]);
        n.setMpls6(v[36]);
        n.setMpls7(v[37]);
        n.setMpls8(v[38]);
        n.setMpls9(v[39]);
        n.setMpls10(v[40]);
        n.setCl(v[41]);
        n.setSl(v[42]);
        n.setAl(v[43]);
        n.setRa(v[44]);
        n.setEng(v[45]);
        n.setExid(v[46]);
        n.setTr(v[47]);


        return n;
    }
}
