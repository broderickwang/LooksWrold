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
 * Created by Broderick on 16/9/19.
 */
public class Main2Fragement extends Fragment {

	@Bind(R.id.yangli1)
	TextView yangli1;
	@Bind(R.id.yinli1)
	TextView yinli1;
	@Bind(R.id.wuxing1)
	TextView wuxing1;
	@Bind(R.id.chongsha1)
	TextView chongsha1;
	@Bind(R.id.jishen1)
	TextView jishen1;
	@Bind(R.id.xs1)
	TextView xs1;
	@Bind(R.id.yi1)
	TextView yi1;
	@Bind(R.id.ji1)
	TextView ji1;
	@Bind(R.id.pengzubaiji1)
	TextView pengzubaiji1;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_frge_layout, container, false);
		ButterKnife.bind(this, view);
		getData();
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
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
				if (resultBean != null) {
					if (yangli1 != null)
						yangli1.setText(resultBean.getYangli());
					yinli1.setText(resultBean.getYinli());
					wuxing1.setText(resultBean.getWuxing());
					chongsha1.setText(resultBean.getChongsha());
					jishen1.setText(resultBean.getJishen());
					xs1.setText(resultBean.getXiongshen());
					yi1.setText(resultBean.getYi());
					ji1.setText(resultBean.getJi());

					String a = resultBean.getBaiji();
					String[] ss = a.split(" ");
					if (ss.length == 2)
						pengzubaiji1.setText(ss[0] + "\n" + ss[1]);
					else
						pengzubaiji1.setText(a);
				}

			}

			@Override
			public void onFailure(Throwable t) {

			}
		});
	}
}
