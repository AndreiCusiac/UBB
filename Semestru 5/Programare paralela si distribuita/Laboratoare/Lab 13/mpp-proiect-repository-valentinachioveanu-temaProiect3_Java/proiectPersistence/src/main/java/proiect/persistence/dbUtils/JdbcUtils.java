package proiect.persistence.dbUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private Properties jdbcProperties;
    private static final Logger logger = LogManager.getLogger();
    private Connection instance = null;

    public JdbcUtils(Properties properties) {
        jdbcProperties = properties;
    }

    private Connection getNewConnection() {
        logger.traceEntry();

        String driver =jdbcProperties.getProperty("server.jdbc.driver");
        String url = jdbcProperties.getProperty("server.jdbc.url");
        String user = jdbcProperties.getProperty("server.jdbc.user");
        String pass = jdbcProperties.getProperty("server.jdbc.pass");

        logger.info("trying to connect to database {}...", url);
        logger.info("user: {}", user);
        logger.info("pass: {}", pass);

        Connection connection = null;
        try {
            Class.forName(driver);
            if(user != null && pass!= null)
                connection = DriverManager.getConnection(url, user, pass);
            else
                connection = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error connecting to database " + ex);
        } catch (ClassNotFoundException ex) {
            logger.error(ex);
            System.out.println("Error loading driver " + ex);
        }
        return connection;
    }

    public Connection getConnection() {
        logger.traceEntry();

        try {
            if(instance == null || instance.isClosed())
                instance = getNewConnection();
        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Database error " + ex);
        }
        logger.traceExit(instance);
        return instance;
    }
}
