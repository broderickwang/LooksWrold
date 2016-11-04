package marc.com.lookswrold.services;

import java.util.List;
import marc.com.lookswrold.bean.CommBean;
import marc.com.lookswrold.bean.CommShortBean;
import marc.com.lookswrold.bean.Contributor;
import marc.com.lookswrold.bean.StartUser;
import marc.com.lookswrold.bean.ZhihuDescBean;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Broderick on 16/9/12.
 */
public interface GetZhihuService {
	@GET("/api/4/start-image/{size}")
	Call<StartUser> getSplash(@Path("size")String size);

	@GET("/repos/{owner}/{repo}/contributors")
	Call<List<Contributor>> contributors(
			@Path("owner") String owner,
			@Path("repo") String repo);
	@GET("/api/4/news/{time}")
	Call<ResponseBody> getZhihuLastData(@Path("time")String time);

	@GET("/api/4/news/{time}")
	Call<ZhihuDescBean> getZhihuDescData(@Path("time")String time);

	@GET("/api/4/story/{id}/long-comments")
	Call<CommBean> getLongComment(@Path("id")String id);

	@GET("/api/4/story/{id}/short-comments")
	Call<CommShortBean> getShortComment(@Path("id")String id);
}
