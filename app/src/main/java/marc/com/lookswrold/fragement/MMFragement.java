package marc.com.lookswrold.fragement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;

/**
 * viewpager的广告轮询
 * Created by Broderick on 2016/12/23.
 */

public class MMFragement extends Fragment {
	@Bind(R.id.mm_viewpager)
	ViewPager mmViewpager;
	List<ImageView> list_iv = new ArrayList<>();
	Bitmap b1, b2, b3;
	List<Bitmap> list_bt = new ArrayList<>();
	@Bind(R.id.layout)
	LinearLayout layout;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.layout_mmfragement, container, false);
		ButterKnife.bind(this, v);

		b1 = BitmapFactory.decodeResource(getResources(), R.drawable.a);
		b2 = BitmapFactory.decodeResource(getResources(), R.drawable.b);
		b3 = BitmapFactory.decodeResource(getResources(), R.drawable.c);
		list_bt.add(b1);
		list_bt.add(b2);
		list_bt.add(b3);

		for (int i = 0; i < 3; i++) {
			ImageView iv = new ImageView(getContext());
			iv.setAdjustViewBounds(true);
			iv.setImageBitmap(list_bt.get(i));
			list_iv.add(iv);
		}

		mmViewpager.setAdapter(new T());

		new A().run();

		return v;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	class A implements Runnable {
		@Override
		public void run() {
			if (mmViewpager != null) {
				int index = mmViewpager.getCurrentItem() + 1;
				if (index >= list_iv.size())
					index = 0;
				mmViewpager.setCurrentItem(index);
				layout.postDelayed(new A(), 3000);
			}
		}
	}

	class T extends PagerAdapter {
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View v = list_iv.get(position);
			ViewGroup parent = (ViewGroup) v.getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
			container.addView(v);
			return v;
		}

		@Override
		public int getCount() {
			return list_iv.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(list_iv.get(position));
		}
	}
}
