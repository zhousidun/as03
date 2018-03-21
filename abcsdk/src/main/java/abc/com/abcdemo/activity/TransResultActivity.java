package abc.com.abcdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import abc.com.abcdemo.R;
import abc.com.abcdemo.config.TransType;
import abc.com.abcdemo.context.TradeContext;
import abc.com.abcdemo.databinding.ActivityTransResultBinding;
import abc.com.abcdemo.model.OrderRequest;

public class TransResultActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OrderRequest request = (OrderRequest) getIntent().getSerializableExtra("request");

        ActivityTransResultBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_trans_result);
        binding.setClicker(this);
        binding.setCardNo(request.getCardNo());
        binding.setMoney(request.getMoney());

        switch (TradeContext.getInstance().getTransType()){
            case TransType.WITHDRAW:
                binding.setTitle("取款申请提交成功");
                binding.setAccNoTitle("取款帐号:  ");
                binding.setAmtTitle("取款金额:  ");
                break;
            case TransType.PAYMENT:
                binding.setTitle("缴费申请提交成功");
                binding.setAccNoTitle("缴费帐号:  ");
                binding.setAmtTitle("缴费金额:  ");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
