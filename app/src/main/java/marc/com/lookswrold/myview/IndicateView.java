package marc.com.lookswrold.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.view.ViewPager;

import marc.com.lookswrold.R;

/**
 * Created by Broderick on 2017/2/21.
 */

public class IndicateView extends LinearLayout implements ViewPager.OnPageChangeListener {
	/**
	 * 需要创建的指示器个数
	 */
	private int childViewCount = 0;
	/**
	 * 设置圆点间margin
	 */
	private int mInterval;
	/**
	 * 当前选中的位置
	 */
	private int mCurrentPosition = 0;
	/**
	 * 普通显示的图片
	 */
	private Bitmap normalBp;
	/**
	 * 选中时显示的图片
	 */
	private Bitmap selectBp;
	/**
	 * 设置的轮播图Vp
	 */
	private ViewPager mViewPager;
	/**
	 * 指示器单项宽度
	 */
	private int mWidth;
	/**
	 * 指示器单项高度
	 */
	private int mHeight;
	/**
	 * 圆点半径
	 */
	private int mRadius;
	/**
	 * 普通状态圆点颜色
	 */
	private int normalColor;
	/**
	 * 选中状态圆点颜色
	 */
	private int selectColor;

	/**
	 * 对外提供ViewPager的回调接口
	 */
	public interface OnPageChangeListener{
		public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels);
		public void onPageSelected(int postion);
		public void onPageScrollStateChanged(int state);
	}

	/**
	 * 设置回调
	 *
	 * @param
	 */
	public void setmListener(OnPageChangeListener mListener) {
		this.mListener = mListener;
	}

	/**
	 * 设置Vp
	 *
	 * @param viewPager
	 */
	public void setViewPager(ViewPager viewPager){
		if(null == viewPager){
			return;
		}
		if(null == viewPager.getAdapter()){
			throw new IllegalStateException("Viewpager does not have a adaptor!");
		}
		this.mViewPager = viewPager;
		this.mViewPager.addOnPageChangeListener(this);
		this.childViewCount = viewPager.getAdapter().getCount();
		invalidate();
	}
	/**
	 * 设置Vp
	 *
	 * @param viewPager
	 * @param currentPosition 当前选中的位置
	 */
	public void  setViewPager(ViewPager viewPager,int currentPosition){
		if(null == viewPager){
			return;
		}
		if(null == viewPager.getAdapter()){
			throw new IllegalStateException("Viewpager does not have a adaptor!");
		}
		this.mViewPager = viewPager;
		this.mViewPager.addOnPageChangeListener(this);
		this.childViewCount = viewPager.getAdapter().getCount();
		this.mCurrentPosition = currentPosition;
		invalidate();
	}


	public IndicateView(Context context) {
		this(context,null);
	}

	public IndicateView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public IndicateView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.IndicateView);
		normalBp = drawableToBitamp(ta.getDrawable(R.styleable.IndicateView_normalDrawable));
		selectBp = drawableToBitamp(ta.getDrawable(R.styleable.IndicateView_selectDrawable));
		mInterval = ta.getDimensionPixelOffset(R.styleable.IndicateView_indicatorInterval,6);
		normalColor = ta.getColor(R.styleable.IndicateView_normalColor, Color.GRAY);
		selectColor = ta.getColor(R.styleable.IndicateView_selectColor,Color.rgb(52,184,244));
		mRadius = ta.getInteger(R.styleable.IndicateView_indicatorRadius,6);
		ta.recycle();
		init();
	}

	private OnPageChangeListener mListener;
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if(null != mListener){
			mListener.onPageScrolled(position,positionOffset,positionOffsetPixels);
		}
	}

	@Override
	public void onPageSelected(int position) {
		setIndicateState(position);
		if(null != mListener){
			mListener.onPageSelected(position);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
		if(null != mListener){
			mListener.onPageScrollStateChanged(state);
		}
	}
	private void setIndicateState(int position){
		for(int i=0;i<getChildCount();i++){
			if(i == position){
				((ImageView)getChildAt(i)).setImageBitmap(selectBp);
			}else{
				((ImageView)getChildAt(i)).setImageBitmap(normalBp);
			}
		}
	}
	private void init(){
		if(null == normalBp){
			normalBp = makeIndicattBp(normalColor);
		}
		if(null == selectBp){
			selectBp = makeIndicattBp(selectColor);
		}
		mWidth = normalBp.getWidth();
		mHeight = normalBp.getHeight();
	}
	/**
	 * 创建圆点指示器图片
	 *
	 * @param color 创建不同颜色的指示器项
	 * @return
	 */
	private Bitmap makeIndicattBp(int color){
		Bitmap bp = Bitmap.createBitmap(mRadius*2,mRadius*2,Bitmap.Config.ARGB_8888);
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
		Canvas canvas = new Canvas(bp);
		canvas.drawCircle(mRadius,mRadius,mRadius,paint);
		return bp;
	}
	/**
	 * drawable转bitmap
	 *
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitamp(Drawable drawable) {
		if (null == drawable) {
			return null;
		}
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 重绘
	 *
	 * @param canvas
	 */
	@Override
	protected void dispatchDraw(Canvas canvas) {
		// 创建指示器圆点
		if (getChildCount() < childViewCount && getChildCount() == 0) {
			for (int i = 0; i < childViewCount; i++) {
				addView(makeIndicatorItem());
			}
			// 设置默认选中指示器
			setIndicateState(mCurrentPosition);
		}
		super.dispatchDraw(canvas);
	}
	/**
	 * 创建指示器
	 *
	 * @return
	 */
	private View makeIndicatorItem() {
		ImageView iv = new ImageView(getContext());
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lp.width = normalBp.getWidth();
		lp.height = normalBp.getHeight();
		lp.rightMargin = mInterval;
		iv.setImageBitmap(normalBp);
		iv.setLayoutParams(lp);
		return iv;
	}
}
