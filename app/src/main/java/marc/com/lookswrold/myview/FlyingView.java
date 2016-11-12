package marc.com.lookswrold.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import marc.com.lookswrold.R;

/**
 * Created by hannahxian on 2016/11/12.
 */

public class FlyingView extends View {
    private int mWidth,mHeight;
    private float currentVlaue = 0;
    private float[] tan;
    private float[] pos;
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private Paint mDeafultPaint;


    public FlyingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w/2;
        mHeight = h/2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(mWidth,mHeight);

        Path path = new Path();
        path.addCircle(0,0,200, Path.Direction.CW);
        PathMeasure measure = new PathMeasure(path,false);

        currentVlaue += 0.005;
        if(currentVlaue >= 1)
            currentVlaue = 0;

        measure.getPosTan(measure.getLength()*currentVlaue,pos,tan);

        mMatrix.reset();

        float degrees = (float) (Math.atan2(tan[1],tan[0])*180.0/Math.PI);

        mMatrix.postRotate(degrees,mBitmap.getWidth()/2,mBitmap.getHeight()/2);
        mMatrix.postTranslate(pos[0] - mBitmap.getWidth() / 2, pos[1] - mBitmap.getHeight() / 2);   // 将图片绘制中心调整到与当前点重合

        canvas.drawPath(path, mDeafultPaint);                                   // 绘制 Path
        canvas.drawBitmap(mBitmap, mMatrix, mDeafultPaint);                     // 绘制箭头

        invalidate();
    }

    private void init(Context context){
        pos = new float[2];
        tan = new float[2];
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 7;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.fly2,options);
        mMatrix = new Matrix();
        mDeafultPaint = new Paint();
        mDeafultPaint.setStyle(Paint.Style.STROKE);
        mDeafultPaint.setColor(Color.BLACK);
        mDeafultPaint.setStrokeWidth(7);
    }
}
