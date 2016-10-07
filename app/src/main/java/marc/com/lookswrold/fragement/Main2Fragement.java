package marc.com.lookswrold.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.datepicker.SublimeDatePicker;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import marc.com.lookswrold.R;
import marc.com.lookswrold.bean.DateBean;
import marc.com.lookswrold.face.GetDateService;
import retrofit2.Call;
import retrofit2.Callback;
//import retrofit.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Broderick on 16/9/19.
 */
public class Main2Fragement extends Fragment {

	@Bind(R.id.yangli1)
	TextView yangli1;
	@Bind(R.id.yinli1)
	TextView yinli1;
	@Bind(R.id.wuxing1)
	TextView wuxing1;
	@Bind(R.id.chongsha1)
	TextView chongsha1;
	@Bind(R.id.jishen1)
	TextView jishen1;
	@Bind(R.id.xs1)
	TextView xs1;
	@Bind(R.id.yi1)
	TextView yi1;
	@Bind(R.id.ji1)
	TextView ji1;
	@Bind(R.id.pengzubaiji1)
	TextView pengzubaiji1;

	SublimePickerFragment.Callback mFragmentCallback;


	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_frge_layout, container, false);
		ButterKnife.bind(this, view);
		getData();
		initView();
		return view;
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}
	private void initView(){
		final SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd ");
		Date curDate = new Date(System.currentTimeMillis());
		mFragmentCallback = new SublimePickerFragment.Callback() {
			@Override
			public void onCancelled() {
//                rlDateTimeRecurrenceInfo.setVisibility(View.GONE);
			}

			@Override
			public void onDateTimeRecurrenceSet(SelectedDate mSelectedDate,
			                                    int hourOfDay, int minute,
			                                    SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
			                                    String recurrenceRule) {

				if (mSelectedDate.getType() == SelectedDate.Type.RANGE) {
					String str_start = formatter.format(mSelectedDate.getStartDate().getTime());
					String str_end = formatter.format(mSelectedDate.getEndDate().getTime());

				}else{
					String str_time = formatter.format(mSelectedDate.getEndDate().getTime());
					getData2(str_time);
				}
			}
		};
	}

	private void getData2(String time){
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(DateBean.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		GetDateService getDateService = retrofit.create(GetDateService.class);

		Call<DateBean> call = getDateService.getLHL(time, DateBean.APP_KEY);

		call.enqueue(new Callback<DateBean>() {
			@Override
			public void onResponse(Call<DateBean> call, Response<DateBean> response) {
				DateBean bean = response.body();
				DateBean.ResultBean resultBean = bean.getResult();
				if (resultBean != null) {
					yangli1.setText(resultBean.getYangli());
					yinli1.setText(resultBean.getYinli());
					wuxing1.setText(resultBean.getWuxing());
					chongsha1.setText(resultBean.getChongsha());
					jishen1.setText(resultBean.getJishen());
					xs1.setText(resultBean.getXiongshen());
					yi1.setText(resultBean.getYi());
					ji1.setText(resultBean.getJi());

					String a = resultBean.getBaiji();
					String[] ss = a.split(" ");
					if (ss.length == 2)
						pengzubaiji1.setText(ss[0] + "\n" + ss[1]);
					else
						pengzubaiji1.setText(a);
				}
			}

			@Override
			public void onFailure(Call<DateBean> call, Throwable t) {

			}
		});
	}

	public void getData() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(DateBean.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		GetDateService getDateService = retrofit.create(GetDateService.class);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());

		Call<DateBean> call = getDateService.getLHL(date, DateBean.APP_KEY);

		call.enqueue(new Callback<DateBean>() {
			@Override
			public void onResponse(Call<DateBean> call, Response<DateBean> response) {
				DateBean bean = response.body();
				DateBean.ResultBean resultBean = bean.getResult();
				if (resultBean != null) {
					yangli1.setText(resultBean.getYangli());
					yinli1.setText(resultBean.getYinli());
					wuxing1.setText(resultBean.getWuxing());
					chongsha1.setText(resultBean.getChongsha());
					jishen1.setText(resultBean.getJishen());
					xs1.setText(resultBean.getXiongshen());
					yi1.setText(resultBean.getYi());
					ji1.setText(resultBean.getJi());

					String a = resultBean.getBaiji();
					String[] ss = a.split(" ");
					if (ss.length == 2)
						pengzubaiji1.setText(ss[0] + "\n" + ss[1]);
					else
						pengzubaiji1.setText(a);
				}

			}

			@Override
			public void onFailure(Call<DateBean> call, Throwable t) {

			}
		});
	}

	@OnClick(R.id.yangli1)
	public void onClick() {
		showFragement();
	}
	private void showFragement(){
		SublimePickerFragment pickerFrag = new SublimePickerFragment();
		pickerFrag.setCallback(mFragmentCallback);

		// Options
		Pair<Boolean, SublimeOptions> optionsPair = getOptions();

		if (!optionsPair.first) { // If options are not valid
			Toast.makeText(getContext(), "No pickers activated",
					Toast.LENGTH_SHORT).show();
			return;
		}

		// Valid options
		Bundle bundle = new Bundle();
		bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
		pickerFrag.setArguments(bundle);

		pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		FragmentManager manager = getFragmentManager();
		pickerFrag.show(manager, "SUBLIME_PICKER");

	}
	Pair<Boolean, SublimeOptions> getOptions() {
		SublimeOptions options = new SublimeOptions();
		int displayOptions = 0;

		displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;

		options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);

		options.setDisplayOptions(displayOptions);

		// Enable/disable the date range selection feature
		options.setCanPickDateRange(true);

		// Example for setting date range:
		// Note that you can pass a date range as the initial date params
		// even if you have date-range selection disabled. In this case,
		// the user WILL be able to change date-range using the header
		// TextViews, but not using long-press.

        /*Calendar startCal = Calendar.getInstance();
        startCal.set(2016, 2, 4);
        Calendar endCal = Calendar.getInstance();
        endCal.set(2016, 2, 17);

        options.setDateParams(startCal, endCal);*/

		// If 'displayOptions' is zero, the chosen options are not valid
		return new Pair<>(displayOptions != 0 ? Boolean.TRUE : Boolean.FALSE, options);
	}
}
