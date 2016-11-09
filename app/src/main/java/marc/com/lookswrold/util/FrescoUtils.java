package marc.com.lookswrold.util;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Broderick on 2016/11/9.
 */

public class FrescoUtils {
	/**
	 * 普通加载图片
	 * @param view
	 * @param uri
	 */
	public static void loadImage(SimpleDraweeView view , String uri){
		view.setImageURI(uri);
	}

	/**
	 * 渐变的方式加载图片
	 * @param view
	 * @param uristr
	 */
	public static void loadImageViaDraweeController(SimpleDraweeView view , String uristr){
		Uri uri = Uri.parse(uristr);
		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
				.setProgressiveRenderingEnabled(true)
				.build();
		DraweeController controller = Fresco.newDraweeControllerBuilder()
				.setImageRequest(request)
				.setOldController(view.getController())
				.build();
		view.setController(controller);
	}
}
