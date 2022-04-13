package test;

import doMain.Student;
import orm.RowMapper;
import orm.SqlSession;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TetsMain {
    public static void main(String[] args) {
        String className = "";
        String url = "";
        String name = "";
        String pass = "";

        //创建一个SqlSession对象
       // SqlSession sqlSession = new SqlSession(className, url, name, pass);
        SqlSession sqlSession = new SqlSession();

        //刷新一下
        String sql1 = "insert into students (id,name,sex) values (?,?,?)";
        sqlSession.update(sql1, 7, "测试", "sex");



        //查询一下
        String sql2 = "select id,name,sex,math,chinese,englishi from students where id = ?";
        MapperRow mapperRow = new MapperRow();
        Student student = sqlSession.selectOne(sql2,mapperRow,1);
        System.out.println(student.toString());


        //用新的方法刷新一下
        String sql3 = "insert into students (id,name,sex) values (#{id},#{name},#{sex})";
        Student student2 = new Student();
        student.setId(15);
        student.setName("测试2022.04.06");
        student.setSex("男");
        sqlSession.update(sql3,student2);

        //用新的方法查询一下
        String sql4 = "select id,name,sex,math,chinese,englishi from students where id = #{id}";
        Student student3 = sqlSession.selectOne(sql4,4,Student.class);
        System.out.println(student3.getName());


        String sql5 = "select id,name,sex,math,chinese,englishi from students";
        List<Student> list = sqlSession.selectList(sql5,Student.class);
        System.out.println(list.toString());


        //代理模式
        testDao t  = sqlSession.getMapper(testDao.class);
        List<Student> students = t.selectList();
        System.out.println(students.toString());


    }

}


class Test1{
    public  <T> T re(){
        //方法返回的是Object 不可以用Student类型接
//        Student student = this.returnStudent();
        Object o = this.returnStudent();

        return (T) o;
    }
    private Object returnStudent(){
        return new Student();
    }
}



class MapperRow implements RowMapper {
    @Override
    public <T> T resultObject(ResultSet re) throws SQLException {
        Student student = new Student();
        student.setId(re.getInt("id"));
        student.setName(re.getString("name"));
        student.setSex(re.getString("sex"));
        student.setMath(re.getInt("math"));
        student.setChinese(re.getInt("chinese"));
        student.setEnglishi(re.getInt("englishi"));
        return (T) student;
    }




}