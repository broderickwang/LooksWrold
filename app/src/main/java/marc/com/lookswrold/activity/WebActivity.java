package marc.com.lookswrold.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;

import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.ZhihuBean;
import marc.com.lookswrold.face.GetZhihuService;
import marc.com.lookswrold.fragement.ZhihuFragement;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class WebActivity extends AppCompatActivity {

	@Bind(R.id.web)
	WebView web;

	String url = "";
	String type = "";
	String id = "";

	ProgressDialog dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		ButterKnife.bind(this);
		dlg = ProgressDialog.show(this,null,"Loading...");
		dlg.show();
		web.getSettings().setDefaultTextEncodingName("UTF-8");
		type = getIntent().getStringExtra("type");
		if("github".equalsIgnoreCase(type)) {
			url = "https" + getIntent().getStringExtra("url");
			web.loadUrl(url);
			dlg.dismiss();
		}else if("zhihu".equalsIgnoreCase(type)){
			id = getIntent().getStringExtra("id");
			getData(id);
		}
	}
	private void getData(String id){
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(ZhihuFragement.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetZhihuService github = retrofit.create(GetZhihuService.class);
		Call<ResponseBody> call = github.getZhihuLastData(id);

		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
				try {
					JSONObject obj = new JSONObject(response.body().string());
					String o = obj.getString("body");
					Log.i("TAG", "onResponse: "+o);
					web.loadData(o, "text/html;charset=UTF-8", null);
					dlg.dismiss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable t) {
				dlg.dismiss();
			}
		});
	}
}
