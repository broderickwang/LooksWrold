package marc.com.lookswrold.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.DateBean;
import marc.com.lookswrold.face.GetDateService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Broderick on 16/9/12.
 */
public class MainFragement extends Fragment {

	@Bind(R.id.yangli)
	TextView yangli;
	@Bind(R.id.yinli)
	TextView yinli;
	@Bind(R.id.wuxing)
	TextView wuxing;
	@Bind(R.id.chongsha)
	TextView chongsha;
	@Bind(R.id.jishen)
	TextView jishen;
	@Bind(R.id.xs)
	TextView xs;
	@Bind(R.id.yi)
	TextView yi;
	@Bind(R.id.ji)
	TextView ji;

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
				.baseUrl(DateBean.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetDateService getDateService = retrofit.create(GetDateService.class);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());

		Call<DateBean> call = getDateService.getLHL(date, DateBean.APP_KEY);

		call.enqueue(new Callback<DateBean>() {
			@Override
			public void onResponse(Response<DateBean> response, Retrofit retrofit) {
				DateBean bean = response.body();
				DateBean.ResultBean resultBean = bean.getResult();
				yangli.setText(resultBean.getYangli());
				yinli.setText(resultBean.getYinli());


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
