package marc.com.lookswrold.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.adapter.NewsImgPageAdapter;

public class NewsImgActivity extends AppCompatActivity {

	String igms;
	String[] igs;
	@Bind(R.id.new_viewpager)
	ViewPager newViewpager;

	LayoutInflater inflater;
	ArrayList<View> views ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_img);
		ButterKnife.bind(this);

		igms = getIntent().getStringExtra("imgs");
		if (igms != null) {
			igs = igms.split(",");
		}

		initData();

		NewsImgPageAdapter adapter = new NewsImgPageAdapter(this);
		adapter.setList(views);
		newViewpager.setAdapter(adapter);
	}

	private void initData(){
		views = new ArrayList<>();
		inflater = LayoutInflater.from(this);

		View v = inflater.inflate(R.layout.news_image,null);
		View v1 = inflater.inflate(R.layout.news_image1,null);
		View v2 = inflater.inflate(R.layout.news_image2,null);

		ImageView ig,ig1,ig2,ig3;


		ig3 = (ImageView)v.findViewById(R.id.news_igggg);
		ig1 = (ImageView)v1.findViewById(R.id.news_ig1);
		ig2 = (ImageView)v2.findViewById(R.id.news_ig2);

		Glide.with(this)
				.load(igs[0])
				.into(ig3);
		Glide.with(this)
				.load(igs[1])
				.into(ig1);
		Glide.with(this)
				.load(igs[2])
				.into(ig2);

		views.add(v);
		views.add(v1);
		views.add(v2);
	}
}
