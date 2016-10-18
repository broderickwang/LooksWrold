package marc.com.lookswrold.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Broderick on 2016/10/14.
 */

public class SmoothColoredSquare extends Square {

	float[] colors = {
			1f,0f,0f,1f,
			0f,1f,0f,1f,
			0f,0f,1f,1f,
			1f,0f,1f,1f
	};

	FloatBuffer colorBuffer;
	FloatBuffer vertexBuffer;

	public SmoothColoredSquare() {
		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);

		vertexBuffer = cbb.asFloatBuffer();
		vertexBuffer.put(colors);
		vertexBuffer.position(0);
	}

	@Override
	public void draw(GL10 gl) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// Enable the color array buffer to be
		//used during rendering.
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		// Point out the where the color buffer is.
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		// Disable the color buffer.
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
		super.draw(gl);
	}
}
