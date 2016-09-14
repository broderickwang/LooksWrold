package marc.com.lookswrold.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.StartUser;
import marc.com.lookswrold.face.GetZhihuService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Broderick on 16/9/12.
 */
public class TestFragement extends Fragment {
	static String BASE_URL = "http://news-at.zhihu.com";
	@Bind(R.id.image)
	ImageView image;
	@Bind(R.id.name)
	TextView name;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.test, container, false);
		ButterKnife.bind(this, view);
//		getData();
		return view;
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
				Glide.with(getContext())
						.load(response.body().getImg())
						.into(image);
				name.setText(response.body().getText());
			}

			@Override
			public void onFailure(Throwable t) {

			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}
