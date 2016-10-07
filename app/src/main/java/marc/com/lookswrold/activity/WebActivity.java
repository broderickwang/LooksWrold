package marc.com.lookswrold.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.DragEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.adapter.ZHWebAdaptor;
import marc.com.lookswrold.bean.ZhihuDescBean;
import marc.com.lookswrold.face.GetZhihuService;
import marc.com.lookswrold.fragement.ZhihuFragement;
import marc.com.lookswrold.util.WebUtil;
import retrofit2.Call;
import retrofit2.Callback;
//import retrofit.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebActivity extends AppCompatActivity {

	@Bind(R.id.web)
	WebView web;

	String url = "";
	String type = "";
	String id = "";
	String mBody;
	String[] scc;

	ProgressDialog dlg;
	@Bind(R.id.tt_pic)
	ImageView ttPic;
	@Bind(R.id.tt_title)
	TextView ttTitle;
	@Bind(R.id.tt_lay)
	RelativeLayout ttLay;
	@Bind(R.id.zh_web_recycle)
	RecyclerView zhWebRecycle;
	ZHWebAdaptor adaptor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		ButterKnife.bind(this);
		dlg = ProgressDialog.show(this, null, "Loading...");
		dlg.show();
		/////////////////////////////////////////////////////////////////////
		web.getSettings().setDefaultTextEncodingName("UTF-8");
		WebSettings settings = web.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		settings.setLoadWithOverviewMode(true);
//		settings.setBuiltInZoomControls(true);
//		settings.setUseWideViewPort(true);
		settings.setDomStorageEnabled(true);
		settings.setDatabaseEnabled(true);
		settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
		settings.setAppCacheEnabled(true);
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		web.setWebChromeClient(new WebChromeClient());
		/////////////////////////////////////////////////////////////////////////
		web.setOnDragListener(new View.OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				ttLay.setVisibility(View.GONE);
				return true;
			}
		});
		type = getIntent().getStringExtra("type");
		if ("github".equalsIgnoreCase(type)) {
			web.setVisibility(View.VISIBLE);
			url = "https" + getIntent().getStringExtra("url");
			web.loadUrl(url);
			dlg.dismiss();
		} else if ("zhihu".equalsIgnoreCase(type)) {
//			zhWebRecycle.setVisibility(View.VISIBLE);
			web.setVisibility(View.VISIBLE);
			zhWebRecycle.setLayoutManager(new LinearLayoutManager(WebActivity.this));
			adaptor = new ZHWebAdaptor(WebActivity.this);
			zhWebRecycle.setAdapter(adaptor);
			id = getIntent().getStringExtra("id");
			getData(id);
		} else if ("news".equalsIgnoreCase(type)) {
			web.setVisibility(View.VISIBLE);
			String url = getIntent().getStringExtra("url");
			web.loadUrl(url);
			dlg.dismiss();
		}
	}

	private void getData(String id) {
//		ttLay.setVisibility(View.VISIBLE);
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(ZhihuFragement.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		GetZhihuService github = retrofit.create(GetZhihuService.class);
		Call<ZhihuDescBean> call = github.getZhihuDescData(id);
		call.enqueue(new Callback<ZhihuDescBean>() {
			@Override
			public void onResponse(Call<ZhihuDescBean> call, Response<ZhihuDescBean> response) {
				ZhihuDescBean b_d = response.body();
				//设置移动端css样式
				mBody = b_d.getBody();
				List<String> ls = b_d.getCss();
				scc = new String[ls.size()];
				for (int i = 0; i < ls.size(); i++) {
					scc[i] = ls.get(i);
				}
				String data = WebUtil.buildHtmlWithCss(mBody, scc, false);
				web.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
				Glide.with(WebActivity.this)
						.load(response.body().getImage())
						.into(ttPic);
				ttTitle.setText(response.body().getTitle());
				/*String[] ss = {data,response.body().getImage(),response.body().getTitle()};
				adaptor.setObjs(ss);
				adaptor.notifyDataSetChanged();*/
				dlg.dismiss();
			}

			@Override
			public void onFailure(Call<ZhihuDescBean> call, Throwable t) {

			}
		});

	}
}
