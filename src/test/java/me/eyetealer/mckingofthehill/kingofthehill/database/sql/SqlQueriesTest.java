package me.eyetealer.mckingofthehill.kingofthehill.database.sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.sql.Connection;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.mysql.MySqlQueries;
import org.junit.jupiter.api.Test;

class SqlQueriesTest {

  @Test
  void shouldReturnFileContent() {

    Connection connection = mock(Connection.class);

    String fileName = "queries/test.sql";

    MySqlQueries sqlQueries = new MySqlQueries(connection);

    String content = sqlQueries.readFile(fileName);

    String expected = "CREATE TABLE Location ( Name VARCHAR(255) PRIMARY KEY, World VARCHAR(255), X INT, Y INT, Z INT, Yaw FLOAT, Pitch FLOAT );";
    assertThat(content).isEqualTo(expected);

  }

}