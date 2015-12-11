package woody.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class InstallUtils {
	public static void install(Context context, String apkPath){
		Intent intent = new Intent();
		Uri data = Uri.fromFile(new File(apkPath));
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(data, type);
		context.startActivity(intent);
	}
}
