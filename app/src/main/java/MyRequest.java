import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import java.util.ArrayList;

/**
 * Created by Styve on 07/01/2015.
 */

public class MyRequest {
    HttpPost        post;
    HttpGet         get;
    HttpResponse    response;
    HttpClient      httpclient;
    Boolean         isInit;

    private void InitHttpClient() {
        this.httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
        this.isInit = true;
    }

    public MyRequest() {
        this.isInit = false;
    }

    public HttpPost createPost(String function, ArrayList<NameValuePair> data) {
        if (this.isInit)
            this.InitHttpClient();
        this.post = new HttpPost("http://epitech-api.herokuapp.com/login");
        this.post.setHeader("User-Agent", System.getProperty("http.agent"));
        try {
            this.post.setEntity(new UrlEncodedFormEntity(data));
            response = httpclient.execute(this.post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StatusLine statusLine = response.getStatusLine();
        return (this.post);
    }
}