package abc.com.abcdemo.network;

import java.util.Map;

import abc.com.abcdemo.model.ScanConfirmReq;
import abc.com.abcdemo.model.ScanConfirmRes;
import abc.com.abcdemo.model.ScanInformReq;
import abc.com.abcdemo.model.ScanInformRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by LiYang on 2018/3/19.
 */

public interface ScanService {

    @POST("QRScanInform")
    Call<ScanInformRes> informScan(@Body ScanInformReq req);

    @POST("QRScanConfirm")
    Call<ScanConfirmRes> confirmScan(@Body ScanConfirmReq req);
}
