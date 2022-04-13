package orm.ConnectionPool;

import orm.configuartionReader.ConfigRead;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyConnection extends AbstractConnection{
    private Connection connection;
    private Boolean use = false;

    static{
        try {
            Class.forName(ConfigRead.getString("className"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    MyConnection(){
        try {
            connection = DriverManager.getConnection(ConfigRead.getString("url"),ConfigRead.getString("userName"),ConfigRead.getString("userPass"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public Connection getConnection(){
        return connection;
    }

    public Boolean isUsed(){
        return use;
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    @Override
    public void close() throws SQLException {
        this.use = false;
    }
}
