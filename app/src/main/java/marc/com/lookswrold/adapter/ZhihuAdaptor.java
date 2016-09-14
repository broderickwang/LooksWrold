package marc.com.lookswrold.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.ZhihuBean;

/**
 * Created by Broderick on 16/9/13.
 */
public class ZhihuAdaptor extends RecyclerView.Adapter<ZhihuAdaptor.ZHViewHolder>
					implements View.OnClickListener,View.OnLongClickListener{

	public static interface OnRecycleViewItemClick{
		void onItemClick(View v,String data);
	};
	public static interface OnRecycleViewItemLongPress{
		void onItemLongPress(View v,String data);
	};

	List<ZhihuBean.StoriesBean> beans;
	Context context;

	public void setClick(OnRecycleViewItemClick click) {
		this.click = click;
	}

	OnRecycleViewItemClick click;
	OnRecycleViewItemLongPress longPress;
	public void setLongPress(OnRecycleViewItemLongPress longPress){
		this.longPress = longPress;
	}


	public ZhihuAdaptor(Context context) {
		this.context = context;
	}

	public void setBeans(List<ZhihuBean.StoriesBean> beans) {
		this.beans = beans;
	}

	@Override
	public int getItemCount() {
		return beans==null?0:beans.size();
	}

	@Override
	public ZHViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.zh_apt_layout,parent,false);
		ZHViewHolder holder = new ZHViewHolder(view);
		view.setOnClickListener(this);
		view.setOnLongClickListener(this);
		return holder;
	}

	@Override
	public void onBindViewHolder(ZHViewHolder holder, int position) {
		ZhihuBean.StoriesBean sb = beans.get(position);
		String url  = sb.getImages().get(0);
		String name = sb.getTitle();
		Glide.with(context)
				.load(url)
				.into(holder.ig);
		holder.name.setText(name);
		holder.name.setTag(sb.getId());

	}

	@Override
	public void onClick(View v) {
		if(click!=null){
			TextView t = (TextView)v.findViewById(R.id.zh_titile);
			click.onItemClick(v,t.getTag().toString());
		}
	}

	@Override
	public boolean onLongClick(View v) {
		if(longPress!=null){
			TextView t = (TextView)v.findViewById(R.id.zh_titile);
			longPress.onItemLongPress(v,t.getTag().toString());
			return true;
		}
		return false;
	}

	static class ZHViewHolder extends RecyclerView.ViewHolder{
		private ImageView ig;
		private TextView name;
		public ZHViewHolder(View itemView) {
			super(itemView);
			ig = (ImageView)itemView.findViewById(R.id.zh_tx);
			name = (TextView)itemView.findViewById(R.id.zh_titile);
		}
	}
}
