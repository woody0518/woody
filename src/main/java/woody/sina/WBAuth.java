package woody.sina;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class WBAuth {
	private Oauth2AccessToken mAccessToken;
	private static WBAuth mInstance = new WBAuth();
	private AuthInfo mAuthInfo;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;

	private Context mContext;
	private WeiboAuthListener authListener;

	private WBAuth() {
	}

	public static WBAuth getInstance() {
		return mInstance;
	}

	public void initWB(Activity activity) {
		this.mContext = activity;
		// 创建微博实例
		// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
		mAuthInfo = new AuthInfo(activity, Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(activity, mAuthInfo);
		mAccessToken = AccessTokenKeeper.readAccessToken(mContext);
	}

	public void authorize(WeiboAuthListener authListener) {
		this.authListener = authListener;
		mSsoHandler.authorize(new AuthListener());
	}
	

	public Oauth2AccessToken getmAccessToken() {
		return mAccessToken;
	}

	public void setmAccessToken(Oauth2AccessToken mAccessToken) {
		this.mAccessToken = mAccessToken;
	}


	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			setmAccessToken(Oauth2AccessToken.parseAccessToken(values));
			if (getmAccessToken().isSessionValid()) {
				// ��ʾ Token
				AccessTokenKeeper.writeAccessToken(mContext,
						getmAccessToken());
//				updateTokenView(false);
				authListener.onComplete(values);

			} 
		}

		@Override
		public void onCancel() {
		}

		@Override
		public void onWeiboException(WeiboException e) {
		}
	}


	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
	}

}
