package abc.com.abcdemo.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import abc.com.abcdemo.R;
import abc.com.abcdemo.context.TradeContext;
import abc.com.abcdemo.databinding.ActivityLoginBinding;


public class LoginActivity extends Activity implements View.OnClickListener {
    String TAG = "LoginActivity";
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setClicker(this);
    }

    @Override
    public void onClick(View v) {
        String username = binding.etUsername.getText().toString();
        String pwd = binding.etPwd.getText().toString();
        Log.i(TAG,"username = "+username+" pwd = "+pwd);
        //将登录名作为唯一标识符
        TradeContext.getInstance().setPhoneNo(username);
        Intent intent = new Intent(this, AccListActivity.class);
        startActivity(intent);
        finish();
    }
}
