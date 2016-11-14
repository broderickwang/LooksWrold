package marc.com.lookswrold.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Broderick on 2016/11/11.
 */

public class TJView extends View {
	private Paint mPaint;
	private int mWidth,mHeight;

	public TJView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init(){
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(Color.GRAY);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w/2;
		mHeight = h/2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(mWidth,mHeight);
		Path bgPath = new Path();
		bgPath.addRect(-400,-400,400,400,Path.Direction.CW);
		canvas.drawPath(bgPath,mPaint);

		mPaint.setColor(Color.BLACK);

		Path path1 = new Path();
		Path path2 = new Path();
		Path path3 = new Path();
		Path path4 = new Path();

		path1.addCircle(0, 0, 200, Path.Direction.CW);
		path2.addRect(0, -200, 200, 200, Path.Direction.CW);
		path3.addCircle(0, -100, 100, Path.Direction.CW);
		path4.addCircle(0, 100, 100, Path.Direction.CCW);


		path1.op(path2, Path.Op.DIFFERENCE);
		path1.op(path3, Path.Op.UNION);
		path1.op(path4, Path.Op.DIFFERENCE);

		Path path11 = new Path();
		Path path22 = new Path();
		Path path33 = new Path();
		Path path44 = new Path();



		canvas.drawPath(path1, mPaint);
		path11.addCircle(0,0,200, Path.Direction.CW);
		path22.addRect(-200,-200,0,200, Path.Direction.CW);
		path33.addCircle(0,100,100, Path.Direction.CW);
		path44.addCircle(0,-100,100, Path.Direction.CCW);

		path11.op(path22, Path.Op.DIFFERENCE);
		path11.op(path33, Path.Op.UNION);
		path11.op(path44, Path.Op.DIFFERENCE);
		mPaint.setColor(Color.WHITE);
		canvas.drawPath(path11,mPaint);
	}
}
