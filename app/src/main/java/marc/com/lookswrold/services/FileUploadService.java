package marc.com.lookswrold.services;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Broderick on 16/9/22.
 */
public interface FileUploadService {
	@Multipart
	@POST("upload")
	Call<ResponseBody> upload(@Part("description") RequestBody description,
	                          @Part MultipartBody.Part file);
}
