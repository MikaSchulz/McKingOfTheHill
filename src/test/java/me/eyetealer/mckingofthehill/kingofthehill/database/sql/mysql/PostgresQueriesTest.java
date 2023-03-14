package me.eyetealer.mckingofthehill.kingofthehill.database.sql.mysql;

import java.sql.Connection;
import java.sql.SQLException;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlConnectionInformation;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostgresQueriesTest {

  private PostgresQueries postgresQueries;

  @BeforeEach
  void setup() {
    SqlConnectionInformation sqlConnectionInformation = new SqlConnectionInformation(
        "postgresql", "127.0.0.1", 5432, "mckingofthehill",
        "mylocaluser", "mylocalpassword");
    SqlConnector sqlConnector = new SqlConnector(sqlConnectionInformation);
    sqlConnector.connect();
    Connection connection = sqlConnector.getConnection();
    postgresQueries = new PostgresQueries(connection);
  }

  @Test
  void shouldCreateLocationTable() {
    try {
      postgresQueries.createLocationTable();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}