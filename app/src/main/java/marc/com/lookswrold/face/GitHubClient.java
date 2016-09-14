package marc.com.lookswrold.face;

import java.util.List;

import marc.com.lookswrold.bean.Contributor;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Broderick on 16/9/12.
 *
 */
public interface GitHubClient {
	@GET("/repos/{owner}/{repo}/contributors")
	Call<List<Contributor>> contributors(
			@Path("owner") String owner,
			@Path("repo") String repo);
}
