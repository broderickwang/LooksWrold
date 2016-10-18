package marc.com.lookswrold.listnese;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.event.OnZoomListener;
import com.esri.core.geodatabase.GeodatabaseFeature;
import com.esri.core.geodatabase.GeodatabaseFeatureServiceTable;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polyline;
import com.esri.core.table.TableException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Broderick on 2016/10/12.
 */

public class MapTouchListner extends MapOnTouchListener  implements OnZoomListener{
	@Override
	public void preAction(float v, float v1, double v2) {

	}

	@Override
	public void postAction(float v, float v1, double v2) {

	}

	public  enum EDIT_GEO_TYPE{
		MPOINT,
		MPOLYLINE,
		MPOLYGON,
		MNULL
	};

	private final String TAG = "MapTouchListner";

	private EDIT_GEO_TYPE type = EDIT_GEO_TYPE.MNULL;

	private GeodatabaseFeatureServiceTable pointTable;

	private GeodatabaseFeatureServiceTable lineTable;

	private GeodatabaseFeatureServiceTable gonTable;

	private FeatureLayer pointlayer;

	private long fid = 0;

	private Point startPoint = null,endPoint = null;

	private MapView mapView;

	private Polyline polyline = new Polyline();


	public MapTouchListner(Context context, MapView view) {
		super(context, view);
		this.mapView = view;
	}

	@Override
	public boolean onSingleTap(MotionEvent point) {
		switch (type){
			case MPOINT:
				addPoint(mapView.toMapPoint(new Point(point.getX(),point.getY())));
				break;
			case MPOLYLINE:
				if(startPoint == null){
					startPoint = mapView.toMapPoint(point.getX(),point.getY());
					polyline.startPath(startPoint);
				}else{
					polyline.lineTo(mapView.toMapPoint(point.getX(),point.getY()));
				}
				break;
			case MPOLYGON:
				break;
			case MNULL:
				long[] selectedFeatures = pointlayer.getFeatureIDs(point.getX(), point.getY(), 5, 1000);
				// select the features
				pointlayer.selectFeatures(selectedFeatures, false);
				break;
		}

		return super.onSingleTap(point);
	}


	private void addPolyline(){

	}

	private void addPoint(Point p) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("name", p.toString());
		Date d = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = simpleDateFormat.format(d);
		attributes.put("time", time);


		try {
			GeodatabaseFeature feature = new GeodatabaseFeature(attributes, p, pointTable);
			fid = pointTable.addFeature(feature);

			String geoStr = pointTable.getFeature(fid).getGeometry().toString();

			Log.d(TAG, "addfeature: " + geoStr + " fid = " + fid);

		} catch (TableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onDoubleTap(MotionEvent point) {
		return super.onDoubleTap(point);
	}

	@Override
	public boolean onLongPressUp(MotionEvent point) {
		return super.onLongPressUp(point);
	}

	public void setGonTable(GeodatabaseFeatureServiceTable gonTable) {
		this.gonTable = gonTable;
	}

	public void setLineTable(GeodatabaseFeatureServiceTable lineTable) {
		this.lineTable = lineTable;
	}

	public void setPointTable(GeodatabaseFeatureServiceTable pointTable) {
		this.pointTable = pointTable;
	}

	public void setType(EDIT_GEO_TYPE type) {
		this.type = type;
	}

	public void setPointlayer(FeatureLayer pointlayer) {
		this.pointlayer = pointlayer;
	}
}
