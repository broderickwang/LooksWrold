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
import marc.com.lookswrold.bean.CommBean;

/**
 * Created by Broderick on 16/9/14.
 */
public class ZHCommAdaptor extends RecyclerView.Adapter<ZHCommAdaptor.ZHCommViewHold> {
	Context context;
	List<CommBean.CommentsBean> beans;

	public ZHCommAdaptor(Context context) {
		this.context = context;
	}

	@Override
	public int getItemCount() {
		return this.beans==null?0:beans.size();
	}
	public void setData(List<CommBean.CommentsBean> beans){
		this.beans = beans;
	}

	@Override
	public ZHCommViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.zh_comm_adpt_layout,parent,false);
		ZHCommViewHold hold = new ZHCommViewHold(view);
		return hold;
	}

	@Override
	public void onBindViewHolder(ZHCommViewHold holder, int position) {
		CommBean.CommentsBean bean = beans.get(position);
		Glide.with(context)
				.load(bean.getAvatar())
				.into(holder.userphoto);
		holder.author.setText(bean.getAuthor());
		holder.content.setText(bean.getContent());
	}

	static class ZHCommViewHold extends RecyclerView.ViewHolder{
		ImageView userphoto;
		TextView author;
		TextView content;
		public ZHCommViewHold(View itemView) {
			super(itemView);
			userphoto = (ImageView)itemView.findViewById(R.id.user_photo);
			author = (TextView)itemView.findViewById(R.id.user_name);
			content = (TextView)itemView.findViewById(R.id.user_comm);
		}
	}
}
