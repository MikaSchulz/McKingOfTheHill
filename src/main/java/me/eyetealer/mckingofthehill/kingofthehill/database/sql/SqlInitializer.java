package me.eyetealer.mckingofthehill.kingofthehill.database.sql;

import java.sql.SQLException;
import me.eyetealer.mckingofthehill.kingofthehill.configuration.ConfigProvider;
import org.bukkit.configuration.file.FileConfiguration;

public class SqlInitializer {

  public static void init() {
    SqlConnectionInformation sqlConnectionInformation = readSqlConnectionInformation();
    SqlConnector sqlConnector = new SqlConnector(sqlConnectionInformation);
    sqlConnector.connect();
    sqlConnector.initSqlQueries();
    setupTables(sqlConnector);
  }

  private static void setupTables(SqlConnector sqlConnector) {
    try {
      sqlConnector.getSqlQueries().createLocationTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static SqlConnectionInformation readSqlConnectionInformation() {
    FileConfiguration config = ConfigProvider.getConfigFile().getConfig();
    String driver = config.getString("sql.driver");
    String host = config.getString("sql.host");
    int port = config.getInt("sql.port");
    String user = config.getString("sql.user");
    String password = config.getString("sql.password");
    String database = config.getString("sql.database");
    return new SqlConnectionInformation(driver, host, port, database, user, password);
  }

}
