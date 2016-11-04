package marc.com.lookswrold.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;

/**
 * Created by Broderick on 2016/10/12.
 */

public class APPUtils {
	public static int isInto = 0;

	public static boolean writeResponseBodyToDisk(ResponseBody body) {
		try {
			Log.i("TAG", "writeResponseBodyToDisk: "+Environment.getExternalStorageDirectory().toString());
			// todo change the file location/name according to your needs
			File futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + File.separator
					+ "Download"+File.separator+"test.jpg");
			if(!futureStudioIconFile.exists()){
				File f = new File(Environment.getExternalStorageDirectory() + File.separator
						+ "Marc");
				f.mkdir();
				if(f.isDirectory()){

					futureStudioIconFile = new File(Environment.getExternalStorageDirectory() + File.separator
							+ "Marc"+File.separator+"test.jpg");
				}
			}

			InputStream inputStream = null;
			OutputStream outputStream = null;

			try {
				byte[] fileReader = new byte[4096];

				long fileSize = body.contentLength();
				long fileSizeDownloaded = 0;

				inputStream = body.byteStream();
				outputStream = new FileOutputStream(futureStudioIconFile);

				while (true) {
					int read = inputStream.read(fileReader);

					if (read == -1) {
						break;
					}

					outputStream.write(fileReader, 0, read);

					fileSizeDownloaded += read;

					Log.d("TAG", "file download: " + fileSizeDownloaded + " of " + fileSize);
				}

				outputStream.flush();

				return true;
			} catch (IOException e) {
				return false;
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}

				if (outputStream != null) {
					outputStream.close();
				}
			}
		} catch (IOException e) {
			return false;
		}
	}
}
