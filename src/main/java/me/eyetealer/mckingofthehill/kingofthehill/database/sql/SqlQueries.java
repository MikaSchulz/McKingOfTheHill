package me.eyetealer.mckingofthehill.kingofthehill.database.sql;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SqlQueries {

  protected final Connection connection;

  public SqlQueries(Connection connection) {
    this.connection = connection;
  }

  public String readFile(String fileName) {
    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
    if (inputStream == null) {
      throw new RuntimeException(String.format("fileName=%s wasn't found", fileName));
    }
    Stream<String> stream = new BufferedReader(new InputStreamReader(inputStream)).lines();
    String result = stream.collect(Collectors.joining(" "));
    return result.replaceAll("\\s+", " ");
  }

  public abstract void createLocationTable() throws SQLException;

}
