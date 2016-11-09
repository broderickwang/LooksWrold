package marc.com.lookswrold;


import android.support.multidex.MultiDexApplication;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Broderick on 2016/10/21.
 */

public class MyApplication extends MultiDexApplication {


	@Override
	public void onCreate() {
		super.onCreate();
		Fresco.initialize(getApplicationContext());
	}
}
