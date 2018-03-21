package abc.com.abcdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.HashMap;
import java.util.Map;

import abc.com.abcdemo.R;
import abc.com.abcdemo.config.TransType;
import abc.com.abcdemo.context.PropertiesManager;
import abc.com.abcdemo.context.TradeContext;
import abc.com.abcdemo.databinding.ActivityIndexBinding;
import abc.com.abcdemo.model.ScanConfirmReq;
import abc.com.abcdemo.model.ScanConfirmRes;
import abc.com.abcdemo.model.ScanInformReq;
import abc.com.abcdemo.model.ScanInformRes;
import abc.com.abcdemo.model.ScanResult;
import abc.com.abcdemo.network.RetrofitFactory;
import abc.com.abcdemo.network.ScanService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndexActivity extends Activity implements View.OnClickListener {
    String TAG = "IndexActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityIndexBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_index);
        binding.setClicker(this);

        TradeContext.init();
        TradeContext.getInstance().setPhoneNo("18899887654");

        PropertiesManager.getInstance().initialize(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                TradeContext.getInstance().setTransType(TransType.WITHDRAW);
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_payment:
                TradeContext.getInstance().setTransType(TransType.PAYMENT);
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btn_scan:
                scanQRCode();
                break;
        }
    }

    private void scanQRCode() {
        Intent openCameraIntent = new Intent(this, CaptureActivity.class);
        startActivityForResult(openCameraIntent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Log.i(TAG, "scanResult = " + scanResult);
            final ScanResult scan = new Gson().fromJson(scanResult, ScanResult.class);
            String uuid = scan.getUUID();
            Log.i(TAG, "uuid = " + uuid);

            TradeContext.getInstance().setUuid(uuid);

            ScanInformReq req = new ScanInformReq();
            req.setUuid(uuid);

            Retrofit retrofit = RetrofitFactory.createRetrofit();

            ScanService scanService = retrofit.create(ScanService.class);
            Call<ScanInformRes> call = scanService.informScan(req);
            call.enqueue(new Callback<ScanInformRes>() {
                @Override
                public void onResponse(Call<ScanInformRes> call, Response<ScanInformRes> response) {
                    Log.i(TAG, "onResponse");

                    ScanInformRes res = response.body();
                    String retCode = res.getRetCode();
                    String retMsg = res.getRetMsg();
                    Log.i(TAG, "retCode:" + retCode + " informScan res:" + retMsg);
                    if ("0000".equals(retCode)) {
                        String authInfo = res.getAuthInfo();
                        Log.i(TAG, "authInfo = " + authInfo);
                        showConfirmAlert(scan.getBusType());
                    }
                }

                @Override
                public void onFailure(Call<ScanInformRes> call, Throwable t) {
                    Log.i(TAG, "onFailure");
                }
            });

        }

    }

    private void showConfirmAlert(String transType) {

        String authInfo = "";
        switch (transType) {
            case TransType.WITHDRAW:
                authInfo = "您正在授权终端机具的无卡预约取现业务访问您的信息";
                break;
            case TransType.PAYMENT:
                authInfo = "您正在授权终端机具的电卡缴费业务访问您的信息";
                break;
        }
        new AlertDialog.Builder(this)
                .setTitle("信息确认")
                .setMessage(authInfo)
                .setPositiveButton("授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //            showSuccessAlert();
                        confirmAuth();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void confirmAuth() {

        ScanConfirmReq req = new ScanConfirmReq();
        String phoneNo = TradeContext.getInstance().getPhoneNo();
        String uuid = TradeContext.getInstance().getUuid();
        Log.i(TAG, "confirmAuth phoneNo:" + phoneNo + " uuid:" + uuid);
        req.setOpenId(phoneNo);
        req.setUuid(uuid);

        Retrofit retrofit = RetrofitFactory.createRetrofit();
        ScanService scanService = retrofit.create(ScanService.class);
        Call<ScanConfirmRes> call = scanService.confirmScan(req);
        call.enqueue(new Callback<ScanConfirmRes>() {
            @Override
            public void onResponse(Call<ScanConfirmRes> call, Response<ScanConfirmRes> response) {
                ScanConfirmRes res = response.body();
                Log.i(TAG, "retCode:" + res.getRetCode() + " retMst:" + res.getRetMsg());
                showSuccessAlert();
            }

            @Override
            public void onFailure(Call<ScanConfirmRes> call, Throwable t) {

            }
        });
    }

    private void showSuccessAlert() {
        new AlertDialog.Builder(this)
                .setTitle("授权成功")
                .setPositiveButton("确定", null)
                .setNegativeButton("取消", null)
                .show();
    }
}
