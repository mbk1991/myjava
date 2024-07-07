package modules.flowcollector.db.jdbc;

import codes.prop.DBProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import modules.flowcollector.dao.Netflow;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FlowJdbc {

    private final String JDBC_DRIVER;
    private final String JDBC_URL;
    private final String JDBC_USER;
    private final String JDBC_PW;

    private HikariConfig hikariConfig;
    private HikariDataSource hikariDataSource;

    private Connection con;


//    public FlowJdbc(Connection con) {
//        this.con = con;
//    }

    public FlowJdbc(boolean usePool) {
        JDBC_DRIVER = DBProperties.DB_DRIVER;
        JDBC_URL = DBProperties.DB_URL;
        JDBC_USER = DBProperties.DB_USER;
        JDBC_PW = DBProperties.DB_PW;


        if (usePool) {
            hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(JDBC_DRIVER);
            hikariConfig.setJdbcUrl(JDBC_URL);
            hikariConfig.setUsername(JDBC_USER);
            hikariConfig.setPassword(JDBC_PW);
            hikariDataSource = new HikariDataSource(hikariConfig);

            getConnectionFromPool();
        } else {
            getConnection();
        }
    }


    public void getConnection() {
        try {
            if (con == null) {
                Class.forName(JDBC_DRIVER).newInstance();
                con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PW);
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getConnectionFromPool() {
        try {
            if (con == null) {
                con = hikariDataSource.getConnection();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void clearConnection() throws SQLException {
        if (!con.isClosed()) {
            con.close();
        }
    }

    public void insertNetflow(List<Netflow> l) {
        String q = new StringBuilder()
                .append("INSERT INTO NC_FLOW_RAW_FLOW_T ")
                .append("(COLLECT_TIME, RA, SA, DA, SP, DP, PR, TS, TE, SAS,")
                .append("DAS, TOS, DIR, SMK, DMK, FWD, MPLS1, MPLS2, MPLS3, MPLS4,")
                .append("TD, FLAG, STOS, IPKT, IBYT, OPKT, OBYT, INIF, OUTIF, DTOS, ")
                .append("NH, NHB, SVLN, DVLN, ISMC, IDMC, OSMC, ODMC,  CL, SL, ")
                .append("AL, ENG, EXID, TR, IP_VER)")
                .append("VALUES (?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,")
                .append("?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?) ")
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
                ps.setInt(28, n.getInif());
                ps.setInt(29, n.getOutif());
                ps.setInt(30, n.getDtos());

                ps.setString(31, n.getNh());
                ps.setString(32, n.getNhb());
                ps.setInt(33, n.getSvln());
                ps.setInt(34, n.getDvln());
                ps.setString(35, n.getIsmc());
                ps.setString(36, n.getIdmc());
                ps.setString(37, n.getOsmc());
                ps.setString(38, n.getOdmc());
                ps.setString(39, n.getCl());
                ps.setString(40, n.getSl());

                ps.setString(41, n.getAl());
                ps.setString(42, n.getEng());
                ps.setString(43, n.getExid());
                ps.setString(44, n.getTr());
                ps.setString(45, n.getIpVer());

                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void insertNetFlowCSV(File[] fileList) {

        String q = new StringBuilder()
                .append("LOAD DATA INFILE ?")
                .append("INTO TABLE NC_FLOW_RAW_FLOW_T ")
                .append("FIELD TERMINATED BY ',' ")
                .append("ENCLOSED BY '\"'")
                .append("LINES TERMINATED BY '\\n")
                .append("IGNORE 1 LINES")
                .append("SET col1 = @col1")
                .append("WHERE NOT (@col1 = 'Summary')")
                .toString();

        try (PreparedStatement statement = con.prepareStatement(q)) {
            for (File f : fileList) {
                statement.setString(1, f.getName());
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
