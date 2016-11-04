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

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.activity.WebActivity;
import marc.com.lookswrold.adapter.GitHubAdaptor;
import marc.com.lookswrold.bean.Contributor;
import marc.com.lookswrold.services.GitHubClient;
import retrofit2.Call;
import retrofit2.Callback;
//import retrofit.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Broderick on 16/9/12.
 */
public class GithubFragement extends Fragment {

	public static final String API_URL = "https://api.github.com";
	@Bind(R.id.recycleview)
	RecyclerView recycleview;
	List<Contributor> contirs = new ArrayList<>();
	GitHubAdaptor adpt;
	ProgressDialog dlg;
	@Bind(R.id.ref)
	SwipeRefreshLayout ref;

	boolean isRef = false;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.github_layout, container, false);
		ButterKnife.bind(this, view);
		dlg = ProgressDialog.show(getContext(), null, "Loading...");
		dlg.show();

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
		recycleview.setLayoutManager(linearLayoutManager);
		adpt = new GitHubAdaptor(getContext(), contirs);
		recycleview.setAdapter(adpt);
		adpt.setOnItemClickListner(new GitHubAdaptor.OnRecycleViewItemClickListner() {
			@Override
			public void onItemClick(View view, String data) {
				String[] ss = data.split("https");
				Intent i = new Intent(getContext(), WebActivity.class);
				i.putExtra("url", ss[1]);
				i.putExtra("type", "github");
				startActivity(i);
			}
		});
		getData();
		ref.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3);
		ref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				getData();
				isRef = true;
			}
		});
		return view;
	}

	private void refreshData(){
		getData();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	public void getData() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://api.github.com")
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GitHubClient github = retrofit.create(GitHubClient.class);
		Call<List<Contributor>> call = github.contributors("square", "retrofit");

		call.enqueue(new Callback<List<Contributor>>() {
			@Override
			public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
				adpt.setContributors(response.body());
				adpt.notifyDataSetChanged();
				dlg.dismiss();
				if(isRef)
					ref.setRefreshing(false);
			}

			@Override
			public void onFailure(Call<List<Contributor>> call, Throwable t) {

			}
		});
	}
}
