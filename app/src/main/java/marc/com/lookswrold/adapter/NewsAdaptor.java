package marc.com.lookswrold.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.NewsBean;

/**
 * Created by Broderick on 16/9/18.
 */
public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.NewsAdptViewhold>
					implements View.OnClickListener{
	Context context;
	List<NewsBean.ResultBean.DataBean> list;
	public interface ItemClick{
		void OnItemClick(View view,String data);
	}

	ItemClick click;

	public NewsAdaptor(Context context) {
		this.context = context;
	}

	public void setData(List<NewsBean.ResultBean.DataBean> list) {
		this.list = list;
	}

	@Override
	public void onClick(View v) {
		if(click != null){
			TextView tv = (TextView)v.findViewById(R.id.news_title);
			click.OnItemClick(tv,tv.getTag().toString());
		}
	}

	public void setClick(ItemClick click) {
		this.click = click;
	}

	@Override
	public int getItemCount() {
		return list==null?0:list.size();
	}

	@Override
	public NewsAdptViewhold onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(context).inflate(R.layout.news_adpt_layout,parent,false);
		NewsAdptViewhold holder = new NewsAdptViewhold(v);
		v.setOnClickListener(this);
		return holder;
	}

	@Override
	public void onBindViewHolder(NewsAdptViewhold holder, int position) {
		NewsBean.ResultBean.DataBean bean = list.get(position);
		holder.title.setText(bean.getTitle());
		holder.title.setTag(bean.getUrl());
		holder.info.setText(bean.getDate() +"   "+bean.getAuthor_name());
		Glide.with(context)
				.load(bean.getThumbnail_pic_s())
				.into(holder.ig);
	}

	static class NewsAdptViewhold extends RecyclerView.ViewHolder{
		TextView title;
		TextView info;
		ImageView ig;
		public NewsAdptViewhold(View itemView) {
			super(itemView);

			title = (TextView)itemView.findViewById(R.id.news_title);
			info = (TextView)itemView.findViewById(R.id.news_info);
			ig = (ImageView)itemView.findViewById(R.id.new_ig);
		}
	}
}
