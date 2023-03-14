package me.eyetealer.mckingofthehill.kingofthehill.database.sql;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.mysql.MySqlQueries;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.mysql.PostgresQueries;
import org.junit.jupiter.api.Test;

class SqlConnectorTest {

  private final String host = "127.0.0.1";
  private final String user = "mylocaluser";
  private final String password = "mylocalpassword";
  private final String database = "mckingofthehill";

  @Test
  void shouldConnectToMariaDb() throws SQLException {
    // given
    String driver = "mariadb";
    int port = 3306;
    SqlConnectionInformation sqlConnectionInformation = new SqlConnectionInformation(driver, host,
        port, database, user, password);
    SqlConnector sqlConnector = new SqlConnector(sqlConnectionInformation);

    // when
    sqlConnector.connect();

    // then
    assertThat(sqlConnector.getConnection().getCatalog()).isEqualTo(database);

    // when
    sqlConnector.initSqlQueries();

    // then
    assertThat(sqlConnector.getSqlQueries()).isInstanceOf(MySqlQueries.class);
  }


  @Test
  void shouldConnectToMysql() throws SQLException {
    // given
    String driver = "mysql";
    int port = 3306;
    SqlConnectionInformation sqlConnectionInformation = new SqlConnectionInformation(driver, host,
        port, database, user, password);
    SqlConnector sqlConnector = new SqlConnector(sqlConnectionInformation);

    // when
    sqlConnector.connect();

    // then
    assertThat(sqlConnector.getConnection().getCatalog()).isEqualTo(database);

    // when
    sqlConnector.initSqlQueries();

    // then
    assertThat(sqlConnector.getSqlQueries()).isInstanceOf(MySqlQueries.class);
  }

  @Test
  void shouldConnectToPostgres() throws SQLException {
    // given
    String driver = "postgresql";
    int port = 5432;
    SqlConnectionInformation sqlConnectionInformation = new SqlConnectionInformation(driver, host,
        port, database, user, password);
    SqlConnector sqlConnector = new SqlConnector(sqlConnectionInformation);

    // when
    sqlConnector.connect();

    // then
    assertThat(sqlConnector.getConnection().getCatalog()).isEqualTo(database);

    // when
    sqlConnector.initSqlQueries();

    // then
    assertThat(sqlConnector.getSqlQueries()).isInstanceOf(PostgresQueries.class);
  }
}