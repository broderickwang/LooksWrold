package marc.com.lookswrold;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.activity.FABShow_Hide_Activity;
import marc.com.lookswrold.activity.NewsActivity;
import marc.com.lookswrold.activity.PieChartActivity;
import marc.com.lookswrold.activity.ThreeDActivity;
import marc.com.lookswrold.bean.StartUser;
import marc.com.lookswrold.fragement.MMFragement;
import marc.com.lookswrold.services.GetZhihuService;
import marc.com.lookswrold.fragement.GISFragement;
import marc.com.lookswrold.fragement.GithubFragement;
import marc.com.lookswrold.fragement.Main2Fragement;
import marc.com.lookswrold.fragement.NewsFragement;
import marc.com.lookswrold.fragement.ThreeDFragement;
import marc.com.lookswrold.fragement.ZhihuFragement;
import marc.com.lookswrold.util.GlideCircleTransform;
import retrofit2.Call;
import retrofit2.Callback;
//import retrofit.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener {

	long firstTime = 0;

	@Bind(R.id.navigation)
	NavigationView navigation;
	@Bind(R.id.draw_layout)
	DrawerLayout drawLayout;
	@Bind(R.id.toolbar)
	Toolbar toolbar;
	FragmentTransaction trance;

	MMFragement main2Fragement;
	GithubFragement githubFragement;
	ZhihuFragement zhihuFragement;
	NewsFragement newsFragement;
	GISFragement gisFragement;
	ThreeDFragement threeDFragement;

	ActionBarDrawerToggle mDrawerToggle;
	ImageView head_tx;
	TextView head_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		toolbar.setTitle("看天下");
		toolbar.setTitleTextColor(Color.WHITE);
		toolbar.inflateMenu(R.menu.app_menu);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this,drawLayout,toolbar,
				R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		drawLayout.setDrawerListener(mDrawerToggle);
		//navigationicon需要最后设置,不然无效
//		toolbar.setOnMenuItemClickListener();
		toolbar.setNavigationIcon(R.drawable.menu);

		navigation.setNavigationItemSelectedListener(this);
		initView();

		trance = getSupportFragmentManager().beginTransaction();
		trance.replace(R.id.frame,new MMFragement());
		trance.addToBackStack(null);
		trance.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.app_menu,menu);
		return true;
	}

	private void initView(){
		main2Fragement = new MMFragement();
		githubFragement = new GithubFragement();
		zhihuFragement = new ZhihuFragement();
		newsFragement = new NewsFragement();
		gisFragement = new GISFragement();
		threeDFragement = new ThreeDFragement();
		getPhoto();
	}
	public void getPhoto() {

		//获取navigation中的控件
		View view = navigation.getHeaderView(0);
		head_tx = (ImageView)view.findViewById(R.id.head_tx);
		head_name = (TextView)view.findViewById(R.id.head_name);

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(ZhihuFragement.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetZhihuService github = retrofit.create(GetZhihuService.class);
		Call<StartUser> call = github.getSplash("1080*1776");

		call.enqueue(new Callback<StartUser>() {
			@Override
			public void onResponse(Call<StartUser> call, Response<StartUser> response) {
				if(response.body() != null) {
					Glide.with(Main.this)
							.load(response.body().getImg())
							.centerCrop()
							.diskCacheStrategy(DiskCacheStrategy.ALL)
							.transform(new GlideCircleTransform(Main.this))
							.placeholder(R.drawable.home)
							.error(R.drawable.zombie)
							.crossFade()
							.into(head_tx);
					head_name.setText(response.body().getText());
					Log.i("Tag", "onResponse: "+response.body().getText());

				}
			}

			@Override
			public void onFailure(Call<StartUser> call, Throwable t) {

			}
		});
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		item.setChecked(true);
		drawLayout.closeDrawers();
		trance = getSupportFragmentManager().beginTransaction();
		switch (item.getItemId()){
			case R.id.github:
				trance.replace(R.id.frame,githubFragement);
				toolbar.setTitle("GitHub");
				break;
			case R.id.home:
				trance.replace(R.id.frame,main2Fragement);
				toolbar.setTitle("看天下");
				break;
			case R.id.zh:
				trance.replace(R.id.frame,zhihuFragement);
				toolbar.setTitle("知乎日报");
				break;
			case R.id.news:
				trance.replace(R.id.frame,newsFragement);
				toolbar.setTitle("今日头条");
				break;
			case R.id.gis:
				trance.replace(R.id.frame,gisFragement);
				toolbar.setTitle("GIS在线");
				break;
			case R.id.ddd:
				Intent i = new Intent(Main.this, ThreeDActivity.class);
				startActivity(i);
				break;
			case R.id.baidu_map:
				/*Intent i1 = new Intent(Main.this, GaodeAcitivity.class);
				startActivity(i1);*/
//				Toast.makeText(this, "nothing to show", Toast.LENGTH_SHORT).show();
				Intent it = new Intent(Main.this, NewsActivity.class);
				startActivity(it);
				break;
			case R.id.jump2piechart:
				startActivity(new Intent(Main.this, FABShow_Hide_Activity.class));
				break;
		}
		trance.addToBackStack(null);
		trance.commit();
		return super.onContextItemSelected(item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.action_search:
				break;
			case R.id.action_notification:
				break;
			case R.id.action_settings:
				break;
			case R.id.action_about:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		long secondTime = System.currentTimeMillis();
		if((secondTime-firstTime)>2000){
			Toast.makeText(Main.this, "再点击一次,退出", Toast.LENGTH_SHORT).show();
			firstTime = secondTime;
			return ;
		}else{
			System.exit(0);
		}
	}
	//https://api.github.com/repos/square/retrofit/contributors
}
