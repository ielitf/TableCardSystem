package com.hskj.tablecardsystem.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hskj.tablecardsystem.R;
import com.hskj.tablecardsystem.control.CodeConstants;
import com.hskj.tablecardsystem.utils.LogUtil;
import com.hskj.tablecardsystem.utils.MqttService;
import com.hskj.tablecardsystem.utils.SDCardUtils;
import com.hskj.tablecardsystem.utils.SharedPreferencesManager;

public class SetActivity3 extends AppCompatActivity implements View.OnClickListener {
    private EditText ipET,roomNumET,tableNumET,nameEditET,textSizeET,accountET,passwordET;
    private Button confirmBtn,cancleBtn,setBtn;
    private String ip,roomNum,tableNum,name,textSize,account,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 去除通知栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set);
        this.setFinishOnTouchOutside(false);//取消点击外面隐藏activity
        ipET = findViewById(R.id.service_ip_edit_text);
        accountET  = findViewById(R.id.account_edit_text);
        passwordET  = findViewById(R.id.password_edit_text);
        roomNumET  = findViewById(R.id.roomNum_edit_text);
        tableNumET  = findViewById(R.id.tableNum_edit_text);
        nameEditET  = findViewById(R.id.name_edit_text);
        textSizeET  = findViewById(R.id.size_edit_text);

        account = SharedPreferencesManager.getAccount();
        password = SharedPreferencesManager.getPassword();
        ip = SharedPreferencesManager.getServiceIp();
        roomNum = SharedPreferencesManager.getRoomNum();
        tableNum = SharedPreferencesManager.getTableNum();
        name = SharedPreferencesManager.getPersonName();
        textSize = SharedPreferencesManager.getTextSize();

        ipET.setText(ip);
        accountET.setText(account);
        passwordET.setText(password);
        roomNumET.setText(roomNum);
        tableNumET.setText(tableNum);
        nameEditET.setText(name);
        textSizeET.setText(textSize);

        cancleBtn  = findViewById(R.id.cancel_btn);
        confirmBtn  = findViewById(R.id.confirm_btn);
        setBtn  = findViewById(R.id.setting_btn);
        cancleBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        setBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.confirm_btn:
                Intent intent = new Intent();
                if(TextUtils.isEmpty(ipET.getText())){
                    Toast.makeText(this,"请输入服务器IP",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(TextUtils.isEmpty(accountET.getText())){
                    Toast.makeText(this,"请输入服务器账号",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(TextUtils.isEmpty(passwordET.getText())){
                    Toast.makeText(this,"请输入服务器密码",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(TextUtils.isEmpty(roomNumET.getText())){
                    Toast.makeText(this,"请输入会议室编号",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(TextUtils.isEmpty(tableNumET.getText())){
                    Toast.makeText(this,"请输入桌牌号",Toast.LENGTH_SHORT).show();
                    break;
                }
                SharedPreferencesManager.setServiceIp(ipET.getText()+"");
                SharedPreferencesManager.setAccount(accountET.getText()+"");
                SharedPreferencesManager.setPassword(passwordET.getText()+"");

                intent.putExtra("roomNumber",roomNumET.getText()+"");
                SharedPreferencesManager.setRoomNum(roomNumET.getText()+"");

                intent.putExtra("tableNumber",tableNumET.getText()+"");
                SharedPreferencesManager.setTableNum(tableNumET.getText()+"");

//                MqttService.URL_QUERY = "tcp://" + ipET.getText() + ":1883";
//                MqttService.clientId = roomNumET.getText() + CodeConstants.CLIENT_ID+ tableNumET.getText();
//                MqttService.TOPIC_NAME = roomNumET.getText() + CodeConstants.ZP_NAME+ tableNumET.getText();
//                MqttService.TOPIC_SIGN = roomNumET.getText() + CodeConstants.ZP_SIGN + tableNumET.getText();

                if(TextUtils.isEmpty(textSizeET.getText())){
                    intent.putExtra("textSize",200+"");//
                }else{
                    intent.putExtra("textSize",textSizeET.getText()+"");
                    SharedPreferencesManager.setTextSize(textSizeET.getText()+"");
                }
                intent.putExtra("name",nameEditET.getText()+"");
                SharedPreferencesManager.setPersonName(nameEditET.getText()+"");
                setResult(1,intent);
                finish();
                break;
            case R.id.setting_btn:
                Intent intent2  = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }
}
