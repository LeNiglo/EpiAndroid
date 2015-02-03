package epitech.epiandroid;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Styve on 07/01/2015.
 */

public class MyRequest {
    public static HttpPost      post;
    public static HttpGet       get;
    public static HttpResponse  response;
    public static HttpClient    httpclient;
    public static String        baseUrl;

    public static int           statusCode;
    private static MyRequest    ourInstance = new MyRequest();
    public static MyRequest     getInstance() {
        return ourInstance;
    }
    public static ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

    private static void InitHttpClient() {
        try {
            MyRequest.httpclient = new DefaultHttpClient();
            httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyRequest.baseUrl = "http://epitech-api.herokuapp.com/";
    }

    private MyRequest() {
        MyRequest.InitHttpClient();
    }

    public static HttpResponse CreateGet(String function) {
        String myFinalString = "";
        for (int i = 0; i < MyRequest.nameValuePairs.size(); ++i) {
            myFinalString += MyRequest.nameValuePairs.get(i).getName() + "=" + MyRequest.nameValuePairs.get(i).getValue() + "&";
        }
        MyRequest.get = new HttpGet(MyRequest.baseUrl + function + "?" + myFinalString);
        try {
            MyRequest.response = httpclient.execute(get);
            MyRequest.clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (MyRequest.response);
    }

    public static HttpResponse CreatePost(String function) {
        MyRequest.post = new HttpPost(MyRequest.baseUrl + function);
        MyRequest.post.setHeader("User-Agent", System.getProperty("http.agent"));
        try {
            MyRequest.post.setEntity(new UrlEncodedFormEntity(MyRequest.nameValuePairs));
            MyRequest.response = httpclient.execute(MyRequest.post);
            MyRequest.clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MyRequest.statusCode = MyRequest.response.getStatusLine().getStatusCode();
        return (MyRequest.response);
    }

    public static String getResponseString() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            MyRequest.response.getEntity().writeTo(out);
            out.close();
            try {
                MyRequest.response.getEntity().getContent().close();
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (out.toString());
    }

    public static String getReasonPhrase() {
        return (MyRequest.response.getStatusLine().getReasonPhrase());
    }

    public static void addField(String field, String data) {
        MyRequest.nameValuePairs.add(new BasicNameValuePair(field, data));
    }

    public static void clearFields() {
        MyRequest.nameValuePairs.clear();
    }

    public static Boolean isStatusOk() {
        return (MyRequest.statusCode == HttpStatus.SC_OK);
    }

    public static Boolean isStatusUnauthorized() {
        return (MyRequest.statusCode == HttpStatus.SC_UNAUTHORIZED);
    }

    public static Boolean isStatusForbidden() {
        return (MyRequest.statusCode == HttpStatus.SC_FORBIDDEN);
    }

    public static Boolean isStatusTimeout() {
        return (MyRequest.statusCode  == HttpStatus.SC_GATEWAY_TIMEOUT || MyRequest.statusCode == HttpStatus.SC_REQUEST_TIMEOUT);
    }
}