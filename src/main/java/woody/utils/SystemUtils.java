package woody.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

import woody.bean.PhoneInfo;

public final class SystemUtils {

	private static SystemUtils systemUtils = null;

	private SystemUtils() {
	}

	public static SystemUtils getInstance() {
		if (systemUtils == null) {
			systemUtils = new SystemUtils();
		}
		return systemUtils;
	}

	public Intent ReadImageFromSystemFile() {
		Intent ImageInSystemIntent = new Intent();
		ImageInSystemIntent.setAction(Intent.ACTION_GET_CONTENT);
		ImageInSystemIntent.setType("image/*");
		return ImageInSystemIntent;
	}

	public Intent cutImageBySystemCut(Uri uri) {
		Intent CutImageBySystemIntent = new Intent();
		CutImageBySystemIntent.setAction("com.android.camera.action.crop");
		CutImageBySystemIntent.setDataAndType(uri, "image/*");
		return CutImageBySystemIntent;
	}

	public boolean isSdCardEnable() {
		String sdcardState = Environment.getExternalStorageState();
		return Environment.MEDIA_MOUNTED.equals(sdcardState);
	}

	/*
	 * savePhotoFile: the photo where to save ViewdieoQuality:The name of the
	 * Intent-extra used to control the quality of a recorded video Value from 0
	 * to 100
	 */
	public Intent TakePhotoFromSystem(File savePhotoFile, int ViewdieoQuality) {
		Intent TakePhotoFromSystemIntent = new Intent();
		TakePhotoFromSystemIntent
				.setAction("android.media.action.IMAGE.CAPTURE");
		Uri uri = Uri.fromFile(savePhotoFile);
		TakePhotoFromSystemIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		TakePhotoFromSystemIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,
				ViewdieoQuality);
		return TakePhotoFromSystemIntent;
	}

	/** 获取手机联系人和联系电话 封装在PhoneInfo里面
	 * @param context
	 * @return ArrayList<PhoneInfo>
	 */
	ArrayList<PhoneInfo> mPhoneInfos = new ArrayList<PhoneInfo>();
	public ArrayList<PhoneInfo> ReadPhoneInfoFromSystem(Context context) {
		Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI,
				null, null, null, null);
		while (cursor.moveToNext()) {
//			电话
			String phoneNum = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
//			联系人
			String phoneName = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
			mPhoneInfos.add(new PhoneInfo(phoneNum,phoneName));
		}
		return mPhoneInfos;
	}
	
}
