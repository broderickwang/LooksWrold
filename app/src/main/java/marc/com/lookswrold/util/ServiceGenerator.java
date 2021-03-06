package marc.com.lookswrold.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Broderick on 16/9/22.
 */
public class ServiceGenerator {
	public static final String API_BASE_URL = "http://192.168.9.45:8080/";

	private static okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();

	private static Retrofit.Builder builder =
			new Retrofit.Builder()
					.baseUrl(API_BASE_URL)
					.addConverterFactory(GsonConverterFactory.create());

	public static <S> S createService(Class<S> serviceClass) {
		Retrofit retrofit = builder.client(httpClient.build()).build();
		return retrofit.create(serviceClass);
	}
}
