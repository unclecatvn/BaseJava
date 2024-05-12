package dal;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {

    private static Connection connection;
    private static final Logger LOGGER = Logger.getLogger(DBContext.class.getName());

    static {
        AnsiConsole.systemInstall();
        initializeDBConnection();
    }

    private static void initializeDBConnection() {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/resources/db.properties"));
            setupConnection(props);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to load database properties", e);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Database connection failed", e);
        }
    }

    private static void setupConnection(Properties props) throws Exception {
        String dbType = props.getProperty("DB_CONNECTION");
        String driver = dbType.equals("mysql") ? "com.mysql.cj.jdbc.Driver" : "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        Class.forName(driver);

        String url = buildConnectionString(props, dbType);
        String username = props.getProperty(dbType + ".username");
        String password = props.getProperty(dbType + ".password");

        connection = DriverManager.getConnection(url, username, password);
        LOGGER.log(Level.INFO, "Connection to database {0} successful.", props.getProperty(dbType + ".database"));
    }

    private static String buildConnectionString(Properties props, String dbType) {
        String host = props.getProperty(dbType + ".host");
        String port = props.getProperty(dbType + ".port");
        String database = props.getProperty(dbType + ".database");
        if ("mysql".equals(dbType)) {
            return "jdbc:mysql://" + host + ":" + port + "/" + database;
        } else {
            return "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + database + ";encrypt=true;trustServerCertificate=true;";
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    protected void finalize() throws Throwable {
        AnsiConsole.systemUninstall();
        if (connection != null) {
            connection.close();
        }
        super.finalize();
    }

    public static void main(String[] args) {
        new DBContext();
    }
}
