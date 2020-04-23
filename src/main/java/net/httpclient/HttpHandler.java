//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.httpclient;

import java.io.IOException;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpHandler {
  public HttpHandler() {
  }

  public static String sendGet(String url) {
    CloseableHttpClient httpclient = HttpClients.createDefault();

    String var6;
    try {
      HttpGet httpget = new HttpGet(url);
      CloseableHttpResponse response = null;

      try {
        response = httpclient.execute(httpget);
      } catch (IOException var29) {
        var29.printStackTrace();
      }

      try {
        HttpEntity entity = response.getEntity();
        if (entity == null) {
          return null;
        }

        var6 = EntityUtils.toString(entity);
      } finally {
        response.close();
      }
    } catch (ClientProtocolException var31) {
      var31.printStackTrace();
      return null;
    } catch (ParseException var32) {
      var32.printStackTrace();
      return null;
    } catch (IOException var33) {
      var33.printStackTrace();
      return null;
    } finally {
      try {
        httpclient.close();
      } catch (IOException var28) {
        var28.printStackTrace();
      }

    }

    return var6;
  }

  public static String sendPost(String url, List<NameValuePair> params) {
    CloseableHttpClient httpClient = HttpClients.createDefault();

    String var7;
    try {
      HttpPost httpPost = new HttpPost(url);
      httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
      CloseableHttpResponse httpResponse = null;

      try {
        httpResponse = httpClient.execute(httpPost);
      } catch (IOException var24) {
        var24.printStackTrace();
      }

      try {
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null) {
          return null;
        }

        var7 = EntityUtils.toString(httpEntity);
      } finally {
        httpResponse.close();
      }
    } catch (IOException var26) {
      var26.printStackTrace();
      return null;
    } finally {
      try {
        httpClient.close();
      } catch (IOException var23) {
        var23.printStackTrace();
      }

    }

    return var7;
  }
}
