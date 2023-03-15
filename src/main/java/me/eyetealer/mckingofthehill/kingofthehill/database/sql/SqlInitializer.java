package me.eyetealer.mckingofthehill.kingofthehill.database.sql;

import java.sql.SQLException;
import me.eyetealer.mckingofthehill.kingofthehill.KingOfTheHill;
import org.bukkit.configuration.file.FileConfiguration;

public class SqlInitializer {

  private final KingOfTheHill plugin;

  public SqlInitializer(KingOfTheHill plugin) {
    this.plugin = plugin;
  }

  public void init() {
    FileConfiguration config = plugin.getConfigProvider().getConfigFile().getConfig();
    SqlConnectionInformation sqlConnectionInformation = readSqlConnectionInformation(config);
    SqlConnector sqlConnector = new SqlConnector(sqlConnectionInformation);
    sqlConnector.connect();
    sqlConnector.initSqlQueries();
    setupTables(sqlConnector);
  }

  private void setupTables(SqlConnector sqlConnector) {
    try {
      sqlConnector.getSqlQueries().createLocationTable();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private SqlConnectionInformation readSqlConnectionInformation(FileConfiguration config) {
    String driver = config.getString("sql.driver");
    String host = config.getString("sql.host");
    int port = config.getInt("sql.port");
    String user = config.getString("sql.user");
    String password = config.getString("sql.password");
    String database = config.getString("sql.database");
    return new SqlConnectionInformation(driver, host, port, database, user, password);
  }

}
