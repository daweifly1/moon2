package net.httpclient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

public class HttpClientDownloadUtil
  extends AbstractHttpClientUtil
{
  public static boolean executeDownloadFile(String remoteFileUrl, String localFilePath, boolean closeHttpClient)
    throws ClientProtocolException, IOException
  {
    return executeDownloadFile(createHttpClient(), remoteFileUrl, localFilePath, closeHttpClient);
  }

  public static boolean executeDownloadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, boolean closeHttpClient)
    throws ClientProtocolException, IOException
  {
    CloseableHttpResponse response = null;
    InputStream in = null;
    FileOutputStream fout = null;
    try
    {
      HttpGet httpget = new HttpGet(remoteFileUrl);
      response = httpClient.execute(httpget);
      HttpEntity entity = response.getEntity();
      if (entity == null) {
        return false;
      }
      in = entity.getContent();
      File file = new File(localFilePath);
      fout = new FileOutputStream(file);

      byte[] tmp = new byte['Ѐ'];
      int l;
      while ((l = in.read(tmp)) != -1)
      {
        fout.write(tmp, 0, l);
      }
      fout.flush();
      EntityUtils.consume(entity);
      return true;
    }
    finally
    {
      if (fout != null) {
        try
        {
          fout.close();
        }
        catch (Exception localException6) {}
      }
      if (response != null) {
        try
        {
          response.close();
        }
        catch (Exception localException7) {}
      }
      if ((closeHttpClient) && (httpClient != null)) {
        try
        {
          httpClient.close();
        }
        catch (Exception localException8) {}
      }
    }
  }

  public static HttpResult executeUploadFile(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, String charset, boolean closeHttpClient)
    throws IOException
  {
    CloseableHttpResponse httpResponse = null;
    try
    {
      if (httpClient == null) {
        httpClient = createHttpClient();
      }
      File localFile = new File(localFilePath);
      FileBody fileBody = new FileBody(localFile);

      HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("uploadFile", fileBody).setCharset(CharsetUtils.get("UTF-8")).build();

      HttpPost httpPost = new HttpPost(remoteFileUrl);
      httpPost.setEntity(reqEntity);
      httpResponse = httpClient.execute(httpPost);
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
        catch (Exception localException2) {}
      }
      if ((closeHttpClient) && (httpClient != null)) {
        try
        {
          httpClient.close();
        }
        catch (Exception localException3) {}
      }
    }
  }

  public static HttpResult executeUploadFileStream(CloseableHttpClient httpClient, String remoteFileUrl, String localFilePath, String charset, boolean closeHttpClient)
    throws ClientProtocolException, IOException
  {
    CloseableHttpResponse httpResponse = null;
    FileInputStream fis = null;
    ByteArrayOutputStream baos = null;
    try
    {
      if (httpClient == null) {
        httpClient = createHttpClient();
      }
      File localFile = new File(localFilePath);
      fis = new FileInputStream(localFile);
      byte[] tmpBytes = new byte['Ѐ'];
      byte[] resultBytes = null;
      baos = new ByteArrayOutputStream();
      int len;
      while ((len = fis.read(tmpBytes, 0, 1024)) != -1)
      {
        baos.write(tmpBytes, 0, len);
      }
      resultBytes = baos.toByteArray();
      ByteArrayEntity byteArrayEntity = new ByteArrayEntity(resultBytes, ContentType.APPLICATION_OCTET_STREAM);
      HttpPost httpPost = new HttpPost(remoteFileUrl);
      httpPost.setEntity(byteArrayEntity);
      httpResponse = httpClient.execute(httpPost);
      Integer statusCode = Integer.valueOf(httpResponse.getStatusLine().getStatusCode());
      String content = getResult(httpResponse, charset);
      return new HttpResult(statusCode, content);
    }
    finally
    {
      if (baos != null) {
        try
        {
          baos.close();
        }
        catch (Exception localException4) {}
      }
      if (fis != null) {
        try
        {
          fis.close();
        }
        catch (Exception localException5) {}
      }
      if (httpResponse != null) {
        try
        {
          httpResponse.close();
        }
        catch (Exception localException6) {}
      }
      if ((closeHttpClient) && (httpClient != null)) {
        try
        {
          httpClient.close();
        }
        catch (Exception localException7) {}
      }
    }
  }
}
