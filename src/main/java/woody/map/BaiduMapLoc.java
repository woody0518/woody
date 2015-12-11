package woody.map;

import android.content.Context;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult.AddressComponent;

public class BaiduMapLoc {

	public String getmCity() {
		return mCity;
	}

	private LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private GeoCoder mSearch;
	private Context context;
	private String mCity;
		
	private final String LOC_LOADING = "定位中";
	private final String LOC_ERROR = "抱歉未能定位到你的城市,请检查网络链接和GPS";
	private TextView mCityLocate;

	public BaiduMapLoc() {
	}

	public BaiduMapLoc(Context context,final TextView mCityLocate) {
		this.context = context;
		this.mCityLocate = mCityLocate;
		// 定位客户端
		mLocClient = new LocationClient(context);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		// 打开gps
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		// 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();
//		TODO 打开会有问题
//		Log.e("mCity", mCity);
		// 初始化搜索模块，注册事件监听
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
			
			@Override
			public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
				if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
					mCity = LOC_ERROR;
					return;
				}
				AddressComponent addressDetail = result.getAddressDetail();
				mCity = addressDetail.city;
				if (mCityLocate!=null) {
					mCityLocate.setText(mCity);
				}
			}
			
			@Override
			public void onGetGeoCodeResult(GeoCodeResult result) {
				
			}
		});
	}

	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null) {
				mCity = LOC_ERROR;
				return;
			}
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			mCity = LOC_LOADING;
//			Log.e("location.getLatitude()", location.getLatitude()+"");
//			Log.e("location.getLongitude()", location.getLongitude()+"");
			mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ll));
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

}
