package com.hskj.tablecardsystem.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hskj.tablecardsystem.R;
import com.hskj.tablecardsystem.listener.NameCallBack;
import com.hskj.tablecardsystem.listener.SignCallBack;
import com.hskj.tablecardsystem.utils.LogUtil;
import com.hskj.tablecardsystem.utils.MqttService;
import com.hskj.tablecardsystem.utils.SharedPreferencesManager;

import java.util.List;

public class MainActivity extends Activity implements NameCallBack, SignCallBack, View.OnClickListener {
    public static final int MESSAGE_NAME = 1;
    public static final int MESSAGE_SIGN = 2;
    private TextView nameTV, stateTV;//人名，签到状态
    private ImageView set_img;//设置
    private String name, sign;
    private Intent intent;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_NAME:
                    nameTV.setText(name);
                    stateTV.setText("未签到");
                    SharedPreferencesManager.setPersonName(name);
                    break;
                case MESSAGE_SIGN:
                    if ("1".equals(sign)) {
                        stateTV.setText("签到成功");
                    } else {
                        stateTV.setText("签到失败");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除通知栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (!isServiceRunning(String.valueOf(MqttService.class))) {
            intent = new Intent(this, MqttService.class);
            startService(intent);

        } else {
            LogUtil.i("===服务正在运行", "return");
            return;
        }
        MqttService.setNameCallBack(this);
        MqttService.setSignCallBack(this);
        nameTV = findViewById(R.id.name);
        stateTV = findViewById(R.id.state);
        set_img = findViewById(R.id.setting_ibtn);
        set_img.setOnClickListener(this);

        nameTV.setText(SharedPreferencesManager.getPersonName());
        if (!TextUtils.isEmpty(SharedPreferencesManager.getTextSize())) {
            nameTV.setTextSize(Float.parseFloat(SharedPreferencesManager.getTextSize()));
        }
    }

    @Override
    public void setNameData(String topic, String strMessage) {
        name = strMessage;
        Message msg = new Message();
        msg.what = MESSAGE_NAME;
        handler.sendMessage(msg);
    }

    @Override
    public void setSignData(String topic, String strMessage) {
        sign = strMessage;
        Message msg = new Message();
        msg.what = MESSAGE_SIGN;
        handler.sendMessage(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            stopService(intent);
            startService(intent);
            nameTV.setText(data.getStringExtra("name"));
            nameTV.setTextSize(Float.parseFloat(data.getStringExtra("textSize")));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_ibtn:
                Intent intent = new Intent(this, SetActivity.class);
                startActivityForResult(intent, 1);
                break;
            default:
                break;
        }
    }

    /**
     * 判断服务是否运行
     */
    private boolean isServiceRunning(final String className) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);
    }
}
