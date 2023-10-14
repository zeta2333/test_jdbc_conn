import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * @author Pycro
 * @version 1.0
 * 2023-10-14 9:15
 */
public class TestConnection {

    public static Properties properties = new Properties();

    public static void main(String[] args) throws IOException {
        InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get("jdbc.properties")));
        properties.load(is);
        testConnection();
    }

    public static void testConnection() {
        Connection conn = null;
        PreparedStatement pstat = null;
        ResultSet rs = null;
        String dbtype = properties.getProperty("dbtype");
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        // 打印参数
        System.out.println("数据库类型：" + dbtype);
        System.out.println("数据库URL：" + url);
        System.out.println("用户名：" + username);
        System.out.println("密码：" + password);
        try {
            // 加载驱动
            Class.forName(driver);

            // 连接数据库
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("数据库连接已建立...");

            // 测试查询
            String testMsg = dbtype + "数据库测试连接成功！";
            String sql = "SELECT '" + testMsg + "' FROM DUAL";
            pstat = conn.prepareStatement(sql);
            rs = pstat.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("加载数据库驱动失败");
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("连接数据库失败");
            throw new RuntimeException(e);
        } finally {
            closeResource(rs, pstat, conn);
        }
    }

    public static void closeResource(ResultSet rs, PreparedStatement pstat, Connection conn) {
        DBUtil.close(rs);
        DBUtil.close(pstat);
        DBUtil.close(conn);
    }
}
