package marc.com.lookswrold.myclass;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import marc.com.lookswrold.interfaces.ScrollInterface;

/**
 * Created by Broderick on 2016/11/7.
 */

public class MarcWebView extends WebView {

	ScrollInterface web;
	public MarcWebView(Context context) {
		super(context);
	}
	public MarcWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public MarcWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		web.onSChanged(l, t, oldl, oldt);
	}

	public void setOnCustomScroolChangeListener(ScrollInterface t) {
		this.web = t;
	}
}
