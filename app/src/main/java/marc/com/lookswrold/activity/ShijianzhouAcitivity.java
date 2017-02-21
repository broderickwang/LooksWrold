package marc.com.lookswrold.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import marc.com.lookswrold.R;
import marc.com.lookswrold.adapter.ShijianzhouAdapter;
import marc.com.lookswrold.bean.ItemBean;

public class ShijianzhouAcitivity extends AppCompatActivity {

	@Bind(R.id.shijianzhou)
	RecyclerView shijianzhou;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shijianzhou_acitivity);
		ButterKnife.bind(this);

		List<ItemBean> datas = new ArrayList<>();
		for (int i=0;i<7;i++){
			if(i<5)
				datas.add(new ItemBean(true,"time"+i,"a"+1));
			else
				datas.add(new ItemBean(false,"time"+i,"a"+1));
		}

		ShijianzhouAdapter adapter=new ShijianzhouAdapter(datas,this);
		//设置布局管理器
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
		//纵向
//		linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		//横向
		linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		shijianzhou.setLayoutManager(linearLayoutManager);
		shijianzhou.setAdapter(adapter);
	}
}
