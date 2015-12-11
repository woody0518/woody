package woody.app;

/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;
import com.bmob.BmobConfiguration;
import com.bmob.BmobPro;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.bmob.v3.Bmob;

public class BaseApp extends Application {

	public static String APPID = "971cf1fff2ef479fc006845792040dc8";

	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
		initImageLoader(getApplicationContext());
		initConfig(getApplicationContext());
//		Bmob PID
		Bmob.initialize(getApplicationContext(), APPID);
//		baidu initApp Application context
		SDKInitializer.initialize(this);

		// JPushInterface.setDebugMode(true); // 鐠佸墽鐤嗛敓锟�閸氼垱妫╅敓锟�,閸欐垵绔烽弮鎯邦嚞閸忔娊妫撮弮銉ョ箶
		// JPushInterface.init(this); // 閸掓繂顫愰敓锟� JPush

	}

	/**
	 * 鍒濆鍖栨枃浠堕厤缃�	 * 
	 * @param context
	 */
	public static void initConfig(Context context) {
		BmobConfiguration config = new BmobConfiguration.Builder(context)
				.customExternalCacheDir("Smile").build();
		BmobPro.getInstance(context).initConfig(config);
	}

	// @Override
	// public void onTerminate() {
	// super.onTerminate();
	// ActiveAndroid.dispose();
	// }

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024)
				// 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}