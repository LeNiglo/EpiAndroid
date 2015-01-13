package epitech.epiandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Styve on 10/01/2015.
 */
public class PhotoClass extends AsyncTask<Object, Void, Boolean> {

    ImageView mImageView;
    ImageView mImageView2;
    String mUrl;
    Context ctx;


    public PhotoClass(Context _ctx, String url, ImageView _imageView, ImageView _imageView2) {
        mImageView = _imageView;
        mImageView2 = _imageView2;
        mUrl = url;
        ctx = _ctx;
    }


    protected Boolean doInBackground(Object... params) {

        if (mUrl.equals("null")) {
            return null;
        }
        Utils.writeToCacheImg(ctx, "profil.bmp", mUrl);
        return true;
    }

    protected void onPostExecute(final Boolean success) {
        Utils.getImgFromCache(ctx, "profil.bmp", mImageView);
        if (mImageView2 != null)
            Utils.getImgFromCache(ctx, "profil.bmp", mImageView2);
    }
}
