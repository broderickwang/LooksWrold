package marc.com.lookswrold.myview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Broderick on 2016/11/10.
 */

public class RadarView extends View {

	private int centerX,centerY;
	private Path mPath = new Path();
	private Paint mPaint;
	private int count = 6;
	private float angle = (float) (Math.PI*2/count);

	public RadarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		centerX = w/2;
		centerY = h/2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(centerX,centerY);
		mPath.moveTo(centerX,0);

		float fx = centerX/2;
		float fy = (float) Math.cos(angle/2)*centerX;

		mPath.lineTo(fx,fy);
		mPath.lineTo(-fx,fy);
//		mPath.lineTo(-(centerX/4),0);
		mPath.lineTo(-fx,-fy);
		mPath.lineTo(fx,-fy);
		mPath.close();

		canvas.drawPath(mPath,mPaint);

		/*Path path = new Path();

		path.lineTo(200, 200);
		path.lineTo(200,0);

		canvas.drawPath(path, mPaint);*/
	}

	private void init(){
		mPaint = new Paint();
		mPaint.setColor(0x666666);
		mPaint.setStyle(Paint.Style.STROKE);
	}
}
