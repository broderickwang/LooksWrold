package marc.com.lookswrold.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Broderick on 16/9/20.
 */
public class NewsImgPageAdapter extends PagerAdapter {
	Context context;

	ArrayList<View> list;

	public NewsImgPageAdapter(Context context) {
		this.context = context;
	}

	public void setList(ArrayList<View> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return list==null?0:list.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
//		return view==object;
		return  view == list.get(Integer.parseInt(object.toString()));
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(list.get(position));
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(list.get(position));
//		return list.get(position);
		return position;
	}
}
