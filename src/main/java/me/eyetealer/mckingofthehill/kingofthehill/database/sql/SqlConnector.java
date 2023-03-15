package me.eyetealer.mckingofthehill.kingofthehill.database.sql;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import lombok.Getter;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.mysql.MySqlQueries;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.mysql.PostgresQueries;

public class SqlConnector {

  private final SqlConnectionInformation sqlConnectionInformation;
  @Getter
  private Connection connection;
  @Getter
  private SqlQueries sqlQueries;

  public SqlConnector(SqlConnectionInformation sqlConnectionInformation) {
    this.sqlConnectionInformation = sqlConnectionInformation;
  }

  public void initSqlQueries() {
    String driver = sqlConnectionInformation.driver();
    sqlQueries = switch (driver) {
      case "mariadb", "mysql" -> new MySqlQueries(connection);
      case "postgresql" -> new PostgresQueries(connection);
      default -> throw new IllegalStateException(
          "Unexpected sql driver=" + driver);
    };
  }

//  private Driver

  public void connect() {
    try {
      String connectionString = String.format("jdbc:%s://%s:%s/%s?autoReconnect=true",
          sqlConnectionInformation.driver(),
          sqlConnectionInformation.host(),
          sqlConnectionInformation.port(),
          sqlConnectionInformation.database());
      Class.forName(com.mysql.cj.jdbc.Driver.class.getCanonicalName());
      Class.forName(org.mariadb.jdbc.Driver.class.getCanonicalName());
      Class.forName(org.postgresql.Driver.class.getCanonicalName());
      connection = DriverManager.getConnection(connectionString,
          sqlConnectionInformation.user(),
          sqlConnectionInformation.password());
    } catch (SQLException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

//  public void disconnect() {
//    if (hasConnection()) {
//      try {
//        this.connection.close();
//      } catch (SQLException exception) {
//        exception.printStackTrace();
//      }
//    }
//  }
//
//  public boolean hasConnection() {
//    return (this.connection != null);
//  }

}
