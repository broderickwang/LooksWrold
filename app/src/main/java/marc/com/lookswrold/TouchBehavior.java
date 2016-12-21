package marc.com.lookswrold;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Broderick on 2016/11/14.
 */

public class TouchBehavior extends CoordinatorLayout.Behavior implements NestedScrollingChild {

	private NestedScrollingChildHelper childHelper;
	private float ox,oy;
	private int[] consumed = new int[2];
	private int[] offseInWindow = new int[2];
	public TouchBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
		if(childHelper == null){
			childHelper = new NestedScrollingChildHelper(child);
			setNestedScrollingEnabled(true);
		}
		switch (ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				ox = ev.getX();
				oy = ev.getY();
				if(oy<child.getTop() || oy>child.getBottom()
						||ox<child.getLeft() || ox>child.getRight())
					return true;
				startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
				break;
			case MotionEvent.ACTION_HOVER_MOVE:
				float clampedX = ev.getX();
				float clampedY = ev.getY();

				int dx = (int)(clampedX-ox);
				int dy = (int)(clampedY-oy);

				if(Math.abs(dy)>Math.abs(dx)){
					dispatchNestedPreScroll(dx,-dy,consumed,offseInWindow);
				}
				break;
			case MotionEvent.ACTION_UP:
				stopNestedScroll();
				break;
		}
		return true;
	}

	@Override
	public void setNestedScrollingEnabled(boolean enabled) {
		childHelper.setNestedScrollingEnabled(enabled);
	}

	@Override
	public boolean isNestedScrollingEnabled() {
		return childHelper.isNestedScrollingEnabled();
	}

	@Override
	public boolean startNestedScroll(int axes) {
		return childHelper.startNestedScroll(axes);
	}

	@Override
	public void stopNestedScroll() {
		childHelper.stopNestedScroll();
	}

	@Override
	public boolean hasNestedScrollingParent() {
		return childHelper.hasNestedScrollingParent();
	}

	@Override
	public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
		return childHelper.dispatchNestedScroll(dxConsumed,dyConsumed,
				dxUnconsumed,dyUnconsumed,offsetInWindow);
	}

	@Override
	public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
		return childHelper.dispatchNestedPreScroll(dx,dy,consumed,offsetInWindow);
	}

	@Override
	public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
		return childHelper.dispatchNestedFling(velocityX,velocityY,consumed);
	}

	@Override
	public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
		return childHelper.dispatchNestedPreFling(velocityX,velocityY);
	}
}
