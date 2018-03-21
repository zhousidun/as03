package abc.com.abcdemo.context;

import android.content.Context;
import android.util.Log;

import java.io.InputStream;
import java.util.Properties;



public class PropertiesManager {

    private final String TAG = "PropertiesManager";

    private static PropertiesManager instance;

    private Properties properties;

    public static PropertiesManager getInstance(){
        synchronized (PropertiesManager.class){
            if (instance == null) {
                instance = new PropertiesManager();
            }
        }
        return instance;
    }

    private PropertiesManager(){}

    public void initialize(Context context){
        Log.d(TAG, "properties初始化");
        properties = new Properties();
        try{
            properties.load(context.getAssets().open("zft.properties"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void initialize(){
        try {
            properties = new Properties();
            InputStream in = PropertiesManager.class.getResourceAsStream("/assets/zft.properties");
            properties.load(in);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getValue(String key){
        Log.d(TAG, "key=" + key);
        if (properties == null){
            initialize();
        }

        return properties.get(key).toString();
    }
}
