package marc.com.lookswrold.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.Contributor;

/**
 * Created by Broderick on 16/9/12.
 */
public class GitHubAdaptor extends RecyclerView.Adapter<GitHubAdaptor.GHItemViewHolder>
		implements View.OnClickListener{

	@Override
	public void onClick(View v) {
		if(mOnClicke!=null){
			TextView t = (TextView)v.findViewById(R.id.description);
			mOnClicke.onItemClick(v,t.getText().toString());
		}
	}

	public static interface OnRecycleViewItemClickListner{
		void onItemClick(View view,String data);
	}
	private List<Contributor> contributors;
	private Context context;
	private OnRecycleViewItemClickListner mOnClicke = null;

	public GitHubAdaptor(Context context, List<Contributor> contributors) {
		this.context = context;
		this.contributors = contributors;
	}
	public void setOnItemClickListner(OnRecycleViewItemClickListner listner){
		this.mOnClicke = listner;
	}

	public void setContributors(List<Contributor> contributors) {
		this.contributors = contributors;
	}

	@Override
	public GHItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(context).inflate(R.layout.gh_adpt_layout,parent,false);
		GHItemViewHolder holder = new GHItemViewHolder(view);

		view.setOnClickListener(this);
		return holder;
	}

	@Override
	public void onBindViewHolder(GHItemViewHolder holder, int position) {
		Contributor c = contributors.get(position);
		Glide.with(context)
				.load(c.getAvatar_url())
				.into(holder.image);
		holder.name.setText("Name:"+c.getLogin());
		holder.description.setText("Contributes:"+c.getContributions()+"\nHomePage:"+c.getHtml_url());
	}


	@Override
	public int getItemCount() {
		if(contributors!=null)
			return contributors.size();
		else
			return 0;
	}

	class GHItemViewHolder extends RecyclerView.ViewHolder{
		ImageView image;
		TextView name;
		TextView description;

		public GHItemViewHolder(View itemView) {
			super(itemView);
			image = (ImageView)itemView.findViewById(R.id.img);
			name = (TextView)itemView.findViewById(R.id.name);
			description = (TextView)itemView.findViewById(R.id.description);
		}
	}
}
