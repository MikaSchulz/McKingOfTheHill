package me.eyetealer.mckingofthehill.kingofthehill.database.sql;

public record SqlConnectionInformation
    (String driver, String host, int port, String database, String user, String password) {

}
