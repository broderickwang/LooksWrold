package marc.com.lookswrold.adapter;

/**
 * Created by Broderick on 2016/11/8.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

public class MainPageAdapter extends FragmentPagerAdapter{
	Fragment[] fragments;

	public MainPageAdapter(FragmentManager fm,Fragment[] fragments){
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public int getCount() {
		if(fragments==null){
			return 0;
		}
		return fragments.length;
	}
	@Override
	public Fragment getItem(int position) {
		Bundle bundle = new Bundle();
		bundle.putInt("position",position);
		fragments[position].setArguments(bundle);
		return fragments[position];
	}
	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}
	@Override
	public long getItemId(int position){
		return super.getItemId(position);
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object){
		super.destroyItem(container,position,object);
	}

}