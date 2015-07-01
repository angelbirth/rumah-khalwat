package dao;

import bean.Tamu;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import model.ClassTableModel;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class TamuDAO {
    private static final String ID_TAMU = "id_tamu", NAMA = "nama",
            JENIS_KELAMIN = "jenis_kelamin", UMUR = "umur", ALAMAT = "alamat",
            TELP = "no_telpon_hp", STATUS = "status_perkawinan",
            PEKERJAAN = "pekerjaan";

    private static final Map<String, String> MAP = new HashMap<>();
    private static final BeanProcessor bp = new BeanProcessor(MAP);
    private static Connection connection;

    static {
        MAP.put(ID_TAMU, "id");
        MAP.put(NAMA, "nama");
        MAP.put(JENIS_KELAMIN, "jenisKelamin");
        MAP.put(UMUR, "umur");
        MAP.put(ALAMAT, "asal");
        MAP.put(TELP, "noHp");
        MAP.put(STATUS, "statusKawin");
        MAP.put(PEKERJAAN, "pekerjaan");
    }

    private static void createConnection() throws SQLException {
        if (connection == null) {

            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL("jdbc:mysql://localhost:3306/rumah_khalwat");
            ds.setUser("root");
            ds.setPassword("root");
            connection = ds.getConnection();
        }
    }

    public static List<Tamu> getAllData() throws SQLException {
        createConnection();
        ResultSet rs = connection.createStatement().executeQuery(
                "select * from tamu");
        return bp.toBeanList(rs, Tamu.class);

    }

    public static void main(String[] args) {
        try {
            System.out.println(getAllData());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static int addData(Tamu t) throws SQLException {
        createConnection();
        String sql = String
                .format("insert into tamu(%s,%s,%s,%s,%s,%s,%s,%s) values(?,?,?,?,?,?,?,?)",
                        ID_TAMU, NAMA, JENIS_KELAMIN, UMUR, ALAMAT, TELP,
                        STATUS, PEKERJAAN);
        PreparedStatement ps = connection.prepareStatement(sql);
        // binding
        {
            ps.setString(1, t.getId());
            ps.setString(2, t.getNama());
            ps.setString(3, t.getJenisKelamin().toString());
            ps.setInt(4, t.getUmur());
            ps.setString(5, t.getAsal());
            ps.setString(6, t.getNoHp());
            ps.setString(7, t.getStatusKawin().toString());
            ps.setString(8, t.getPekerjaan());
        }
        int rowsAffected = ps.executeUpdate();
        ps.close();
        connection.close();
        connection = null;
        return rowsAffected;
    }

    public static int deleteData(String idTamu) throws SQLException {
        createConnection();
        return connection.createStatement().executeUpdate(
                "delete from tamu where " + ID_TAMU + "='" + idTamu + "'");
    }

    public static class Fetcher extends SwingWorker<Void, Tamu> {
        private final ClassTableModel<Tamu> model;

        public Fetcher(ClassTableModel<Tamu> model) {
            this.model = model;
        }

        @Override
        protected Void doInBackground() throws Exception {
            createConnection();
            QueryRunner runner = new QueryRunner();
            runner.query(connection, "select * from tamu", (ResultSet rs) -> {
                while (rs.next()) {
                    Tamu t = bp.toBean(rs, Tamu.class);
                    publish(t);
                }
                return null;
            });
            return null;
        }

        @Override
        protected void process(List<Tamu> chunks) {
            model.setData(chunks);
        }
    }

}
