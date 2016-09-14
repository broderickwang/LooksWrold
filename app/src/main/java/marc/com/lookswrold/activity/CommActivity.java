package marc.com.lookswrold.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.adapter.ZHCommAdaptor;
import marc.com.lookswrold.bean.CommBean;
import marc.com.lookswrold.face.GetZhihuService;
import marc.com.lookswrold.fragement.ZhihuFragement;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class CommActivity extends AppCompatActivity {

	@Bind(R.id.comm_recycle_zh)
	RecyclerView commRecycleZh;
	@Bind(R.id.com_ref_zh)
	SwipeRefreshLayout comRefZh;
	String id;
	ZHCommAdaptor adaptor;
	boolean isRef = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comm);
		ButterKnife.bind(this);

		id = getIntent().getStringExtra("id");
		adaptor = new ZHCommAdaptor(this);
		commRecycleZh.setLayoutManager(new LinearLayoutManager(this));
		commRecycleZh.setAdapter(adaptor);
		comRefZh.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3);
		comRefZh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getData();
				isRef = true;
			}
		});
		getData();
	}
	private void getData(){
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(ZhihuFragement.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetZhihuService getZhihuService = retrofit.create(GetZhihuService.class);
		Call<CommBean> call = getZhihuService.getLongComment(id);
		call.enqueue(new Callback<CommBean>() {
			@Override
			public void onResponse(Response<CommBean> response, Retrofit retrofit) {
				CommBean bean = response.body();
				if(bean.getComments().size() == 0) {
					Toast.makeText(CommActivity.this, "暂无评论", Toast.LENGTH_SHORT).show();
					CommActivity.this.finish();
				}
				adaptor.setData(bean.getComments());
				adaptor.notifyDataSetChanged();
				if(isRef)
					comRefZh.setRefreshing(false);
			}

			@Override
			public void onFailure(Throwable t) {
				Log.e("TAG", "onFailure: ", t);
			}
		});
	}
}
