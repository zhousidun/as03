package abc.com.abcdemo.network;

import android.util.Log;

import abc.com.abcdemo.context.PropertiesManager;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by LiYang on 2018/3/19.
 */

public class RetrofitFactory {
    static String TAG = "RetrofitFactory";
    public static Retrofit createRetrofit(){
        String baseUrl = PropertiesManager.getInstance().getValue("serverBaseUrl");
        Log.i(TAG,"baseUrl="+baseUrl);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    };
}
