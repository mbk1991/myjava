package modules.db.jdbc;

import modules.csv_reader.data.Netflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcUtil {

    private final String JDBC_DRIVER;
    private final String JDBC_URL;
    private final String JDBC_USER;
    private final String JDBC_PW;
    private Connection con;


    public JdbcUtil(String jdbcDriver, String jdbcUrl, String jdbcUser, String jdbcPw) {
        JDBC_DRIVER = jdbcDriver;
        JDBC_URL = jdbcUrl;
        JDBC_USER = jdbcUser;
        JDBC_PW = jdbcPw;

        getConnection();
    }


    public void getConnection() {
        try {
            if (con != null) {
                con.close();
            }
            Class.forName(JDBC_DRIVER).newInstance();
            con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PW);

        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertNetflow(List<Netflow> l) {

        String q = new StringBuilder()
                .append("INSERT INTO NC_FLOW_RAW_FLOW_T ")
                .append("(COLLECT_TIME, RA, SA, DA, SP, DP, PR, TS, TE,")
                .append("SAS, DAS, TOS, DIR, SMK, DMK, FWD, ")
                .append("MPLS1, MPLS2, MPLS3, MPLS4 ")
                .append("TD, FLAG, STOS, IPKT, IBYT, OPKT, OBYT, IN, OUT, DTOS, NH, NHB ")
                .append("SVLN, DVLN, ISMC, IDMC, OSMC, CL, SL, AL, ENG, EXID, TR) ")
                .append("INTO VALUES(?,?,?,?,?,?,?,?,?,?")
                .append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?")
                .append("?.?.?.?.?.?.?.?.?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?")
                .toString();

        try (PreparedStatement ps = con.prepareStatement(q)) {

            for (Netflow n : l) {
                ps.setLong(1, n.getCollect_time());
                ps.setString(2, n.getRa());
                ps.setString(3, n.getSa());
                ps.setString(4, n.getDa());
                ps.setInt(5, n.getSp());
                ps.setInt(6, n.getDp());
                ps.setString(7, n.getPr());
                ps.setString(8, n.getTs());
                ps.setString(9, n.getTe());
                ps.setInt(10, n.getSas());

                ps.setInt(11, n.getDas());
                ps.setInt(12, n.getTos());
                ps.setInt(13, n.getDir());
                ps.setInt(14, n.getSmk());
                ps.setInt(15, n.getDmk());
                ps.setInt(16, n.getFwd());
                ps.setString(17, n.getMpls1());
                ps.setString(18, n.getMpls2());
                ps.setString(19, n.getMpls3());
                ps.setString(20, n.getMpls4());

                ps.setString(21, n.getTd());
                ps.setString(22, n.getFlg());
                ps.setInt(23, n.getStos());
                ps.setLong(24, n.getIpkt());
                ps.setLong(25, n.getIbyt());
                ps.setLong(26, n.getOpkt());
                ps.setLong(27, n.getObyt());
                ps.setInt(28, n.getIn());
                ps.setInt(29, n.getOut());
                ps.setInt(30, n.getDtos());

                ps.setString(31, n.getNh());
                ps.setString(32, n.getNhb());
                ps.setInt(33, n.getSvln());
                ps.setInt(34, n.getDvln());
                ps.setString(35, n.getIsmc());
                ps.setString(36, n.getIdmc());
                ps.setString(37, n.getOsmc());
                ps.setString(38, n.getCl());
                ps.setString(39, n.getSl());
                ps.setString(40, n.getAl());

                ps.setString(41, n.getRa());
                ps.setString(42, n.getEng());
                ps.setString(43, n.getExid());
                ps.setString(44, n.getTr());

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
