package marc.com.lookswrold.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import marc.com.lookswrold.R;
import marc.com.lookswrold.adapter.MainPageAdapter;
import marc.com.lookswrold.fragement.NewsFragement_Tab;

public class NewsActivity extends AppCompatActivity {
	private Fragment[] fragments = {new NewsFragement_Tab(), new NewsFragement_Tab(),
			new NewsFragement_Tab(), new NewsFragement_Tab(), new NewsFragement_Tab()};
	private int[] imgs = {R.drawable.bottombar_01, R.drawable.bottombar_02,
			R.drawable.bottombar_03, R.drawable.bottombar_04, R.drawable.bottombar_05 };
	private String[] names = {"头条", "娱乐", "体育", "财经", "国际" };

	private TabLayout tabLayout;
	private MainPageAdapter adapter;//PageAdapter适配器
	private ViewPager container;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);

		initView();//初始化布局组件

		setView();//对组件按需求进行相关属性设置
	}

	public void initView(){
		tabLayout = (TabLayout) findViewById(R.id.main_tablayout);
		adapter = new MainPageAdapter(getSupportFragmentManager(),fragments);
		container = (ViewPager) findViewById(R.id.container);
	}
	public void setView(){
		tabLayout.setTabMode(TabLayout.MODE_FIXED);
		container.setAdapter(adapter);
		tabLayout.setupWithViewPager(container,false );//第二个参数autoRefresh：是否自动刷新(切换的时候是否刷PagerAdapter选中的Fragment)
		//以下是对TabLayout进行相关设置
		int tabCount = tabLayout.getTabCount();//获取TabLayout的个数
		for (int i=0; i<tabCount; i++) {
			View view = LayoutInflater.from(NewsActivity.this).inflate(R.layout.main_tab,null);
			ImageView imageView = (ImageView) view.findViewById(R.id.main_tab_img);
			imageView.setImageDrawable(getResources().getDrawable(imgs[i]));
			TextView textView = (TextView) view.findViewById(R.id.main_tab_tv);
			textView.setText(names[i]);

			TabLayout.Tab tab = tabLayout.getTabAt(i);////获取TabLayout的子元素Tab
			tab.setCustomView(view);//设置TabLayout的子元素Tab的布局View
		}
	}
}
