package net.httpclient;

import java.io.IOException;
import java.io.PrintStream;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

public class HttpClientRequestUtil
  extends AbstractHttpClientUtil
{
  public static HttpResult executeGet(String url, String charset, int socketTimeout)
    throws IOException
  {
    CloseableHttpClient httpClient = createHttpClient(socketTimeout);
    return executeGet(httpClient, url, null, null, charset, true);
  }
  
  public static String executeGetString(String url, String charset, int socketTimeout)
    throws IOException
  {
    CloseableHttpClient httpClient = createHttpClient(socketTimeout);
    return executeGetString(httpClient, url, null, null, charset, true);
  }
  
  public static HttpResult executeGet(CloseableHttpClient httpClient, String url, String referer, String cookie, String charset, boolean closeHttpClient)
    throws IOException
  {
    CloseableHttpResponse httpResponse = null;
    try
    {
      charset = getCharset(charset);
      httpResponse = executeGetResponse(httpClient, url, referer, cookie);
      System.out.println(httpResponse);
      
      Integer statusCode = Integer.valueOf(httpResponse.getStatusLine().getStatusCode());
      String content = getResult(httpResponse, charset);
      return new HttpResult(statusCode, content);
    }
    finally
    {
      if (httpResponse != null) {
        try
        {
          httpResponse.close();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      if ((closeHttpClient) && (httpClient != null)) {
        try
        {
          httpClient.close();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  public static CloseableHttpResponse executeGetResponse(CloseableHttpClient httpClient, String url, String referer, String cookie)
    throws IOException
  {
    if (httpClient == null) {
      httpClient = createHttpClient();
    }
    HttpGet get = new HttpGet(url);
    if ((cookie != null) && (!"".equals(cookie))) {
      get.setHeader("Cookie", cookie);
    }
    if ((referer != null) && (!"".equals(referer))) {
      get.setHeader("referer", referer);
    }
    return httpClient.execute(get);
  }
  
  public static String executeGetString(CloseableHttpClient httpClient, String url, String referer, String cookie, String charset, boolean closeHttpClient)
    throws IOException
  {
    CloseableHttpResponse httpResponse = null;
    try
    {
      charset = getCharset(charset);
      httpResponse = executeGetResponse(httpClient, url, referer, cookie);
      return getResult(httpResponse, charset);
    }
    finally
    {
      if (httpResponse != null) {
        try
        {
          httpResponse.close();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
      if ((closeHttpClient) && (httpClient != null)) {
        try
        {
          httpClient.close();
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  public static HttpResult executePost(String url, Object paramsObj, String charset, int socketTimeout)
    throws IOException
  {
    CloseableHttpClient httpClient = createHttpClient(socketTimeout);
    return executePost(httpClient, url, paramsObj, null, null, charset, true);
  }
  
  public static String executePostString(String url, Object paramsObj, String charset, int socketTimeout)
    throws IOException
  {
    CloseableHttpClient httpClient = createHttpClient(socketTimeout);
    return executePostString(httpClient, url, paramsObj, null, null, charset, true);
  }
  
  public static HttpResult executePost(CloseableHttpClient httpClient, String url, Object paramsObj, String referer, String cookie, String charset, boolean closeHttpClient)
    throws IOException
  {
    CloseableHttpResponse httpResponse = null;
    try
    {
      charset = getCharset(charset);
      httpResponse = executePostResponse(httpClient, url, paramsObj, referer, cookie, charset);
      
      Integer statusCode = Integer.valueOf(httpResponse.getStatusLine().getStatusCode());
      String content = getResult(httpResponse, charset);
      return new HttpResult(statusCode, content);
    }
    finally
    {
      if (httpResponse != null) {
        try
        {
          httpResponse.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if ((closeHttpClient) && (httpClient != null)) {
        try
        {
          httpClient.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
  }
  
  public static String executePostString(CloseableHttpClient httpClient, String url, Object paramsObj, String referer, String cookie, String charset, boolean closeHttpClient)
    throws IOException
  {
    CloseableHttpResponse httpResponse = null;
    try
    {
      charset = getCharset(charset);
      httpResponse = executePostResponse(httpClient, url, paramsObj, referer, cookie, charset);
      return getResult(httpResponse, charset);
    }
    finally
    {
      if (httpResponse != null) {
        try
        {
          httpResponse.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
      if ((closeHttpClient) && (httpClient != null)) {
        try
        {
          httpClient.close();
        }
        catch (Exception e2)
        {
          e2.printStackTrace();
        }
      }
    }
  }
  
  private static CloseableHttpResponse executePostResponse(CloseableHttpClient httpClient, String url, Object paramsObj, String referer, String cookie, String charset)
    throws IOException
  {
    if (httpClient == null) {
      httpClient = createHttpClient();
    }
    HttpPost post = new HttpPost(url);
    if ((cookie != null) && (!"".equals(cookie))) {
      post.setHeader("Cookie", cookie);
    }
    if ((referer != null) && (!"".equals(referer))) {
      post.setHeader("referer", referer);
    }
    HttpEntity httpEntity = getEntity(paramsObj, charset);
    if (httpEntity != null) {
      post.setEntity(httpEntity);
    }
    return httpClient.execute(post);
  }
}
