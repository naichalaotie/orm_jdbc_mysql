package test;

import doMain.Student;
import orm.SqlSession;
import orm.annocation.Select;

import java.util.List;

public interface testDao {
    @Select("select id,name,sex,math,chinese,englishi from students")
    public List<Student> selectList();
}
