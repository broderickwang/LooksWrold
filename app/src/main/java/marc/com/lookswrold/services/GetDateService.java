package marc.com.lookswrold.services;

import marc.com.lookswrold.bean.DateBean;
import marc.com.lookswrold.bean.NewsBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Broderick on 16/9/19.
 */
public interface GetDateService {

	@GET("/toutiao/index?type=yule")
	Call<NewsBean> getYule(@Query("key") String key) ;


	@GET("http://v.juhe.cn/laohuangli/d?")
	Call<DateBean> getLHL(@Query("date")String date,@Query("key")String key);
}
