package abc.com.abcdemo.network;

import abc.com.abcdemo.model.OrderRequest;
import abc.com.abcdemo.model.OrderResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by LiYang on 2018/3/15.
 */

public interface ATMService {

    @POST("Order")
    Call<OrderResponse> submitOrder(@Body OrderRequest request);
}
