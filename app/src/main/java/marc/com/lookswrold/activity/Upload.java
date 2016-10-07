package marc.com.lookswrold.activity;

import android.net.Uri;

import java.io.File;

import marc.com.lookswrold.face.FileUploadService;
import marc.com.lookswrold.util.ServiceGenerator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import android.os.*;

/**
 * Created by Broderick on 16/9/22.
 */
public class Upload {

	private void UplaodFile(Uri fileUri){
		FileUploadService service = ServiceGenerator.createService(FileUploadService.class);
//		File file = FileUtils.getFile(this, fileUri);
		// TODO: 2016/10/7  find fileutils package
		File file = new File(fileUri.getAuthority());

		// create RequestBody instance from file
		RequestBody requestFile =
				RequestBody.create(MediaType.parse("multipart/form-data"), file);

		// MultipartBody.Part is used to send also the actual file name
		MultipartBody.Part body =
				MultipartBody.Part.createFormData("picture", file.getName(), requestFile);

		// add another part within the multipart request
		String descriptionString = "hello, this is description speaking";
		RequestBody description =
				RequestBody.create(
						MediaType.parse("multipart/form-data"), descriptionString);

		// finally, execute the request
		Call<ResponseBody> call = service.upload(description, body);
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {

			}
		});
	}
}
