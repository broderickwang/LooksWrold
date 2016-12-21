package marc.com.lookswrold.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import marc.com.lookswrold.R;
import marc.com.lookswrold.myview.Bezier2View;
import marc.com.lookswrold.myview.CheckView;

public class PieChartActivity extends AppCompatActivity {



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		ButterKnife.bind(this);
	}


}
