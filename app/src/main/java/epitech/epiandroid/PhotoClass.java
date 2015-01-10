package epitech.epiandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Styve on 10/01/2015.
 */
public class PhotoClass extends AsyncTask<Object, Void, Boolean> {

    ImageView mImageView;
    String mUrl;
    Bitmap bitmap;


    public PhotoClass(String url, ImageView _imageView) {
        mImageView = _imageView;
        mUrl = url;
    }


    protected Boolean doInBackground(Object... params) {

        if (mUrl.equals("null")) {
            bitmap = null;
            return null;
        }
        try {
            bitmap = BitmapFactory.decodeStream((InputStream) new URL(mUrl).getContent());

        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        return true;
    }

    protected void onPostExecute(final Boolean success) {
        if (bitmap != null)
            mImageView.setImageBitmap(bitmap);

        else
            mImageView.setImageResource(android.R.color.transparent);
    }
}
