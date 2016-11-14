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

	@Bind(R.id.cv)
	CheckView cv;
	@Bind(R.id.check)
	Button check;
	@Bind(R.id.uncheck)
	Button uncheck;
	@Bind(R.id.bv)
	Bezier2View bv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie_chart);
		ButterKnife.bind(this);
		cv.setDuration(5000);
	}

	@OnClick({R.id.check, R.id.uncheck})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.check:
				cv.check();
				bv.setisControl1();
				break;
			case R.id.uncheck:
				cv.uncheck();
				bv.setisControl2();
				break;
		}
	}
}
