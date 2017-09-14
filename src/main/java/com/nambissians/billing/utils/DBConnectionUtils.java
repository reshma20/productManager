package com.nambissians.billing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This is a copyright of the Brahmana food products
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 * <p>
 * Redistribution and use in source and binary forms, without permission
 * from copyright owner is not permited.
 **/


public class DBConnectionUtils {
    private static String username;
    private static String password;
    private static final String JDBC_CONNECTION_URL = "jdbc_connection_url";
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final Logger logger = LoggerFactory.getLogger(DBConnectionUtils.class);
    private static DBConnectionUtils connectionUtils;
    private static Connection getNewConnection() throws SQLException, ClassNotFoundException, InstantiationException,IllegalAccessException {
        Class.forName(MYSQL_JDBC_DRIVER).newInstance();
        String url = System.getProperty(JDBC_CONNECTION_URL);
        Connection connection = DriverManager.getConnection(url, username,password);
        return connection;
    }
    private DBConnectionUtils(String username, String password) throws SQLException, ClassNotFoundException, InstantiationException,IllegalAccessException  {
        this.username = username;
        this.password = password;
        Connection connection = getNewConnection();
        connection.close();
    }
    public static Connection startTransaction(){
        try {
            try {
                Connection connection = getNewConnection();
                connection.setAutoCommit(false);
                return connection;
            } catch (Exception exp) {
                logger.debug("Connection issues retrying again", exp);
                Connection connection = getNewConnection();
                return connection;
            }
        } catch (Exception exp) {
            logger.error("Could not get a connection ", exp);
            return null;
        }
    }

    public static Connection getConnection() {
        return startTransaction();
    }

    public static boolean commitTransaction(Connection connection) throws SQLException{
        try {
            connection.commit();
            return true;
        } catch (SQLException exp) {
            logger.error("Could not commit transaction",exp);
            return false;
        } finally {
            connection.close();
        }
    }

    public static boolean rollbackTransaction(Connection connection) throws SQLException{
        try {
            connection.rollback();
            return true;
        } catch (SQLException exp) {
            logger.error("Could not commit transaction",exp);
            return false;
        } finally {
            connection.close();
        }
    }
    public static boolean initializeDbConnections(String username, String password) {
        try {
            if (connectionUtils == null) {
                synchronized (DBConnectionUtils.class) {
                    if (connectionUtils == null) {
                        connectionUtils = new DBConnectionUtils(username, password);
                    }
                }
            }
            return true;
        } catch (SQLException exp) {
            logger.error("Couldn't fetch connection with username {} password {}", username, password, exp);
            return false;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException exp ) {
            logger.error("Couldn't load jdbc drivers with username {} password {}", username, password, exp);
            return false;
        }
    }
}
