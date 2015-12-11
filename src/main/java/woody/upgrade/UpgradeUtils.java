package woody.upgrade;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.woody.lib.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import github.volley.HTTPUtils;
import github.volley.VolleyListener;
import woody.utils.GsonUtils;
import woody.utils.VersionUtils;

public class UpgradeUtils {
	public static final String APK_UPGRADE = Environment
			.getExternalStorageDirectory() + "/zhihu/upgrade/upgrade.apk";
	private static Context mContext;
	private static NotificationManager mNotifiMgr;
	private static Notification mNotifi;
	private static RemoteViews mNotifiviews;
	private static Upgrade upgrade;

	/**
	 * 浣跨敤姝ゆ柟娉曪紝json鏍煎紡鍙傝�assets/upgrade.txt鏂囦欢鏍煎紡
	 * @param context
	 * @param url
	 */
	
	public static void checkUpgrade(Context context, String url) {
		mContext = context;
		HTTPUtils.get(context, url, new VolleyListener() {
			public void onResponse(String json) {
				checkUpgrade(json);
			}

			@Override
			public void onErrorResponse(VolleyError arg0) {}
		});
	}

	private static void checkUpgrade(String json) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.dialog_update_rela, null);
		TextView updateTitle = (TextView) view.findViewById(R.id.update_title);
		TextView updateContent = (TextView) view.findViewById(R.id.update_content_tv);
		Button positiveBt = (Button) view.findViewById(R.id.positive_btn);
		Button nativeBt = (Button) view.findViewById(R.id.native_btn);
		upgrade = GsonUtils.parseJSON(json, Upgrade.class);
		int currVersion = VersionUtils.getCurrVersion(mContext);
		if (upgrade.getVersion() > currVersion) {
			final Dialog dialog = new Dialog(mContext);
			dialog.setTitle("Find New Version");
			updateContent.setText(upgrade.getUpdatecontent());
			updateTitle.setText(upgrade.getUpdatetitle());
			positiveBt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					upgrade(upgrade);
					dialog.dismiss();
				}
			});
			
			nativeBt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
			dialog.setContentView(view);
			dialog.show();
		}
	}

	protected static void upgrade(Upgrade upgrade) {
		new UpgradeTask().execute(upgrade.getApkurl());
	}

	static class UpgradeTask extends AsyncTask<String, Integer, Void> {
		@Override
		protected void onPreExecute() {
			sendNotify();
		}

		@Override
		protected Void doInBackground(String... params) {
			String apkUrl = params[0];
			InputStream is = null;
			FileOutputStream fos = null;
			try {
				URL url = new URL(apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(25000);
				conn.setReadTimeout(25000);
				if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
					return null;
				}

				is = conn.getInputStream();
				File apkFile = new File(APK_UPGRADE);
				if (!apkFile.getParentFile().exists()) {
					apkFile.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(apkFile);
				byte[] buffer = new byte[1024];
				int len = 0;
				int loadedLen = 0;
				int updateSize = upgrade.getFilelen() / 13;
				int num = 1;
				while (-1 != (len = is.read(buffer))) {
					loadedLen += len;
					fos.write(buffer, 0, len);
					if (loadedLen > updateSize * num) {
						Log.e("in", "in");
						num++;
						publishProgress(loadedLen);
					}
				}
				fos.flush();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SocketTimeoutException e) {
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// 鏇存柊閫氱煡
			updateNotify(values[0]);
		}

		@Override
		protected void onPostExecute(Void result) {
			Toast.makeText(mContext, "Download misson complete,please check up,Tks", Toast.LENGTH_LONG).show();
			finishNotify();
		}
	}

	private static void sendNotify() {
		Intent intent = new Intent();
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				intent, 0);
		mNotifiviews = new RemoteViews(mContext.getPackageName(), R.layout.custom_notify);
		mNotifiviews.setViewVisibility(R.id.tv_subtitle, View.VISIBLE);
		mNotifiviews.setViewVisibility(R.id.progressBar1, View.VISIBLE);

		mNotifi = new NotificationCompat.Builder(mContext)
				.setContent(mNotifiviews)
				.setAutoCancel(true)
				// 鍗曞嚮鍚庤嚜鍔ㄥ垹闄�
				// .setOngoing(true)// 鏃犳硶鍒犻櫎鐨勯�鐭�
				// 瀹氬埗閫氱煡甯冨眬
				.setSmallIcon(R.drawable.ic_launcher).setTicker("ticker")
				.setWhen(System.currentTimeMillis()).setSound(Uri.parse(""))
				.setVibrate(new long[] { 0, 100, 300, 400 })
				.setContentIntent(contentIntent).build();
		mNotifiMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotifiMgr.notify(12345, mNotifi);
	}

	private static void updateNotify(int loadedLen) {
		int progress = loadedLen * 100 / upgrade.getFilelen();
		mNotifiviews.setTextViewText(R.id.tv_subtitle, progress + "%");
		mNotifiviews.setProgressBar(R.id.progressBar1, upgrade.getFilelen(),
				loadedLen, false);
		// mNotifiviews.setViewVisibility(R.id.tv_title, View.INVISIBLE);
		mNotifiMgr.notify(12345, mNotifi);
	}

	private static void finishNotify() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(APK_UPGRADE)),
				"application/vnd.android.package-archive");
		PendingIntent contentIntent = PendingIntent.getActivity(mContext, 0,
				intent, 0);
		mNotifi.contentIntent = contentIntent;
		mNotifiviews.setTextViewText(R.id.tv_title, "涓嬭浇瀹屾垚锛岃鐐瑰嚮瀹屾垚鍗囩骇");
		mNotifiviews.setViewVisibility(R.id.tv_subtitle, View.INVISIBLE);
		mNotifiviews.setViewVisibility(R.id.progressBar1, View.INVISIBLE);
		mNotifiMgr.notify(12345, mNotifi);
	}
}
