package abc.com.abcdemo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import abc.com.abcdemo.R;
import abc.com.abcdemo.config.TransType;
import abc.com.abcdemo.context.PropertiesManager;
import abc.com.abcdemo.context.TradeContext;
import abc.com.abcdemo.databinding.ActivityOrderConfirmBinding;
import abc.com.abcdemo.model.OrderRequest;
import abc.com.abcdemo.model.OrderResponse;
import abc.com.abcdemo.network.ATMService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderConfirmActivity extends Activity implements View.OnClickListener{
    String TAG = "OrderConfirmActivity";
    private OrderRequest request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        request = (OrderRequest) getIntent().getSerializableExtra("request");
        ActivityOrderConfirmBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_order_confirm);
        binding.setClicker(this);
        binding.setCardNo(request.getCardNo());
        binding.setMoney(request.getMoney());
        binding.setClicker(this);

        switch (TradeContext.getInstance().getTransType()){
            case TransType.WITHDRAW:
                binding.setTitle("确认取款信息");
                binding.setAccNoTitle("取款帐号:  ");
                binding.setAmtTitle("取款金额:  ");
                binding.setBtnText("确认取款");
                request.setBusType(TransType.WITHDRAW);

                break;
            case TransType.PAYMENT:
                binding.setTitle("确认缴费信息");
                binding.setAccNoTitle("缴费帐号:  ");
                binding.setAmtTitle("缴费金额:  ");
                binding.setBtnText("确认缴费");
                request.setBusType(TransType.PAYMENT);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        submitOrder();
    }

    private void submitOrder() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();


        String baseUrl = PropertiesManager.getInstance().getValue("serverBaseUrl");
        Log.i(TAG,"baseUrl="+baseUrl);
        Log.i(TAG,"submitOrder");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ATMService atmService = retrofit.create(ATMService.class);
        Call<OrderResponse> call = atmService.submitOrder(request);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                dialog.dismiss();
                Log.i(TAG,"onResponse");
                OrderResponse orderResponse = response.body();
                Log.i(TAG,"orderResponse:"+orderResponse.toString());

                Intent intent = new Intent(OrderConfirmActivity.this, TransResultActivity.class);
                intent.putExtra("request",request);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                dialog.dismiss();
                Log.i(TAG,"onFailure "+t.getMessage());

            }
        });
    }
}
