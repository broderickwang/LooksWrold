package marc.com.lookswrold.opengl;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Broderick on 2016/10/14.
 */

public class FlatColoredSquare extends Square {
	@Override
	public void draw(GL10 gl) {
		gl.glColor4f(0.5f,0.5f,1.0f,1.0f);
		super.draw(gl);
	}
}
