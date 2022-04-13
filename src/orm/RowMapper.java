package orm;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
    <T>T resultObject(ResultSet resultSet) throws SQLException;
}
