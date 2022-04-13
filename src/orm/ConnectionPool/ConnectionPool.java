package orm.ConnectionPool;

import orm.configuartionReader.ConfigRead;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private List<MyConnection> pool = new ArrayList<>();
    private final int defaultPollSize = 10;
    {
            int poolSize ;
            if(ConfigRead.getString("poolSize")==null){
                poolSize = defaultPollSize;
            }else{
                poolSize = ConfigRead.getValue("poolSize");
            }

            for(int i = 0;i<poolSize;i++){
                pool.add(new MyConnection());
            }

    }

    private MyConnection getMC(){
        for(int i=0;i<pool.size();i++){
            MyConnection m = pool.get(i);
            if(!m.isUsed()){
                synchronized (m) {
                    if (!m.isUsed()){
                    return m;
                    }
                }
            }

        }
        return null;
    }

    public MyConnection getMyConnection(){
        try {
            MyConnection myConnection;
            for (int i = 0;i<150;i++) {
                myConnection = this.getMC();
                if (myConnection != null) {
                    return myConnection;
                }
                Thread.sleep(20);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
