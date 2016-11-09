package marc.com.lookswrold.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import marc.com.lookswrold.R;

/**
 * Created by Broderick on 2016/11/9.
 */

public class PieChart extends View {
	private boolean mShowText;
	private int mTextPos;
	private int mTextColor;
	private float mTextHeight;
	private Drawable mDrawable;
	private int mWidth,mHeight;

	Paint mTextPaint,mPiePaint,mShadowPaint, mBlackPaint
			,mBluePaint,mRedPaint,mWhitePaint,mGrayPaint;

	public PieChart(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs, R.styleable.PieChart,0,0
		);

		try {
			mShowText = a.getBoolean(R.styleable.PieChart_showText,false);
			mTextPos = a.getInteger(R.styleable.PieChart_labelPosition,0);
			mTextColor = a.getColor(R.styleable.PieChart_textColor, Color.BLACK);
			mDrawable = a.getDrawable(R.styleable.PieChart_igs);
		} finally {
			a.recycle();
		}
		init();
	}

	public boolean isShowText(){
		return mShowText;
	}

	public void setShowText(boolean showText){
		this.mShowText = showText;
		invalidate();
		requestLayout();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.translate(mWidth/2,mHeight/2);
		RectF rectF = new RectF(100,100,600,600);
		canvas.drawRect(rectF,mGrayPaint);
		canvas.drawArc(rectF,0,90,true, mBlackPaint);
		canvas.drawArc(rectF,90,40,true,mBluePaint);
		canvas.drawArc(rectF,130,100,true,mRedPaint);
		canvas.drawArc(rectF,230,130,true,mWhitePaint);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
	}

	private void init(){
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(mTextColor);
		if(mTextHeight == 0)
			mTextHeight = mTextPaint.getTextSize();
		else
			mTextPaint.setTextSize(mTextHeight);

		mPiePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPiePaint.setStyle(Paint.Style.FILL);
		mPiePaint.setTextSize(mTextHeight);

		mShadowPaint = new Paint(0);
		mShadowPaint.setColor(0xff101010);
		mShadowPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

		mBlackPaint = new Paint();
		mBlackPaint.setColor(Color.BLACK);
		mBlackPaint.setStrokeWidth(20);
		mBlackPaint.setStyle(Paint.Style.FILL);

		mBluePaint = new Paint();
		mBluePaint.setColor(Color.BLUE);
		mBluePaint.setStrokeWidth(20);
		mBluePaint.setStyle(Paint.Style.FILL);

		mRedPaint = new Paint();
		mRedPaint.setColor(Color.RED);
		mRedPaint.setStrokeWidth(20);
		mRedPaint.setStyle(Paint.Style.FILL);

		mWhitePaint = new Paint();
		mWhitePaint.setColor(Color.WHITE);
		mWhitePaint.setStrokeWidth(20);
		mWhitePaint.setStyle(Paint.Style.FILL);

		mGrayPaint = new Paint();
		mGrayPaint.setColor(Color.GRAY);
		mGrayPaint.setStrokeWidth(20);
		mGrayPaint.setStyle(Paint.Style.FILL);
	}
}
