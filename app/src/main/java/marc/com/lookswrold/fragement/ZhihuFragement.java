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

import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.activity.CommActivity;
import marc.com.lookswrold.activity.WebActivity;
import marc.com.lookswrold.adapter.ZhihuAdaptor;
import marc.com.lookswrold.bean.ZhihuBean;
import marc.com.lookswrold.face.GetZhihuService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Broderick on 16/9/13.
 */
public class ZhihuFragement extends Fragment {
	public static String BASE_URL = "http://news-at.zhihu.com";
	@Bind(R.id.zh_rcyview)
	RecyclerView zhRcyview;
	ZhihuAdaptor adaptor;
	ProgressDialog dlg;
	@Bind(R.id.zh_ref)
	SwipeRefreshLayout zhRef;
	boolean isRef = false;
	Retrofit retrofit;
	GetZhihuService github;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.zhihu_layout, container, false);

		ButterKnife.bind(this, view);
		initD();
		dlg.show();
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		zhRcyview.setLayoutManager(linearLayoutManager);
		adaptor = new ZhihuAdaptor(getContext());
		adaptor.setClick(new ZhihuAdaptor.OnRecycleViewItemClick() {
			@Override
			public void onItemClick(View v, String data) {
				Intent i = new Intent(getContext(), WebActivity.class);
				i.putExtra("id", data);
				i.putExtra("type", "zhihu");
				startActivity(i);
			}
		});
		adaptor.setLongPress(new ZhihuAdaptor.OnRecycleViewItemLongPress() {
			@Override
			public void onItemLongPress(View v, String data) {
				Intent i = new Intent(getContext(), CommActivity.class);
				i.putExtra("id", data);
				startActivity(i);
			}
		});
		zhRcyview.setAdapter(adaptor);
		getData();
		zhRef.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3);
		zhRef.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getData();
				isRef = true;
			}
		});
		return view;
	}

	private void initD(){

		dlg = ProgressDialog.show(getContext(), null, "Loading...");

		retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		github = retrofit.create(GetZhihuService.class);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	private void getComm(){

	}

	private void getData() {
		Call<ResponseBody> call = github.getZhihuLastData("latest");

		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
				try {
					String a = response.body().string();
					JSONObject object = new JSONObject(a);
					JSONArray array = object.getJSONArray("stories");
					List<ZhihuBean.StoriesBean> lsb = new ArrayList<ZhihuBean.StoriesBean>();
					for (int i = 0; i < array.length(); i++) {
						JSONObject o = array.getJSONObject(i);
						JSONArray igs_arr = o.getJSONArray("images");
						List<String> ls = new ArrayList<String>();
						for (int j = 0; j < igs_arr.length(); j++) {
							ls.add(igs_arr.get(j).toString());
						}

						ZhihuBean.StoriesBean sb = new ZhihuBean.StoriesBean();
						sb.setId(o.getInt("id"));
						sb.setGa_prefix(o.getString("ga_prefix"));
						sb.setTitle(o.getString("title"));
						sb.setType(o.getInt("type"));
						sb.setImages(ls);
						lsb.add(sb);
					}
					adaptor.setBeans(lsb);
					adaptor.notifyDataSetChanged();
					dlg.dismiss();
					if(isRef)
						zhRef.setRefreshing(false);
				} catch (Exception e) {
					e.printStackTrace();
					dlg.dismiss();
				}
			}

			@Override
			public void onFailure(Throwable t) {

			}
		});
	}
}
