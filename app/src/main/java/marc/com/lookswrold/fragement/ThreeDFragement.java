package marc.com.lookswrold.fragement;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import marc.com.lookswrold.rander.OpenGLRender;

/**
 * Created by Broderick on 2016/10/13.
 */

public class ThreeDFragement extends Fragment {

	private GLSurfaceView glSurfaceView;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
		getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		glSurfaceView = new GLSurfaceView(getContext());

		glSurfaceView.setRenderer(new OpenGLRender());

		return glSurfaceView;
	}
}
