package com.payhub.demo;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.payhub.pay.PHPayResult;
import com.payhub.pay.PHResult;
import com.payhub.pay.PayCallBack;
import com.payhub.pay.PayChannel;
import com.payhub.pay.PayHub;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, PayCallBack, RadioGroup.OnCheckedChangeListener {
    private Button btn;
    private RadioGroup rgChannel;
    private EditText edtAmount,edtMerchantID,edtUserID,edtWebHookUrl;
    PayHub.PayParam payParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        edtAmount = (EditText) findViewById(R.id.edt_amount);
        edtMerchantID = (EditText) findViewById(R.id.edt_merchant_id);
        edtUserID = (EditText) findViewById(R.id.edt_user_id);
        edtWebHookUrl = (EditText) findViewById(R.id.edt_webhook_url);
        rgChannel = (RadioGroup) findViewById(R.id.rg_channel);

        //初始化 app_id app_secret
        PayHub.getInstance(this).init("Pt0OWdb36WMuGhYq","19tZWvZ6JVN7awt2XASWA0VTyJvpK9qX");

        //配置参数
        payParam = new PayHub.PayParam();
        // 支付渠道
        payParam.pay_channel = PayChannel.UN_H5;
//        // 商户 ID
//        payParam.merchant_id ="";
//        // 用户ID
//        payParam.user_id =  "";
//        // 回调地址
//        payParam.webhook_url ="";
//        // 支付金额
//        payParam.amount = 0;

        btn.setOnClickListener(this);
        rgChannel.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                String amount = edtAmount.getText().toString().trim();
                if(amount !=null && amount.length() !=0) {
                    payParam.amount = Integer.parseInt(amount);
                }
                payParam.merchant_id = edtMerchantID.getText().toString().trim();
                payParam.user_id = edtUserID.getText().toString().trim();
                payParam.webhook_url = edtWebHookUrl.getText().toString().trim();

                //发起支付
                PayHub.getInstance(MainActivity.this).Pay(payParam, this);
                break;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){
            case R.id.rb_un_pay:
                payParam.pay_channel = PayChannel.UN_H5;
                break;

            case R.id.rb_ali_pay:
                payParam.pay_channel = PayChannel.ALI_APP;
                break;

            case R.id.rb_wx_pay:
                payParam.pay_channel = PayChannel.WX_APP;
                break;
        }
    }

    @Override
    public void done(PHResult phResult) {
        PHPayResult payResult = (PHPayResult) phResult;
        String result = payResult.getResult();
        if(result.equals(PHPayResult.RESULT_SUCCESS)){
            Toast.makeText(this,"交易成功",Toast.LENGTH_SHORT).show();
        }else if(result.equals(PHPayResult.RESULT_FAIL)){
            Toast.makeText(this,"交易失败  "+ payResult.getErrMsg(),Toast.LENGTH_SHORT).show();
        }
    }

}
