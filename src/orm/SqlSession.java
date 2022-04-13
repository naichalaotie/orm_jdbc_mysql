package orm;

import orm.ConnectionPool.ConnectionPool;
import orm.annocation.Delete;
import orm.annocation.Insert;
import orm.annocation.Select;
import orm.annocation.Update;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlSession {
    private String className;
    private String url;
    private String name;
    private String pass;

    public SqlSession(String className, String url, String name, String pass) {
        this.className = className;
        this.url = url;
        this.name = name;
        this.pass = pass;
    }

    public SqlSession(){

    }

    Connection conn;
    PreparedStatement pstat;
    ResultSet re;
    ConnectionPool connectionPool = new ConnectionPool();

    //利用动态参数列表实现sql的拼接
    public void update(String sql, Object... array){
        try {
            conn = connectionPool.getMyConnection();
            pstat = conn.prepareStatement(sql);
            for(int i = 0;i<array.length;i++){
                pstat.setObject(i+1,array[i]);
            }
            pstat.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                pstat.close();
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

//        try {
//            //加载驱动类
//            Class.forName(className);
//            //获取链接
//            conn = DriverManager.getConnection(url,name,pass);
//            //获取状态参数
//            pstat = conn.prepareStatement(sql);
//            for(int i = 0;i<array.length;i++){
//                pstat.setObject(i+1,array[i]);
//            }
//            pstat.executeUpdate();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            try {
//                pstat.close();
//                conn.close();
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//        }
    }


//    这个方法用来解析sql
    private SqlAndColumn parseSql(String sql){
        StringBuilder stringBuilder = new StringBuilder();
        List<String> list = new ArrayList<>();
        while(true) {
            int left = sql.indexOf("#{");
            int right = sql.indexOf("}");
            if(left!=-1&&right!=-1&&left<right){
                stringBuilder.append(sql.substring(0,left));
                stringBuilder.append("?");
                list.add(sql.substring(left+2,right));
                sql = sql.substring(right+1);
            }else{
                stringBuilder.append(sql);
                break;
            }
        }
        return new SqlAndColumn(stringBuilder.toString(),list);
    }


    //这个方法拼接map类型参数的sql
    private PreparedStatement handlerMap(PreparedStatement psat,List<String> list,Object obj) throws NoSuchFieldException, IllegalAccessException, SQLException {
        Map map = (Map) obj;
        for (int i = 0;i<list.size();i++){
            pstat.setObject(i+1,map.get(list.get(i)));
        }
        return pstat;
    }

    //这个方法拼接Object类型参数的sql
    private PreparedStatement handlerObject(PreparedStatement psat,List<String> list,Object obj) throws NoSuchFieldException, IllegalAccessException, SQLException {
        Class clazz = obj.getClass();
        for(int i=0;i<list.size();i++){
            Field field = clazz.getDeclaredField(list.get(i));
            field.setAccessible(true);
            pstat.setObject(i+1,field.get(obj));
        }
        return pstat;
    }
    //这个方法用来拼接sql
    private PreparedStatement handlerParameter(PreparedStatement psat,List<String> list,Object obj) throws Exception, NoSuchFieldException, IllegalAccessException {
        if(obj.getClass()==int.class || obj.getClass()==Integer.class){
            pstat.setInt(1,(int)obj);
        }else if(obj.getClass()==Float.class){
                    pstat.setFloat(1,(Float)obj);
        }else if(obj.getClass()==double.class){
                    pstat.setDouble(1,(double)obj);
        }else if(obj.getClass()==String.class){
                    pstat.setString(1,(String)obj);
        }else if(obj.getClass()== Map.class){
                    pstat = this.handlerMap(pstat,list,obj);
        }else {
                    pstat = this.handlerObject(psat,list,obj);
        }

        return pstat;
    }


    //利用反射技术进行sql的拼接
    //传入sql的形式应为 "insert into students (id,name,sex) values (#{id},#{name},#{sex})"
    public void update(String sql,Object obj){
        //sql解析
        SqlAndColumn sqlAndColumn = this.parseSql(sql);
        try {
//            Class.forName(className);
//            conn = DriverManager.getConnection(url,name,pass);
            conn = connectionPool.getMyConnection();
            pstat = conn.prepareStatement(sqlAndColumn.getSql());
            if (obj==null&&sqlAndColumn.getList().get(0)!=null){
                    throw  new Exception("不要忘了传递参数哦");
            }
            if(obj!=null) {
                pstat = this.handlerParameter(pstat, sqlAndColumn.getList(), obj);
            }
            pstat.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                pstat.close();
                conn.close();
            }catch(SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }


//查询多条结果
    public <T> List<T> selectList(String sql,RowMapper rowMapper,Object... array){
        List<T> list = null;
        try {
//            Class.forName(className);
//            conn = DriverManager.getConnection(url,name,pass);

            conn = connectionPool.getMyConnection();

            pstat = conn.prepareStatement(sql);
            for (int i = 0;i<array.length;i++){
                pstat.setObject(i+1,array[i]);
            }
            re = pstat.executeQuery();
            while(re.next()){
                list = new ArrayList<>();
                list.add((T)rowMapper.resultObject(re));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                re.close();
                conn.close();
                pstat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        return list;
    }

    //查询单条数据
    public <T> T selectOne(String sql,RowMapper rowMapper,Object... array){

        return (T) this.selectList(sql,rowMapper,array).get(0);
    }





    //这个办法将返回值组装成map
    public Map getMap(ResultSet re) throws SQLException {
        Map map = new HashMap();
        ResultSetMetaData resultSetMetaData = re.getMetaData();
        for(int i=1;i<=resultSetMetaData.getColumnCount();i++){
            map.put(resultSetMetaData.getColumnName(i),re.getObject(i));
        }
        return map;
    }

    //这个方法将返回值组装成对象
    public Object getObject(ResultSet re,Class resultType)throws Exception{
        Object obj = null;
        obj = resultType.newInstance();
        ResultSetMetaData resultSetMetaData = re.getMetaData();
        for(int i=1;i<=resultSetMetaData.getColumnCount();i++){
            Field field = resultType.getDeclaredField(resultSetMetaData.getColumnName(i));
            //如果查出来的列为空 则不进行拼装操作
            field.setAccessible(true);
            if(re.getObject(i)!=null){
                field.set(obj,re.getObject(i));
            }
        }
        return obj;
    }

    //这个办法用来处理返回值
    public Object handlerResult(ResultSet re,Class resultType) throws Exception {
        Object obj = null;
        if(resultType==int.class&&resultType==Integer.class){
             obj = re.getInt(1);
        }else if(resultType==String.class){
             obj = re.getString(1);
        }else if(resultType==Map.class){
             obj = this.getMap(re);
        }else{
            obj = this.getObject(re,resultType);
        }

        return obj;
    }

    //利用反射技术处理查询
    public <T> List<T> selectList(String sql,Object obj,Class resultType){
        List<T> list = new ArrayList<>();
        try {
            SqlAndColumn sqlAndColumn = this.parseSql(sql);
            if(obj==null&&sqlAndColumn.getList().size()!=0){
                throw new Exception("不要忘了传参数哦");
            }
            //Class.forName(className);
            //conn = DriverManager.getConnection(url,name,pass);
            conn = connectionPool.getMyConnection();
            pstat = conn.prepareStatement(sqlAndColumn.getSql());
            if(obj!=null){
                this.handlerParameter(pstat, sqlAndColumn.getList(), obj);
            }
            re = pstat.executeQuery();
            while (re.next()){
                list.add((T)this.handlerResult(re,resultType));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                re.close();
                conn.close();
                pstat.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return list;
    }

    public <T> List<T> selectList(String sql,Class resultType){
        return this.selectList(sql,null,resultType);
    }

    //查询单条数据
    public <T> T selectOne(String sql,Object obj,Class resultType){
        return (T)this.selectList(sql,obj,resultType).get(0);
    }

    //这个方法返回一个代理
    public <T> T getMapper(Class clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz}, new InvocationHandler(){
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Annotation an  = method.getAnnotations()[0];
                Class type = an.annotationType();
                Method valueMethod = type.getDeclaredMethod("value");
                String sql = (String)valueMethod.invoke(an);
                Object obj = args==null?null:args[0];
                if(type == Insert.class){
                    SqlSession.this.update(sql,obj);
                }else if(type == Delete.class){
                    SqlSession.this.update(sql,obj);
                }else if(type== Update.class){
                    SqlSession.this.update(sql,obj);
                }else if(type== Select.class){
                    Class methodReturnType = method.getReturnType();
                    if (methodReturnType==List.class){
                        Type returnType = method.getGenericReturnType();
                        ParameterizedTypeImpl parameterizedType = (ParameterizedTypeImpl)returnType;
                        Type[] types = parameterizedType.getActualTypeArguments();
                        Type paraType = types[0];
                        Class clazz = (Class) paraType;
                        return SqlSession.this.selectList(sql,obj,clazz);
                    }else{
                        return SqlSession.this.selectOne(sql,obj,methodReturnType);
                    }
                }else{
                    System.out.println("无法处理的返回值类型");
                }


                return null;
            }
        });
    }
}
