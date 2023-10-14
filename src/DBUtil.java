/**
 * @author Pycro
 * @version 1.0
 * 2023-10-14 9:16
 */
public class DBUtil {
    public static void close(AutoCloseable res) {
        if (res != null) {
            try {
                res.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
