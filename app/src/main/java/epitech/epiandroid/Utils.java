package epitech.epiandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Styve on 13/01/2015.
 */
public class Utils {
    public static File getCacheFolder(Context context) {
        File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");
            if(!cacheDir.isDirectory()) {
                cacheDir.mkdirs();
            }
        }
        if(cacheDir != null && !cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir(); //get system cache folder
        }
        return cacheDir;
    }

    public static boolean writeToCacheImg(Context ctx, String filename, String url) {
        try {
            URL wallpaperURL = new URL(url);
            URLConnection connection = wallpaperURL.openConnection();
            InputStream inputStream = new BufferedInputStream(wallpaperURL.openStream(), 10240);
            File cacheDir = getCacheFolder(ctx);
            File cacheFile = new File(cacheDir, filename);
            FileOutputStream outputStream = new FileOutputStream(cacheFile);

            byte buffer[] = new byte[1024];
            int dataSize;
            while ((dataSize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, dataSize);
            }

            outputStream.close();
        } catch (Exception e) {
            Toast.makeText(ctx, "Shit happened... write", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static void getImgFromCache(Context ctx, String filename, ImageView image) {
        try {
            File cacheDir = getCacheFolder(ctx);
            File cacheFile = new File(cacheDir, filename);
            InputStream fileInputStream = new FileInputStream(cacheFile);
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            //bitmapOptions.inSampleSize = scale;
            bitmapOptions.inJustDecodeBounds = false;
            Bitmap wallpaperBitmap = BitmapFactory.decodeStream(fileInputStream, null, bitmapOptions);
            image.setImageBitmap(wallpaperBitmap);
        } catch (Exception e) {
            //Toast.makeText(ctx, "Shit happened : get", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
