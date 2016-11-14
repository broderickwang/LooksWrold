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

public class BezierView extends View {
	private Paint mPaint;
	private int centerX,centerY;
	private PointF start, end, control;


	public BezierView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(8);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setTextSize(60);

		start = new PointF(0,0);
		end = new PointF(0,0);
		control = new PointF(0,0);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		centerX = w/2;
		centerY = h/2;

		start.x = centerX-200;
		start.y = centerY;

		end.x = centerX+200;
		end.y = centerY;

		control.x = centerX;
		control.y = centerY-100;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		control.x = event.getX();
		control.y = event.getY();
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
		canvas.drawPoint(control.x,control.y,mPaint);

		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(8);

		Path p = new Path();
		p.moveTo(start.x,start.y);
		p.quadTo(control.x,control.y,end.x,end.y);
		canvas.drawPath(p,mPaint);
	}
}
