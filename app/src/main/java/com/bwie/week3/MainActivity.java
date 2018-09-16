package com.bwie.week3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.week3.adapter.SubAdapter;
import com.bwie.week3.bean.MainBean;
import com.bwie.week3.presenter.IPesenterImp;
import com.bwie.week3.view.IView;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements IView{

    private ImageView iv_head;
    private UMShareAPI umShareAPI;
    private IPesenterImp iPesenter;
    public static final String URL_STRING="https://restapi.amap.com/v3/place/around?key=d78f39012867929dc6ad174dd498f51f&location=116.473168,39.993015&keywords=%E7%BE%8E%E9%A3%9F&types=&radius=1000&offset=20&page=1&extensions=all";
    private RecyclerView re;
    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String string = (String) msg.obj;
                Log.i("xxx",string);
                //Toast.makeText(MainActivity.this, string, Toast.LENGTH_SHORT).show();
                Gson gson = new Gson();
                MainBean mainBean = gson.fromJson(string, MainBean.class);
                List<MainBean.Pois> pois = mainBean.getPois();
                Log.i("xxx",pois+"");
                SubAdapter adapter=new SubAdapter(pois,MainActivity.this);
                LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
                re.setAdapter(adapter);
                re.setLayoutManager(manager);

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        re = findViewById(R.id.re);
        iv_head = findViewById(R.id.iv_head);
        umShareAPI = UMShareAPI.get(this);
        iv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMAuthListener authListener = new UMAuthListener() {
                    /**
                     * @desc 授权开始的回调
                     * @param platform 平台名称
                     */
                    @Override
                    public void onStart(SHARE_MEDIA platform) {

                    }

                    /**
                     * @desc 授权成功的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     * @param data 用户资料返回
                     */
                    @Override
                    public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

                        Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
                        String iconurl = data.get("iconurl");
                        Glide.with(MainActivity.this).load(iconurl).into(iv_head);
                    }

                    /**
                     * @desc 授权失败的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     * @param t 错误原因
                     */
                    @Override
                    public void onError(SHARE_MEDIA platform, int action, Throwable t) {

                        Toast.makeText(MainActivity.this, "失败：" + t.getMessage(),                                     Toast.LENGTH_LONG).show();
                    }

                    /**
                     * @desc 授权取消的回调
                     * @param platform 平台名称
                     * @param action 行为序号，开发者用不上
                     */
                    @Override
                    public void onCancel(SHARE_MEDIA platform, int action) {
                        Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
                    }
                };
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.SINA.QQ, authListener);
            }
        });
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }

        iPesenter = new IPesenterImp(this);
        iPesenter.getData(URL_STRING);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showSuccess( String string) {
        Message message = new Message();
        message.what=0;
        message.obj=string;
        handler.sendMessage(message);
    }
}
