package me.eyetealer.mckingofthehill.kingofthehill.database.sql.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import me.eyetealer.mckingofthehill.kingofthehill.database.sql.SqlQueries;

public class MySqlQueries extends SqlQueries {

  public MySqlQueries(Connection connection) {
    super(connection);
  }

  public void createLocationTable() throws SQLException {
    String query = readFile("queries/mysql/createLocationTable.sql");
    PreparedStatement createTableStmt = connection.prepareStatement(query);
    createTableStmt.executeUpdate();
  }


}
