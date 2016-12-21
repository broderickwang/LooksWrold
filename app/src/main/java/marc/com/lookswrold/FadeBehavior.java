package marc.com.lookswrold;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import marc.com.lookswrold.R;

/**
 * Created by Broderick on 2016/11/14.
 */

public class FadeBehavior extends CoordinatorLayout.Behavior {
	private boolean isHeaden = false;
	private boolean isAnimoting = false;
	private final int SCROLL_VALUE = 50;
	private int childHeight;
	private final int animationDuration = 500;


	public FadeBehavior(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
		if (target.getId() == R.id.rel_body) {
			if (childHeight == 0) {
				childHeight = child.getHeight();
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
		super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
		if (isAnimoting) {
			return;
		}
		if (dy > SCROLL_VALUE && !isHeaden) {
			hide(child, target);
		} else if (dy < -SCROLL_VALUE && isHeaden) {
			show(child, target);
		}
	}
	private void hide(final View child,final View target){
		isHeaden = true;
		ValueAnimator animator = new ValueAnimator();
		animator.setIntValues(0,childHeight);
		animator.setDuration(animationDuration);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				if(child.getBottom() > 0){
					int value = (int) animation.getAnimatedValue();
					isAnimoting = value != childHeight;
					child.layout(child.getLeft(),-value,child.getRight(),-value+child.getHeight());
					target.layout(target.getLeft(),-value+childHeight,target.getRight(),target.getBottom());
				}
			}
		});
		animator.start();
	}
	private void show(final View child,final View target){
		isHeaden = false;
		isHeaden = true;
		ValueAnimator animator = new ValueAnimator();
		animator.setIntValues(0,childHeight);
		animator.setDuration(animationDuration);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				if(child.getBottom() < childHeight){
					int value = (int)animation.getAnimatedValue();
					isAnimoting = value != childHeight;
					child.layout(child.getLeft(),value-childHeight,child.getRight(),value);
					target.layout(target.getLeft(),value,target.getRight(),target.getBottom());
				}
			}
		});
		animator.start();
	}
}
