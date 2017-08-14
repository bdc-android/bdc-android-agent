# Android用户行为数据SDK集成文档

## 1.功能概述

Android用户行为数据SDK主要功能是监听点击，监听输入框，监听滑动，上传设备信息，上传崩溃日志

## 2.版本说明

版本号：v1.0.1
发布日期：2017-07-05

sit下载地址：[http://sit-bdc.bqjr.cn/sdk/android/Behaviorsdk.zip](http://sit-bdc.bqjr.cn/sdk/android/Behaviorsdk.zip)

uat下载地址：[http://uat-bdc.bqjr.cn/sdk/android/Behaviorsdk.zip](http://uat-bdc.bqjr.cn/sdk/android/Behaviorsdk.zip)

## 3.运行环境

本SDK支持Android 4.0及以上版本的Android系统

手机需要有网络支持（包括3G、4G或Wifi网络等）

## 4.快速集成

参考SDK demo 工程，两种方式集成

1.将behaviorsdk library工程复制到主工程目录下, 在Gradle依赖中添加

`compile project(':behaviorsdk')`

2.将behaviorsdk.aar复制到主工程libs目录下，在Gradle中添加

`compile(name: 'behaviorsdk', ext: 'aar')`

```
repositories {
    flatDir {
        dirs 'libs'
    }
}
```

调试设置

`BehaviorAgent.setDebugMode(true);`

初始化SDK 在Application的onCreat()中调用方法

`BehaviorAgent.initialize(this, "channelCode", "channelSecret", "appChannel");`

参数替换为app的channelCode, channelSecret, appChannel为app安装渠道

## 5.注意事项
### 5.1运行时权限申请

若主工程的targetSdkVersion为23及以上，请在代码中遵循Android 6.0的运行时权限机制申请权限，需要申请的权限有

READ_EXTERNAL_STORAGE

WRITE_EXTERNAL_STORAGE

READ_PHONE_STATE

READ_CALENDAR

ACCESS_FINE_LOCATION

ACCESS_COARSE_LOCATION

READ_CONTACTS

READ_CALL_LOG

READ_SMS

### 5.2.同步监听网络请求

需要使用SDK的HttpClient进行Post Get网络请求，不使用不监听网络请求

### 5.3.页面控件的监听

页面：记录了所有Activity的开始时间和结束时间(onResume/onPause)

Activity页面取了类名，页面名取值activity.getTitle()，需要在Manifest给每个Activity设置label，如果不设置页面名取值都是application的label

控件：同一个app的安卓端跟iOS端，同一个需求控件需赋一样的值，如下示例:需要监听"身份证号码输入框"，需要给输入框设置tag

`view.setTag("身份证号码");` 

SDK自动监听了Activity里面所有的输入框, 所有可点击的控件以及滑动事件

Dialog和PopupWindow的控件暂时无法监听

点击事件包括

onClick

onLongClick

ListView和GridView的onItemClick，onItemLongClick，onItemSelected

滑动监听仅支持Android 6.0以上，View.OnScrollChangeListener require API Level >= 23

### 5.4.设置Cookie

用户登录后需要将userId传给SDK，调用方法

`BehaviorAgent.getInstance().setUserId("userId");`

在WebView中需要将用户userId和设备号deviceId写入cookie，JS所需要的信息会从cookie中读取

```
CookieManager cookieManager = CookieManager.getInstance();
cookieManager.setAcceptCookie(true);  
if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
    cookieManager.flush();
}else{
    CookieSyncManager.getInstance().sync();
}
cookieManager.setCookie("url", "userId = userId");
cookieManager.setCookie("url", "deviceId = deviceId");
```


###5.5.崩溃日志

SDK通过 Thread.UncaughtExceptionHandler 捕获程序崩溃日志，保存至SD卡Android/data/包名/log目录，并在程序下次启动时发送到服务器，如果发送成功会自动删除该文件。 如不需要错误统计功能，可通过此方法关闭(该方法需要在初始化之前调用，初始化之后调用无效)

`BehaviorAgent.setUncaughtExceptions(false);`

### 5.6.代码混淆

如果应用使用了混淆，请添加

`-keep class com.billionsfinance.behaviorsdk.** {*;}`