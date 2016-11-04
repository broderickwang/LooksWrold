package marc.com.lookswrold;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;

public class GaodeAcitivity extends AppCompatActivity {

	MapView mMapView = null;
	AMap aMap = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gaode_acitivity);
		//获取地图控件引用
		mMapView = (MapView) findViewById(R.id.amap);
		//在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
		mMapView.onCreate(savedInstanceState);

		//初始化地图变量
		aMap = mMapView.getMap();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}
	@Override
	protected void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}
	@Override
	protected void onPause() {
		super.onPause();
		//在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
		mMapView.onSaveInstanceState(outState);
	}
}
