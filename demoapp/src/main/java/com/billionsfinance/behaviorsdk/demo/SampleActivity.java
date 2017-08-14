package com.billionsfinance.behaviorsdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.billionsfinance.behaviorsdk.BehaviorAgent;

/**
 *sample
 */

public class SampleActivity extends Activity implements View.OnClickListener {

    private Button init_device;
    private Button sync_device;
    private Button sync_config;
    private Button sync_upload;
    private Button async_upload;
    private TextView device_info;
    private TextView result_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init_device = (Button) findViewById(R.id.init_device);
        sync_device = (Button) findViewById(R.id.sync_device);
        sync_config = (Button) findViewById(R.id.sync_config);
        sync_upload = (Button) findViewById(R.id.sync_upload);
        async_upload = (Button) findViewById(R.id.async_upload);
        device_info = (TextView) findViewById(R.id.device_info);
        result_text = (TextView) findViewById(R.id.result_text);

        init_device.setOnClickListener(this);
        sync_device.setOnClickListener(this);
        sync_config.setOnClickListener(this);
        sync_upload.setOnClickListener(this);
        async_upload.setOnClickListener(this);
        async_upload.setTag("异步上传");

        //getDeviceInfo();
        //setCookie();

        getCellInfo();
    }

    /** 基站信息结构体 */
    public class SCell{
        public int MCC;
        public int MNC;
        public int LAC;
        public int CID;
    }

    /** 经纬度信息结构体 */
    public class SItude{
        public String latitude;
        public String longitude;
    }

    private SCell getCellInfo(){
        SCell cell = new SCell();

        /** 调用API获取基站信息 */
        TelephonyManager mTelNet = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        GsmCellLocation location = (GsmCellLocation) mTelNet.getCellLocation();
        if (location == null){
            //LogUtils.d("获取基站信息失败");
        }

        String operator = mTelNet.getNetworkOperator();
        int mcc = Integer.parseInt(operator.substring(0, 3));
        int mnc = Integer.parseInt(operator.substring(3));
        int cid = location.getCid();
        int lac = location.getLac();

        /** 将获得的数据放到结构体中 */
        cell.MCC = mcc;
        cell.MNC = mnc;
        cell.LAC = lac;
        cell.CID = cid;

        //LogUtils.d("mcc = " + mcc + " mnc = " + mnc + " lac = " + lac + " cid = " + cid);

        return cell;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 999) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //getLocation();
            } else {
                //LogUtils.d("定位权限被拒绝");
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.init_device:
                BehaviorAgent.initialize(getApplication(), "FQG", "15ac789c029548e7ab09462230496b4d", "应用宝");
                break;
            case R.id.sync_device:
                break;
            case R.id.sync_config:
                //getLocation();
                break;
            case R.id.sync_upload:
                //同步上传
                //BehaviorAgent.getInstance().uploadSyncData("https://www.sensorsdata.cn/manual/vtrack_intro.html", "{\"resultCode\":\"success\",\"resultData\":null,\"errorCode\":null,\"errorMessage\":null}");
                break;
            case R.id.async_upload:
                startActivity(new Intent(SampleActivity.this, TestActivity.class));
                break;
        }
    }

//    public void getLocationInfo() {
//        LocationUtils.register(this, 1000, 1, new LocationUtils.OnLocationChangeListener() {
//            @Override
//            public void getLastKnownLocation(Location location) {
//
//            }
//
//            @Override
//            public void onLocationChanged(Location location) {
//                //LogUtils.d("经度: " + location.getLongitude() + " 纬度: " + location.getLatitude());
//                //LocationUtils.unregister();
//
//            }
//
//            @Override
//            public void onStatusChanged(String provider, int status, Bundle extras) {
//                //LogUtils.d("onStatusChanged: provider = " + provider + " status = " + status);
//            }
//        });
//    }

//    private void setCookie() {
//        CookieManager cookieManager = CookieManager.getInstance();
//        cookieManager.setAcceptCookie(true);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            cookieManager.flush();
//        } else {
//            CookieSyncManager.getInstance().sync();
//        }
//        cookieManager.setCookie("userId", "270162589755");
//    }
//
//    private void getLocation() {
//        //获取地理位置
//        if (PermissionUtils.checkIsLocationOpen(this)) {
//            getLocationInfo();
//        } else {
//            LogUtils.d("没有权限获取位置信息");
//
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                    || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//                ActivityCompat.requestPermissions(this, new String[]{
//                        Manifest.permission.ACCESS_FINE_LOCATION,
//                        Manifest.permission.ACCESS_COARSE_LOCATION,}, 999);
//            }
//        }
//    }
//
//    private void getDeviceInfo() {
//        device_info.setText(
//                "设备信息" + "\n" +
//                        "设备ID:" + InfoUtil.getDeviceId(this) + "\n\n" +
//                        "AndroidID:" + Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID) + "\n\n" +
//                        "认证TOKEN:" + BehaviorAgent.getInstance().getToken() + "\n\n" +
//                        "设备名称:" + InfoUtil.getPhoneModel() + "\n\n" +
//                        "设备品牌:" + InfoUtil.getPhoneBrand() + "\n\n" +
//                        "设备root:" + InfoUtil.isRoot() + "\n\n" +
//                        //"运营商:" + InfoUtil.getProvidersName(this) + "\n\n" +
//                        "蓝牙地址:" + InfoUtil.getBluetooth() + "\n\n" +
//                        "系统版本:" + InfoUtil.getSystemVersion() + "\n\n" +
//                        "IP地址:" + InfoUtil.getPhoneIp() + "\n\n" +
//                        "浏览器:" + InfoUtil.getBrowser(this) + "\n\n" +
//                        //"手机号:" + InfoUtil.getPhoneNumber(this) + "\n\n" +
//                        "已安装APP:" + InfoUtil.getAppList(this) + "\n\n" +
//                        "后台服务:" + InfoUtil.getRunningServicesInfo(this) + "\n\n" +
//                        //"通讯录:" + InfoUtil.getContactList(this) + "\n\n" +
//                        //"短信:"+ InfoUtil.getMsgInfo(this) + "\n\n" +
//                        //"通话记录:"+ InfoUtil.getCallInfo(this).toString() + "\n\n" +
//                        //"日历:" + InfoUtil.getCalendar(this) + "\n\n" +
//                        "wifi:" + InfoUtil.getWifiName(this) + "\n\n" +
//                        "mac:" + InfoUtil.getWifiMac(this) + "\n\n" +
//                        "经度:" + (BehaviorAgent.getInstance().getLocation() == null ? "无法获取地理位置" : BehaviorAgent.getInstance().getLocation().getLongitude()) + "\n\n" +
//                        "纬度:" + (BehaviorAgent.getInstance().getLocation() == null ? "无法获取地理位置" : BehaviorAgent.getInstance().getLocation().getLatitude()));
//
//
//    }

//    public void crash(View v) {
//        String s = null;
//        Log.i("crash", s);
//    }
//
//    public void logUpload(View v) {
//        boolean isWifiOnly = false;//是否在wifi下上传文件
//        LogCollector.upload(isWifiOnly);//上传文件
//    }
}
