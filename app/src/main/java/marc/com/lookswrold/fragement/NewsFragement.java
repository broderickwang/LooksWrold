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

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import marc.com.lookswrold.R;
import marc.com.lookswrold.activity.NewsImgActivity;
import marc.com.lookswrold.activity.WebActivity;
import marc.com.lookswrold.adapter.NewsAdaptor;
import marc.com.lookswrold.bean.NewsBean;
import marc.com.lookswrold.services.GetNewsService;
import retrofit2.Call;
import retrofit2.Callback;
//import retrofit.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Broderick on 16/9/18.
 */
public class NewsFragement extends Fragment {
	@Bind(R.id.news_recycleview)
	RecyclerView newsRecycleview;
	NewsAdaptor adaptor;
	@Bind(R.id.top)
	FloatingActionButton top;
	@Bind(R.id.yl)
	FloatingActionButton yl;
	@Bind(R.id.ty)
	FloatingActionButton ty;
	@Bind(R.id.fbmenu)
	FloatingActionsMenu fbmenu;
	@Bind(R.id.cj)
	FloatingActionButton cj;
	@Bind(R.id.gj)
	FloatingActionButton gj;
	@Bind(R.id.news_ref)
	SwipeRefreshLayout newsRef;

	ProgressDialog dlg;

	String mType = "top";

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.news_layout, container, false);
		ButterKnife.bind(this, v);


		dlg = ProgressDialog.show(getContext(),null,"Loading...");
		dlg.show();
		newsRef.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3);
		newsRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getData(mType);
			}
		});

		newsRecycleview.setLayoutManager(new LinearLayoutManager(getContext()));
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

		newsRecycleview.setAdapter(adaptor);

		getData(mType);

		return v;

	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
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
				if(newsRef != null)
					newsRef.setRefreshing(false);
			}

			@Override
			public void onFailure(Call<NewsBean> call, Throwable t) {

			}
		});
	}

	private void getData_yule() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(GetNewsService.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetNewsService getNews = retrofit.create(GetNewsService.class);
		Call<NewsBean> call = getNews.getYule(GetNewsService.APP_KEY);

		call.enqueue(new Callback<NewsBean>() {
			@Override
			public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
				NewsBean body = response.body();
				NewsBean.ResultBean resultBean = body.getResult();
				adaptor.setData(resultBean.getData());
				adaptor.notifyDataSetChanged();

			}

			@Override
			public void onFailure(Call<NewsBean> call, Throwable t) {

			}
		});
	}

	private void getData_tiyu() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(GetNewsService.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetNewsService getNews = retrofit.create(GetNewsService.class);
		Call<NewsBean> call = getNews.getTiyu(GetNewsService.APP_KEY);

		call.enqueue(new Callback<NewsBean>() {
			@Override
			public void onResponse(Call<NewsBean> call, Response<NewsBean> response) {
				NewsBean body = response.body();
				NewsBean.ResultBean resultBean = body.getResult();
				adaptor.setData(resultBean.getData());
				adaptor.notifyDataSetChanged();

			}

			@Override
			public void onFailure(Call<NewsBean> call, Throwable t) {

			}
		});
	}


	@OnClick({R.id.top, R.id.yl, R.id.ty, R.id.cj, R.id.gj})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.top:
				mType = "top";
				getData("top");
				break;
			case R.id.yl:
				mType = "yule";
//				getData_yule();
				getData("yule");
				break;
			case R.id.ty:
				mType = "tiyu";
//				getData_tiyu();
				getData("tiyu");
				break;
			case R.id.cj:
				mType = "caijing";
				getData("caijing");
				break;
			case R.id.gj:
				mType = "guoji";
				getData("guoji");
				break;
		}
		fbmenu.collapse();
	}
}
