package marc.com.lookswrold.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.Main;
import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.StartUser;
import marc.com.lookswrold.face.GetZhihuService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class SplashActivity extends AppCompatActivity {
	static String BASE_URL = "http://news-at.zhihu.com";

	@Bind(R.id.spalsh)
	ImageView spalsh;
	@Bind(R.id.name)
	TextView name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ButterKnife.bind(this);
		getData();

		AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
		alphaAnimation.setDuration(6000);
		spalsh.startAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(SplashActivity.this, Main.class));
				SplashActivity.this.finish();
			}
		});
	}

	public void getData() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetZhihuService github = retrofit.create(GetZhihuService.class);
		Call<StartUser> call = github.getSplash("1080*1776");

		call.enqueue(new Callback<StartUser>() {
			@Override
			public void onResponse(Response<StartUser> response, Retrofit retrofit) {
				if(response.body() != null) {
					Glide.with(SplashActivity.this)
							.load(response.body().getImg())
							.into(spalsh);
					name.setText(response.body().getText());
				}
			}

			@Override
			public void onFailure(Throwable t) {

			}
		});
	}
}
