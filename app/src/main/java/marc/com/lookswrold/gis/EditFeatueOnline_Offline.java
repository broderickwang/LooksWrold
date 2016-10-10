package marc.com.lookswrold.gis;

import android.app.Activity;
import android.util.Log;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.core.geodatabase.Geodatabase;
import com.esri.core.geodatabase.GeodatabaseEditError;
import com.esri.core.geodatabase.GeodatabaseFeature;
import com.esri.core.geodatabase.GeodatabaseFeatureServiceTable;
import com.esri.core.geodatabase.GeodatabaseFeatureTable;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.CallbackListener;
import com.esri.core.table.TableException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Broderick on 2016/10/10.
 */

public class EditFeatueOnline_Offline extends Activity {

	public static final String TAG = "tag";
	MapView mapView = new MapView(this);

	public GeodatabaseFeatureServiceTable featureServiceTable;

	public GeodatabaseFeatureTable featureTable;

	public FeatureLayer featureLayer;

	/**
	 * load offline featurelayer by geodatabase
	 */
	private void loadlayer_offline(){
		Geodatabase data = null;
		try {
			data = new Geodatabase("path");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		featureTable = data.getGeodatabaseFeatureTableByLayerId(0);
		featureLayer = new FeatureLayer(featureTable);
		mapView.addLayer(featureLayer);
	}

	/**
	 * load online featurelayer by geoservices
	 */
	private void loadlayer_online(){
		featureServiceTable = new GeodatabaseFeatureServiceTable("test",0);

		featureServiceTable.setSpatialReference(SpatialReference.create(102100));

		featureServiceTable.initialize(
				new CallbackListener<GeodatabaseFeatureServiceTable.Status>() {
					@Override
					public void onCallback(GeodatabaseFeatureServiceTable.Status status) {
						if(status == GeodatabaseFeatureServiceTable.Status.INITIALIZED){
							featureLayer = new FeatureLayer(featureServiceTable);
						}
					}

					@Override
					public void onError(Throwable throwable) {

					}
				}
		);

		mapView.setOnSingleTapListener(new OnSingleTapListener() {
			@Override
			public void onSingleTap(float x, float y) {
				//获得xy坐标点featureLayer的前1000个要素（误差为5个像素）
				long[] ids = featureLayer.getFeatureIDs(x,y,5,1000);
				//选择要素
				featureLayer.selectFeatures(ids,false);
			}
		});
	}

	/**
	 * add feature to featurelayer
	 * @param point  the created feature (Point,Polyline,Polygon)
	 */
	private void addfeature(Point point){
		Map<String,Object> attributes = new HashMap<>();
		attributes.put("TYPDAMAGE", "Minor");
		attributes.put("PRIMCAUSE" , "Earthquake");
		attributes.put("TYPDAMAGE_INT", new Integer(3));

		try {
			GeodatabaseFeature feature = new GeodatabaseFeature(attributes,point,featureServiceTable);
			long fid = featureServiceTable.addFeature(feature);

			String geoStr = featureServiceTable.getFeature(fid).getGeometry().toString();

			Log.d(TAG, "addfeature: "+geoStr);
		} catch (TableException e) {
			e.printStackTrace();
		}
	}

	private void addfeatureattach(long fid){
		try {
			featureServiceTable.addAttachment(fid, new File("ssd"), "abc", "def", new CallbackListener<Long>() {
				@Override
				public void onCallback(Long aLong) {

				}

				@Override
				public void onError(Throwable throwable) {

				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	/**
	 * update feature and attachment
	 * @param fid
	 * @param point
	 */
	private void updatefeature(long fid,Point point){
		try {
			featureServiceTable.updateFeature(fid,point);
			featureServiceTable.updateAttachment(fid, 3, new File("Tds"), "test", "a", new CallbackListener<Void>() {
				@Override
				public void onCallback(Void aVoid) {

				}

				@Override
				public void onError(Throwable throwable) {

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * delete feature
	 * @param fid
	 */
	private void delfeature(long fid){
		try {
			featureServiceTable.deleteFeature(fid);
			featureServiceTable.deleteAttachment(fid, 3, new CallbackListener<Void>() {
				@Override
				public void onCallback(Void aVoid) {

				}

				@Override
				public void onError(Throwable throwable) {

				}
			});
		} catch (TableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * delete features
	 */
	private void delfeatures(){
		try {
			featureServiceTable.deleteFeatures(featureLayer.getSelectionIDs());
		} catch (TableException e) {
			e.printStackTrace();
		}
	}

	/**
	 * commite the edit options by online (feature edit and attachments edit)
	 */
	private void commitEdit_ONLINE(){
		featureServiceTable.getUpdatedFeatures();
		featureServiceTable.applyEdits(new CallbackListener<List<GeodatabaseEditError>>() {
			@Override
			public void onCallback(List<GeodatabaseEditError> geodatabaseEditErrors) {
				for(GeodatabaseEditError error : geodatabaseEditErrors){
					Log.d(TAG, "onCallback: "+error.toString());
				}
			}

			@Override
			public void onError(Throwable throwable) {
				Log.e(TAG, "onError: ", throwable);
			}
		});

		featureServiceTable.applyAttachmentEdits(new CallbackListener<List<GeodatabaseEditError>>() {
			@Override
			public void onCallback(List<GeodatabaseEditError> geodatabaseEditErrors) {
				for(GeodatabaseEditError error : geodatabaseEditErrors){
					Log.d(TAG, "onCallback: "+error.toString());
				}
			}

			@Override
			public void onError(Throwable throwable) {
				Log.e(TAG, "onError: ", throwable);
			}
		});
	}

}
