package marc.com.lookswrold.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.ZhihuDescBean;
import marc.com.lookswrold.fragement.ZhihuFragement;
import marc.com.lookswrold.interfaces.ScrollInterface;
import marc.com.lookswrold.myclass.MarcWebView;
import marc.com.lookswrold.services.GetZhihuService;
import marc.com.lookswrold.util.FrescoUtils;
import marc.com.lookswrold.util.WebUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class WebRecyActivity extends AppCompatActivity {

	@Bind(R.id.my_image_draweeview)
	SimpleDraweeView myImageDraweeview;
	@Bind(R.id.col_title)
	TextView colTitle;
	private CollapsingToolbarLayoutState state;

	private enum CollapsingToolbarLayoutState {
		EXPANDED,
		COLLAPSED,
		INTERNEDIATE
	}

	@Bind(R.id.toolbar)
	Toolbar toolbar;
	@Bind(R.id.collapsing_toolbar_layout)
	CollapsingToolbarLayout collapsingToolbarLayout;
	@Bind(R.id.recyclerView)
	RecyclerView recyclerView;

	String type, url, id;
	String mBody;
	String[] scc;
	@Bind(R.id.recyweb_img)
	ImageView recywebImg;
	@Bind(R.id.appbar)
	AppBarLayout appbar;

	ProgressDialog dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_recy);
		ButterKnife.bind(this);

		//透明状态栏
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		dlg = ProgressDialog.show(this, null, "Loading...");
		dlg.show();

		initToolbar();
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new MyAdapter());

		getDataFromLastContext();
	}

	private void getDataFromLastContext() {
		type = getIntent().getStringExtra("type");
		if ("github".equalsIgnoreCase(type)) {
			url = "https" + getIntent().getStringExtra("url");
		} else if ("zhihu".equalsIgnoreCase(type)) {
			id = getIntent().getStringExtra("id");
//			getData(id);
		} else if ("news".equalsIgnoreCase(type)) {
			url = getIntent().getStringExtra("url");
		}
	}

	private void getData(final WebView web, String id) {
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

				collapsingToolbarLayout.setTitle(response.body().getTitle());
				colTitle.setText(response.body().getTitle());

				Glide.with(WebRecyActivity.this)
						.load(response.body().getImage())
						.into(recywebImg);
				FrescoUtils.loadImageViaDraweeController(myImageDraweeview, response.body().getImage());

				//rxjava demo -added by marc
				Observable.create(new Observable.OnSubscribe<Object>() {
					@Override
					public void call(Subscriber<? super Object> subscriber) {

					}
				})
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Action1<Object>() {
							@Override
							public void call(Object o) {

							}
						});
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
				dlg.dismiss();
			}

			@Override
			public void onFailure(Call<ZhihuDescBean> call, Throwable t) {
			}
		});

	}

	private void initToolbar() {
		setSupportActionBar(toolbar);
		/*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.left3));*/
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		//使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
		//通过CollapsingToolbarLayout修改字体颜色
		collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
		collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色

		// TODO: 2016/11/7  do something later!!!
		appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
			@Override
			public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
				if (verticalOffset == 0) {
					if (state != CollapsingToolbarLayoutState.EXPANDED) {
						state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
						getSupportActionBar().setDisplayHomeAsUpEnabled(false);
					}
				} else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
					if (state != CollapsingToolbarLayoutState.COLLAPSED) {
//						playButton.setVisibility(View.VISIBLE);//隐藏播放按钮
						state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
						getSupportActionBar().setDisplayHomeAsUpEnabled(true);
						toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.left3));
					}
				} else {
					if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
						if (state == CollapsingToolbarLayoutState.COLLAPSED) {
//							playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
						}
						state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
						getSupportActionBar().setDisplayHomeAsUpEnabled(false);
					}
				}
			}
		});
	}

	class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

		public MyAdapter() {

		}

		@Override
		public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			MyHolder myHolder = new MyHolder(
					LayoutInflater.from(WebRecyActivity.this).inflate(R.layout.webrecy_myadaptor, parent, false));
			initWebCSS(myHolder.webView);
			return myHolder;
		}

		@Override
		public void onBindViewHolder(MyHolder holder, int position) {
			getData(holder.webView, id);
		}

		@Override
		public int getItemCount() {
			return 1;
		}

		class MyHolder extends RecyclerView.ViewHolder {
			MarcWebView webView;

			public MyHolder(View itemView) {
				super(itemView);
				webView = (MarcWebView) itemView.findViewById(R.id.myadpt_webview);
			}
		}
	}

	private void initWebCSS(final MarcWebView web) {
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
				return true;
			}
		});
		web.setOnCustomScroolChangeListener(new ScrollInterface() {
			@Override
			public void onSChanged(int l, int t, int oldl, int oldt) {
				// TODO Auto-generated method stub
				//WebView的总高度
				float webViewContentHeight = web.getContentHeight() * web.getScale();
				//WebView的现高度
				float webViewCurrentHeight = (web.getHeight() + web.getScrollY());
				System.out.println("webViewContentHeight=" + webViewContentHeight);
				System.out.println("webViewCurrentHeight=" + webViewCurrentHeight);
				if ((webViewContentHeight - webViewCurrentHeight) == 0) {
					Toast.makeText(WebRecyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
