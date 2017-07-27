package com.creec.crungooyummy;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import Fragment.YummyListFragment;
import okhttp3.*;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class YummyListActivity extends AppCompatActivity {
    private OkHttpClient mOkHttpClient;
    private String rawData;
    private String url = "http://apis.juhe.cn/catering/query?key=401e7875f9251ec24fe080a202f774d2&lng=121.538123&lat=31.677132&radius=2000";
    private String url2 = "http://apis.juhe.cn/catering/query?key=401e7875f9251ec24fe080a202f774d2&lng=";
    private JSONArray jsonArray;
    private FrameLayout mContainer;
    private int mSelectedPos = -1;
    private Location mLocation = null;

    private static final int KSuccess = 0;
    // private static final int KFailure = 1;

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {     // 写法不好，但是好理解
        this.jsonArray = jsonArray;
    }

//    public int getmSelectedPos() {       写法不好，但是好理解
//        return mSelectedPos;
//    }
//
//    public void setmSelectedPos(int mSelectedPos) {
//        this.mSelectedPos = mSelectedPos;
//    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KSuccess:
                    YummyListFragment f = new YummyListFragment();
                    launchFragment(f);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm
                .isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent, 0); //此为设置完成后返回到获取界面
    }

    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yummy_container);
        mContainer =  (FrameLayout) findViewById(R.id.framelayout_container);   // 一定要放在getAsynHttp前面
        getLocation();  // 要加个线程 延时 获取到地理位置后 通知
        getAsynHttp();
    }

    private void getAsynHttp() {
        mOkHttpClient = new OkHttpClient(); // 初始化请求客户端
        String catUrl = url2;

        if (mLocation != null && mLocation.getLongitude() > 0) {
            catUrl += mLocation.getLongitude();
            catUrl += "&lat=";
            catUrl += mLocation.getLatitude();
            catUrl += "&radius=2000";
        } else {
            catUrl = url;
        }


        Request request = new Request.Builder().url(catUrl).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {  // 当响应可读时回调Callback接口，enqueue进入子线程
            @Override
            public void onFailure(Call call, IOException e) {   // 出现错误的情况
                Log.i("test","Request Failure" + e.toString());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {   // 成功访问的情况
                if (response.isSuccessful()) {
                    rawData = response.body().string();
                    JSONObject jsonObject = JSON.parseObject(rawData);   // 将data转换为json对象
                    jsonArray = jsonObject.getJSONArray("result");    // 获取result json对象

                    handler.obtainMessage(KSuccess, jsonObject).sendToTarget();
                    // Log.i("CrungooYummy", rawData);
//                  Toast.makeText(YummyListActivity.this, "Request Successful", Toast.LENGTH_SHORT).show();
                } else {
//                  Toast.makeText(YummyListActivity.this, "Request Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void launchFragment(android.support.v4.app.Fragment f) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayout_container, f)
                .commit();  //addToBackStack  JSONArray最好随着程序走到哪就传到哪 因为存在activity会比较占用开销
    }
}
