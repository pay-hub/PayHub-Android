# PayHub-Android

## 接入

PayHub-Android SDK 提供两种接入方式：aar接入（推荐）、jar接入

### AAR接入

1. 拷贝 `payhubSDK.aar` 文件到项目 `lib` 目录
2. 配置项目 `build.gradle` 文件
	* 在 `android{}` 标签内添加如下代码
	
		```
		//aar 引入方式
    	repositories {
        	flatDir {
            	dirs 'libs'
        	}
    	}
		```
	
	* 在 `dependencies{}` 标签内添加如下代码
	
		```
		  // aar 引入方式
    	compile(name:'payhubSDK',ext:'aar')
		```
	

### JAR 接入

1. 拷贝 `PayHubSDK.jar` 文件到项目 `lib` 目录
2. 配置项目 `build.gradle` 文件
	* 在 `dependencies{}` 标签内添加如下代码
	
		```
		// jar 引入方式
    	compile fileTree(dir: 'libs', include: ['*.jar'])
		```
3. `AndroidManifest.xml`文件注册相关Activity
	
	```
	 <activity android:name="com.payhub.pay.UNPayActivity"
            android:screenOrientation="portrait">

    </activity>
	 <activity android:name="com.payhub.pay.PayHubActivity"
            android:screenOrientation="portrait">

    </activity>
	```

## 权限

```
<uses-permission android:name="android.permission.INTERNET"/>
```



## 支付

### 初始化

在需要调用的 `Activity `中 调用初始化代码

```
 //初始化 app_id app_secret
 PayHub.getInstance(this).init(app_id, app_secret);
```

### 参数配置

```
 //配置参数
PayHub.PayParam payParam = new PayHub.PayParam();
// 支付渠道
payParam.pay_channel = PayChannel.UN_H5;
// 商户 ID
payParam.merchant_id ="";
// 用户ID
payParam.user_id =  "";
// 回调地址
payParam.webhook_url ="";
// 支付金额
payParam.amount = 0;
```

### 发起支付

```
//发起支付
PayHub.getInstance(MainActivity.this).Pay(payParam, this);
```