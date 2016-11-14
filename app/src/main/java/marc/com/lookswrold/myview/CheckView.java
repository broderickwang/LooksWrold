package marc.com.lookswrold.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import marc.com.lookswrold.R;

/**
 * Created by Broderick on 2016/11/10.
 */

public class CheckView extends View {
	private static final int ANIM_NULL = 0;     //anim is not begenning
	private static final int ANIM_CHECK = 1;    //anim start
	private static final int ANIM_UNCHECK = 2;   //anim end

	private Context mContext;
	private int mWidth,mHeight;
	private Handler mHandler;

	private Paint mPaint;
	private Bitmap okBitmap;

	private int animCurrentPage = -1;       //now page number
	private int animMaxPige = 13;           //Max pages
	private int animDuration = 500;         //anim duration
	private int animState = ANIM_NULL;          //anim state

	private boolean isChecked = false;          //isn't checked

	public CheckView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.translate(mWidth/2,mHeight/2);
		canvas.drawCircle(0,0,240,mPaint);

		// 得出图像边长
		int sideLength = okBitmap.getHeight();

		// 得到图像选区 和 实际绘制位置
		Rect src = new Rect(sideLength * animCurrentPage, 0, sideLength * (animCurrentPage + 1), sideLength);
		Rect dst = new Rect(-200, -200, 200, 200);


		canvas.drawBitmap(okBitmap,src,dst,null);
	}

	private void init(Context context){
		mContext = context;

		mPaint = new Paint();
		mPaint.setColor(0xffFF5317);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);

		okBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.checkmark);

		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(animCurrentPage<animMaxPige && animCurrentPage>=0){
					invalidate();
					if(animState == ANIM_NULL)
						return;
					if(animState == ANIM_CHECK)
						animCurrentPage++;
					else if(animState == ANIM_UNCHECK)
						animCurrentPage--;
					this.sendEmptyMessageAtTime(0,animDuration/animMaxPige);
				}else{
					if(isChecked)
						animCurrentPage = animMaxPige-1;
					else
						animCurrentPage = -1;
					invalidate();
					animState = ANIM_NULL;
				}
			}
		};
	}

	public void check(){
		if(animState!=ANIM_NULL || isChecked)
			return;
		animState = ANIM_CHECK;
		animCurrentPage = 0;
		mHandler.sendEmptyMessageAtTime(0,animDuration/animMaxPige);
		isChecked = true;
	}
	public void uncheck(){
		if (animState != ANIM_NULL || (!isChecked))
			return;
		animState = ANIM_UNCHECK;
		animCurrentPage = animMaxPige - 1;
		mHandler.sendEmptyMessageDelayed(0, animDuration / animMaxPige);
		isChecked = false;
	}
	public void setDuration(int animDuration){
		if(animDuration<=0)
			return;
		this.animDuration = animDuration;
	}
	public void setBackgroud(int color){
		mPaint.setColor(color);
	}
}
