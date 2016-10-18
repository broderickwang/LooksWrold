package marc.com.lookswrold.activity;

import android.app.Activity;
import android.opengl.GLES10;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import javax.microedition.khronos.opengles.GL10;

import marc.com.lookswrold.opengl.Square;
import marc.com.lookswrold.rander.OpenGLRender;

/**
 * Created by Broderick on 2016/10/13.
 */

public class ThreeDActivity extends Activity {
	private GLSurfaceView glSurfaceView;
	GL10 gl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		glSurfaceView = new GLSurfaceView(this);

		glSurfaceView.setRenderer(new OpenGLRender());
		setContentView(glSurfaceView);
		super.onCreate(savedInstanceState);
	}
}
