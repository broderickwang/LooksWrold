package marc.com.lookswrold.services;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Broderick on 2016/10/20.
 */

public interface FileDownloadService {
	// option 1: a resource relative to your base URL
	@GET("/resource/example.zip")
	public Call<ResponseBody> downloadFileWithFixedUrl();

	// option 2: using a dynamic URL
	@GET
	public Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
