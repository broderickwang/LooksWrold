package marc.com.lookswrold.face;

import java.util.List;

import marc.com.lookswrold.bean.Contributor;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
