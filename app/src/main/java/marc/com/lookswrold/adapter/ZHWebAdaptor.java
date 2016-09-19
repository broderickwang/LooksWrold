package marc.com.lookswrold.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import marc.com.lookswrold.R;
import marc.com.lookswrold.util.WebUtil;

/**
 * Created by Broderick on 16/9/19.
 */
public class ZHWebAdaptor extends RecyclerView.Adapter<ZHWebAdaptor.OBJ>{
	Context context;

	String[] objs;

	public ZHWebAdaptor(Context context) {
		this.context = context;
	}

	public void setObjs(String[] objs) {
		this.objs = objs;
	}

	@Override
	public int getItemCount() {
		return objs == null?0:objs.length;
	}

	@Override
	public OBJ onCreateViewHolder(ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(context).inflate(R.layout.zhwebadpt_layout,parent,false);
		OBJ holder = new OBJ(view);
		return holder;
	}

	@Override
	public void onBindViewHolder(OBJ holder, int position) {
		if(objs != null) {
			switch (position){
				case 0:
					ZHWebViewHolder_Web web = (ZHWebViewHolder_Web)holder;
					web.webView.loadDataWithBaseURL(WebUtil.BASE_URL, objs[0], WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
					break;
				case 1:
					ZHWebViewHolder_Image image = (ZHWebViewHolder_Image)holder;
					Glide.with(context)
							.load(objs[1])
							.into(image.webImage);
					break;
				case 2:
					ZHWebViewHolder_Title title = (ZHWebViewHolder_Title)holder;
					title.webTitle.setText(objs[2]);
					break;
			}
		}
	}

	static class OBJ extends RecyclerView.ViewHolder{
		public OBJ(View itemView) {
			super(itemView);
		}
	}

	static class ZHWebViewHolder_Image extends OBJ{
		ImageView webImage;

		public ZHWebViewHolder_Image(View itemView) {
			super(itemView);

			webImage = (ImageView)itemView.findViewById(R.id.zhweb_img);
		}
	}

	static class ZHWebViewHolder_Title extends  OBJ{
		TextView webTitle;
		public ZHWebViewHolder_Title(View itemView) {
			super(itemView);
			webTitle = (TextView)itemView.findViewById(R.id.zhweb_title);
		}
	}
	static class ZHWebViewHolder_Web extends OBJ{
		WebView webView;
		public ZHWebViewHolder_Web(View itemView) {
			super(itemView);
			webView = (WebView)itemView.findViewById(R.id.zhweb_webview);
		}
	}
}
