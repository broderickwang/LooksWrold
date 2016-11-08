package marc.com.lookswrold.fragement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.activity.NewsImgActivity;
import marc.com.lookswrold.activity.WebActivity;
import marc.com.lookswrold.adapter.NewsAdaptor;
import marc.com.lookswrold.bean.NewsBean;
import marc.com.lookswrold.services.GetNewsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Broderick on 2016/11/8.
 */

public class NewsFragement_Tab extends Fragment {
	@Bind(R.id.news_recycleview_tab)
	RecyclerView newsRecycleviewTab;
	@Bind(R.id.news_ref_taba)
	SwipeRefreshLayout newsRefTaba;

	ProgressDialog dlg;
	NewsAdaptor adaptor;

	String mType = "top";

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.news_layout_tab, container, false);
		ButterKnife.bind(this, v);
		dlg = ProgressDialog.show(getContext(),null,"Loading...");
		dlg.show();

		newsRefTaba.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3);
		newsRefTaba.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getData(mType);
			}
		});
		int position = getArguments().getInt("position",0);
		switch (position){
			case 0:
				mType = "top";
				break;
			case 1:
				mType = "yule";
				break;
			case 2:
				mType = "tiyu";
				break;
			case 3:
				mType = "caijing";
				break;
			case 4:
				mType = "guoji";
				break;
		}

		newsRecycleviewTab.setLayoutManager(new LinearLayoutManager(getContext()));
		adaptor = new NewsAdaptor(getContext());
		adaptor.setClick(new NewsAdaptor.ItemClick() {
			@Override
			public void OnItemClick(View view, String data) {
				Intent i = new Intent(getContext(), WebActivity.class);
				i.putExtra("url", data);
				i.putExtra("type", "news");
				startActivity(i);
			}
		});
		adaptor.setLongPress(new NewsAdaptor.ItemLongPress() {
			@Override
			public void OnItemLongPress(View view, String data) {
				Intent i = new Intent(getContext(), NewsImgActivity.class);
				i.putExtra("imgs", data);
				startActivity(i);
			}
		});

		newsRecycleviewTab.setAdapter(adaptor);

		getData(mType);
		return v;
	}
	private void getData(String type) {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(GetNewsService.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetNewsService getNews = retrofit.create(GetNewsService.class);
		Call<NewsBean> call = getNews.getNews(type, GetNewsService.APP_KEY);

		call.enqueue(new Callback<NewsBean>() {
			@Override
			public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
				response.raw().request().url();//获取请求的url

				NewsBean body = response.body();
				NewsBean.ResultBean resultBean = body.getResult();
				adaptor.setData(resultBean.getData());
				adaptor.notifyDataSetChanged();
				dlg.dismiss();
				if(newsRefTaba != null)
					newsRefTaba.setRefreshing(false);
			}

			@Override
			public void onFailure(Call<NewsBean> call, Throwable t) {

			}
		});
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
}
