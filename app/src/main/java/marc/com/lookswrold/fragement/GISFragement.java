package marc.com.lookswrold.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.esri.android.map.FeatureLayer;
import com.esri.android.map.MapOptions;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.core.geodatabase.GeodatabaseEditError;
import com.esri.core.geodatabase.GeodatabaseFeatureServiceTable;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.io.UserCredentials;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.Feature;
import com.esri.core.portal.LicenseInfo;
import com.esri.core.portal.Portal;
import com.esri.core.portal.PortalInfo;
import com.esri.core.runtime.LicenseLevel;
import com.esri.core.runtime.LicenseResult;
import com.esri.core.table.TableException;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import marc.com.lookswrold.R;
import marc.com.lookswrold.listnese.MapTouchListner;

/**
 * Created by Broderick on 2016/10/11.
 * https://services7.arcgis.com/3akh8Z2IgZoIusnm/arcgis/rest/services/point1/FeatureServer
 */

public class GISFragement extends Fragment {
	public final String TAG = "GISFragement";

	@Bind(R.id.gis_map)
	MapView mapView;


	ArcGISFeatureLayer featureLayer;

	final String servicepointURL = "https://services7.arcgis.com/3akh8Z2IgZoIusnm/arcgis/rest/services/point1/FeatureServer";
	//"https://services7.arcgis.com/3akh8Z2IgZoIusnm/arcgis/rest/services/polyline1/FeatureServer";
	//"https://services7.arcgis.com/3akh8Z2IgZoIusnm/arcgis/rest/services/point1/FeatureServer";
	//"https://services7.arcgis.com/3akh8Z2IgZoIusnm/arcgis/rest/services/pointwork/FeatureServer"
	final String servicelineURL = "https://services7.arcgis.com/3akh8Z2IgZoIusnm/arcgis/rest/services/polyline1/FeatureServer";

	final String worldstreetURL = "http://server.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";

	final MapOptions mStreetsBasemap = new MapOptions(MapOptions.MapType.STREETS);

	GeodatabaseFeatureServiceTable pointTable , lineTable,gonTable,worldTable;

	long fid = 0;

	MapTouchListner mapTouchListner;

	FeatureLayer pointlayer,linelayer,gonlayer,worldlayer;
	@Bind(R.id.upload_newdata)
	FloatingActionButton uploadNewdata;
	@Bind(R.id.fbmenu)
	FloatingActionsMenu fbmenu;
	@Bind(R.id.zoomin)
	FloatingActionButton zoomin;
	@Bind(R.id.zoomout)
	FloatingActionButton zoomout;
	@Bind(R.id.add_point)
	FloatingActionButton addPoint;
	@Bind(R.id.add_polyline)
	FloatingActionButton addPolyline;
	@Bind(R.id.add_polygon)
	FloatingActionButton addPolygon;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.gis_layout, container, false);
		ButterKnife.bind(this, v);
		init();
		return v;
	}

	private void init() {
		mapView.setMapBackground(0xffffff, 0xffffff, 0, 0);
		mapTouchListner = new MapTouchListner(getContext(),mapView);

		mapView.setOnTouchListener(mapTouchListner);
		mapView.setOnZoomListener(mapTouchListner);

		ArcGISRuntime.setClientId("4BAEtnkIGZeIOf6s");
		// Add code here to fetch the saved license information from local storage
		/*String loadedString = "string fetched from local storage";
		LicenseInfo info = LicenseInfo.fromJson(loadedString);
		ArcGISRuntime.License.setLicense(info);*/

		mapView.setMapOptions(mStreetsBasemap);
		pointTable = new GeodatabaseFeatureServiceTable(servicepointURL, 0);
		pointTable.setSpatialReference(SpatialReference.create(102100));
		pointTable.initialize(new CallbackListener<GeodatabaseFeatureServiceTable.Status>() {
			@Override
			public void onCallback(GeodatabaseFeatureServiceTable.Status status) {
				if (status == GeodatabaseFeatureServiceTable.Status.INITIALIZED) {
					pointlayer = new FeatureLayer(pointTable);
					mapView.addLayer(pointlayer);

				}
			}

			@Override
			public void onError(Throwable throwable) {

			}
		});
		mapTouchListner.setPointTable(pointTable);
		lineTable = new GeodatabaseFeatureServiceTable(servicelineURL,0);
		lineTable.setSpatialReference(SpatialReference.create(102100));
		lineTable.initialize(new CallbackListener<GeodatabaseFeatureServiceTable.Status>() {
			@Override
			public void onCallback(GeodatabaseFeatureServiceTable.Status status) {
				if(status == GeodatabaseFeatureServiceTable.Status.INITIALIZED){
					linelayer = new FeatureLayer(lineTable);
					mapView.addLayer(linelayer);
				}
			}

			@Override
			public void onError(Throwable throwable) {

			}
		});
		mapTouchListner.setLineTable(lineTable);

		ArcGISDynamicMapServiceLayer dynamicLayer = new ArcGISDynamicMapServiceLayer(worldstreetURL
				/*"http://sampleserver6.arcgisonline.com/arcgis/rest/services/DamageAssessment/MapServer"*/);
		mapView.addLayer(dynamicLayer);
	}

	private void setStandardLicenseWithLicenseInfo(UserCredentials credentials) {
		Portal portal = new Portal("PORTAL_URL", credentials);
		PortalInfo portalInfo = null;

		try {
			portalInfo = portal.fetchPortalInfo();
		} catch (Exception e) {
			//MessageDialogFragment.showMessage(getString(R.string.standard_license_failed), getFragmentManager());

			return;
		}

		LicenseInfo licenseInfo = portalInfo.getLicenseInfo();

		LicenseResult licenseResult = ArcGISRuntime.License.setLicense(licenseInfo);
		LicenseLevel licenseLevel = ArcGISRuntime.License.getLicenseLevel();

		if (licenseResult == LicenseResult.VALID && licenseLevel == LicenseLevel.STANDARD) {
			//MessageDialogFragment.showMessage(getString(R.string.standard_license_succeeded), getFragmentManager());
		} else {
			//MessageDialogFragment.showMessage(getString(R.string.standard_license_failed), getFragmentManager());
		}

		//showMap();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}


	@OnClick({R.id.upload_newdata, R.id.fbmenu, R.id.zoomin, R.id.zoomout
			,R.id.add_point,R.id.add_polyline,R.id.add_polygon})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.upload_newdata:
				commitEdit_ONLINE();
				fbmenu.collapse();
				break;
			case R.id.fbmenu:
				break;
			case R.id.zoomin:
				mapView.zoomin();
				break;
			case R.id.zoomout:
				mapView.zoomout();
				break;
			case R.id.add_point:
				mapTouchListner.setType(MapTouchListner.EDIT_GEO_TYPE.MPOINT);
				break;
			case R.id.add_polyline:
				break;
			case R.id.add_polygon:
				break;
		}
	}

	private void uploadData(long fid, Geometry geometry) {
		Geometry.Type type = geometry.getType();
		switch (type) {
			case POINT:
				Point p = (Point) geometry;
				try {
					pointTable.updateFeature(fid, p);
				} catch (TableException e) {
					e.printStackTrace();
				}
				break;
			case POLYLINE:
				break;
			case POLYGON:
				break;
		}
	}

	private void commitEdit_ONLINE() {
		List<Feature> fs = pointTable.getUpdatedFeatures();
		Log.d(TAG, "commitEdit_ONLINE: edit count = " + fs.size());
		pointTable.applyEdits(new CallbackListener<List<GeodatabaseEditError>>() {
			@Override
			public void onCallback(List<GeodatabaseEditError> geodatabaseEditErrors) {
				for (GeodatabaseEditError error : geodatabaseEditErrors) {
					Log.d(TAG, "onCallback: " + error.toString());
				}
			}

			@Override
			public void onError(Throwable throwable) {
				Log.e(TAG, "onError: ", throwable);
			}
		});

		pointTable.applyAttachmentEdits(new CallbackListener<List<GeodatabaseEditError>>() {
			@Override
			public void onCallback(List<GeodatabaseEditError> geodatabaseEditErrors) {
				for (GeodatabaseEditError error : geodatabaseEditErrors) {
					Log.d(TAG, "onCallback: " + error.toString());
				}
			}

			@Override
			public void onError(Throwable throwable) {
				Log.e(TAG, "onError: ", throwable);
			}
		});
	}
}
