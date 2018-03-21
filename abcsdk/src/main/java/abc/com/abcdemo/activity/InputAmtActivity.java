package abc.com.abcdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import abc.com.abcdemo.R;
import abc.com.abcdemo.config.TransType;
import abc.com.abcdemo.context.TradeContext;
import abc.com.abcdemo.databinding.ActivityInputAmtBinding;
import abc.com.abcdemo.model.OrderRequest;

public class InputAmtActivity extends Activity implements View.OnClickListener {
    private String accNo;
    private ActivityInputAmtBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        accNo = getIntent().getStringExtra("accNo");

        binding = DataBindingUtil.setContentView(this, R.layout.activity_input_amt);
        binding.setAccNo(accNo);
        binding.setClicker(this);

        switch (TradeContext.getInstance().getTransType()){
            case TransType.WITHDRAW:
                binding.setTitle("输入取款信息");
                binding.setAccNoTitle("取款帐号:  ");
                binding.setAmtTitle("取款金额:  ");
                break;
            case TransType.PAYMENT:
                binding.setTitle("输入缴费信息");
                binding.setAccNoTitle("缴费帐号:  ");
                binding.setAmtTitle("缴费金额:  ");
                break;
        }
    }


    @Override
    public void onClick(View v) {
        String money = binding.etMoney.getText().toString();
        if(money==null){
            Toast.makeText(this,"请输入金额",Toast.LENGTH_LONG).show();
            return;
        }

        money = money.trim();

        OrderRequest request = new OrderRequest();
        request.setCardNo(accNo);
        request.setOpenId(TradeContext.getInstance().getPhoneNo());
        request.setMoney(money);

        Intent intent = new Intent(this, OrderConfirmActivity.class);
        intent.putExtra("request",request);
        startActivity(intent);
        finish();
    }
}
