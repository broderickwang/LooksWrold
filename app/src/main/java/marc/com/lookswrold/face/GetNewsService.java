package marc.com.lookswrold.face;

import marc.com.lookswrold.bean.NewsBean;
import marc.com.lookswrold.bean.StartUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Broderick on 16/9/18.
 */
public interface GetNewsService {

	String BASE_URL = "http://v.juhe.cn";
	String APP_KEY = "3f20345966c9d8b26b8db2de26b3a5d8";

	//http://v.juhe.cn/toutiao/index?type={type}&key=3f20345966c9d8b26b8db2de26b3a5d8
	@GET("/toutiao/index?")
	Call<NewsBean> getNews(@Query("type")String type, @Query("key")String key);

	@GET("/toutiao/index?type=yule")
	Call<NewsBean> getYule(@Query("key")String key);

	@GET("/toutiao/index?type=tiyu")
	Call<NewsBean> getTiyu(@Query("key")String key);
}
