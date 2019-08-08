package com.hskj.tablecardsystem.control;

import android.app.Application;

import com.hskj.tablecardsystem.utils.SDCardUtils;
import com.hskj.tablecardsystem.utils.SharedPreferencesManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;

public class MyApplication extends Application {
	private static final String SHARED_PREFERENCE_NAME = "tableCardSystem_sp";
	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler.getInstance().init(this);
		SharedPreferencesManager.init(getApplicationContext(), SHARED_PREFERENCE_NAME);
		if(SharedPreferencesManager.getIsFirstUse()){
			SDCardUtils.writeTxt("192.168.10.2:1883",CodeConstants.IP_HOST);
			SharedPreferencesManager.setIsFirstUse(false);
		}
		//全局初始化
		OkGo.init(this);
		OkGo.getInstance().setConnectTimeout(3000)
				.setReadTimeOut(3000)
				.setWriteTimeOut(3000)
				.setCacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
				.setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
				.setRetryCount(3);
	}
}
