package orm.configuartionReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigRead {

    private static Map<String,String> map = new HashMap<>();

    static{
        try {
            Properties properties = new Properties();
            //InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.properties");
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("configuration.properties");
            properties.load(inputStream);
            Enumeration enumeration = properties.propertyNames();
            while(enumeration.hasMoreElements()){

                //这个方法不能重复出现 遍历到最后一个元素时 出现NoSuchElementException异常
                //enumeration.nextElement()

                String str = (String)enumeration.nextElement();
                map.put(str,properties.getProperty(str));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("加载配置文件的的时候出错啦");
        }

    }

    public static String getString(String key){
        return map.get(key);
    }

    public static int getValue(String key){
        return Integer.parseInt(map.get(key));
    }
}
