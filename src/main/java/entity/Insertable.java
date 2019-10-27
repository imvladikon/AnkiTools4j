package entity;

import java.sql.Connection;
import java.sql.SQLException;

public interface Insertable {
    public void save(Connection connection) throws SQLException;
}
