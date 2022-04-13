package orm;

import java.util.List;

public class SqlAndColumn {
    private String sql;

    public SqlAndColumn(String sql, List<String> list) {
        this.sql = sql;
        this.list = list;
    }

    private List<String> list ;

    public String getSql() {
        return sql;
    }

    public List<String> getList() {
        return list;
    }
}
