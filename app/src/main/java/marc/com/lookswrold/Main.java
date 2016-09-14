package marc.com.lookswrold;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.adapter.GitHubAdaptor;
import marc.com.lookswrold.bean.Contributor;
import marc.com.lookswrold.face.GitHubClient;
import marc.com.lookswrold.fragement.GithubFragement;
import marc.com.lookswrold.fragement.TestFragement;
import marc.com.lookswrold.fragement.ZhihuFragement;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

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

	TestFragement testFragement;
	GithubFragement githubFragement;
	ZhihuFragement zhihuFragement;
	ActionBarDrawerToggle mDrawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		toolbar.setTitle("看天下");
//		toolbar.setSubtitle("");
		toolbar.inflateMenu(R.menu.app_menu);
		setSupportActionBar(toolbar);


		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this,drawLayout,toolbar,
				R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		drawLayout.setDrawerListener(mDrawerToggle);

		navigation.setNavigationItemSelectedListener(this);
		initView();

		trance = getSupportFragmentManager().beginTransaction();
		trance.replace(R.id.frame,new TestFragement());
		trance.addToBackStack(null);
		trance.commit();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.app_menu,menu);
		return true;
	}

	private void initView(){
		testFragement = new TestFragement();
		githubFragement = new GithubFragement();
		zhihuFragement = new ZhihuFragement();
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		item.setChecked(true);
		drawLayout.closeDrawers();
		trance = getSupportFragmentManager().beginTransaction();
		switch (item.getItemId()){
			case R.id.github:
				trance.replace(R.id.frame,githubFragement);
				break;
			case R.id.home:
				trance.replace(R.id.frame,testFragement);
				break;
			case R.id.zh:
				trance.replace(R.id.frame,zhihuFragement);
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
