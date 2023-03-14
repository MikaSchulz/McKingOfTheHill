package me.eyetealer.mckingofthehill.kingofthehill.database.sql.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlConnectionInformation;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MySqlQueriesTest {

  private MySqlQueries mySqlQueries;

  @BeforeEach
  void setup() {
    SqlConnectionInformation sqlConnectionInformation = new SqlConnectionInformation(
        "mariadb", "127.0.0.1", 3306, "mckingofthehill",
        "mylocaluser", "mylocalpassword");
    SqlConnector sqlConnector = new SqlConnector(sqlConnectionInformation);
    sqlConnector.connect();
    Connection connection = sqlConnector.getConnection();
    mySqlQueries = new MySqlQueries(connection);
  }

  @Test
  void shouldCreateLocationTable() {
    try {
      mySqlQueries.createLocationTable();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}