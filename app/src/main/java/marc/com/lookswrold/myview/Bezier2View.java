package marc.com.lookswrold.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Broderick on 2016/11/10.
 */

public class Bezier2View extends View {
	private int mWidth,mHeight;
	private PointF start,end,control1,control2;
	private boolean isControl1 = true;
	private Paint mPaint;
	public Bezier2View(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(20);

		start = new PointF(0,0);
		end = new PointF(0,0);
		control1 = new PointF(0,0);
		control2 = new PointF(0,0);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w/2;
		mHeight = h/2;

		start.x = mWidth-200;
		start.y = mHeight;

		end.x = mWidth + 200;
		end.y = mHeight;

		control1.x = mWidth-100;
		control1.y = mHeight-100;

		control2.x = mWidth +100;
		control2.y = mHeight-100;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(isControl1){
			control1.x = event.getX();
			control1.y = event.getY();
		}else{
			control2.x = event.getX();
			control2.y = event.getY();
		}
		invalidate();
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mPaint.setColor(Color.GRAY);
		mPaint.setStrokeWidth(20);

		canvas.drawPoint(start.x,start.y,mPaint);
		canvas.drawPoint(end.x,end.y,mPaint);
		canvas.drawPoint(control1.x,control1.y,mPaint);
		canvas.drawPoint(control2.x,control2.y,mPaint);

		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(8);

		Path path = new Path();
		path.moveTo(start.x,start.y);
		path.cubicTo(control1.x,control1.y,control2.x,control2.y,end.x,end.y);

		canvas.drawPath(path,mPaint);

	}
	public void setisControl1(){
		isControl1 = true;
	}
	public void setisControl2(){
		isControl1 = false;
	}
}
