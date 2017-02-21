package marc.com.lookswrold.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.ItemBean;

/**
 * Created by Broderick on 2017/2/21.
 */

public class ShijianzhouAdapter extends RecyclerView.Adapter<ShijianzhouAdapter.MyViewHolder> {
	List<ItemBean> list;
	Context mContext;
	Drawable d , d2;
	Bitmap b1,b2;

	public ShijianzhouAdapter(List<ItemBean> list, Context mContext) {
		this.list = list;
		this.mContext = mContext;
		d = mContext.getResources().getDrawable(R.drawable.point2);
		d2 = mContext.getResources().getDrawable(R.drawable.point1);
		b1 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point1);
		b2 = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.point2);
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		//纵向时间轴
//		View v = inflater.inflate(R.layout.shijianzhou_item_view,parent,false);
		//横向时间轴
		View v = inflater.inflate(R.layout.shijianzhou2_item_view,parent,false);
		MyViewHolder viewHolder = new MyViewHolder(v);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		holder.title.setText(list.get(position).getTitle());
		holder.time.setText(list.get(position).getTime());

		if(position == list.size()-1){
			holder.line.setVisibility(View.GONE);
		}
		if(!list.get(position).isState()){
//			holder.img.setBackground(d);
			holder.img.setImageBitmap(b2);
		}else{
			holder.img.setImageBitmap(b1);
		}
	}

	@Override
	public int getItemCount() {
		return list==null?0:list.size();
	}

	class MyViewHolder extends RecyclerView.ViewHolder{
		TextView title,time;
		View line;
		ImageView img;
		public MyViewHolder(View itemView) {
			super(itemView);
			title = (TextView)itemView.findViewById(R.id.show_title);
			time = (TextView)itemView.findViewById(R.id.show_time);
			line = itemView.findViewById(R.id.line_normal);
			img = (ImageView)itemView.findViewById(R.id.show_image);
		}
	}
}
